package action.in.blog.utils;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DefaultRandomKeyGenerator implements RandomKeyGenerator {
    @Override
    public String getRandomKey() {
        return UUID.randomUUID().toString();
    }
}
