package blog.in.action.controller;

import blog.in.action.dto.MultipartDto;
import blog.in.action.dto.MultipartListDto;
import blog.in.action.dto.MultipartMapDto;
import blog.in.action.dto.MultipartMapListDto;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin("*")
@RestController
public class FileController {

    @PostMapping(value = "/dto")
    public @ResponseBody
    String uploadFileInDto(@ModelAttribute MultipartDto dto) {
        MultipartFile multipartFile = dto.getFile();
        try (FileOutputStream writer = new FileOutputStream("./images/" + multipartFile.getOriginalFilename())) {
            writer.write(multipartFile.getBytes());
        } catch (Exception e) {
            return "upload fail";
        }
        return "upload success";
    }

    @PostMapping(value = "/dto/multipart/list")
    public @ResponseBody
    String uploadFileListInDto(@ModelAttribute MultipartListDto dto) {
        for (MultipartFile multipartFile : dto.getFiles()) {
            try (FileOutputStream writer = new FileOutputStream("./images/" + multipartFile.getOriginalFilename())) {
                writer.write(multipartFile.getBytes());
            } catch (Exception e) {
                return "upload fail";
            }
        }
        return "upload success";
    }

    @PostMapping(value = "/dto/multipart/map")
    public @ResponseBody
    String uploadFileMapInDto(@ModelAttribute MultipartMapDto dto) {
        Map<String, MultipartFile> files = dto.getFiles();
        for (String key : files.keySet()) {
            MultipartFile multipartFile = files.get(key);
            try (FileOutputStream writer = new FileOutputStream("./images/" + multipartFile.getOriginalFilename())) {
                writer.write(multipartFile.getBytes());
            } catch (Exception e) {
                return "upload fail";
            }
        }
        return "upload success";
    }

    @PostMapping(value = "/dto/multipart/map/list")
    public @ResponseBody
    String uploadFileMapListInDto(@ModelAttribute MultipartMapListDto dto) {
        Map<String, List<MultipartFile>> files = dto.getFiles();
        for (String key : files.keySet()) {
            List<MultipartFile> multipartFiles = files.get(key);
            for (MultipartFile multipartFile : multipartFiles) {
                try (FileOutputStream writer = new FileOutputStream("./images/" + multipartFile.getOriginalFilename())) {
                    writer.write(multipartFile.getBytes());
                } catch (Exception e) {
                    return "upload fail";
                }
            }
        }
        return "upload success";
    }
}