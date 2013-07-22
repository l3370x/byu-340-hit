package gui.batches;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import com.itextpdf.text.*;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.*;

import common.util.DateUtils;
import core.model.Item;
import core.model.exception.ExceptionHandler;

public class ItemLabelController {

    private static int printCount = 1;
    private static final int COLUMNS = 4;
    private static final String BASE_FILE_NAME = ItemLabelController.class
            .getClassLoader().getResource(".").getPath();

    public static void createDocument(Item... items) {
        try {
            File outFile = initOutputFile();
            Document document = new Document(PageSize.LETTER);
            PdfWriter pdfWriter = PdfWriter.getInstance(document,
                    new FileOutputStream(outFile, true));
            document.open();
            formatDocument(document, pdfWriter.getDirectContent(), items);
            document.close();
            displayDocument(outFile);
        } catch (FileNotFoundException | DocumentException e) {
            ExceptionHandler.TO_USER.reportException(e,
                    "Can't print bar code labels");
            ExceptionHandler.TO_LOG.reportException(e,
                    "Can't print bar code labels");
        }
    }

    private static void formatDocument(Document document,
            PdfContentByte contentByte, Item... items) {
        try {
            PdfPTable table = new PdfPTable(COLUMNS);
            int remainingCells = 4 - (items.length % COLUMNS);
            for (Item item : items) {
                renderBarCode(item, table, contentByte);
            }
            for (int i = 0; i < remainingCells; i++) {
                PdfPCell blankCell = new PdfPCell();
                table.addCell(blankCell);
            }
            document.add(table);
        } catch (DocumentException e) {
            ExceptionHandler.TO_USER.reportException(e,
                    "Can't print bar code labels");
            ExceptionHandler.TO_LOG.reportException(e,
                    "Can't print bar code labels");
        }
    }

    private static void renderBarCode(Item item, PdfPTable table,
            PdfContentByte contentByte) {
        BarcodeEAN barCode = new BarcodeEAN();
        barCode.setCodeType(Barcode.UPCA);
        barCode.setCode(item.getBarCode().getValue());
        barCode.setAltText(item.getProduct().getDescription());
        Image img = barCode.createImageWithBarcode(contentByte, null, null);
        PdfPTable nestedTable = new PdfPTable(1);
        Paragraph p = setDescriptionFont(item.getProduct().getDescription());
        PdfPCell nestedCell = new PdfPCell(new Phrase(p));
        nestedCell.setBorder(Rectangle.NO_BORDER);
        nestedTable.addCell(nestedCell);
        nestedCell = new PdfPCell(img);
        nestedCell.setBorder(Rectangle.NO_BORDER);
        nestedTable.addCell(nestedCell);
        table.addCell(nestedTable);
    }

    private static Paragraph setDescriptionFont(String description) {
        Paragraph p = null;
        p = new Paragraph(description);
        Font font = new Font(FontFamily.HELVETICA, 6, Font.NORMAL, BaseColor.BLACK);
        return p;
    }

    private static File initOutputFile() {
        File outFile = null;
        try {
            Date date = DateUtils.removeTimeFromDate(DateUtils.currentDate());
            String dateString = DateUtils.formatDate(date);
            dateString = dateString.replace("/", "_");

            String fileName = BASE_FILE_NAME.replace("build/", "");
            outFile = new File(fileName + "printouts/labels/", dateString + "_"
                    + printCount + ".pdf");
            outFile.getParentFile().mkdirs();

            if (!outFile.exists()) {
                outFile.createNewFile();
            }

            printCount++;
        } catch (IOException e) {
            ExceptionHandler.TO_USER.reportException(e,
                    "Can't print bar code labels");
            ExceptionHandler.TO_LOG.reportException(e,
                    "Can't print bar code labels");
        }

        return outFile;
    }

    private static void displayDocument(File file) {
        try {
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            ExceptionHandler.TO_USER.reportException(e,
                    "Can't print bar code labels");
            ExceptionHandler.TO_LOG.reportException(e,
                    "Can't print bar code labels");
        }
    }

    /**
     * Don't allow construction
     */
    private ItemLabelController() {
    }
}
