module ru.hse.iuturakulov.serverjigsawsockets {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.logging;
    requires org.json;
    requires com.google.gson;
    requires java.sql;
    requires org.apache.derby.engine;
    requires org.apache.derby.commons;

    opens ru.hse.iuturakulov.serverjigsawsockets to javafx.fxml;
    exports ru.hse.iuturakulov.serverjigsawsockets;
    exports ru.hse.iuturakulov.serverjigsawsockets.controllers;
    exports ru.hse.iuturakulov.serverjigsawsockets.models to com.google.gson;
    exports ru.hse.iuturakulov.serverjigsawsockets.models.enums to com.google.gson;
    opens ru.hse.iuturakulov.serverjigsawsockets.models to com.google.gson;
    opens ru.hse.iuturakulov.serverjigsawsockets.models.enums to com.google.gson;
    opens ru.hse.iuturakulov.serverjigsawsockets.controllers to javafx.fxml;
}