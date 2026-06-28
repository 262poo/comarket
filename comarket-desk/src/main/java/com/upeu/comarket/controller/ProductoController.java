package com.upeu.comarket.controller;

import com.upeu.comarket.entity.Producto;
import com.upeu.comarket.service.ProductoService;
import com.upeu.comarket.service.ProductoServiceImplSQLite;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ProductoController {
    private enum ModoFormulario {
        CONSULTA,
        CREACION,
        EDICION
    }

    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtPrecio;

    @FXML
    private TextField txtStock;

    @FXML
    private TableView<Producto> tablaProductos;

    @FXML
    private TableColumn<Producto, String> colCodigo;

    @FXML
    private TableColumn<Producto, String> colNombre;

    @FXML
    private TableColumn<Producto, Number> colPrecio;

    @FXML
    private TableColumn<Producto, Number> colStock;

    @FXML
    private Button btnNuevo;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnAccion;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnCancelar;

    private final ProductoService productoService = new ProductoServiceImplSQLite();
    private ModoFormulario modoFormulario = ModoFormulario.CONSULTA;

    @FXML
    private void initialize() {
        colCodigo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCodigo()));
        colNombre.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNombre()));
        colPrecio.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getPrecio()));
        colStock.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getStock()));

        tablaProductos.getSelectionModel().selectedItemProperty().addListener(
                (observable, anterior, seleccionado) -> cargarProductoSeleccionado(seleccionado)
        );

        cargarDatosInicialesSiLaBaseEstaVacia();
        actualizarTabla();
        cambiarModo(ModoFormulario.CONSULTA);
    }

    public void recargarDatos() {
        Producto seleccionado = tablaProductos.getSelectionModel().getSelectedItem();
        String codigoSeleccionado = seleccionado == null ? null : seleccionado.getCodigo();

        actualizarTabla();

        if (codigoSeleccionado != null) {
            Producto actualizado = productoService.buscarPorCodigo(codigoSeleccionado);
            if (actualizado != null) {
                tablaProductos.getSelectionModel().select(actualizado);
                cargarProductoSeleccionado(actualizado);
                return;
            }
        }

        limpiarFormulario();
        cambiarModo(ModoFormulario.CONSULTA);
    }

    @FXML
    private void onNuevoClick() {
        limpiarFormulario();
        tablaProductos.getSelectionModel().clearSelection();
        cambiarModo(ModoFormulario.CREACION);
        txtCodigo.requestFocus();
    }

    @FXML
    private void onEditarClick() {
        Producto seleccionado = tablaProductos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarMensaje("Seleccione un producto para editar.");
            return;
        }

        cambiarModo(ModoFormulario.EDICION);
        txtNombre.requestFocus();
    }

    @FXML
    private void onAccionClick() {
        if (modoFormulario == ModoFormulario.CREACION) {
            guardarProducto();
            return;
        }

        if (modoFormulario == ModoFormulario.EDICION) {
            actualizarProducto();
        }
    }

    private void guardarProducto() {
        Producto producto = leerProductoFormulario();
        if (producto == null) {
            return;
        }

        try {
            productoService.registrar(producto);
            actualizarTabla();
            limpiarFormulario();
            cambiarModo(ModoFormulario.CONSULTA);
        } catch (IllegalArgumentException | IllegalStateException ex) {
            mostrarMensaje(ex.getMessage());
        }
    }

    private void actualizarProducto() {
        Producto seleccionado = tablaProductos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarMensaje("Seleccione un producto para editar.");
            return;
        }

        Producto producto = leerProductoFormulario();
        if (producto == null) {
            return;
        }

        try {
            if (!productoService.actualizar(producto)) {
                mostrarMensaje("Producto no encontrado.");
                return;
            }
            actualizarTabla();
            tablaProductos.getSelectionModel().select(productoService.buscarPorCodigo(producto.getCodigo()));
            cambiarModo(ModoFormulario.CONSULTA);
        } catch (IllegalArgumentException | IllegalStateException ex) {
            mostrarMensaje(ex.getMessage());
        }
    }

    @FXML
    private void onEliminarClick() {
        Producto producto = tablaProductos.getSelectionModel().getSelectedItem();
        if (producto == null) {
            mostrarMensaje("Seleccione un producto para eliminar.");
            return;
        }

        if (!confirmar("Desea eliminar el producto " + producto.getCodigo() + "?")) {
            return;
        }

        try {
            if (!productoService.eliminar(producto.getCodigo())) {
                mostrarMensaje("Producto no encontrado.");
                return;
            }
        } catch (IllegalStateException ex) {
            mostrarMensaje(ex.getMessage());
            return;
        }

        actualizarTabla();
        limpiarFormulario();
        cambiarModo(ModoFormulario.CONSULTA);
    }

    @FXML
    private void onCancelarClick() {
        Producto seleccionado = tablaProductos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            limpiarFormulario();
        } else {
            cargarProductoSeleccionado(seleccionado);
        }
        cambiarModo(ModoFormulario.CONSULTA);
    }

    private Producto leerProductoFormulario() {
        String codigo = txtCodigo.getText().trim();
        String nombre = txtNombre.getText().trim();
        String precioTexto = txtPrecio.getText().trim();
        String stockTexto = txtStock.getText().trim();

        double precio;
        try {
            precio = Double.parseDouble(precioTexto);
        } catch (NumberFormatException e) {
            mostrarMensaje("Ingrese un precio valido.");
            txtPrecio.requestFocus();
            return null;
        }

        int stock;
        try {
            stock = Integer.parseInt(stockTexto);
        } catch (NumberFormatException e) {
            mostrarMensaje("Ingrese un stock valido.");
            txtStock.requestFocus();
            return null;
        }

        return new Producto(codigo, nombre, precio, stock);
    }

    private void cargarProductoSeleccionado(Producto producto) {
        if (producto == null) {
            return;
        }

        txtCodigo.setText(producto.getCodigo());
        txtNombre.setText(producto.getNombre());
        txtPrecio.setText(String.valueOf(producto.getPrecio()));
        txtStock.setText(String.valueOf(producto.getStock()));
        if (modoFormulario != ModoFormulario.EDICION) {
            cambiarModo(ModoFormulario.CONSULTA);
        }
    }

    private void cargarDatosInicialesSiLaBaseEstaVacia() {
        if (!productoService.listar().isEmpty()) {
            return;
        }
        productoService.registrar(new Producto("P001", "Arroz", 4.5, 20));
        productoService.registrar(new Producto("P002", "Aceite", 9.9, 12));
    }

    private void limpiarFormulario() {
        txtCodigo.clear();
        txtNombre.clear();
        txtPrecio.clear();
        txtStock.clear();
    }

    private void actualizarTabla() {
        tablaProductos.getItems().setAll(productoService.listar());
    }

    private void cambiarModo(ModoFormulario modoFormulario) {
        this.modoFormulario = modoFormulario;

        boolean haySeleccion = tablaProductos.getSelectionModel().getSelectedItem() != null;
        boolean formularioActivo = modoFormulario != ModoFormulario.CONSULTA;
        boolean creacion = modoFormulario == ModoFormulario.CREACION;

        txtCodigo.setDisable(!creacion);
        txtNombre.setDisable(!formularioActivo);
        txtPrecio.setDisable(!formularioActivo);
        txtStock.setDisable(!formularioActivo);
        tablaProductos.setDisable(formularioActivo);

        btnNuevo.setDisable(formularioActivo);
        btnEditar.setDisable(formularioActivo || !haySeleccion);
        btnEliminar.setDisable(formularioActivo || !haySeleccion);

        btnAccion.setVisible(formularioActivo);
        btnAccion.setManaged(formularioActivo);
        btnAccion.setText(creacion ? "Guardar" : "Actualizar");

        btnCancelar.setVisible(formularioActivo);
        btnCancelar.setManaged(formularioActivo);
    }

    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Productos");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private boolean confirmar(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Productos");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        return alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK;
    }
}
