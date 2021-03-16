package morse.translator.server.logger;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class LoggerUtil {
    private static final HashMap<String, Logger> loggers = new HashMap<>();
    private static final Logger errorLogger = LoggerUtil.getLogger(LogType.ERROR);

    public static Logger getLogger(String name) {
        if (loggers.containsKey(name)) {
            return loggers.get(name);
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
                Objects.requireNonNull(errorLogger).error(e.getMessage(), e);
            }
            loggers.put(name, logger);
            return logger;
        }
    }
}
