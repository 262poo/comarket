package com.upeu.comarket;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class HelloController {
    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtPrecio;

    @FXML
    private TextField txtStock;

    @FXML
    private TableView<Producto> tablaProductos;

    @FXML
    private TableColumn<Producto, Number> colId;

    @FXML
    private TableColumn<Producto, String> colNombre;

    @FXML
    private TableColumn<Producto, Number> colPrecio;

    @FXML
    private TableColumn<Producto, Number> colStock;

    private final List<Producto> productos = new ArrayList<>();
    private int siguienteId = 1;

    @FXML
    private void initialize() {
        colId.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()));
        colNombre.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNombre()));
        colPrecio.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getPrecio()));
        colStock.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getStock()));

        tablaProductos.getSelectionModel().selectedItemProperty().addListener(
                (observable, anterior, seleccionado) -> cargarProductoSeleccionado(seleccionado)
        );
    }

    @FXML
    private void onNuevoClick() {
        limpiarFormulario();
        tablaProductos.getSelectionModel().clearSelection();
        txtNombre.requestFocus();
    }

    @FXML
    private void onGuardarClick() {
        Producto producto = leerProductoFormulario(0);
        if (producto == null) {
            return;
        }

        producto.setId(siguienteId++);
        productos.add(producto);
        actualizarTabla();
        limpiarFormulario();
        txtNombre.requestFocus();
    }

    @FXML
    private void onActualizarClick() {
        Producto producto = tablaProductos.getSelectionModel().getSelectedItem();

        if (producto == null) {
            mostrarMensaje("Seleccione un producto para actualizar.");
            return;
        }

        Producto datos = leerProductoFormulario(producto.getId());
        if (datos == null) {
            return;
        }

        producto.setNombre(datos.getNombre());
        producto.setPrecio(datos.getPrecio());
        producto.setStock(datos.getStock());
        actualizarTabla();
        tablaProductos.getSelectionModel().select(producto);
    }

    @FXML
    private void onEliminarClick() {
        Producto producto = tablaProductos.getSelectionModel().getSelectedItem();

        if (producto == null) {
            mostrarMensaje("Seleccione un producto para eliminar.");
            return;
        }

        productos.remove(producto);
        actualizarTabla();
        limpiarFormulario();
    }

    @FXML
    private void onLimpiarClick() {
        limpiarFormulario();
    }

    private Producto leerProductoFormulario(int id) {
        String nombre = txtNombre.getText().trim();
        String precioTexto = txtPrecio.getText().trim();
        String stockTexto = txtStock.getText().trim();

        if (nombre.isEmpty()) {
            mostrarMensaje("Ingrese el nombre del producto.");
            txtNombre.requestFocus();
            return null;
        }

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

        if (precio < 0) {
            mostrarMensaje("El precio no puede ser negativo.");
            txtPrecio.requestFocus();
            return null;
        }

        if (stock < 0) {
            mostrarMensaje("El stock no puede ser negativo.");
            txtStock.requestFocus();
            return null;
        }

        return new Producto(id, nombre, precio, stock);
    }

    private void cargarProductoSeleccionado(Producto producto) {
        if (producto == null) {
            return;
        }

        txtNombre.setText(producto.getNombre());
        txtPrecio.setText(String.valueOf(producto.getPrecio()));
        txtStock.setText(String.valueOf(producto.getStock()));
    }

    private void limpiarFormulario() {
        txtNombre.clear();
        txtPrecio.clear();
        txtStock.clear();
    }

    private void actualizarTabla() {
        tablaProductos.getItems().setAll(productos);
    }

    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Productos");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
