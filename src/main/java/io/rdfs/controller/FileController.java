package io.rdfs.controller;

import io.rdfs.model.DistributedFile;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class FileController {

    @FXML
    private Button downloadButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Label nameLabel;

    @FXML
    private Label statusLabel;

    public void setData(DistributedFile info) {
        nameLabel.setText(info.name);
        statusLabel.setText(info.status);
    }
}
