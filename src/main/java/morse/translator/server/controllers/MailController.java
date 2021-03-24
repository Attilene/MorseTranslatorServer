package morse.translator.server.controllers;

import morse.translator.server.components.mail.Sender;
import morse.translator.server.dbms.repositories.UserRepository;
import morse.translator.server.dbms.services.UserService;
import morse.translator.server.utils.logger.LogType;
import morse.translator.server.utils.logger.LoggerUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>Controller for processing requests for mail sending</p>
 * Supported operations: password recovery
 *
 * @author  Artem Bakanov aka Attilene
 */
@RestController
public class MailController {
    private static final Logger LOGGER_MAIL_SENDER = LoggerUtil.getLogger(LogType.MAIL_SENDER);
    private static final Logger LOGGER_ERROR = LoggerUtil.getLogger(LogType.ERROR);

    /**
     * Repository for manipulating data in the users table
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Method for sending mails to user`s email for recovering password
     *
     * @param   email  user email
     * @return         "mail_send_success", if mail was sent successfully, or "mail_send_failed", if mail was sent failed
     */
    @PostMapping("/enter/check_email")
    public ResponseEntity<String> sendPasswordRecovery(@RequestParam String email) {
        UserService userService = new UserService(userRepository);
        try {
            if (userService.getByEmail(email) != null) {
                Sender sender = new Sender();
                sender.sendMessageWithAttachment(email,
                        "Восстановление пароля",
                        "src/main/resources/templates/RecoveryPassword.html"
                );
                LOGGER_MAIL_SENDER.info("Mail with confirm password change has been sent to " + email);
                return new ResponseEntity<>("mail_send_success", HttpStatus.OK);
            } else throw new Exception();
        } catch (Exception e) {
            LOGGER_ERROR.error("Failed sending a mail with confirm password change", e);
            return new ResponseEntity<>("mail_send_failed", HttpStatus.OK);
        }
    }
}
