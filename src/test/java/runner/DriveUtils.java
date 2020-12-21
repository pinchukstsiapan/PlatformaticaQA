package runner;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;

import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
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

    private static final String baseFolderID = "1n0GPe676ldpnQ34bCVZ6KhlWdEtau6Ma";

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

    private static String getStackTrace(Exception ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        return sw.toString();
    }

    public static String createFolder(Drive drive, String parentID, String folderName) {
        if (drive == null || parentID == null) {
            return null;
        }

        File fileMetadata = new File();
        fileMetadata.setName(folderName);
        fileMetadata.setMimeType("application/vnd.google-apps.folder");

        if (parentID == null || parentID.isEmpty()) {
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



    public static void deleteFolder(Drive drive, String fileID){
        try {
            drive.files().delete(fileID).execute();
        } catch (IOException ioException) {
            LoggerUtils.logRed(String.format("unable to delete folder/file %s\n%s", fileID, getStackTrace(ioException)));
        }
    }

}
