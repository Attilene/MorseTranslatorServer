package morse.translator.server.components.mail;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailProperties {
    @Bean
    public JavaMailSender getJavaMailSender() {
        String os = System.getProperty("os.name").toLowerCase();

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.mail.ru");
        if (os.contains("win"))
            mailSender.setPort(465);
        else if (os.contains("nix") || os.contains("nux"))
            mailSender.setPort(25);
        mailSender.setUsername(System.getenv("MAIL_FROM"));
        mailSender.setPassword(System.getenv("MAIL_PASSWORD"));

        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        mailSender.setJavaMailProperties(properties);

        return mailSender;
    }
}
