module com.upeu.comarket {
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;


    opens com.upeu.comarket to javafx.fxml;
    opens com.upeu.comarket.controller to javafx.fxml;
    exports com.upeu.comarket;
    exports com.upeu.comarket.dao;
    exports com.upeu.comarket.db;
    exports com.upeu.comarket.entity;
    exports com.upeu.comarket.service;
}
