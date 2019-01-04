package io.rdfs;

import io.rdfs.controller.FilesController;
import io.rdfs.helper.DataHelper;

import io.rdfs.model.DistributedFile;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);

        //--------------- TESTING -------------------//

        DataHelper data = new DataHelper();
        List<DistributedFile> files = new ArrayList<>();

        DistributedFile file1 = new DistributedFile();
        file1.name = "File 1";
        file1.owner = "Owner 1";
        file1.status = "Status 1";
        file1.path = "Path 1";

        DistributedFile file2 = new DistributedFile();
        file2.name = "File 1";
        file2.owner = "Owner 2";
        file2.status = "Status 2";
        file2.path = "Path 2";

        DistributedFile file3 = new DistributedFile();
        file3.name = "File 3";
        file3.owner = "Owner 3";
        file3.status = "Status 3";
        file3.path = "Path 3";


        DistributedFile file4 = new DistributedFile();
        file4.name = "File 4";
        file4.owner = "Owner 4";
        file4.status = "Status 4";
        file4.path = "Path 4";

        files.add(file1);
        files.add(file2);
        files.add(file3);
        files.add(file4);
        //data.updateFile(file2);   PROBLEM
        data.updateAllFiles(files);

        // ----------------- TESTING ------------ //
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
