package morse.translator.server.test.security;

import morse.translator.server.utils.security.CryptoUtil;
import org.junit.jupiter.api.Test;


public class CryptoSecurityTest {
    @Test
    void hashing() {
        try {
            System.out.println("Running hashing test...");
            for (int i = 0; i < 100; i++)
                System.out.println(CryptoUtil.createHash("qwerty123#", CryptoUtil.createSalt()));
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    @Test
    void encrypt() {
        boolean failure = false;
        try {
            System.out.println("Running encrypt test...");
            for (int i = 0; i < 100; i++) {
                String password = String.valueOf(i);
                String hash = CryptoUtil.createHash(password, CryptoUtil.createSalt());
                String secondHash = CryptoUtil.createHash(password, CryptoUtil.createSalt());
                if (hash.equals(secondHash)) {
                    System.out.println("Failure: two hashes are equal!");
                    failure = true;
                }
                String wrongPassword = String.valueOf(i + 1);
                if (CryptoUtil.checkPassword(wrongPassword, hash)) {
                    System.out.println("Failure: wrong password accepted!");
                    failure = true;
                }
                if (!CryptoUtil.checkPassword(password, hash)) {
                    System.out.println("Failure: good password not accepted!");
                    failure = true;
                }
            }
            if (failure) System.out.println("Tests failed!");
            else System.out.println("Tests passed!");
        } catch(Exception e) {
            System.out.println("Error: " + e);
        }
    }
}
