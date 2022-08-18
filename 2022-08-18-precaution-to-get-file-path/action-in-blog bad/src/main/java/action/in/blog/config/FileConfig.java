package action.in.blog.config;

import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.net.URISyntaxException;
import java.util.function.Supplier;

@Log4j2
public class FileConfig {

    private static final String webResourceDir = "/WEB-INF/";
    private static final String configPath = "/WEB-INF/config/security.properties";

    private Supplier<String> projectPathSupplier = () -> {
        String projectPath = "";
        try {
            projectPath = getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        } catch (URISyntaxException e) {
            log.error(e);
        }
        return projectPath;
    };

    public FileConfig() {
    }

    public FileConfig(Supplier<String> projectPathSupplier) {
        this.projectPathSupplier = projectPathSupplier;
    }

    private boolean isFile(String filePath) {
        return new File(filePath).isFile();
    }

    private String getFilePath() {
        String filePath = null;
        String path = projectPathSupplier.get();
        log.info(path);
        int index = path.indexOf(webResourceDir);
        if (index > -1) {
            filePath = path.substring(0, index) + configPath;
            log.info(filePath);
        }
        return filePath;
    }

    public String getConfigPath() {
        String filePath = getFilePath();
        if (filePath != null && isFile(filePath)) {
            return filePath;
        }
        return null;
    }
}
