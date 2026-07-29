package action.in.blog.controller;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class ThumbnailController {

    private static final int THUMBNAIL_MAX_SIZE = 200;
    private static final double THUMBNAIL_QUALITY = 0.7;

    @GetMapping("/thumbnails")
    public void thumbnails(@RequestParam String filename) throws IOException {
        String projectRoot = System.getProperty("user.dir");
        Path inputPath = Paths.get(projectRoot, "src", "main", "resources", "static", filename);
        try (InputStream inputStream = Files.newInputStream(inputPath)) {
            Thumbnails.of(inputStream)
                    .size(THUMBNAIL_MAX_SIZE, THUMBNAIL_MAX_SIZE)
                    .keepAspectRatio(true)
                    .outputFormat("JPEG")
                    .outputQuality(THUMBNAIL_QUALITY)
                    .toOutputStream(new ByteArrayOutputStream());
        }
    }
}
