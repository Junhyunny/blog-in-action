package blog.in.action.controller;

import blog.in.action.barcode.BarcodeImageFacade;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.Optional;

@Log4j2
@Controller
public class BlogController {

    private final BarcodeImageFacade imageFacade;

    public BlogController(BarcodeImageFacade imageFacade) {
        this.imageFacade = imageFacade;
    }

    @GetMapping(value = {"", "/"})
    public String index() {
        return "image";
    }

    @GetMapping(value = {"/barcode", "/barcode/{value}"})
    public void barcodeInStaticFolder(ServletResponse response, @PathVariable Optional<String> value) throws IOException {
        imageFacade.generateBarcodeImage(response.getOutputStream(), value.orElse("DEFAULT"));
    }
}