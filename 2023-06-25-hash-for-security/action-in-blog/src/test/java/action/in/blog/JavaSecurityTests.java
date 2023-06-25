package action.in.blog;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

class JavaSecurityTests {

    Logger logger = LoggerFactory.getLogger(JavaSecurityTests.class);

    private String getSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return getStringFromBytes(salt);
    }

    private String getStringFromBytes(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

    private String getDigest(String password, String salt) throws NoSuchAlgorithmException {
        var messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] temp = null;
        for (int i = 0; i < 5; i++) {
            String saltedPassword = password + salt;
            messageDigest.update(saltedPassword.getBytes(StandardCharsets.UTF_8));
            temp = messageDigest.digest();
        }
        return getStringFromBytes(temp);
    }

    @Test
    void message_digest_test() throws NoSuchAlgorithmException {

        var password = "Hello World";
        var salt = getSalt();
        var digest = getDigest(password, salt);


        logger.info(salt);
        logger.info(digest);
    }
}
