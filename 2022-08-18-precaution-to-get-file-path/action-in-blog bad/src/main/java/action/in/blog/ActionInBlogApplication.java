package action.in.blog;

import action.in.blog.config.FileConfig;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

@Log4j2
public class ActionInBlogApplication {

    public static void main(String[] args) {
        List<Supplier<String>> projectPathSuppliers = Arrays.asList(
                () -> Paths.get("").toAbsolutePath().toString(),
                () -> System.getProperty("user.dir"),
                () -> FileSystems.getDefault().getPath("").toAbsolutePath().toString(),
                () -> new File("").getAbsolutePath()
        );
        for (Supplier<String> supplier : projectPathSuppliers) {
            FileConfig fileConfig = new FileConfig(supplier);
            log.info(fileConfig.getConfigPath());
        }
    }
}
