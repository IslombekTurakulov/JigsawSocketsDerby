package ru.hse.iuturakulov.jigsawbysockets;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ru.hse.iuturakulov.jigsawbysockets.models.Player;
import ru.hse.iuturakulov.jigsawbysockets.utils.Constants;

import java.io.IOException;
import java.util.logging.Level;

/**
 * The type App.
 */
public class App extends Application {
    private static Scene scene;
    private double xOffset, yOffset;

    /**
     * Sets root.
     *
     * @param fxml the fxml
     */
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

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("login_form"), 800, 600, Color.TRANSPARENT);
        scene.setOnMousePressed((event) -> {
                    xOffset = stage.getX() - event.getScreenX();
                    yOffset = stage.getY() - event.getScreenY();
                }
        );
        stage.setTitle("Client - Jigsaw");
        stage.setOnHidden((e) -> {
            if (Player.getPlayer() != null)
                Player.logout();
            Constants.LOGGER.log(Level.WARNING, "Closing client...");
            System.exit(0);
        });
        stage.setResizable(false);
        stage.setFullScreen(false);
        stage.setScene(scene);
        stage.show();
    }
}