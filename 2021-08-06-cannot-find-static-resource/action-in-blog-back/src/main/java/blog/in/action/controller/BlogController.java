package blog.in.action.controller;

import static blog.in.action.controller.BarcodeUtil.createBarcodeImage;
import blog.in.action.dto.Barcode;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Log4j2
@Controller
public class BlogController {

    private String getServerUrl(HttpServletRequest request) {
        return new StringBuffer("http://").append(request.getServerName()).append(":").append(request.getServerPort()).toString();
    }

    @GetMapping(value = {"", "/"})
    public String index(Model model) {
        model.addAttribute("imagePath", null);
        model.addAttribute("staticBarcode", new Barcode());
        model.addAttribute("dynamicBarcode", new Barcode());
        return "image";
    }

    @PostMapping(value = {"/static"})
    public String staticFolder(HttpServletRequest request, Model model) {
        String serverUrl = getServerUrl(request);
        String filePath = "/static/images";
        model.addAttribute("imagePath", serverUrl + filePath + "/TEST.png");
        model.addAttribute("staticBarcode", new Barcode());
        model.addAttribute("dynamicBarcode", new Barcode());
        return "image";
    }

    @PostMapping(value = {"/static/barcode"})
    public String barcodeInStaticFolder(HttpServletRequest request, Model model, @ModelAttribute Barcode barcode) {
        String serverUrl = getServerUrl(request);
        String filePath = "/static/images";
        String fileName = createBarcodeImage("./src/main/resources" + filePath, barcode.getValue());
        model.addAttribute("staticBarcode", new Barcode());
        model.addAttribute("dynamicBarcode", new Barcode());
        model.addAttribute("imagePath", serverUrl + filePath + "/" + fileName);
        return "image";
    }

    @PostMapping(value = "/extra/barcode")
    public String barcodeInExtraFolder(HttpServletRequest request, Model model, @ModelAttribute Barcode barcode) {
        String serverUrl = getServerUrl(request);
        String filePath = "/images";
        String fileName = createBarcodeImage("." + filePath, barcode.getValue());
        model.addAttribute("staticBarcode", new Barcode());
        model.addAttribute("dynamicBarcode", new Barcode());
        model.addAttribute("imagePath", serverUrl + filePath + "/" + fileName);
        return "image";
    }
}

@Log4j2
class BarcodeUtil {

    private final static int dpi = 100;

    public static String createBarcodeImage(String filePath, String value) {
        String filName = UUID.randomUUID() + ".png";
        Code128Bean bean = new Code128Bean();
        bean.setModuleWidth(UnitConv.in2mm(3.0f / dpi));
        bean.doQuietZone(false);
        File folder = new File(filePath);
        if (!folder.exists()) {
            folder.mkdir();
        }
        String filePathAndName = filePath + "/" + filName;
        File outputFile = new File(filePathAndName);
        try (OutputStream out = new FileOutputStream(outputFile)) {
            BitmapCanvasProvider canvas = new BitmapCanvasProvider(out, "image/x-png", dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);
            bean.generateBarcode(canvas, value);
            canvas.finish();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return filName;
    }
}
