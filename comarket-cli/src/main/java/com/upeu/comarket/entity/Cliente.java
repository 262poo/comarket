package com.upeu.comarket.entity;

public class Cliente extends Persona {
    private String telefono;

    public Cliente(String dni, String nombre, String email, String telefono) {
        super(dni, nombre, email);
        this.telefono = telefono;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String mostrarPerfil() {
        return "Cliente: " + super.mostrarPerfil() + " - telefono: " + telefono;
    }
}
