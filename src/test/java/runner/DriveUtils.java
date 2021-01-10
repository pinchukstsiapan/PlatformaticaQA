package runner;

import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.googleapis.batch.json.JsonBatchCallback;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;

import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.Permission;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.GeneralSecurityException;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

public class DriveUtils {

    private static final String appName = "PlatfromaticaQA";
    private static final String baseFolderID = "1UHAsw6L5h5_yBBxaembAw-QVf7cIaG_f";
    private static final String mediaValues =
            "ewogICJ0eXBlIjogInNlcnZpY2VfYWNjb3VudCIsCiAgInByb2plY3RfaWQiOiAidHJhdmlzLWJ1aWxkZXIiLAogICJwcml2YXRlX2tleV9pZCI6ICJlYzdjNmVjNTliNGEyNWQyMTE4OGNlYTkzYzBjODkwZGU3M2FjZTgzIiwKICAicHJpdmF0ZV9rZXkiOiAiLS0tLS1CRUdJTiBQUklWQVRFIEtFWS0tLS0tXG5NSUlFdlFJQkFEQU5CZ2txaGtpRzl3MEJBUUVGQUFTQ0JLY3dnZ1NqQWdFQUFvSUJBUUROQitwdSt1ZzgyKzNJXG5SNlo1ZEZRRlF1c05Cek9NUGdWVHl0bXVUNm50RCtsRkhod3NkakVWY2k5d1VXeFA1"
          + "N0pWSlZVRUdzZ0Fvc0xvXG56YVNDd0dKVDdoQitPbVFhMGJCOVNxVjFmR1VDajdENFg4MXExQ2EweEhTbS9reU9LZFZwZ1h4c3BUS3V0WEdmXG5YUTM0NGh1L3MrOUQ4eXE2U042UmtzQWhDY1ZtZnVMZHl0aTdiOTB6aTJQUldyKzVzQVpHelBxYzJScWszRkF3XG5rVFhRRXl0YlVyREZ0WDN1RmJJTGt4V2lEQXhWZjFiaG8xTjh4K3ZiVnZSQnNtWSthQUZEdldBelVpWjBFUldkXG5FdER1Z21teUphanY5TDV6ejZCdWRVUGdMYmhKSml3TWQxL2c2bzc1Sit6Q0VVVnBYZVh2TllYUVRsdi9UNng3XG5Xc0ZlRzM2dkFnT"
          + "UJBQUVDZ2dFQVYvSFFSZ0JMdzJjV3JIVEhYSXRnU3MxMFl5YzJuaTR3UE12aTZWajBhMklLXG56Z0huVmM3d3FPRk1wZUhYRXNNd2hFTTZIME9aakdSRU5IV1kzaGpGVkhqbURDN2hwM3RRTjczZ0VPL2xwOTZZXG5xMEQwcktFSlpUcHJTd0lETm11TlJlOHJ2ckp4ZGUwUUtxcFFodlA5c1JIdVRIZ3VXSzlQQUtRdzB6c011RFJxXG5hNEZkUlA2d1J3ZlJkZEpqOEVUQ1VTN0NnRTdIa0pBUGVydXdvSlpwTWdBRFI5VXYyZHJVUDVqMjVBRTVTcTV1XG5VRWI1Vkh4WUN4a3p5amJxZU5vVFRQUVNCZWdOVkl4WEFqTE83ZjhRS"
          + "lgwc05kNEhJTzFCRjFQT2w3VWROV04zXG50bktJN0VKUXVJNkphelZrajNUUEhKa0tTNlpNNjk5eTljQ0pTZUxMR1FLQmdRRG8vZDkrZXNkWnh2N0liUVEvXG5ZV1ZHQmc4NE91TGxIRnc5RnZsWnRUY3djaG8xUHBUM1ZKSUhwaFBoc1BNakMvcU1PazZhTXh3ZzRGdTIrM1lYXG5kQlU1Qy9vS3JKeTRnWWFRbkdVU1lLcEoyc1hmRWZFcmpEMHd2NDIxYUd0bUV2UXZZWmNnUTNKN1ZmeTllN0NDXG5weXQyUmRkbjQvUlh6MEt6Y2R4OHNxZTk4d0tCZ1FEaFJ5N01tbExoVEl3WHNYM2ZQWW9Db2UvYnd6Z20vSkc5XG54OWx6M"
          + "UgyMWZ0NjduMklZNkk0RTRKZEpLMzkxc2kwVFRRYVhaYmtNS1pzU3NreU1TQUF1dEhvR1lTM1lrRmlRXG5YT3cwMSsvV1NrVnRYcHU0YnhZT1dFemFCKzVIdS82aUVLMmFPcTFDcGNvQnQxU3RuNGlKRG44czVjZTlDMDB4XG45aW9IeHpNZlZRS0JnUUMyVStXUC90aitRcUdqaXR4bUZQdkJ2b0F1aXJhQWdKOFdGMkp1ZDBlSEcrT3lneFRrXG5NRVJPeEFLTy9ZQm5qcm8wL25RQWE3cTVaNW1lS2s2UnIvL2pzcUdydE1TNEJuU2R1aVhHS2V0WS9HRVlYZHdUXG5MaDI4aGtxSkNmdngxeWRMNU56MUhKTDhQYUFaVURBakxsc"
          + "nVwVjBhS2VOT3pCendmRXdGTVhIZlRRS0JnSDBZXG5NZnlibFhwV0wxVFkwclNzVDM2MnZhS2kvUU5wTE9UZG5QcWMvRkZVYWdwMXJ0dDJCNmJkc0NTSmF2WElRdzk4XG5yalJUNERYSXhMdlZGbnl2WHFxUXZWSGRsTCsxSi9qQ0lNZ1hRSzhWL3dlRWlhUVl5MzZidWRFNHBqQmZURDVpXG5WSVJZSU8zcGNnc1I2b3VmRXdwSWFvWmI4bFlmZUhaTjQzaitQSDBoQW9HQVlzbjhDeWczQ2phT0czQkNIblFJXG5oeFI2Q0ZsT2ZKaExxZ1RJWmhMMDg2ci90dFNGRFJKVURUc1ZpRzMrN2RWVDk0YTlxL3lwQUhkZWNQd09XMWk4X"
          + "G5kQkphVXFrMlpXaU9DaGM0MGxZcklmK1VtRnBKazM3N1BSazA0MkVLenE2S0YrWGdyMXFrM2E1ZXB2TkkybVJYXG5vLy9JeUhzd3ZWbTQ4dW5hbXcrSHBTbz1cbi0tLS0tRU5EIFBSSVZBVEUgS0VZLS0tLS1cbiIsCiAgImNsaWVudF9lbWFpbCI6ICJwbGF0ZnJvbWF0aWNhcWFAdHJhdmlzLWJ1aWxkZXIuaWFtLmdzZXJ2aWNlYWNjb3VudC5jb20iLAogICJjbGllbnRfaWQiOiAiMTAwNzc2MTA0ODczNjU5MzQ5ODY4IiwKICAiYXV0aF91cmkiOiAiaHR0cHM6Ly9hY2NvdW50cy5nb29nbGUuY29tL28vb2F1dGgyL2F1d"
          + "GgiLAogICJ0b2tlbl91cmkiOiAiaHR0cHM6Ly9vYXV0aDIuZ29vZ2xlYXBpcy5jb20vdG9rZW4iLAogICJhdXRoX3Byb3ZpZGVyX3g1MDlfY2VydF91cmwiOiAiaHR0cHM6Ly93d3cuZ29vZ2xlYXBpcy5jb20vb2F1dGgyL3YxL2NlcnRzIiwKICAiY2xpZW50X3g1MDlfY2VydF91cmwiOiAiaHR0cHM6Ly93d3cuZ29vZ2xlYXBpcy5jb20vcm9ib3QvdjEvbWV0YWRhdGEveDUwOS9wbGF0ZnJvbWF0aWNhcWElNDB0cmF2aXMtYnVpbGRlci5pYW0uZ3NlcnZpY2VhY2NvdW50LmNvbSIKfQ==";

    /**
     * Upload .png image file filePath to the Drive in to specific folder
     * @param drive Google Drive API service handle
     * @param folderID ID of the folder were to upload file
     * @param filePath path to file for upload
     */
    private static void uploadImage(Drive drive, String folderID, java.io.File filePath) {
        File fileMetadata = new File();
        fileMetadata.setName(filePath.getName());
        fileMetadata.setParents(Collections.singletonList(folderID));
        FileContent mediaContent = new FileContent("image/png", filePath);
        try {
            File file = drive.files()
                    .create(fileMetadata, mediaContent)
                    .setFields("id, parents")
                    .execute();
            LoggerUtils.log(String.format("uploaded file %s with id %s", filePath.toString(), file.getId()));
        } catch (IOException ioException) {
            LoggerUtils.logRed(String.format("unable to upload file %s\n%s", filePath.toString(), getStackTrace(ioException)));
        }
    }

    /** Build an authorized Drive client service and login.
     * @return an authorized Drive client service
     */
    public static Drive login() {
        Drive driveService;
        HttpTransport httpTransport;

        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        } catch (GeneralSecurityException | IOException ex) {
            LoggerUtils.logRed(String.format("Unable to get a new instance of NetHttpTransport\n%s", getStackTrace(ex)));
            return null;
        }

        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        GoogleCredentials googleCredentials;

        try {
            googleCredentials = GoogleCredentials.
                    fromStream(new ByteArrayInputStream((new String(Base64.getDecoder().decode(mediaValues))).getBytes())).
                    createScoped(Collections.singleton(DriveScopes.DRIVE));
        } catch (IOException ioException) {
            LoggerUtils.logRed(String.format("Build service account credentials\n%s", getStackTrace(ioException)));
            return null;
        }

        HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(googleCredentials);

        driveService = new Drive.Builder(httpTransport, jsonFactory, requestInitializer)
                .setApplicationName(appName)
                .build();

        return driveService;
    }

    /**
     * Login and then upload whole folder of images to Drive inside of base folder
     * @param directoryPath full path to directory to be uploaded
     */
    public static void uploadFolderAsImages(String directoryPath) {
        Drive drive = DriveUtils.login();

        java.io.File dir = new java.io.File(directoryPath);
        java.io.File[] files = dir.listFiles();
        if (files ==null || files.length == 0) {
            LoggerUtils.logYellow(String.format("The directory %s is empty", directoryPath));
            return;
        }

        String newFolderID = createFolder(drive, baseFolderID, dir.getName());

        for (java.io.File aFile : files) {
            System.out.println(aFile.getName() + " - " + aFile.length());
            uploadImage(drive, newFolderID, aFile);
        }
    }

    /**
     * Create folder inside of another folder. if parentID is null then create new directory in the root.
     * @param drive Google drive API service handle
     * @param parentID ID of the parent folder
     * @param folderName name of the folder to create
     * @return ID of the new folder
     */
    public static String createFolder(Drive drive, String parentID, String folderName) {
        File fileMetadata = new File();
        fileMetadata.setName(folderName);
        fileMetadata.setMimeType("application/vnd.google-apps.folder");

        if (parentID != null && !parentID.isEmpty()) {
            fileMetadata.setParents(Collections.singletonList(parentID));
        }

        try {
            File file = drive.files().create(fileMetadata)
                    .setFields("id")
                    .execute();
            LoggerUtils.logYellow(String.format("Folder ID: %s", file.getId()));
            return file.getId();
        } catch (IOException ioException) {
            LoggerUtils.logRed(String.format("Build service account credentials\n%s", getStackTrace(ioException)));
        }
        return null;
    }

    public static String getStackTrace(Exception ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        return sw.toString();
    }
}
