package com.upeu.comarket.controller;

import com.upeu.comarket.entity.DetalleVenta;
import com.upeu.comarket.entity.Venta;
import com.upeu.comarket.security.Sesion;
import com.upeu.comarket.service.VentaService;
import com.upeu.comarket.service.VentaServiceImplSQLite;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ConsultaVentasController {
    @FXML
    private TableView<Venta> tablaVentas;

    @FXML
    private TableColumn<Venta, Number> colVentaId;

    @FXML
    private TableColumn<Venta, String> colCliente;

    @FXML
    private TableColumn<Venta, String> colFecha;

    @FXML
    private TableColumn<Venta, String> colTotalVenta;

    @FXML
    private TableColumn<Venta, String> colEstado;

    @FXML
    private TableColumn<Venta, String> colUsuario;

    @FXML
    private TableView<DetalleVenta> tablaDetallesVenta;

    @FXML
    private TableColumn<DetalleVenta, String> colDetalleProducto;

    @FXML
    private TableColumn<DetalleVenta, Number> colDetalleCantidad;

    @FXML
    private TableColumn<DetalleVenta, String> colDetallePrecio;

    @FXML
    private TableColumn<DetalleVenta, String> colDetalleSubtotal;

    @FXML
    private Label lblVentaSeleccionada;

    private final VentaService ventaService = new VentaServiceImplSQLite();

    @FXML
    private void initialize() {
        colVentaId.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId().intValue()));
        colCliente.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCliente()));
        colFecha.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFecha().toString()));
        colTotalVenta.setCellValueFactory(data -> new SimpleStringProperty(formatearMoneda(data.getValue().calcularTotal())));
        colEstado.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEstado()));
        colUsuario.setCellValueFactory(data -> new SimpleStringProperty(obtenerUsername(data.getValue())));

        colDetalleProducto.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProducto().getNombre()));
        colDetalleCantidad.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getCantidad()));
        colDetallePrecio.setCellValueFactory(data -> new SimpleStringProperty(formatearMoneda(data.getValue().getPrecioUnitario())));
        colDetalleSubtotal.setCellValueFactory(data -> new SimpleStringProperty(formatearMoneda(data.getValue().getSubtotal())));

        tablaVentas.getSelectionModel().selectedItemProperty().addListener(
                (observable, anterior, seleccionado) -> cargarDetalleVenta(seleccionado)
        );

        actualizarVentas();
    }

    @FXML
    private void onActualizarClick() {
        actualizarVentas();
    }

    @FXML
    private void onAnularVentaClick() {
        if (!Sesion.estaActiva()) {
            mostrarMensaje("Debe iniciar sesion para anular ventas.");
            return;
        }

        Venta venta = tablaVentas.getSelectionModel().getSelectedItem();
        if (venta == null || venta.getId() == null) {
            mostrarMensaje("Seleccione una venta para anular.");
            return;
        }

        if ("ANULADA".equalsIgnoreCase(venta.getEstado())) {
            mostrarMensaje("La venta seleccionada ya esta anulada.");
            return;
        }

        if (!confirmar("Desea anular la venta #" + venta.getId() + "? Se repondra el stock.")) {
            return;
        }

        try {
            if (!ventaService.anular(venta.getId())) {
                mostrarMensaje("Venta no encontrada.");
                return;
            }
            mostrarMensaje("Venta anulada correctamente.");
            actualizarVentas();
        } catch (IllegalArgumentException | IllegalStateException ex) {
            mostrarMensaje(ex.getMessage());
        }
    }

    private void actualizarVentas() {
        try {
            tablaVentas.getItems().setAll(ventaService.listar());
            tablaDetallesVenta.getItems().clear();
            lblVentaSeleccionada.setText("Seleccione una venta para ver el detalle.");
        } catch (IllegalStateException ex) {
            mostrarMensaje(ex.getMessage());
        }
    }

    private void cargarDetalleVenta(Venta venta) {
        if (venta == null || venta.getId() == null) {
            tablaDetallesVenta.getItems().clear();
            lblVentaSeleccionada.setText("Seleccione una venta para ver el detalle.");
            return;
        }

        try {
            tablaDetallesVenta.getItems().setAll(ventaService.listarDetalles(venta.getId()));
            lblVentaSeleccionada.setText(
                    "Venta #" + venta.getId() + " - " + venta.getCliente() + " - " + formatearMoneda(venta.calcularTotal())
            );
        } catch (IllegalStateException ex) {
            mostrarMensaje(ex.getMessage());
        }
    }

    private String formatearMoneda(double valor) {
        return String.format("S/ %.2f", valor);
    }

    private String obtenerUsername(Venta venta) {
        if (venta.getUsuario() == null) {
            return "Sin usuario";
        }
        return venta.getUsuario().getUsername();
    }

    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Consulta de ventas");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private boolean confirmar(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Consulta de ventas");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        return alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK;
    }
}
