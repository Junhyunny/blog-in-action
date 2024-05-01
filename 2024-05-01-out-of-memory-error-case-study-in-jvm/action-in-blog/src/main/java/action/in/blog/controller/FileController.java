package action.in.blog.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Objects;

@RestController
public class FileController {

//    private File convertToFile(MultipartFile multipartFile) {
//        File copiedFile = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
//        try (OutputStream os = new FileOutputStream(copiedFile)) {
//            os.write(multipartFile.getBytes());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return copiedFile;
//    }

    private File convertToFile(MultipartFile multipartFile) {
        File copiedFile = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try (
                InputStream is = multipartFile.getInputStream();
                OutputStream os = new FileOutputStream(copiedFile)
        ) {
            byte[] buffer = new byte[10240];
            int read;
            while ((read = is.read(buffer)) > 0) {
                os.write(buffer, 0, read);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return copiedFile;
    }

    @PostMapping("/file")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        var result = convertToFile(file);
        System.out.printf("file size: %s bytes\n", result.length());
        return "ok";
    }
}
