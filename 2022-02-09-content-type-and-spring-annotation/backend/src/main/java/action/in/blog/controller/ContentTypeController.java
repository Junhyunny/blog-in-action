package action.in.blog.controller;

import action.in.blog.dto.ContentTypeDto;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin("*")
@RestController
public class ContentTypeController {

    @PostMapping("/nothing")
    public String nothing(ContentTypeDto contentTypeDto) {
        System.out.println(contentTypeDto);
        return "Data what server get via /nothing path: " + contentTypeDto;
    }

    @PostMapping("/model-attribute")
    public String modelAttribute(@ModelAttribute ContentTypeDto contentTypeDto) {
        System.out.println(contentTypeDto);
        return "Data what server get via /model-attribute path: " + contentTypeDto;
    }

    @PostMapping("/request-param")
    public String requestParam(@RequestParam("item") String item, @RequestParam(value = "file", required = false) MultipartFile multipartFile) {
        System.out.println(item);
        System.out.println(multipartFile);
        return "Data what server get via /request-param path: " + item + ", file: " + multipartFile;
    }

    @PostMapping("/request-body")
    public String requestBody(@RequestBody ContentTypeDto contentTypeDto) {
        System.out.println(contentTypeDto);
        return "Data what server get via /request-body path: " + contentTypeDto;
    }

    @PostMapping("/request-body-with-multi-value-map")
    public String requestBody(@RequestBody MultiValueMap<String, Object> multiValueMap) {
        System.out.println(multiValueMap);
        return "Data what server get via /request-body-with-multi-value-map path: " + multiValueMap;
    }
}
