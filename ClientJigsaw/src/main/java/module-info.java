module ru.hse.iuturakulov.clientside {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires javafx.base;
    requires org.json;
    requires com.google.gson;
    requires java.sql;

    opens ru.hse.iuturakulov.clientside to javafx.fxml;
    opens ru.hse.iuturakulov.clientside.models.enums to com.google.gson;
    opens ru.hse.iuturakulov.clientside.models to com.google.gson, javafx.base;
    opens ru.hse.iuturakulov.clientside.controllers to javafx.fxml;


    exports ru.hse.iuturakulov.clientside;
    exports ru.hse.iuturakulov.clientside.controllers;
    exports ru.hse.iuturakulov.clientside.models to com.google.gson, javafx.base;
    exports ru.hse.iuturakulov.clientside.models.enums to com.google.gson;
}