package dev.ricecx.frostygamerzone.common;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingUtils {


    private static Logger logger = LoggerFactory.getLogger("FrostyGamerZone");
    private static boolean VERBOSE = false;

    private LoggingUtils() {
        throw new InstantiationError("This class cannot be instantiated");
    }


    /**
     * Send a DEBUG text
     * @param text Text to send
     */
    public static void debug(String ...text) {
        if(!VERBOSE) return;


        logger.info(String.join(" ", text));
    }
    /**
     * Log an info text
     * @param text Text to send
     */
    public static void info(String text) {
        logger.info(text);
    }

    /**
     * Log a warning message
     * @param text Text to send
     */
    public static void warn(String text) {
        logger.warn(text);
    }

    /**
     * Log an error
     * @param text Text to send
     */
    public static void error(String text) {
        logger.error(text);
    }



    /* Setters and Getters */

    public static boolean isVerbose() {
        return VERBOSE;
    }

    public static void setVerbose(boolean VERBOSE) {
        LoggingUtils.VERBOSE = VERBOSE;
        if(VERBOSE)
            LoggingUtils.info("Verbose mode has been enabled");
    }

    public static void toggleVerbose() {
        LoggingUtils.VERBOSE = !LoggingUtils.VERBOSE;
    }

    public static void setLogger(Logger logger) {
        LoggingUtils.logger = logger;
    }
}
