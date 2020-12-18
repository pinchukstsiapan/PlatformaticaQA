package runner;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerUtils {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    private static final Logger logger = Logger.getLogger("ColoredLogger");

    public void log(Level level, String message) {
        logger.log(level, message);
    }

    public void log(String message) {
        logger.info(message);
    }

    public void logRed(String message) {
        logger.log(Level.INFO, ANSI_RED + message + ANSI_RESET);
    }

    public void logYellow(String message) {
        logger.log(Level.INFO, ANSI_YELLOW + message + ANSI_RESET);
    }

    public void logGreen(String message) {
        logger.log(Level.INFO, ANSI_GREEN + message + ANSI_RESET);
    }

    public void logInfo(String message) {
        logger.info(ANSI_GREEN + message + ANSI_RESET);
    }

    public void logError(String message) {
        logger.severe(ANSI_RED + message + ANSI_RESET);
    }

    public void logWarning(String message) {
        logger.warning(ANSI_YELLOW + message + ANSI_RESET);
    }
}
