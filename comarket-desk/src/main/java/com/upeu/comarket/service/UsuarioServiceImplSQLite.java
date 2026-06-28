package com.upeu.comarket.service;

import com.upeu.comarket.dao.UsuarioDao;
import com.upeu.comarket.entity.Usuario;

public class UsuarioServiceImplSQLite implements UsuarioService {
    private final UsuarioDao usuarioDao = new UsuarioDao();

    @Override
    public Usuario autenticar(String username, String password) {
        if (estaVacio(username) || estaVacio(password)) {
            return null;
        }

        Usuario usuario = usuarioDao.buscarPorUsername(username.trim());
        if (usuario == null) {
            return null;
        }

        if (!usuario.getPasswordHash().equals(password)) {
            return null;
        }

        return usuario;
    }

    private boolean estaVacio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }
}
