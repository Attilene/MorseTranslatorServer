package morse.translator.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Class for running the Morse translator server
 *
 * @author  Artem Bakanov aka Attilene
 * @since   15.0.2
 * @version 1.0
 */
@SpringBootApplication
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
