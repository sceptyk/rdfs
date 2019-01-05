package helper;

import java.io.File;

import io.rdfs.model.DistributedFile;
import io.rdfs.model.Settings;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataHelper implements IDataHelper {

    private static DataHelper instance;
    private DocumentBuilder documentBuilder;

    private String storageFilePath;

    private DataHelper() {
        this.storageFilePath = storageFilePath;
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

        try {
            documentBuilder = documentFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static DataHelper getInstance() {
        return getInstance(DataHelper.class.getResource("/../resources/store/fileStorage.xml").toString());
    }

    public static DataHelper getInstance(String storageFilePath) {
        if (instance == null) {
            instance = new DataHelper();
        }

        instance.setStoragePath(storageFilePath);

        return instance;
    }

    public void setStoragePath(String storagePath) {
        this.storageFilePath = storagePath;
    }

    @Override
    public List<DistributedFile> getAllFiles() {
        List<DistributedFile> files = new ArrayList<>();

        try {
            File storageFile = new File(storageFilePath);
            Document document = documentBuilder.parse(storageFile);
            int totalNumberOfFiles = document.getElementsByTagName("File").getLength();

            // Reading every file from fileStorage xml
            for (int i = 0; i < totalNumberOfFiles; i++) {
                DistributedFile distributedFile = new DistributedFile();

                distributedFile.name = document.getElementsByTagName("Name").item(i).getTextContent();
                distributedFile.status = document.getElementsByTagName("Status").item(i).getTextContent();
                distributedFile.owner = document.getElementsByTagName("Owner").item(i).getTextContent();
                distributedFile.path = document.getElementsByTagName("Path").item(i).getTextContent();

                files.add(distributedFile);
            }

        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return files;
    }

    @Override
    public void updateFile(DistributedFile updatedFile) {
        List<DistributedFile> files = getAllFiles();

        //Checking if name of file matches name in file system
        for (DistributedFile file : files) {
            if (file.name.equals(updatedFile.name)) {

                // Updating the files information and updating the whole list of files
                file.path = updatedFile.path;
                file.status = updatedFile.status;
                file.owner = updatedFile.owner;

                break;
            }
        }

        updateAllFiles(files);
    }

    @Override
    public void updateAllFiles(List<DistributedFile> files) {
        try {
            // Setting root
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element root = document.createElement("FileSystem");
            document.appendChild(root);

            // Iterating through all files in given list
            for (int i = 0; i < files.size(); i++) {

                //TODO use reflection API

                // Setting file element
                Element file = document.createElement("File");
                root.appendChild(file);

                // Setting file name
                Element fileName = document.createElement("Name");
                fileName.appendChild(document.createTextNode(files.get(i).name));
                file.appendChild(fileName);

                // Setting file status
                Element fileStatus = document.createElement("Status");
                fileStatus.appendChild(document.createTextNode(files.get(i).status));
                file.appendChild(fileStatus);

                // Setting file path
                Element filePath = document.createElement("Path");
                filePath.appendChild(document.createTextNode(files.get(i).path));
                file.appendChild(filePath);

                // Setting file owner
                Element fileOwner = document.createElement("Owner");
                fileOwner.appendChild(document.createTextNode(files.get(i).owner));
                file.appendChild(fileOwner);

            }

            // Create the xml file and transform the DOM Object to an XML File
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            // Indenting output
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource domSource = new DOMSource(document);
            File storeFile = new File(storageFilePath);

            try {
                if (storeFile.exists())
                    storeFile.delete();
                else
                    storeFile.getParentFile().mkdirs();
                storeFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            StreamResult streamResult = new StreamResult(storeFile);
            transformer.transform(domSource, streamResult);

        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Settings getSettings() {
        return null;
    }

    @Override
    public void updateSettings(Settings config) {

    }
}
