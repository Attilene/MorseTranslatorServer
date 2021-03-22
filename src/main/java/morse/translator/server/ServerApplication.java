package morse.translator.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Class for running the Morse translator project
 *
 * @author  Artem Bakanov aka Attilene
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
