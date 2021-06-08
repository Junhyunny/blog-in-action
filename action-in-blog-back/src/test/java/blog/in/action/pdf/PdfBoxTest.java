package blog.in.action.pdf;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.junit.jupiter.api.Test;

public class PdfBoxTest {

    private Log log = LogFactory.getLog(this.getClass());

    private String[][] CsvColumns = new String[][]
        {
            {"Material Type", "GAS"},
            {"Vendor", "KC INDUSTRIAL"},
            {"Vendor Code", "E0VT"},
            {"Shipping Date", ""},
            {"B/L No", "1"},
            {"P/O No", "1"},
            {"Material", "CO 47.5L_CGA350"},
            {"Grade", "4N5"},
            {"Total Quantity", "1"},
            {"Invoice No", "1"},
            {"VENDOR_COMMENT", ""},
            {"Manufacture Date", "충전일(Filled Date)"},
            {"Test Date", "분석일(Analyzed Date)"},
            {"Spec No", ""},
            {"Lot No", "로트번호(LOT Number)"},
            {"Lot Quantity", ""},
            {"Cylinder No", "용기번호(Cylinder Number)"},
            {"Cylinder Size", "47.5L"},
            {"CYLINDER DATE", "확인필요"},
            {"Cylinder Material", "AA 6061 T6"},
            {"Cylinder Maker", "LUXFER"},
            {"Valve Size", ""},
            {"Valve Material", "AlSl-316L"},
            {"Valve Maker", "ROTAREX"},
            {"Valve Type", "CGA350"},
            {"Contents", "7.65"},
            {"Cpressure", "2041"},
            {"F_PURITY", "99.995"},
            {"F_LIFE TIME", "12"},
            {"F_GC_COUNT", "0"},
            {"F_H2", "H2"},
            {"F_O2+AR", "O2+Ar"},
            {"F_N2", "N2"},
            {"F_CH4", "CH4"},
            {"F_CO2", "CO2"},
            {"F_NI(CO)4", "Ni(CO)4"},
            {"F_FE(CO)5", "Fe(CO)5"},
            {"F_H2O", "H2O"},
            {"F_AL", "Al"},
            {"F_CA", "Ca"},
            {"F_CD", "Cd"},
            {"F_CR", "Cr"},
            {"F_CU", "Cu"},
            {"F_COBALT", "Cο"},
            {"F_FE", "Fe"},
            {"F_K", "K"},
            {"F_MG", "Mg"},
            {"F_MN", "Mn"},
            {"F_MO", "Mo"},
            {"F_NA", "Na"},
            {"F_NI", "Ni"},
            {"F_PB", "Pb"},
            {"F_TI", "Ti"},
            {"F_W", "W"},
            {"F_ZN", "Zn"},
            {"F_TOTAL METAL", "Total Metal"}
        };

    private String[] keys = {"충전일(Filled Date)",
        "분석일(Analyzed Date)",
        "로트번호(LOT Number)",
        "용기번호(Cylinder Number)",
        "확인 필요"};

    private String[] analysisKeys = {
        "H2",
        "O2+Ar",
        "N2",
        "CH4",
        "CO2",
        "Ni(CO)4",
        "Fe(CO)5",
        "H2O",
        "Al",
        "Ca",
        "Cd",
        "Cr",
        "Cu",
        "Cο",
        "Fe",
        "K",
        "Mg",
        "Mn",
        "Mo",
        "Na",
        "Ni",
        "Pb",
        "Ti",
        "W",
        "Zn",
        "Total Metal"
    };

    @Test
    public void test() throws IOException {
        List<String> keyList = new ArrayList<>();
        for (String key : keys) {
            keyList.add(key);
        }
        List<String> analysisList = new ArrayList<>();
        for (String key : analysisKeys) {
            analysisList.add(key);
        }
        Map<String, String> keyValueMap = new HashMap<>();
        try (PDDocument document = PDDocument.load(new File("C:\\Users\\kang3\\Desktop\\CO COA - KCI(210521).pdf"))) {
            document.getClass();
            if (!document.isEncrypted()) {
                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                stripper.setSortByPosition(true);
                PDFTextStripper tStripper = new PDFTextStripper();
                tStripper.setSortByPosition(true);
                String pdfFileInText = tStripper.getText(document);
                //System.out.println("Text:" + st);
                // split by whitespace
                String lines[] = pdfFileInText.split("\\r?\\n");
                for (String line : lines) {
                    Iterator<String> it = keyList.iterator();
                    while (it.hasNext()) {
                        String key = it.next();
                        if (line.contains(key)) {
                            int startPos = line.indexOf(key);
                            String subStr = line.substring(startPos + key.length() + 1);
                            int pos = subStr.indexOf(" ");
                            String value = null;
                            if (pos > -1) {
                                value = subStr.substring(0, pos).replaceAll("-", "");
                            } else {
                                value = subStr.replaceAll("-", "");
                            }
                            keyValueMap.put(key, value);
                            it.remove();
                        }
                    }
                    Iterator<String> analysisIt = analysisList.iterator();
                    while (analysisIt.hasNext()) {
                        String key = analysisIt.next();
                        if (line.contains(key) && (line.contains("ppmv") || line.contains("ppbw"))) {
                            String subStr = line.substring(key.length());
                            int startPos = subStr.indexOf(".");
                            int endPos = subStr.indexOf("pp");
                            subStr = subStr.substring(startPos + 1, endPos);
                            int pos = subStr.indexOf(" ");
                            String value = subStr.substring(pos).replaceAll("[^0-9.]", "");
                            keyValueMap.put(key, value.trim());
                            analysisIt.remove();
                        }
                    }
                    log.info(line);
                }
            }
        } catch (Exception e) {

        }
    }
}
