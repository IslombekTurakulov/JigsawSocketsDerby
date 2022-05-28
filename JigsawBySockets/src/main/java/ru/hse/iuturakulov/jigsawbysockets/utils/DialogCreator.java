package ru.hse.iuturakulov.jigsawbysockets.utils;

import javafx.application.Platform;
import javafx.scene.control.Alert;

/**
 * Custom dialog class creator
 *
 * @author Islombek Turakulov
 * @version 1.0
 */
public class DialogCreator {

    /**
     * Show custom dialog.
     *
     * @param type         the type
     * @param alertTitle   the alert title
     * @param alertContent the alert content
     * @param exit         the exit
     */
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
