package com.upeu.comarket.controller;

import com.upeu.comarket.entity.DetalleVenta;
import com.upeu.comarket.entity.Venta;
import com.upeu.comarket.service.VentaService;
import com.upeu.comarket.service.VentaServiceImplSQLite;
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

public class ReporteVentasController {
    @FXML
    private TextField txtFiltroCliente;

    @FXML
    private DatePicker dpFechaDesde;

    @FXML
    private DatePicker dpFechaHasta;

    @FXML
    private TextField txtFiltroUsuario;

    @FXML
    private ComboBox<String> cboEstado;

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

    @FXML
    private Label lblResumenConsulta;

    @FXML
    private Label lblConsistencia;

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

        cboEstado.getItems().setAll("TODOS", "ACTIVA", "ANULADA");
        cboEstado.setValue("TODOS");
        limpiarResultados();
    }

    @FXML
    private void onBuscarClick() {
        actualizarVentas();
    }

    @FXML
    private void onLimpiarFiltrosClick() {
        txtFiltroCliente.clear();
        dpFechaDesde.setValue(null);
        dpFechaHasta.setValue(null);
        txtFiltroUsuario.clear();
        cboEstado.setValue("TODOS");
        limpiarResultados();
    }

    private void actualizarVentas() {
        try {
            tablaVentas.getItems().setAll(ventaService.consultar(
                    txtFiltroCliente.getText(),
                    dpFechaDesde.getValue(),
                    dpFechaHasta.getValue(),
                    txtFiltroUsuario.getText(),
                    cboEstado.getValue()
            ));
            tablaDetallesVenta.getItems().clear();
            lblVentaSeleccionada.setText("Seleccione una venta para ver el detalle.");
            lblConsistencia.setText("");
            actualizarResumenConsulta();
        } catch (IllegalArgumentException | IllegalStateException ex) {
            mostrarMensaje(ex.getMessage());
        }
    }

    private void limpiarResultados() {
        tablaVentas.getItems().clear();
        tablaDetallesVenta.getItems().clear();
        lblVentaSeleccionada.setText("Presione Buscar para consultar ventas.");
        lblResumenConsulta.setText("Ventas: 0 | Total mostrado: S/ 0.00");
        lblConsistencia.setText("");
    }

    private void cargarDetalleVenta(Venta venta) {
        if (venta == null || venta.getId() == null) {
            tablaDetallesVenta.getItems().clear();
            lblVentaSeleccionada.setText("Seleccione una venta para ver el detalle.");
            lblConsistencia.setText("");
            return;
        }

        try {
            tablaDetallesVenta.getItems().setAll(ventaService.listarDetalles(venta.getId()));
            double totalDetalle = calcularTotalDetalle();
            lblVentaSeleccionada.setText(
                    "Venta #" + venta.getId() + " - " + venta.getCliente() + " - " + formatearMoneda(venta.calcularTotal())
            );
            lblConsistencia.setText(
                    "Total detalle: " + formatearMoneda(totalDetalle) + " | Diferencia: "
                            + formatearMoneda(Math.abs(venta.calcularTotal() - totalDetalle))
            );
        } catch (IllegalStateException ex) {
            mostrarMensaje(ex.getMessage());
        }
    }

    private void actualizarResumenConsulta() {
        double total = 0;
        for (Venta venta : tablaVentas.getItems()) {
            total += venta.calcularTotal();
        }

        lblResumenConsulta.setText(
                "Ventas: " + tablaVentas.getItems().size() + " | Total mostrado: " + formatearMoneda(total)
        );
    }

    private double calcularTotalDetalle() {
        double total = 0;
        for (DetalleVenta detalle : tablaDetallesVenta.getItems()) {
            total += detalle.getSubtotal();
        }
        return Math.round(total * 100.0) / 100.0;
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
        alert.setTitle("Consulta avanzada");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
