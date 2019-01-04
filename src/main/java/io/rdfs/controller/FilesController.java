package io.rdfs.controller;

import io.rdfs.helper.DataHelper;
import io.rdfs.model.DistributedFile;
import io.rdfs.view.FileListCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FilesController implements Initializable {

    @FXML
    private ListView<DistributedFile> filesList;

    //private List<DistributedFile> files = new ArrayList<>();

    private ObservableList observableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setListView();
    }

    private void setListView() {
        DataHelper dataHelper = DataHelper.getInstance();
        List<DistributedFile> distributedFiles = dataHelper.getAllFiles();
        observableList.clear();
        observableList.addAll(distributedFiles);
        //dataHelper.updateAllFiles(files);

        filesList.setItems(observableList);
        filesList.setCellFactory(param -> new FileListCell());
    }

}
