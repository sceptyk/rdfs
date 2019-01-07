package io.rdfs.controller;

import io.rdfs.helper.EtherHelper;
import io.rdfs.model.DistributedFile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class FileController {

    @FXML private Label statusLabel;
    @FXML private Label fileNameLabel;
    @FXML private Label contractLabel;

    private DistributedFile distributedFile;

    public void setData(DistributedFile info) {
        distributedFile = info;
        fileNameLabel.setText(info.path);
        statusLabel.setText(info.status);
        contractLabel.setText(info.contract);
    }

    public void onDownloadFile(ActionEvent actionEvent) {
        EtherHelper etherHelper = EtherHelper.getInstance();
        etherHelper.requestFile(distributedFile);
    }

    public void onDeleteFile(ActionEvent actionEvent) {
        EtherHelper etherHelper = EtherHelper.getInstance();
        etherHelper.deleteFile(distributedFile);
    }

}
