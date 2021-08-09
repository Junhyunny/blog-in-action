package blog.in.action.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.UUID;
import lombok.extern.log4j.Log4j2;
import org.krysalis.barcode4j.HumanReadablePlacement;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequestMapping("/api")
public class BlogController {

    private final static int dpi = 100;
    private final static String filePath = "./images";
    private final static String staticFilePath = "/images/";

    @GetMapping(value = {"", "/"})
    public String index(Model model) {
        model.addAttribute("imagePath", null);
        return "image";
    }

    @PostMapping(value = "/barcode")
    public String barcode(Model model, Map<String, Object> info) {
        String filName = UUID.randomUUID().toString();
        Code128Bean bean = new Code128Bean();
        bean.setMsgPosition(HumanReadablePlacement.HRP_NONE);
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
            canvas.finish();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return "image";
    }
}
