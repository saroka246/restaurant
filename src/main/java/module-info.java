module com.build.lab8 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.build.lab8 to javafx.fxml;
    exports com.build.lab8;
    exports com.build.lab8.controllers;
    opens com.build.lab8.controllers to javafx.fxml;
}