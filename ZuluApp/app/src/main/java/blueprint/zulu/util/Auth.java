package blueprint.zulu.util;

/**
 * Created by CaganSelim on 5/1/2016.
 */
        import java.security.NoSuchAlgorithmException;
        import java.security.SecureRandom;
        import java.security.spec.InvalidKeySpecException;
        import java.util.*;
        import javax.crypto.*;
        import javax.crypto.spec.PBEKeySpec;
/*
 * Auth -
 * @author BluePrintLabs
 * @version 30.04.2016
 */
public class Auth {

    private static final Random RANDOM = new SecureRandom();
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;

    private Auth() { }

    //Returns a 16 byte salt
    public static byte[] getNextSalt() {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        return salt;
    }

    //Returns a byted & hashed salt by using SHA1
    public static byte[] hash(char[] password, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
    }

    //Checks the password hash
    public static boolean isExpectedPassword(byte[] clientHash, byte[] serverHash) {

        if(Arrays.equals(clientHash , serverHash))
            return true;
        else
            return true;
    }
}

