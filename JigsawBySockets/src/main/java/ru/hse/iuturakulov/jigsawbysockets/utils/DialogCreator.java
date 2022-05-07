package ru.hse.iuturakulov.jigsawbysockets.utils;

import javafx.application.Platform;
import javafx.scene.control.Alert;

public class DialogCreator {

    public static void showCustomDialog(Alert.AlertType type, String alertTitle, String alertContent, Boolean exit) {
        Platform.runLater(() -> {
            Alert alert = new Alert(type);
            alert.setTitle(alertTitle);
            alert.setHeaderText(alertTitle);
            alert.setResizable(true);
            alert.setContentText(alertContent);
            alert.showAndWait();
            if (exit) {
                Platform.exit();
            }
        });
    }
}
