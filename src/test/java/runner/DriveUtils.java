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

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

/** set of utils to work with Google Drive */
public class DriveUtils {

    public static final String baseDirectoryName = "ScreenShots";

    public static final String appName = "PlatfromaticaQA";

    // ScreenShots [1UHAsw6L5h5_yBBxaembAw-QVf7cIaG_f]  mimeType: application/vnd.google-apps.folder
    private static final String baseFolderID = "1UHAsw6L5h5_yBBxaembAw-QVf7cIaG_f";

    /** get files list matching pattern with requested fields */
    private static List<File> getFilesList(Drive drive, String fields, String searchPattern) {
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
            //TODO: add implementation of searchPattern
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
     * @throws IOException
     */
    public static Drive login() {
        Drive driveService = null;

        HttpTransport httpTransport = null;
        try {
            // Get a new instance of NetHttpTransport that uses GoogleUtils.getCertificateTrustStore() for the trusted
            // certificates using NetHttpTransport.Builder.trustCertificates(KeyStore).
            // If `GOOGLE_API_USE_CLIENT_CERTIFICATE` environment variable is set to "true", and the default client
            // certificate key store from Utils#loadDefaultMtlsKeyStore() is not null, then the transport uses the
            // default client certificate and is mutual TLS.
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        } catch (GeneralSecurityException | IOException ex) {
            LoggerUtils.logRed(String.format("Unable to get a new instance of NetHttpTransport\n%s", getStackTrace(ex)));
            return null;
        }

        // Returns a global thread-safe instance of Low-level JSON library implementation based on Jackson 2.
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

        //Build service account credentials for authorizing calls to Google APIs using OAuth2.
        GoogleCredentials googleCredentials = null;
        try {
            googleCredentials = GoogleCredentials.
                    // credentials defined by a JSON file stream.
                    fromStream(new FileInputStream("tokens\\credentials.json")).
                    // creates a copy of the the identity with the specified scopes
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

    public static void deleteFolder(Drive drive, String folderID){
        deleteFile(drive, folderID);
    }

    public static void deleteFile(Drive drive, String fileID){
        try {
            drive.files().delete(fileID).execute();
        } catch (IOException ioException) {
            LoggerUtils.logRed(String.format("unable to delete folder/file %s\n%s", fileID, getStackTrace(ioException)));
        }
    }

    public static void deleteFile(Drive drive, File file){
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

    public static void deleteFileInFolder(Drive drive, String folderID) {

    }

    //java.io.File filePath = new java.io.File("files/photo.jpg");
    public static String uploadFile(Drive drive, String folderID, java.io.File filePath) {
        if (filePath == null) {
            return null;
        }
        File fileMetadata = new File();
        fileMetadata.setName(filePath.getName());
        fileMetadata.setParents(Collections.singletonList(folderID));
        FileContent mediaContent = new FileContent("image/png", filePath);
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
    public static void uploadWholeFolder(String directoryPath) {
        Drive drive = DriveUtils.login();
        uploadWholeFolder(drive, directoryPath);
    }

    public static void uploadWholeFolder(Drive drive, String directoryPath) {
        java.io.File dir = new java.io.File(directoryPath);

        // Create folder on Drive
        String newFolderID = createFolder(drive, baseFolderID, dir.getName());
        java.io.File[] files = dir.listFiles();
        if (files.length == 0) {
            LoggerUtils.logYellow(String.format("The directory %s is empty", directoryPath));
        } else {
            for (java.io.File aFile : files) {
                System.out.println(aFile.getName() + " - " + aFile.length());
                uploadFile(drive, newFolderID, aFile);
            }
        }
    }

    public static List<File> getFilesListInFolder(Drive drive, String folderID) {
        List<File> files = getFilesList(drive, "nextPageToken, files(id, name, size, mimeType, permissions)", "*");
        return files;
    }

    public static void printFilesInFolder(List<File> files) {
        System.out.println("\nFiles:");
        for (File file : files) {
            if (file.getSize() != null) {
                System.out.printf("[%s]  %6.2fK  %s%n", file.getId(),
                        ((int) 100 * file.getSize() / 1024) / 100.0,
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
            public void onFailure(GoogleJsonError e,
                                  HttpHeaders responseHeaders)
                    throws IOException {
                // Handle error
                System.err.println(e.getMessage());
            }

            @Override
            public void onSuccess(Permission permission,
                                  HttpHeaders responseHeaders)
                    throws IOException {
                System.out.println("Permission ID: " + permission.getId());
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

    private static String getStackTrace(Exception ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        return sw.toString();
    }

    /* ---------------------------------------------------------------------------------------------------------------*/
    public static void main(String[] args) {
        Drive drive = login();

        // CREATE Base Folder and share it with PlatformaticaQA@gmail.com
        // String folderID = createFolder(drive, null, "ScreenShots");
        // System.out.println("Base Folder ID: " + folderID);
        // setPermissions(drive, baseFolderID);

//        String newFolderID = "1jnbdA5u3Cv4hxxafLXPUN2L1VqDSfHrh";
//        String newFolderID = createFolder(drive, baseFolderID,"2020-12-19-14-15-77093ec5-4ecf-4e88-ac95-5878a48495c5");
//        System.out.println("new folder ID: " + newFolderID);

        String path =  "c:\\Users\\DmitryErmolaev\\AppData\\Local\\Temp\\2020-12-20-18-40-b028dec4-20a5-4703-bb4d-d29fd65a50e5";
        uploadWholeFolder(drive, path);

        deleteFile(drive, "14x75IGgGakobeCaXQPTbvOEXgemSXvcB");

        List<File> files =  getFilesListInFolder(drive, DriveUtils.baseFolderID);
        printFilesInFolder(files);

//        for (File file : files) {
//            if ( !file.getId().equals(baseFolderID) ) {
//                deleteFile(drive, file);
//            }
//        }
//        deleteFolder(drive, newFolderID);
//
//        System.out.println("---------------------------------------------------------------------------------------");
//        files =  getFilesListInFolder(drive, DriveUtils.baseFolderID);
//        printFilesInFolder(files);
    }
}
