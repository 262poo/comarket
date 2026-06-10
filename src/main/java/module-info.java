module com.upeu.comarket {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.upeu.comarket to javafx.fxml;
    exports com.upeu.comarket;
}