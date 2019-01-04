package io.rdfs.view;

import io.rdfs.controller.FileController;
import io.rdfs.model.DistributedFile;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class FileListCell extends ListCell<DistributedFile> {

    private FileController controller;
    private Parent root;

    public FileListCell() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/../resources/view/file.fxml"));
        try {
            root = loader.load();
            controller = (FileController) loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void updateItem(DistributedFile item, boolean empty) {
        super.updateItem(item, empty);

        if(item != null) {
            controller.setData(item);
            setGraphic(root);
        }
    }
}
