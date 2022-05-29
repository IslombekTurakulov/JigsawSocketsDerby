module ru.hse.iuturakulov.serverside {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.logging;
    requires org.json;
    requires com.google.gson;
    requires java.sql;
    requires org.apache.derby.engine;
    requires org.apache.derby.commons;

    opens ru.hse.iuturakulov.serverside to javafx.fxml;
    exports ru.hse.iuturakulov.serverside;
    exports ru.hse.iuturakulov.serverside.controllers;
    exports ru.hse.iuturakulov.serverside.models to com.google.gson;
    exports ru.hse.iuturakulov.serverside.models.enums to com.google.gson;
    opens ru.hse.iuturakulov.serverside.models to com.google.gson;
    opens ru.hse.iuturakulov.serverside.models.enums to com.google.gson;
    opens ru.hse.iuturakulov.serverside.controllers to javafx.fxml;
}