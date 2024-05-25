package com.hauntedplace.HauntedPlaceAPI.Services;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import com.hauntedplace.HauntedPlaceAPI.Models.StringWrapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.UUID;

@Service
public class FirebaseStorageService {

    private final String bucketName = "haunted-place-681c1.appspot.com";

    public FirebaseStorageService() {
    }

    public static Storage createStorageClient() throws IOException {
        FileInputStream serviceAccountStream = new FileInputStream("./serviceAccountKey.json");
        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccountStream);
        return StorageOptions.newBuilder().setCredentials(credentials).build().getService();

    }


    private String uploadFile(File file, String fileName) throws IOException {
        Storage storage = FirebaseStorageService.createStorageClient();
        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));
        String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media";
        return String.format(DOWNLOAD_URL, bucketName, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }

    private File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
        File tempFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
        }
        return tempFile;
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    public static byte[] fileToBytes(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        byte[] bytesArray = new byte[(int) file.length()];
        fis.read(bytesArray);
        fis.close();
        return bytesArray;
    }


    public StringWrapper upload(MultipartFile multipartFile) {
        try {
            String fileName = multipartFile.getOriginalFilename();
            if (fileName != null) {
                fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));
            }

            File file = this.convertToFile(multipartFile, fileName);
            String URL = this.uploadFile(file, fileName);
            file.delete();
            return new StringWrapper(URL);
        } catch (Exception e) {
            return null;
        }
    }

    public StringWrapper remove(String path) throws Exception {
        try {
            var filename = getFileName(path);
            Storage storage = FirebaseStorageService.createStorageClient();
            BlobId blobId = BlobId.of(bucketName, filename);
            storage.delete(blobId);
        } catch (Exception e) {
            throw new Exception("Image couldn't remove, Something went wrong");
        }
        return null;
    }

    private String getFileName(String profilePictureUrl) {
        var strings = profilePictureUrl.split("/");
        var filename =  Arrays.stream(strings).filter(s -> s.contains(".png") || s.contains(".jpeg") || s.contains(".jpg")).findFirst().get();
        return filename.replace("?alt=media", "");
    }
}
