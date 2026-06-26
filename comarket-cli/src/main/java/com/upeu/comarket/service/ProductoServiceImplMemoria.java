package com.upeu.comarket.service;

import com.upeu.comarket.entity.Producto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductoServiceImplMemoria implements ProductoService {
    private final List<Producto> productos = new ArrayList<>();

    @Override
    public void registrar(Producto producto) {
        validarProducto(producto);
        if (buscarPorCodigo(producto.getCodigo()) != null) {
            throw new IllegalArgumentException("Ya existe un producto con el codigo " + producto.getCodigo());
        }
        productos.add(producto);
    }

    @Override
    public List<Producto> listar() {
        return Collections.unmodifiableList(productos);
    }

    @Override
    public Producto buscarPorCodigo(String codigo) {
        for (Producto producto : productos) {
            if (producto.getCodigo().equalsIgnoreCase(codigo)) {
                return producto;
            }
        }
        return null;
    }

    @Override
    public boolean actualizar(Producto producto) {
        validarProducto(producto);
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigo().equalsIgnoreCase(producto.getCodigo())) {
                productos.set(i, producto);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean eliminar(String codigo) {
        Producto encontrado = buscarPorCodigo(codigo);
        if (encontrado == null) {
            return false;
        }
        return productos.remove(encontrado);
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
