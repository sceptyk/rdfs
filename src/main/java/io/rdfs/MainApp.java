package io.rdfs;

import io.rdfs.controller.FilesController;
import io.rdfs.helper.DataHelper;
import io.rdfs.model.File;
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/../resources/view/files.fxml"));
        Parent root = (Parent)loader.load();

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Randomly Distributed File System");
        primaryStage.show();
    }
}
