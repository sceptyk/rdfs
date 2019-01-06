package io.rdfs.controller;

import io.rdfs.helper.DataHelper;
import io.rdfs.helper.EtherHelper;
import io.rdfs.model.DistributedFile;
import io.rdfs.model.Settings;
import io.rdfs.view.FileListCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FilesController implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private PasswordField walletPasswordField;

    @FXML
    private Button connectButton;
    private boolean connected;

    @FXML
    private CheckBox acceptFilesCheckBox;

    @FXML
    private ListView<DistributedFile> filesList;

    private ObservableList observableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setListView();
    }

    private void setListView() {
        DataHelper dataHelper = new DataHelper();
        List<DistributedFile> distributedFiles = dataHelper.getAllFiles();
        observableList.clear();
        observableList.addAll(distributedFiles);

        filesList.setItems(observableList);
        filesList.setCellFactory(param -> new FileListCell());
    }

    @FXML
    public void handleAddFile(ActionEvent actionEvent) {
        EtherHelper etherHelper = EtherHelper.getInstance();
        DistributedFile distributedFile = new DistributedFile();

        try {
            etherHelper.publishOffer(distributedFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleConnect(ActionEvent actionEvent) {
        EtherHelper etherHelper = EtherHelper.getInstance();

        if(connected){
            etherHelper.disconnect();
            connectButton.setText("Connect");
            connected = true;
        }else{
            String walletPassword = walletPasswordField.getText();

            etherHelper.connect(walletPassword, () -> {
                connected = true;
            });
            connectButton.setText("Disconnect");
        }
    }

    public void handleAcceptFiles(ActionEvent actionEvent) {
        EtherHelper etherHelper = EtherHelper.getInstance();
        if(!acceptFilesCheckBox.isSelected()){
            etherHelper.unsubscribeToOffers();
        } else {
            etherHelper.subscribeToOffers();
        }
    }

    public void onSelectWallet(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select wallet file");
        File walletFile = fileChooser.showOpenDialog(rootPane.getScene().getWindow());

        DataHelper dataHelper = new DataHelper();
        Settings settings = dataHelper.getSettings();
        settings.put(Settings.WALLET_FILE, walletFile.getAbsolutePath());
    }

    public void onSelectDownload(ActionEvent actionEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select download folder");
        File downloadDir = directoryChooser.showDialog(rootPane.getScene().getWindow());

        DataHelper dataHelper = new DataHelper();
        Settings settings = dataHelper.getSettings();
        settings.put(Settings.DOWNLOAD_DIR, downloadDir.getAbsolutePath());
    }
}
