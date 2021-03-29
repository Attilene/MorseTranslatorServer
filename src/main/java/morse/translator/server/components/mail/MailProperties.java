package morse.translator.server.components.mail;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * Settings for connecting to the smtp server and sending a mails on emails addresses by this smtp server
 *
 * @see     Sender
 * @author  Artem Bakanov aka Attilene
 */
@Configuration
public class MailProperties {
    /**
     * Configuration method for setting properties for Spring Mail service
     *
     * @return  new JavaMailSender instance
     */
    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.msndr.net");
        mailSender.setPort(25);
        mailSender.setUsername(System.getenv("MAIL_FROM"));
        mailSender.setPassword(System.getenv("MAIL_PASSWORD"));

        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.ssl.enable", false);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.debug", false);
        mailSender.setJavaMailProperties(properties);

        return mailSender;
    }
}
