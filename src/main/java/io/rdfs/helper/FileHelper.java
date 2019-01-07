package io.rdfs.helper;

import io.rdfs.model.DistributedFile;
import io.rdfs.model.Settings;
import org.apache.commons.lang3.ArrayUtils;

import javax.crypto.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileHelper implements IFileHelper {

    private static FileHelper instance;
    private DataHelper dataHelper;

    private FileHelper(){
        dataHelper = DataHelper.getInstance();
    }

    public static FileHelper getInstance(){
        if(instance == null) {
            instance = new FileHelper();
        }

        return instance;
    }

    private SecretKey getNewKey(){
        SecretKey key = null;
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = new SecureRandom();
            keyGenerator.init(128, secureRandom);
            key = keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return key;
    }

    private byte[] encrypt(byte[] plain, SecretKey key){
        byte[] encrypted = null;
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            encrypted = cipher.doFinal(plain);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        return encrypted;
    }

    private byte[] decrypt(byte[] encrypted, SecretKey key){
        byte[] decrypted = null;
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            decrypted = cipher.doFinal(encrypted);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        return decrypted;
    }

    @Override
    public List<byte[]> splitFile(DistributedFile distributedFile) {
        List<byte[]> chunks = new ArrayList<>();
        try {
            byte[] fileContent = Files.readAllBytes(Paths.get(distributedFile.path));
            int from = 0, step = 128*10, to = step;
            SecretKey key = getNewKey();
            while(to < fileContent.length){
                chunks.add(encrypt(Arrays.copyOfRange(fileContent, from, to), key));

                from = to + 1;
                to = to + step;
            }

            distributedFile.key = key;
            distributedFile.status = DistributedFile.Status.UPLOADING;
            dataHelper.updateFile(distributedFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return chunks;
    }

    @Override
    public void glueFile(List<byte[]> fileChunks, SecretKey key) {
        Settings settings = dataHelper.getSettings();

        byte[] fileContent = new byte[]{};
        for(byte[] chunk: fileChunks){
            byte[] decryptedChunk = decrypt(chunk, key);
            fileContent = ArrayUtils.addAll(fileContent, decryptedChunk);
        }

        try {
            FileOutputStream fos = new FileOutputStream(settings.get("downloadDir"));
            fos.write(fileContent);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public byte[] getFileAsChunk(DistributedFile foundDistributedFile) {
        byte[] fileContent = null;
        try {
            fileContent = Files.readAllBytes(Paths.get(foundDistributedFile.path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileContent;
    }

    @Override
    public DistributedFile saveFileChunk(byte[] chunk, String contract) {
        Settings settings = dataHelper.getSettings();
        DistributedFile distributedFile = null;
        try {
            String path = Settings.DOWNLOAD_DIR + File.pathSeparator + contract + ".bin";
            FileOutputStream fos = new FileOutputStream(new File(settings.get(path)));
            fos.write(chunk);

            distributedFile = new DistributedFile();
            distributedFile.contract = contract;
            distributedFile.path = path;
            distributedFile.status = DistributedFile.Status.COLLECTABLE;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return distributedFile;
    }
}
