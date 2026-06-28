package com.upeu.comarket.service;

import com.upeu.comarket.dao.ProductoDao;
import com.upeu.comarket.entity.Producto;

import java.util.List;

public class ProductoServiceImplSQLite implements ProductoService {
    private final ProductoDao productoDao = new ProductoDao();

    @Override
    public void registrar(Producto producto) {
        validarProducto(producto);
        if (buscarPorCodigo(producto.getCodigo()) != null) {
            throw new IllegalArgumentException("Ya existe un producto con el codigo " + producto.getCodigo());
        }
        productoDao.insertar(producto);
    }

    @Override
    public List<Producto> listar() {
        return productoDao.listar();
    }

    @Override
    public Producto buscarPorCodigo(String codigo) {
        if (estaVacio(codigo)) {
            return null;
        }
        return productoDao.buscarPorCodigo(codigo);
    }

    @Override
    public boolean actualizar(Producto producto) {
        validarProducto(producto);
        return productoDao.actualizar(producto);
    }

    @Override
    public boolean eliminar(String codigo) {
        if (estaVacio(codigo)) {
            return false;
        }
        return productoDao.eliminar(codigo);
    }

    private void validarProducto(Producto producto) {
        if (producto == null) {
            throw new IllegalArgumentException("El producto es obligatorio");
        }
        if (estaVacio(producto.getCodigo())) {
            throw new IllegalArgumentException("El codigo es obligatorio");
        }
        if (estaVacio(producto.getNombre())) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (producto.getPrecio() < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }
        if (producto.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
    }

    private boolean estaVacio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }
}
