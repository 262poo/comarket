package com.upeu.comarket.service;

import com.upeu.comarket.entity.Usuario;

public interface UsuarioService {
    Usuario autenticar(String username, String password);
}
