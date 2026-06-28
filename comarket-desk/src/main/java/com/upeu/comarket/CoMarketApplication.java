package com.upeu.comarket;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CoMarketApplication extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CoMarketApplication.class.getResource("view/LoginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 420, 260);
        stage.setTitle("CoMarket Desk - Login");
        stage.setScene(scene);
        stage.show();
    }
}
