package action.in.blog.security.repository;

import action.in.blog.security.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@DataJpaTest
public class AuthRepositoryTest {

    @Autowired
    AuthRepository authRepository;

    PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Test
    public void givenUserNameJunhyunny_whenFindById_thenReturnUserEntity() {

        Optional<User> optionalUser = authRepository.findById("Junhyunny");
        User user = optionalUser.orElse(null);

        assertThat(user, notNullValue());
        assertThat(user.getUserName(), equalTo("Junhyunny"));
        assertThat(passwordEncoder.matches("123", user.getPassword()), equalTo(true));
        assertThat(user.getAuthority(), equalTo("ROLE_ADMIN"));
    }
}
