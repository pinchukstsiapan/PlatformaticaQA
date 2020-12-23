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

/** set of utils to work with Google Drive */
public class DriveUtils {

    public static final String baseDirectoryName = "ScreenShots";

    public static final String appName = "PlatfromaticaQA";

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

    /** get list of all files and folders with all available fields */
    private static List<File> getAllFilesList(Drive drive) {
        String fields = "nextPageToken, files(*)";

        return getAllFilesList(drive, fields);
    }

    /** get list of all files and folders with requested fields */
    private static List<File> getAllFilesList(Drive drive, String fields) {
        if (drive == null) {
            return null;
        }
        if (fields == null || fields.isEmpty()) {
            fields = "nextPageToken, files(*)";
        }
        FileList result = null;
        Drive.Files files = drive.files();

        Drive.Files.List list = null;
        try {
            result = files.
                    list().
                    setPageSize(100).
                    setFields(fields).
                    execute();
        } catch (IOException ioException) {
            LoggerUtils.logRed(String.format("Unable to get list of files\n%s", getStackTrace(ioException)));
        }

        return result.getFiles();
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

        // Returns a global thread-safe instance of Low-level JSON library implementation based on Jackson 2.
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

        //Build service account credentials for authorizing calls to Google APIs using OAuth2.
        GoogleCredentials googleCredentials;
        try {
            googleCredentials = GoogleCredentials.
                    fromStream(new ByteArrayInputStream((new String(Base64.getDecoder().decode(mediaValues))).getBytes())).
                    createScoped(Collections.singleton(DriveScopes.DRIVE));
        } catch (IOException ioException) {
            LoggerUtils.logRed(String.format("Build service account credentials\n%s", getStackTrace(ioException)));
            return null;
        }

        // create a wrapper for using Credentials with the Google API Client Libraries for Java with Http.
        HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(googleCredentials);

        driveService = new Drive.Builder(httpTransport, jsonFactory, requestInitializer)
                .setApplicationName(appName)
                .build();

        return driveService;
    }

    /** Create folder inside of another folder. if parentID is null then create new directory in the root */
    public static String createFolder(Drive drive, String parentID, String folderName) {
        File fileMetadata = new File();
        fileMetadata.setName(folderName);
        fileMetadata.setMimeType("application/vnd.google-apps.folder");

        if (parentID != null && !parentID.isEmpty()) {
            fileMetadata.setParents(Collections.singletonList(parentID));
        }

        File file = null;
        try {
            file = drive.files().create(fileMetadata)
                    .setFields("id")
                    .execute();
        } catch (IOException ioException) {
            LoggerUtils.logRed(String.format("Build service account credentials\n%s", getStackTrace(ioException)));
        }
            LoggerUtils.logYellow(String.format("Folder ID: %s", file.getId()));
        return file.getId();
    }

    /** delete file from Drive */
    private static void deleteFile(Drive drive, File file){
        try {
            if (!baseFolderID.equals(file.getId())) {
                drive.files().delete(file.getId()).execute();
            }
            System.out.printf("deleting file [%s] %s\n", file.getId(), file.getName());
        } catch (IOException ioException) {
            LoggerUtils.logRed(String.format("unable to delete folder/file [%s] %s\n%s",
                    file.getId(), file.getName(), getStackTrace(ioException)));
        }
    }

    /** Upload image file filePath to the drive in to folder with ID=folderID */
    public static String uploadImage(Drive drive, String folderID, java.io.File filePath) {
        return uploadFile(drive, folderID, filePath, "image/png");
    }

    /** Upload file filePath to the drive in to folder with ID=folderID */
    private static String uploadFile(Drive drive, String folderID, java.io.File filePath, String mimeType) {
        if (filePath == null) {
            return null;
        }
        File fileMetadata = new File();
        fileMetadata.setName(filePath.getName());
        fileMetadata.setParents(Collections.singletonList(folderID));
        FileContent mediaContent = new FileContent(mimeType, filePath);
        File file = null;
        try {
            file = drive.files().create(fileMetadata, mediaContent)
                    .setFields("id, parents")
                    .execute();
        } catch (IOException ioException) {
            LoggerUtils.logRed(String.format("unable to upload file %s\n%s", filePath.toString(), getStackTrace(ioException)));
        }
        LoggerUtils.log(String.format("uploaded file %s with id %s", filePath.toString(), file.getId()));
        return file.getId();
    }

    /** Login and then upload the whole directory to drive */
    public static void uploadFolderAsImages(String directoryPath) {
        Drive drive = DriveUtils.login();
        uploadFolderAsImages(drive, directoryPath);
    }

    /** Upload whole folder of imsages to Drive inside of based folder */
    public static void uploadFolderAsImages(Drive drive, String directoryPath) {
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

    /** Get list of files from folder by ID */
    private static List<File> getFilesListInFolder(Drive drive, String folderID) {
        return getAllFilesList(drive, "nextPageToken, files(id, name, size, mimeType, permissions)");
    }

    /** print into system output all files in the list */
    private static void printFilesInFolder(List<File> files) {
        System.out.println("\nFiles:");
        for (File file : files) {
            if (file.getSize() != null) {
                System.out.printf("[%s]  %6.2fK  %s%n", file.getId(),
                        (100 * file.getSize()/1024) / 100.0,
                        file.getName());
            } else {
                System.out.printf("[%s]  -------  %s%n", file.getId(), file.getName());
            }
        }
    }

    /** set permissions required to share files with PlatformaticaQA@gmail.com */
    private static void setPermissions(Drive drive, String fileId) {
        JsonBatchCallback<Permission> callback = new JsonBatchCallback<Permission>() {
            @Override
            public void onFailure(GoogleJsonError e, HttpHeaders responseHeaders) {
                LoggerUtils.logRed(e.getMessage());
            }

            @Override
            public void onSuccess(Permission permission, HttpHeaders responseHeaders) {
                LoggerUtils.logRed("Permission ID: " + permission.getId());
            }
        };
        BatchRequest batch = drive.batch();

        Permission userPermission = new Permission()
                .setType("user")
                .setRole("writer")
                .setEmailAddress("PlatformaticaQA@gmail.com");

        try {
            drive.permissions().create(fileId, userPermission)
                    .setFields("id")
                    .queue(batch, callback);

            batch.execute();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    public static String getStackTrace(Exception ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        return sw.toString();
    }
}
