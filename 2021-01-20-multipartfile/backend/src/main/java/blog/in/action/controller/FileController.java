package blog.in.action.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.util.List;

@RestController
public class FileController {

    @GetMapping("/files")
    public void getFiles() {
        System.out.println("files");
    }

    @PostMapping(value = "/files")
    public @ResponseBody
    String uploadFiles(@RequestParam("files") List<MultipartFile> files) {
        String projectPath = Paths.get("").toAbsolutePath().toString();
        for (MultipartFile multipartFile : files) {
            try (FileOutputStream writer = new FileOutputStream(projectPath + "/images/" + multipartFile.getOriginalFilename())) {
                writer.write(multipartFile.getBytes());
            } catch (Exception e) {

            }
        }
        return "SUCCESS";
    }
}