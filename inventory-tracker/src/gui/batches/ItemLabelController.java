package gui.batches;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import common.util.DateUtils;
import core.model.Item;
import core.model.exception.ExceptionHandler;

public class ItemLabelController implements IItemLabelController {

    private static final int COLUMNS = 4;
    private String fileName = getClass().getClassLoader().getResource(".")
	    .getPath();
    private Iterator<Item> itemList;
    private File outFile;
    private PdfWriter pdfWriter;
    private static int printCount = 1;

    public ItemLabelController() {

    }

    @Override
    public void createDocument(Iterator<Item> itemList) {
	try {
	    this.itemList = itemList;
	    generateFileName();
	    Document document = new Document(PageSize.LETTER);
	    pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(
		    outFile, true));
	    document.open();
	    formatDocument(document);
	    document.close();
	    displayDocument();
	} catch (FileNotFoundException | DocumentException e) {
            ExceptionHandler.TO_USER.reportException(e, "Can't print bar code labels");
            ExceptionHandler.TO_LOG.reportException(e, "Can't print bar code labels");
	}
    }

    private void formatDocument(Document document) {
	try {
	    PdfPTable table = new PdfPTable(COLUMNS);
	    if (itemList.hasNext()) {
		generateBarCode(itemList.next(), table);
	    }
	    document.add(table);
	} catch (DocumentException e) {
            ExceptionHandler.TO_USER.reportException(e, "Can't print bar code labels");
            ExceptionHandler.TO_LOG.reportException(e, "Can't print bar code labels");
	}
    }

    private void generateBarCode(Item item, PdfPTable table) {
	BarcodeEAN barCode = new BarcodeEAN();
	barCode.setCodeType(Barcode.UPCA);
	barCode.setCode(item.getBarCode().getValue());
	barCode.setAltText(item.getProduct().getDescription());
	Image img = barCode.createImageWithBarcode(
		pdfWriter.getDirectContent(), null, null);
	PdfPCell cell = new PdfPCell(img);
	table.addCell(cell);
    }

    private void generateFileName() {
	try {
	    Date date = DateUtils.removeTimeFromDate(DateUtils.currentDate());
	    String dateString = DateUtils.formatDate(date);
	    dateString = dateString.replace("/", "_");
	    fileName = fileName.replace("build/", "");
	    outFile = new File(fileName + "printouts/labels/", dateString
		    + "_" + printCount + ".pdf");
	    outFile.getParentFile().mkdirs();
	    System.out.println(outFile);
	    if (!outFile.exists()) {
		System.out.println("File DNE. Create " + outFile);
		outFile.createNewFile();
	    }
	    printCount++;
	} catch (IOException e) {
            ExceptionHandler.TO_USER.reportException(e, "Can't print bar code labels");
            ExceptionHandler.TO_LOG.reportException(e, "Can't print bar code labels");
	}
    }

    private void displayDocument() {
	try {
	    Desktop.getDesktop().open(new File(fileName));
	} catch (IOException e) {
            ExceptionHandler.TO_USER.reportException(e, "Can't print bar code labels");
            ExceptionHandler.TO_LOG.reportException(e, "Can't print bar code labels");
	}
    }

}
