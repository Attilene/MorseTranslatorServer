package morse.translator.server.test;

import morse.translator.server.ServerApplication;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ServerApplicationTest {
    @Test
    void main() {
        assertDoesNotThrow(() -> ServerApplication.main(new String[0]));
    }
}
