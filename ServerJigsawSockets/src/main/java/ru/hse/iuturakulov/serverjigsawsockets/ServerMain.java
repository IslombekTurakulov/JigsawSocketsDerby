package ru.hse.iuturakulov.serverjigsawsockets;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.hse.iuturakulov.serverjigsawsockets.network.Client;
import ru.hse.iuturakulov.serverjigsawsockets.network.Server;

import java.io.IOException;

/**
 * The type Server main.
 *
 * @author Islombek Turakulov
 * @version 1.0
 */
public class ServerMain extends Application {

    private static Scene scene;
    private double coordX;
    private double coordY;

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        launch();
    }

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
        FXMLLoader fxmlLoader = new FXMLLoader(ServerMain.class.getResource("fxml/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("server_main"), 800, 600);
        stage.setTitle("Server - Jigsaw");
        stage.setOnCloseRequest(e -> {
            Server.closeSocket();
            Client.onlineClients.removeIf(Client::close);
            System.exit(0);
        });
        scene.setOnMousePressed((event) -> {
                    coordX = stage.getX() - event.getScreenX();
                    coordY = stage.getY() - event.getScreenY();
                }
        );
        scene.setOnMouseDragged((event) -> {
            stage.setX(event.getScreenX() + coordX);
            stage.setY(event.getScreenY() + coordY);

        });
        stage.setResizable(false);
        stage.setFullScreen(false);
        stage.setScene(scene);
        stage.show();
    }

}