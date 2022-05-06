module ru.hse.iuturakulov.serverjigsawsockets {
    requires javafx.controls;
    requires javafx.fxml;


    opens ru.hse.iuturakulov.serverjigsawsockets to javafx.fxml;
    exports ru.hse.iuturakulov.serverjigsawsockets;
}