package io.rdfs.helper;

import java.io.File;
import io.rdfs.model.DistributedFile;
import io.rdfs.model.Settings;
import org.w3c.dom.Attr;
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

    DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder documentBuilder;

    {
        try {
            documentBuilder = documentFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    Document document = documentBuilder.newDocument();

    public DataHelper(){}

    public static DataHelper getInstance(){
        if(instance == null) {
            instance = new DataHelper();
        }

        return instance;
    }

    @Override
    public List<DistributedFile> getAllFiles() {
        //TODO load from file

        List<DistributedFile> files = new ArrayList<>();

        try {
        File file = new File("C:\\Users\\User\\Desktop\\rdfs\\src\\main\\resources\\store\\fileStorage.xml");
        document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
        int totalNumberOfFiles = document.getElementsByTagName("File").getLength();

        // Reading every file from fileStorage xml
        for(int i=0; i<totalNumberOfFiles;i++){
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
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            }

        return files;
    }

    @Override
    public void updateFile(DistributedFile file) {
        List<DistributedFile> files = getAllFiles();

            //Checking if name of file matches name in file system
            for(int i=0; i<files.size();i++){
                if(files.get(i).name.equals(file.name)){

                    // Updating the files information and updating the whole list of files
                    files.get(i).path = file.path;
                    files.get(i).status = file.status;
                    files.get(i).owner = file.owner;
                    updateAllFiles(files);
                }
            }
    }

    @Override
    public void updateAllFiles(List<DistributedFile> files) {

        // Setting root
        Element root = document.createElement("FileSystem");
        document.appendChild(root);
        try {

            // Iterating through all files in given list
            for(int i=0; i<files.size();i++){

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
            StreamResult streamResult = new StreamResult(new File("C:\\Users\\User\\Desktop\\rdfs\\src\\main\\resources\\store\\fileStorage.xml"));
            transformer.transform(domSource, streamResult);

        } catch (TransformerException tfe) {
            tfe.printStackTrace();
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
