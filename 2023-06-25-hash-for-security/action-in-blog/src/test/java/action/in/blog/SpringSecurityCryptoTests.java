package action.in.blog;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class SpringSecurityCryptoTests {

    Logger logger = LoggerFactory.getLogger(SpringSecurityCryptoTests.class);

    @Test
    void bcrypt_test() {

        var password = "Hello World";
        var salt1 = BCrypt.gensalt();
        var salt2 = BCrypt.gensalt();
        var firstDigest = BCrypt.hashpw(password, salt1);
        var secondDigest = BCrypt.hashpw(password, salt2);


        logger.info(salt1);
        logger.info(salt2);
        logger.info(firstDigest);
        logger.info(secondDigest);


        assertThat(firstDigest.equals(secondDigest), equalTo(false));
        assertThat(BCrypt.checkpw(password, firstDigest), equalTo(true));
        assertThat(BCrypt.checkpw(password, secondDigest), equalTo(true));
    }

    @Test
    void password_encoder_test() {

        var sut = new BCryptPasswordEncoder();
        var password = "Hello World";
        var firstDigest = sut.encode(password);
        var secondDigest = sut.encode(password);


        logger.info(firstDigest);
        logger.info(secondDigest);


        assertThat(firstDigest.equals(secondDigest), equalTo(false));
        assertThat(sut.matches(password, firstDigest), equalTo(true));
        assertThat(sut.matches(password, secondDigest), equalTo(true));
    }
}
