package io.rdfs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/files.fxml"));
        Parent root = (Parent)loader.load();

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Randomly Distributed DistributedFile System");
        primaryStage.show();
    }
}
