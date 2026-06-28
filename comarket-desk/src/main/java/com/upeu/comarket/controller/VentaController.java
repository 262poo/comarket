package com.upeu.comarket.controller;

import com.upeu.comarket.entity.DetalleVenta;
import com.upeu.comarket.entity.Producto;
import com.upeu.comarket.entity.Venta;
import com.upeu.comarket.security.Sesion;
import com.upeu.comarket.service.ProductoService;
import com.upeu.comarket.service.ProductoServiceImplSQLite;
import com.upeu.comarket.service.VentaService;
import com.upeu.comarket.service.VentaServiceImplSQLite;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VentaController {
    @FXML
    private TextField txtCliente;

    @FXML
    private DatePicker dpFecha;

    @FXML
    private ComboBox<Producto> cboProducto;

    @FXML
    private TextField txtCantidad;

    @FXML
    private TableView<DetalleVenta> tablaDetalles;

    @FXML
    private TableColumn<DetalleVenta, String> colProducto;

    @FXML
    private TableColumn<DetalleVenta, Number> colCantidad;

    @FXML
    private TableColumn<DetalleVenta, String> colPrecio;

    @FXML
    private TableColumn<DetalleVenta, String> colSubtotal;

    @FXML
    private Label lblTotal;

    private final ProductoService productoService = new ProductoServiceImplSQLite();
    private final VentaService ventaService = new VentaServiceImplSQLite();
    private final List<DetalleVenta> detalles = new ArrayList<>();

    @FXML
    private void initialize() {
        colProducto.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProducto().getNombre()));
        colCantidad.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getCantidad()));
        colPrecio.setCellValueFactory(data -> new SimpleStringProperty(formatearMoneda(data.getValue().getPrecioUnitario())));
        colSubtotal.setCellValueFactory(data -> new SimpleStringProperty(formatearMoneda(data.getValue().getSubtotal())));

        dpFecha.setValue(LocalDate.now());
        txtCantidad.setText("1");
        cargarProductos();
        actualizarTablaDetalles();
    }

    @FXML
    private void onAgregarDetalleClick() {
        Producto producto = cboProducto.getValue();
        if (producto == null) {
            mostrarMensaje("Seleccione un producto.");
            return;
        }

        int cantidad;
        try {
            cantidad = Integer.parseInt(txtCantidad.getText().trim());
        } catch (NumberFormatException e) {
            mostrarMensaje("Ingrese una cantidad valida.");
            txtCantidad.requestFocus();
            return;
        }

        if (cantidad <= 0) {
            mostrarMensaje("La cantidad debe ser mayor que cero.");
            txtCantidad.requestFocus();
            return;
        }

        if (cantidad > producto.getStock()) {
            mostrarMensaje("La cantidad supera el stock disponible.");
            txtCantidad.requestFocus();
            return;
        }

        detalles.add(new DetalleVenta(producto, cantidad, producto.getPrecio()));
        txtCantidad.setText("1");
        cboProducto.getSelectionModel().clearSelection();
        actualizarTablaDetalles();
    }

    @FXML
    private void onQuitarDetalleClick() {
        DetalleVenta seleccionado = tablaDetalles.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarMensaje("Seleccione un detalle para quitar.");
            return;
        }

        detalles.remove(seleccionado);
        actualizarTablaDetalles();
    }

    @FXML
    private void onGuardarVentaClick() {
        if (!Sesion.estaActiva()) {
            mostrarMensaje("Debe iniciar sesion para registrar ventas.");
            return;
        }

        Venta venta = new Venta(txtCliente.getText().trim(), dpFecha.getValue());
        venta.setUsuario(Sesion.getUsuarioActual());
        for (DetalleVenta detalle : detalles) {
            venta.agregarDetalle(detalle);
        }

        try {
            ventaService.registrar(venta);
            mostrarMensaje("Venta registrada correctamente.");
            limpiarFormulario();
            cargarProductos();
        } catch (IllegalArgumentException | IllegalStateException ex) {
            mostrarMensaje(ex.getMessage());
        }
    }

    @FXML
    private void onNuevaVentaClick() {
        limpiarFormulario();
    }

    private void cargarProductos() {
        cboProducto.getItems().setAll(productoService.listar());
    }

    private void limpiarFormulario() {
        txtCliente.clear();
        dpFecha.setValue(LocalDate.now());
        cboProducto.getSelectionModel().clearSelection();
        txtCantidad.setText("1");
        detalles.clear();
        actualizarTablaDetalles();
    }

    private void actualizarTablaDetalles() {
        tablaDetalles.getItems().setAll(detalles);
        Venta venta = new Venta("", LocalDate.now());
        for (DetalleVenta detalle : detalles) {
            venta.agregarDetalle(detalle);
        }
        lblTotal.setText(String.format("Total: S/ %.2f", ventaService.calcularTotal(venta)));
    }

    private String formatearMoneda(double valor) {
        return String.format("S/ %.2f", valor);
    }

    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ventas");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
