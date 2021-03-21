package morse.translator.server.utils.logger;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

/**
 * <p>Class for creating log files based on a pattern: </p>
 * "%d{dd.MM.yyyy HH:mm:ss} %-5p - %m%n"
 * @see LogType
 *
 * @author  Artem Bakanov aka Attilene
 */
public final class LoggerUtil {
    /**
     * Dictionary that stores loggers
     */
    private static final HashMap<String, Logger> LOGGERS = new HashMap<>();
    private static final Logger ERROR_LOGGER = LoggerUtil.getLogger(LogType.ERROR);

    /**
     * Logger factory for creating new loggers and adding their to LOGGERS
     *
     * @param   name  name of log file
     * @return  new logger
     */
    public static Logger getLogger(String name) {
        if (LOGGERS.containsKey(name)) {
            return LOGGERS.get(name);
        } else {
            Logger logger = Logger.getLogger(name);
            String fileName = "logs\\" + name + ".log";
            FileAppender appender;
            try {
                PatternLayout layout = new PatternLayout();
                layout.setConversionPattern("%d{dd.MM.yyyy HH:mm:ss} %-5p - %m%n");
                appender = new FileAppender(layout, fileName);
                logger.addAppender(appender);
            } catch (IOException e) {
                Objects.requireNonNull(ERROR_LOGGER).error(e.getMessage(), e);
            }
            LOGGERS.put(name, logger);
            return logger;
        }
    }
}
