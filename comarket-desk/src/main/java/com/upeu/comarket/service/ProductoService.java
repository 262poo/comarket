package com.upeu.comarket.service;

import com.upeu.comarket.entity.Producto;

import java.util.List;

public interface ProductoService {
    void registrar(Producto producto);

    List<Producto> listar();

    Producto buscarPorCodigo(String codigo);

    boolean actualizar(Producto producto);

    boolean eliminar(String codigo);
}
