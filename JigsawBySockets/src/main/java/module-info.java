module ru.hse.iuturakulov.jigsawbysockets {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires org.json;
    requires com.google.gson;


    opens ru.hse.iuturakulov.jigsawbysockets to javafx.fxml;
    exports ru.hse.iuturakulov.jigsawbysockets;
    exports ru.hse.iuturakulov.jigsawbysockets.contollers;
    exports ru.hse.iuturakulov.jigsawbysockets.models to com.google.gson;
    exports ru.hse.iuturakulov.jigsawbysockets.models.enums to com.google.gson;
    opens ru.hse.iuturakulov.jigsawbysockets.models.enums to com.google.gson;
    opens ru.hse.iuturakulov.jigsawbysockets.models to com.google.gson;
    opens ru.hse.iuturakulov.jigsawbysockets.contollers to javafx.fxml;
}