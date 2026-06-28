package com.upeu.comarket.service;

import com.upeu.comarket.entity.Venta;

import java.time.LocalDate;
import java.util.List;

public interface VentaService {
    void registrar(Venta venta);

    double calcularTotal(Venta venta);

    List<Venta> listar();

    List<Venta> consultar(String cliente, LocalDate fechaDesde, LocalDate fechaHasta, String username, String estado);

    List<com.upeu.comarket.entity.DetalleVenta> listarDetalles(long ventaId);

    boolean anular(long ventaId);
}
