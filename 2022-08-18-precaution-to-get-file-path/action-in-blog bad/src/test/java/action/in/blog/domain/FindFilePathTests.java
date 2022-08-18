package action.in.blog.domain;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class FindFilePathTests {

    String result = "/Users/junhyunk/Desktop/workspace/blog/blog-in-action/2022-08-18-precaution-to-get-file-path/action-in-blog bad";

    @Test
    public void find_path_using_paths_class() {
        String projectPath = Paths.get("").toAbsolutePath().toString();
        assertThat(projectPath, equalTo(result));
    }

    @Test
    public void find_path_using_system_property_method() {
        String projectPath = System.getProperty("user.dir");
        assertThat(projectPath, equalTo(result));
    }

    @Test
    public void find_path_using_file_systems_class() {
        String projectPath = FileSystems.getDefault().getPath("").toAbsolutePath().toString();
        assertThat(projectPath, equalTo(result));
    }
    
    @Test
    public void find_path_using_file_class() {
        String projectPath = new File("").getAbsolutePath();
        assertThat(projectPath, equalTo(result));
    }
}
