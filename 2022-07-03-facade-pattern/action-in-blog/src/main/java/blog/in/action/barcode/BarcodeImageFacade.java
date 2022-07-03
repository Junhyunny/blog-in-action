package blog.in.action.barcode;

import java.io.OutputStream;

public interface BarcodeImageFacade {

    void generateBarcodeImage(OutputStream outputStream, String value);
}
