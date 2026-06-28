package com.upeu.comarket.controller;

import com.upeu.comarket.CoMarketApplication;
import com.upeu.comarket.entity.Usuario;
import com.upeu.comarket.security.Sesion;
import com.upeu.comarket.service.UsuarioService;
import com.upeu.comarket.service.UsuarioServiceImplSQLite;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    private final UsuarioService usuarioService = new UsuarioServiceImplSQLite();

    @FXML
    private void onIngresarClick() {
        Usuario usuario = usuarioService.autenticar(txtUsername.getText(), txtPassword.getText());
        if (usuario == null) {
            mostrarMensaje("Credenciales incorrectas.");
            txtPassword.clear();
            txtPassword.requestFocus();
            return;
        }

        Sesion.iniciar(usuario);
        abrirVentanaPrincipal();
    }

    private void abrirVentanaPrincipal() {
        try {
            FXMLLoader loader = new FXMLLoader(CoMarketApplication.class.getResource("view/MainView.fxml"));
            Scene scene = new Scene(loader.load(), 920, 640);
            Stage stage = (Stage) txtUsername.getScene().getWindow();
            stage.setTitle("CoMarket Desk");
            stage.setScene(scene);
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo abrir la ventana principal.", e);
        }
    }

    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Login");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
