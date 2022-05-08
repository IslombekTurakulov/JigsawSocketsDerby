module ru.hse.iuturakulov.serverjigsawsockets {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires org.json;


    opens ru.hse.iuturakulov.serverjigsawsockets to javafx.fxml;
    exports ru.hse.iuturakulov.serverjigsawsockets;
    exports ru.hse.iuturakulov.serverjigsawsockets.controllers;
    opens ru.hse.iuturakulov.serverjigsawsockets.controllers to javafx.fxml;
}