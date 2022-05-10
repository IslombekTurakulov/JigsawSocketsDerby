package ru.hse.iuturakulov.jigsawbysockets;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ru.hse.iuturakulov.jigsawbysockets.models.Player;

import java.io.IOException;

public class App extends Application {
    private static Scene scene;
    private double xOffset, yOffset;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("login_form"), 800, 600, Color.TRANSPARENT);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initStyle(StageStyle.TRANSPARENT);

        scene.setOnMousePressed((event) -> {
                    xOffset = stage.getX() - event.getScreenX();
                    yOffset = stage.getY() - event.getScreenY();
                }
        );

        scene.setOnMouseDragged((event) -> {
            stage.setX(event.getScreenX() + xOffset);
            stage.setY(event.getScreenY() + yOffset);
        });

        stage.setOnHidden((e) -> {
            if (Player.getPlayer() != null) {
                Player.logout();
            }
            System.exit(0);
        });

        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) {
        try {
            scene.setRoot(loadFXML(fxml));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("fxml/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}