package ru.hse.iuturakulov.jigsawbysockets.contollers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class GameFormController implements Initializable {

    @FXML
    private ImageView imageViewRightPane;
    @FXML
    private BorderPane borderGamePane;
    @FXML
    private AnchorPane leftGamePaneTimer;
    @FXML
    private Text pointGameCurrent;
    @FXML
    private Text maxPointsCurrent;
    @FXML
    private Text timeCurrentGame;
    @FXML
    private AnchorPane gridPaneMain;
    @FXML
    private ImageView infoGameBillBoardImage;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
