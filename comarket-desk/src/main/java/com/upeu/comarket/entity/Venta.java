package com.upeu.comarket.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Venta {
    private Long id;
    private String cliente;
    private LocalDate fecha;
    private Double totalRegistrado;
    private String estado = "ACTIVA";
    private final List<DetalleVenta> detalles = new ArrayList<>();

    public Venta(String cliente, LocalDate fecha) {
        this.cliente = cliente;
        this.fecha = fecha;
    }

    public Venta(Long id, String cliente, LocalDate fecha) {
        this(cliente, fecha);
        this.id = id;
    }

    public Venta(Long id, String cliente, LocalDate fecha, double totalRegistrado) {
        this(id, cliente, fecha);
        this.totalRegistrado = totalRegistrado;
    }

    public Venta(Long id, String cliente, LocalDate fecha, double totalRegistrado, String estado) {
        this(id, cliente, fecha, totalRegistrado);
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void agregarDetalle(DetalleVenta detalleVenta) {
        detalles.add(detalleVenta);
    }

    public List<DetalleVenta> getDetalles() {
        return Collections.unmodifiableList(detalles);
    }

    public double calcularTotal() {
        if (detalles.isEmpty() && totalRegistrado != null) {
            return redondear(totalRegistrado);
        }
        double total = 0;
        for (DetalleVenta detalle : detalles) {
            total += detalle.getSubtotal();
        }
        return redondear(total);
    }

    private double redondear(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }
}
