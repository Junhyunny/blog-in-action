package action.in.blog.domain;

import org.springframework.web.multipart.MultipartFile;

public record Profile(
        String name,
        int age,
        MultipartFile picture
) {
}
