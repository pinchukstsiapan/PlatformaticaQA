import java.util.UUID;

public abstract class TestUtils {

    public static String getUUID() {
        return new UUID((long)(Math.random() * 1000000000), (long)(Math.random() * 1000000000)).toString();
    }

}
