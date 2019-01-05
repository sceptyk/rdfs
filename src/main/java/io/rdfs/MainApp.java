package io.rdfs;

import helper.DataHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.HashMap;

public class MainApp extends Application {

    public static void main(String[] args) { launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/../resources/view/files.fxml"));
        Parent root = (Parent)loader.load();

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Randomly Distributed DistributedFile System");
        primaryStage.show();
    }
}
