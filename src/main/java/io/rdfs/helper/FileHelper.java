package io.rdfs.helper;

import io.rdfs.model.DistributedFile;
import io.rdfs.model.Settings;
import org.apache.commons.lang3.ArrayUtils;

import javax.crypto.*;
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

    private FileHelper(){}

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
            int keyBitSize = 256;
            keyGenerator.init(keyBitSize, secureRandom);
            key = keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return key;
    }

    private byte[] encrypt(byte[] plain, SecretKey key){
        byte[] encrypted = null;
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
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
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
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
            DataHelper dataHelper = new DataHelper();
            dataHelper.updateFile(distributedFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return chunks;
    }

    @Override
    public void glueFile(List<byte[]> fileChunks, SecretKey key) {
        DataHelper dataHelper = new DataHelper();
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
}
