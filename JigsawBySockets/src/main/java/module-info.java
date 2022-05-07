module ru.hse.iuturakulov.jigsawbysockets {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires org.json;


    opens ru.hse.iuturakulov.jigsawbysockets to javafx.fxml;
    exports ru.hse.iuturakulov.jigsawbysockets;
    exports ru.hse.iuturakulov.jigsawbysockets.contollers;
    opens ru.hse.iuturakulov.jigsawbysockets.contollers to javafx.fxml;
}