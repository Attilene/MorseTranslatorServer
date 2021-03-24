package morse.translator.server.utils.logger;

/**
 * Templates for log file names
 *
 * @see     LoggerUtil
 * @author  Artem Bakanov aka Attilene
 */
public final class LogType {
    /**
     * Template for error log file
     */
    public static final String ERROR = "error";

    /**
     * Template for log file of translator commands
     */
    public static final String TRANSLATOR = "translator";

    /**
     * Template for log file of controllers commands
     */
    public static final String CONTROLLER = "controller";

    /**
     * Template for log file of mail sender commands
     */
    public static final String MAIL_SENDER = "mail_sender";

    private LogType() {}
}
