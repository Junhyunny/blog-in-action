package blog.in.action.controller;

import blog.in.action.model.Barcode;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Path;
import java.util.Objects;

import static blog.in.action.util.BarcodeUtil.createBarcodeImage;

@Log4j2
@Controller
public class BlogController {

    private final static ClassLoader classLoader = BlogController.class.getClassLoader();
    private final static String STATIC_IMAGE_PATH = "/static/images";
    private final static String STATIC_IMAGE_DIR = Objects.requireNonNull(classLoader.getResource("")).getPath() + STATIC_IMAGE_PATH;
    private final static String IMAGE_PATH = "/images";
    private final static String IMAGE_DIR = Path.of("./") + IMAGE_PATH;

    private String getServerUrl(HttpServletRequest request) {
        return "http://" +
                request.getServerName() +
                ":" +
                request.getServerPort();
    }

    private void createEmptyBarcode(Model model) {
        model.addAttribute("staticBarcode", new Barcode());
        model.addAttribute("dynamicBarcode", new Barcode());
    }

    @GetMapping(value = {"", "/"})
    public String index(Model model) {
        model.addAttribute("imagePath", null);
        createEmptyBarcode(model);
        return "image";
    }

    @PostMapping("/static")
    public String staticFolder(HttpServletRequest request, Model model) {
        model.addAttribute("imagePath", getServerUrl(request) + STATIC_IMAGE_PATH + "/sample.png");
        createEmptyBarcode(model);
        return "image";
    }

    @PostMapping("/static/barcode")
    public String barcodeInStaticFolder(
            HttpServletRequest request,
            Model model,
            @ModelAttribute Barcode barcode
    ) {
        String fileName = createBarcodeImage(STATIC_IMAGE_DIR, barcode.getValue());
        model.addAttribute("imagePath", getServerUrl(request) + STATIC_IMAGE_PATH + "/" + fileName);
        createEmptyBarcode(model);
        return "image";
    }

    @PostMapping("/extra/barcode")
    public String barcodeInExtraFolder(
            HttpServletRequest request,
            Model model,
            @ModelAttribute Barcode barcode
    ) {
        String fileName = createBarcodeImage(IMAGE_DIR, barcode.getValue());
        model.addAttribute("imagePath", getServerUrl(request) + IMAGE_PATH + "/" + fileName);
        createEmptyBarcode(model);
        return "image";
    }
}

