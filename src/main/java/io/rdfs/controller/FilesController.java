package io.rdfs.controller;

import io.rdfs.helper.DataHelper;
import io.rdfs.helper.EtherHelper;
import io.rdfs.helper.FilesUpdatedListener;
import io.rdfs.model.DistributedFile;
import io.rdfs.model.Settings;
import io.rdfs.view.FileListCell;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FilesController implements Initializable {

    @FXML private AnchorPane rootPane;
    @FXML private TextField blockchainAddressTextField;
    @FXML private PasswordField walletPasswordField;
    @FXML private Button connectButton;
    @FXML private CheckBox acceptFilesCheckBox;
    @FXML private ListView<DistributedFile> filesList;
    @FXML private Label walletFilePreview;
    @FXML private Label downloadFolderPreview;
    @FXML private TitledPane filesPane;
    @FXML private TitledPane settingsPane;
    @FXML private Accordion accordionWrapper;

    private boolean connected;
    private ObservableList observableList = FXCollections.observableArrayList();
    private DataHelper dataHelper;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dataHelper = DataHelper.getInstance();

        setListView();
        loadSettings();
        setViews();
        subscribeToFileChanges();
    }

    private void setListView() {
        List<DistributedFile> distributedFiles = dataHelper.getAllFiles();
        observableList.clear();
        observableList.addAll(distributedFiles);

        filesList.setItems(observableList);
        filesList.setCellFactory(param -> new FileListCell());
    }

    private void loadSettings(){
        Settings settings = dataHelper.getSettings();
        walletFilePreview.setText(settings.get(Settings.WALLET_FILE));
        downloadFolderPreview.setText(settings.get(Settings.DOWNLOAD_DIR));
        blockchainAddressTextField.setText(settings.get(Settings.BLOCKCHAIN_ADDRESS));
        walletPasswordField.setText(settings.get(Settings.WALLET_PASSWORD));
    }

    private void setViews(){
        accordionWrapper.setExpandedPane(settingsPane);
        filesPane.setDisable(true);
    }

    private void subscribeToFileChanges(){
        dataHelper.subscribeToFilesChanges(distributedFileList -> {
            Platform.runLater(() -> {
                observableList.clear();
                observableList.addAll(distributedFileList);
            });
        });
    }

    @FXML
    public void handleAddFile(ActionEvent actionEvent) {
        EtherHelper etherHelper = EtherHelper.getInstance();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file to upload");
        File file = fileChooser.showOpenDialog(rootPane.getScene().getWindow());

        DistributedFile distributedFile = new DistributedFile();
        distributedFile.path = file.getPath();

        try {
            etherHelper.publishOffer(distributedFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleConnect(ActionEvent actionEvent) {
        EtherHelper etherHelper = EtherHelper.getInstance();

        if (connected) {
            etherHelper.disconnect();
            connectButton.setText("Connect");
            connected = false;
            filesPane.setDisable(!connected);
            connectButton.setTextFill(Color.RED);
        } else {
            String walletPassword = walletPasswordField.getText();
            String blockchainAddress = blockchainAddressTextField.getText();

            Settings settings = dataHelper.getSettings();
            settings.put(Settings.WALLET_PASSWORD, walletPassword);
            settings.put(Settings.BLOCKCHAIN_ADDRESS, blockchainAddress);

            dataHelper.updateSettings(settings);

            etherHelper.connect(
                    walletPassword,
                    blockchainAddress,
                    settings.get(Settings.WALLET_FILE))
                    .subscribe(() -> {
                        connected = true;
                        Platform.runLater(() -> {
                            filesPane.setDisable(!connected);
                            connectButton.setTextFill(Color.GREEN);
                            connectButton.setText("Disconnect");
                            accordionWrapper.setExpandedPane(filesPane);
                        });
                    });
            connectButton.setTextFill(Color.BLACK);
            connectButton.setText("Connecting...");
        }
    }

    public void handleAcceptFiles(ActionEvent actionEvent) {
        EtherHelper etherHelper = EtherHelper.getInstance();
        if (!acceptFilesCheckBox.isSelected()) {
            etherHelper.unsubscribeToOffers();
        } else {
            etherHelper.subscribeToOffers();
        }
    }

    public void onSelectWallet(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select wallet file");
        File walletFile = fileChooser.showOpenDialog(rootPane.getScene().getWindow());

        Settings settings = dataHelper.getSettings();
        settings.put(Settings.WALLET_FILE, walletFile.getAbsolutePath());
        dataHelper.updateSettings(settings);
        walletFilePreview.setText(settings.get(Settings.WALLET_FILE));
    }

    public void onSelectDownload(ActionEvent actionEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select download folder");
        File downloadDir = directoryChooser.showDialog(rootPane.getScene().getWindow());

        Settings settings = dataHelper.getSettings();
        settings.put(Settings.DOWNLOAD_DIR, downloadDir.getAbsolutePath());
        dataHelper.updateSettings(settings);
        downloadFolderPreview.setText(settings.get(Settings.DOWNLOAD_DIR));
    }
}
