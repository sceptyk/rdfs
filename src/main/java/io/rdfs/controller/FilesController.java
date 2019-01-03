package io.rdfs.controller;

import io.rdfs.helper.DataHelper;
import io.rdfs.model.File;
import io.rdfs.view.FileListCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FilesController implements Initializable {

    @FXML
    private ListView<File> filesList;

    private ObservableList observableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setListView();
    }

    private void setListView() {
        DataHelper dataHelper = DataHelper.getInstance();
        List<File> files = dataHelper.getAllFiles();
        observableList.clear();
        observableList.addAll(files);

        filesList.setItems(observableList);
        filesList.setCellFactory(param -> new FileListCell());
    }
}
