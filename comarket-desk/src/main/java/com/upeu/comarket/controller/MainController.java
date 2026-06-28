package com.upeu.comarket.controller;

import com.upeu.comarket.CoMarketApplication;
import com.upeu.comarket.entity.Usuario;
import com.upeu.comarket.security.Sesion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    @FXML
    private Label lblUsuario;

    @FXML
    private void initialize() {
        Usuario usuario = Sesion.getUsuarioActual();
        if (usuario != null) {
            lblUsuario.setText(usuario.getUsername() + " (" + usuario.getRol() + ")");
        }
    }

    @FXML
    private void onCerrarSesionClick() {
        Sesion.cerrar();
        try {
            FXMLLoader loader = new FXMLLoader(CoMarketApplication.class.getResource("view/LoginView.fxml"));
            Scene scene = new Scene(loader.load(), 420, 260);
            Stage stage = (Stage) lblUsuario.getScene().getWindow();
            stage.setTitle("CoMarket Desk - Login");
            stage.setScene(scene);
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo cerrar la sesion.", e);
        }
    }
}
