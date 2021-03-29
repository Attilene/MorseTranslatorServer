package morse.translator.server.components.mail;

import morse.translator.server.utils.logger.LogType;
import morse.translator.server.utils.logger.LoggerUtil;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Creating methods for sending a mails on emails addresses
 *
 * @see     MailProperties
 * @author  Artem Bakanov aka Attilene
 */
@Component
public class Sender {
    private static final Logger LOGGER_ERROR = LoggerUtil.getLogger(LogType.ERROR);

    /**
     * Loading the initial settings for sending mails
     */
    private static final ApplicationContext CONTEXT = new AnnotationConfigApplicationContext(MailProperties.class);

    /**
     * The email address from which the messages will be sent
     */
    private static final String FROM = System.getenv("MAIL_FROM");

    /**
     * Sending simple message to email address
     *
     * @param  to       the email address to which the mail will be sent
     * @param  subject  email title
     * @param  text     email text body
     */
    public void sendSimpleMessage(String to, String subject, String text) {
        Thread thread = new Thread(() -> {
            JavaMailSender emailSender = CONTEXT.getBean(JavaMailSender.class);
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(FROM);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            emailSender.send(message);
        });
        thread.start();
    }

    /**
     * Sending text from file to email address
     *
     * @param  to       the email address to which the mail will be sent
     * @param  subject  email title
     * @param  text     email body
     * @param  html     true, if it is an html file, or false, if otherwise
     */
    public void sendMessageWithTextFromFile(String to, String subject, String text, boolean html) {
        Thread thread = new Thread(() -> {
            JavaMailSender emailSender = CONTEXT.getBean(JavaMailSender.class);
            MimeMessage message = emailSender.createMimeMessage();
            try {
                MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
                helper.setFrom(FROM);
                helper.setTo(to);
                helper.setSubject(subject);
                helper.setText(text, html);

                emailSender.send(message);
            } catch (MessagingException e) {
                LOGGER_ERROR.error("Failed sending a mail to " + to, e);
            }
        });
        thread.start();
    }
}
