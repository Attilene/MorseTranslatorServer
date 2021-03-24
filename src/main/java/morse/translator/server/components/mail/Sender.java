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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


@Component
public class Sender {
    private static final Logger LOGGER_ERROR = LoggerUtil.getLogger(LogType.ERROR);

    private final ApplicationContext context = new AnnotationConfigApplicationContext(MailProperties.class);
    private static final String FROM = System.getenv("MAIL_FROM");

    public void sendSimpleMessage(String to, String subject, String text) {
        Thread thread = new Thread(() -> {
            JavaMailSender emailSender = context.getBean(JavaMailSender.class);
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(FROM);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            emailSender.send(message);
        });
        thread.start();
    }

    public void sendMessageWithAttachment(String to, String subject, String pathAttachment) {
        Thread thread = new Thread(() -> {
            JavaMailSender emailSender = context.getBean(JavaMailSender.class);
            MimeMessage message = emailSender.createMimeMessage();
            try {
                MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
                helper.setFrom(FROM);
                helper.setTo(to);
                helper.setSubject(subject);
                helper.setText(new String(Files.readAllBytes(Paths.get(pathAttachment))), true);

                emailSender.send(message);
            } catch (IOException | MessagingException e) {
                LOGGER_ERROR.error("Failed sending a mail to " + to, e);
            }
        });
        thread.start();
    }
}
