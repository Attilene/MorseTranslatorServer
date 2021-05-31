package morse.translator.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Class for running the Morse translator server
 *
 * @author  Artem Bakanov aka Attilene
 * @since   15.0.2
 * @version 1.0
 */
@SpringBootApplication
@EnableJpaAuditing
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
public class ServerApplication {
    /**
     * Main method for running the project
     *
     * @param  args  project variables
     */
    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }
}
