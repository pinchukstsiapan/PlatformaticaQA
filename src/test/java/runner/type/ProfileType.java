package runner.type;

import runner.BaseTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

public enum ProfileType {

    DEFAULT("https://ref.eteam.work"),
    MARKETPLACE("https://ref.eteam.work");

    private static Properties credentials;
    static {
        renewCredentials();
    }

    private final String userNameKey = getName() + ".username";
    private final String passwordKey = getName() + ".password";

    private final String url;

    ProfileType(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUserName(String userName) {
        credentials.setProperty(userNameKey, userName);
    }

    public String getUserName() {
        return credentials.getProperty(userNameKey);
    }

    public void setPassword(String password) {
        credentials.setProperty(passwordKey, password);
    }

    public String getPassword() {
        return credentials.getProperty(passwordKey);
    }

    public String getCredentialUrl() {
        return getUrl() + "/next_tester.php";
    }

    public String getName() {
        return this.name().toLowerCase();
    }

    public static void renewCredentials() {
        ProfileType.credentials = getCredentials();
    }

    private static Properties getCredentials() {
        Properties properties = new Properties();
        try {
            if (!BaseTest.isRemoteWebDriver()) {
                InputStream inputStream = ProfileType.class.getClassLoader().getResourceAsStream("local.properties");
                if (inputStream == null) {
                    throw new RuntimeException("Copy and paste the local.properties.TEMPLATE file to local.properties");
                }

                properties.load(inputStream);
                return properties;
            }
        } catch (IOException ignore) {}

        if (BaseTest.isRemoteWebDriver() || Boolean.parseBoolean(properties.getProperty("serverCredentials"))) {
            for (ProfileType profile : ProfileType.values()) {
                try {
                    HttpURLConnection con = (HttpURLConnection) new URL(profile.getCredentialUrl()).openConnection();
                    try {
                        con.setRequestMethod("GET");
                        if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                            String response = in.readLine();
                            String[] responseArray = response.split(";");

                            properties.setProperty(profile.userNameKey, responseArray[0]);
                            properties.setProperty(profile.passwordKey, responseArray[1]);
                        }
                    } finally {
                        con.disconnect();
                    }

                } catch (IOException ignore) {}
            }
        }

        return properties;
    }
}
