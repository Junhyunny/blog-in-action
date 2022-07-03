package blog.in.action.barcode;

import lombok.extern.log4j.Log4j2;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.io.OutputStream;

@Log4j2
@Component
public class DefaultBarcodeImageFacade implements BarcodeImageFacade {

    private final static int dpi = 100;

    @Override
    public void generateBarcodeImage(OutputStream outputStream, String value) {
        Code128Bean bean = new Code128Bean();
        bean.setModuleWidth(UnitConv.in2mm(3.0f / dpi));
        bean.doQuietZone(false);
        try (OutputStream out = outputStream) {
            BitmapCanvasProvider canvas = new BitmapCanvasProvider(out, "image/x-png", dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);
            bean.generateBarcode(canvas, value);
            canvas.finish();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
