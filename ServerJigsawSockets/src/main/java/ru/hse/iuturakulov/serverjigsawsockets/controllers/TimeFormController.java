package ru.hse.iuturakulov.serverjigsawsockets.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class TimeFormController implements Initializable {

    @FXML
    private Spinner<Integer> hoursSpinner;
    @FXML
    private Spinner<Integer> minutesSpinner;
    @FXML
    private Spinner<Integer> secondsSpinner;
    @FXML
    private TextField timePreviewText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Integer> items = FXCollections.observableArrayList(0, 60);
        for (Spinner spinner : Arrays.asList(minutesSpinner, secondsSpinner, hoursSpinner)) {
            spinner.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(items));
        }
    }
}
