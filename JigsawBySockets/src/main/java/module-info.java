module ru.hse.iuturakulov.jigsawbysockets {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires javafx.base;
    requires org.json;
    requires com.google.gson;
    requires java.sql;

    opens ru.hse.iuturakulov.jigsawbysockets to javafx.fxml;
    opens ru.hse.iuturakulov.jigsawbysockets.models.enums to com.google.gson;
    opens ru.hse.iuturakulov.jigsawbysockets.models to com.google.gson, javafx.base;
    opens ru.hse.iuturakulov.jigsawbysockets.controllers to javafx.fxml;


    exports ru.hse.iuturakulov.jigsawbysockets;
    exports ru.hse.iuturakulov.jigsawbysockets.controllers;
    exports ru.hse.iuturakulov.jigsawbysockets.models to com.google.gson, javafx.base;
    exports ru.hse.iuturakulov.jigsawbysockets.models.enums to com.google.gson;
}