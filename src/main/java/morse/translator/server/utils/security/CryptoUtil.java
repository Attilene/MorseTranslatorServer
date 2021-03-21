package morse.translator.server.utils.security;

import morse.translator.server.utils.logger.LogType;
import morse.translator.server.utils.logger.LoggerUtil;
import org.apache.log4j.Logger;

import java.security.SecureRandom;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKeyFactory;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;


/**
 * Cryptographic class for password hashing and
 * password verification using the PBKDF2 algorithm
 *
 * @author  Artem Bakanov aka Attilene
 */
public final class CryptoUtil {
    private static final Logger LOGGER_ERROR = LoggerUtil.getLogger(LogType.ERROR);

    /**
     * Algorithm of hashing password string
     */
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";

    /**
     * Quantity of hashing iterations
     */
    private static final int ITERATIONS = 50000;

    /**
     * Length of hash string
     */
    private static final int HASH_BYTES = 256;

    /**
     * Length of salt string for hashing
     */
    private static final int SALT_BYTES = 64;

    private CryptoUtil() {}

    /**
     * Returns a salted PBKDF2 hash of the password.
     *
     * @param   password  password string to hash
     * @return            a salted PBKDF2 hash of the password
     */
    public static String createHash(String password, byte[] salt) {
        char[] passwordChar = password.toCharArray();
        byte[] hash = pbkdf2(passwordChar, salt, ITERATIONS, HASH_BYTES);
        return ITERATIONS + ":" + toHex(salt) + ":" + toHex(hash);
    }

    /**
     * Creates salt byte array.
     *
     * @return  salt byte array
     */
    public static byte[] createSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_BYTES];
        random.nextBytes(salt);
        return salt;
    }

    /**
     * Validates a password using a hash.
     *
     * @param   password  the password to check
     * @param   goodHash  the hash of the valid password
     * @return            true if the password is correct, false if not
     */
    public static boolean checkPassword(String password, String goodHash) {
        String[] params = goodHash.split(":");
        char[] passwordChar = password.toCharArray();

        int iterations = Integer.parseInt(params[0]);
        byte[] salt = fromHex(params[1]);
        byte[] hash = fromHex(params[2]);

        byte[] testHash = pbkdf2(passwordChar, salt, iterations, hash.length);
        return slowEquals(hash, testHash != null ? testHash : new byte[0]);
    }

    /**
     * Compares two byte arrays in length-constant time. This comparison method
     * is used so that password hashes cannot be extracted from an online
     * system using a timing attack and then attacked offline.
     *
     * @param   a  the first byte array
     * @param   b  the second byte array
     * @return     true if both byte arrays are the same, false if not
     */
    private static boolean slowEquals(byte[] a, byte[] b) {
        int diff = a.length ^ b.length;
        for(int i = 0; i < a.length && i < b.length; i++)
            diff |= a[i] ^ b[i];
        return diff == 0;
    }

    /**
     *  Computes the PBKDF2 hash of a password.
     *
     * @param   password    the password to hash.
     * @param   salt        the salt
     * @param   iterations  the iteration count (slowness factor)
     * @param   bytes       the length of the hash to compute in bytes
     * @return              the PBKDF2 hash of the password
     */
    private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes) {
        try {
            PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
            SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGORITHM);
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            LOGGER_ERROR.error("Error creating an encrypted key");
            return new byte[0];
        }
    }

    /**
     * Converts a string of hexadecimal characters into a byte array.
     *
     * @param   hex  the hex string
     * @return       the hex string decoded into a byte array
     */
    public static byte[] fromHex(String hex) {
        byte[] binary = new byte[hex.length() / 2];
        for(int i = 0; i < binary.length; i++)
            binary[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        return binary;
    }

    /**
     * Converts a byte array into a hexadecimal string.
     *
     * @param   array  the byte array to convert
     * @return         a length * 2 character string encoding the byte array
     */
    public static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0)
            return String.format("%0" + paddingLength + "d", 0) + hex;
        else
            return hex;
    }
}
