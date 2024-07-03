package blog.in.action.util;

import lombok.extern.log4j.Log4j2;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

@Log4j2
public class BarcodeUtil {

    private final static int dpi = 100;

    public static String createBarcodeImage(String filePath, String value) {
        File output = createFileInto(filePath);
        try (OutputStream out = new FileOutputStream(output)) {
            generateBarcode(
                    createCanvasProvider(out),
                    value
            );
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return output.getName();
    }

    private static BitmapCanvasProvider createCanvasProvider(OutputStream out) {
        return new BitmapCanvasProvider(
                out,
                "image/x-png",
                dpi,
                BufferedImage.TYPE_BYTE_BINARY,
                false,
                0
        );
    }

    private static File createFileInto(String filePath) {
        String filename = UUID.randomUUID() + ".png";
        File folder = new File(filePath);
        if (!folder.exists()) {
            log.info("create file path({}) - {}", filePath, folder.mkdir());
        }
        String filePathAndName = filePath + "/" + filename;
        return new File(filePathAndName);
    }

    private static void generateBarcode(
            BitmapCanvasProvider canvasProvider,
            String value
    ) throws IOException {
        Code128Bean bean = new Code128Bean();
        bean.setModuleWidth(UnitConv.in2mm(3.0f / dpi));
        bean.doQuietZone(false);
        bean.generateBarcode(canvasProvider, value);
        canvasProvider.finish();
    }
}
