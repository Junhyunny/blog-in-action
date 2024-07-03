package blog.in.action.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.nio.file.Path;

@Slf4j
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        String currentAppPath = Path.of("./").toString();
        String imagePath = currentAppPath + "/images/";
        File imagesDirectory = new File(imagePath);
        if (!imagesDirectory.exists()) {
            log.info("create {} - {}", imagePath, imagesDirectory.mkdirs());
        }
        registry.addResourceHandler("/images/**").addResourceLocations("file:" + imagePath);
    }
}
