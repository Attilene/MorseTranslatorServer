package morse.translator.server.controllers;

import morse.translator.server.components.mail.Sender;
import morse.translator.server.dbms.models.Password;
import morse.translator.server.dbms.models.User;
import morse.translator.server.dbms.repositories.UserRepository;
import morse.translator.server.dbms.services.UserService;
import morse.translator.server.utils.logger.LogType;
import morse.translator.server.utils.logger.LoggerUtil;
import morse.translator.server.utils.security.CryptoUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <p>Controller for processing requests for mail sending</p>
 * Supported operations: password recovery
 *
 * @author  Artem Bakanov aka Attilene
 */
@RestController
public class MailController {
    private static final Logger LOGGER_MAIL_SENDER = LoggerUtil.getLogger(LogType.MAIL_SENDER);
    private static final Logger LOGGER_CONTROLLER = LoggerUtil.getLogger(LogType.CONTROLLER);
    private static final Logger LOGGER_ERROR = LoggerUtil.getLogger(LogType.ERROR);

    /**
     * Server address and port
     */
    private static final String ROOT_URL = "http://localhost:22222";

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
            User user = userService.getByEmail(email);
            if (user != null) {
                Sender sender = new Sender();
                sender.sendSimpleMessage(user.getEmail(),
                        "Восстановление пароля: подтверждение",
                        "Перейдите по следующей ссылке, чтобы создать " +
                                "новый пароль для входа в приложение Morse Translator\n\n" +
                                ROOT_URL + "/enter/change_password?sec_key=" +
                                user.getPassword().getSalt() + user.getId() +
                                "\n\n\nЕсли вы не регистрировались в приложении Morse Translator, " +
                                "то проигнорируйте данное сообщение"
                );

                LOGGER_MAIL_SENDER.info("Mail with confirm password change has been sent to " + user.getEmail());
                return new ResponseEntity<>("mail_send_success", HttpStatus.OK);
            } else throw new Exception();
        } catch (Exception e) {
            LOGGER_ERROR.error("Failed sending a mail with confirm password change", e);
            return new ResponseEntity<>("mail_send_failed", HttpStatus.OK);
        }
    }

    /**
     * Method for sending mails to user`s email with new password
     *
     * @param   sec_key  user`s secret key for sending a new password
     * @return           "New password send on email", if mail was sent successfully,
     *                   or HTTP status "400", if mail was sent failed
     */
    @RequestMapping("/enter/change_password")
    public ResponseEntity<?> changePasswordRecovery(@RequestParam String sec_key) {
        UserService userService = new UserService(userRepository);
        Long id = CryptoUtil.getIdFromSecretKey(sec_key);
        try {
            User user = userService.getById(id);
            if (user != null && sec_key.contains(user.getPassword().getSalt())) {
                Password password = user.getPassword();
                String newPassword = CryptoUtil.generatingNewPassword(8);
                byte[] newSalt = CryptoUtil.createSalt();
                password.setHash(CryptoUtil.createHash(newPassword, newSalt));
                password.setSalt(CryptoUtil.toHex(newSalt));
                user.setPassword(password);
                userService.updateUser(user);
                LOGGER_CONTROLLER.info("New password assigned to the user with id " + id);

                Sender sender = new Sender();
                sender.sendSimpleMessage(user.getEmail(),
                        "Восстановление пароля: новый пароль",
                        "Новый пароль для входа в приложение Morse Translator: " + newPassword +
                                "\n\n\nЕсли вы не регистрировались в приложении Morse Translator, " +
                                "то проигнорируйте данное сообщение"
                );

                LOGGER_MAIL_SENDER.info("Mail with a new password has been sent to " + user.getEmail());
                return new ResponseEntity<>("New password send on email", HttpStatus.OK);
            } else throw new Exception();
        } catch (Exception e) {
            LOGGER_ERROR.error("Failed sending a mail with a new password", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
