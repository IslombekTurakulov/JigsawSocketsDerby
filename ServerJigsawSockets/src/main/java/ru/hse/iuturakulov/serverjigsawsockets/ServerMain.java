package ru.hse.iuturakulov.serverjigsawsockets;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ru.hse.iuturakulov.serverjigsawsockets.network.Client;
import ru.hse.iuturakulov.serverjigsawsockets.network.Server;

import java.io.IOException;

public class ServerMain extends Application {

    private static Scene scene;
    private double coordX;
    private double coordY;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ServerMain.class.getResource("server_main.fxml"));
        scene = new Scene(fxmlLoader.load());
        stage.setTitle("Server - Jigsaw");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initStyle(StageStyle.TRANSPARENT);
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
        stage.setScene(scene);
        stage.show();
    }
}