package com.upeu.comarket.entity;

public class Usuario extends Persona {
    private String username;
    private String rol;

    public Usuario(String dni, String nombre, String email, String username, String rol) {
        super(dni, nombre, email);
        this.username = username;
        this.rol = rol;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public String mostrarPerfil() {
        return "Usuario: " + super.mostrarPerfil() + " - username: " + username + " - rol: " + rol;
    }
}
