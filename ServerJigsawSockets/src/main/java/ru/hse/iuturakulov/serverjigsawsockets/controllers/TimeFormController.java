package ru.hse.iuturakulov.serverjigsawsockets.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ru.hse.iuturakulov.serverjigsawsockets.ServerMain;
import ru.hse.iuturakulov.serverjigsawsockets.utils.Constants;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The type Time form controller.
 */
public class TimeFormController implements Initializable {
    /**
     * The Preview minutes.
     */
    String preview_minutes;
    /**
     * The Preview hours.
     */
    String preview_hours;
    /**
     * The Preview seconds.
     */
    String preview_seconds;
    /**
     * The Flag.
     */
    boolean flag = false;
    /**
     * The Duration.
     */
    Double duration;
    @FXML
    private Label labelSeconds;
    @FXML
    private Label labelMinutes;
    @FXML
    private Label labelHours;

    /**
     * Back to main menu.
     */
    @FXML
    protected void backToMainMenu() {
        Constants.timeCurrent = String.format("%02d:%02d:%02d", (int) (duration / 3600), (int) ((duration % 3600) / 60), (int) (duration % 60));
        ServerMain.setRoot("server_main");
    }

    /**
     * Change seconds.
     *
     * @param actionEvent the action event
     */
    @FXML
    protected void changeSeconds(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        labelSeconds.setText(changeTimer(labelSeconds.getText(), button.getId()));
        preview_seconds = labelSeconds.getText();
        duration = Double.parseDouble(labelHours.getText()) * 3600 + Double.parseDouble(labelMinutes.getText()) * 60 + Double.parseDouble(labelSeconds.getText());
    }

    /**
     * Change minutes.
     *
     * @param actionEvent the action event
     */
    @FXML
    protected void changeMinutes(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        labelMinutes.setText(changeTimer(labelMinutes.getText(), button.getId()));
        preview_minutes = labelMinutes.getText();
        duration = Double.parseDouble(labelHours.getText()) * 3600 + Double.parseDouble(labelMinutes.getText()) * 60 + Double.parseDouble(labelSeconds.getText());
    }

    /**
     * Reset.
     */
    @FXML
    protected void reset() {
        flag = false;
        preview_minutes = "00";
        preview_seconds = "00";
        preview_hours = "00";
        duration = 0D;
        Constants.timeCurrent = null;
        labelSeconds.setText(preview_seconds);
        labelMinutes.setText(preview_minutes);
        labelHours.setText(preview_hours);
        labelSeconds.setVisible(true);
        labelMinutes.setVisible(true);
        labelHours.setVisible(true);
    }

    /**
     * Change hours.
     *
     * @param actionEvent the action event
     */
    @FXML
    protected void changeHours(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        labelHours.setText(changeTimer(labelHours.getText(), button.getId()));
        preview_hours = labelHours.getText();
        duration = (Double.parseDouble(labelHours.getText()) * 3600) + (Double.parseDouble(labelMinutes.getText()) * 60) + Double.parseDouble(labelSeconds.getText());
    }

    /**
     * Change timer string.
     *
     * @param timeLabel the time label
     * @param timeId    the time id
     * @return the string
     */
    String changeTimer(String timeLabel, String timeId) {
        if (timeId.contains("1")) {
            if (timeId.contains("U") && Integer.parseInt("" + timeLabel.charAt(1)) < 9) {
                timeLabel = "%s%s".formatted(timeLabel.charAt(0), String.valueOf((Integer.parseInt("" + timeLabel.charAt(1)) + 1)));
            }
            if (timeId.contains("D") && Integer.parseInt("" + timeLabel.charAt(1)) > 0) {
                timeLabel = "%s%s".formatted(timeLabel.charAt(0), String.valueOf((Integer.parseInt("" + timeLabel.charAt(1)) - 1)));
            }
        }
        if (timeId.contains("2")) {
            if (timeId.contains("U") && Integer.parseInt("" + timeLabel.charAt(0)) < 9) {
                timeLabel = String.valueOf((Integer.parseInt("%s".formatted(timeLabel.charAt(0))) + 1)) + timeLabel.charAt(1);
            }
            if (timeId.contains("D") && Integer.parseInt("" + timeLabel.charAt(0)) > 0) {
                timeLabel = String.valueOf((Integer.parseInt("" + timeLabel.charAt(0)) - 1)) + timeLabel.charAt(1);
            }
        }
        return timeLabel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String tempTime = null;
        if (Constants.timeCurrent != null) {
            tempTime = Constants.timeCurrent;
        }
        duration = 0D;
        String[] arr = tempTime != null ? tempTime.split(":") : null;
        preview_hours = tempTime == null ? labelHours.getText() : arr[0];
        preview_minutes = tempTime == null ? labelMinutes.getText() : arr[1];
        preview_seconds = tempTime == null ? labelSeconds.getText() : arr[2];
        labelSeconds.setText(preview_seconds);
        labelMinutes.setText(preview_minutes);
        labelHours.setText(preview_hours);
    }
}
