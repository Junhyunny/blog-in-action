package action.in.blog.provider;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class AuthProviderImpl implements AuthProvider {

    private final Random random = new Random();

    @Override
    public void authenticate() {
        if (random.nextBoolean()) {
            throw new RuntimeException("client_id or client_secret is invalid");
        }
    }
}
