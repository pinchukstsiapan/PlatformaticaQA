package runner;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class LoggerUtils {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";

    private static final Logger logger;
    static {
        try (FileInputStream fileInputStream = new FileInputStream("src/test/resources/log.properties")) {
            LogManager.getLogManager().readConfiguration(fileInputStream);
        } catch (IOException ex) {
            throw new ExceptionInInitializerError(ex);
        }

        logger =  Logger.getLogger("BaseLogger");
    }

    public static void log(Level level, String message) {
        logger.log(level, message);
    }

    public static void log(String message) {
        logger.info(message);
    }

    public static void logRed(String message) {
        logger.log(Level.INFO, ANSI_RED + message + ANSI_RESET);
    }

    public static void logYellow(String message) {
        logger.log(Level.INFO, ANSI_YELLOW + message + ANSI_RESET);
    }

    public static void logGreen(String message) {
        logger.log(Level.INFO, ANSI_GREEN + message + ANSI_RESET);
    }
}
