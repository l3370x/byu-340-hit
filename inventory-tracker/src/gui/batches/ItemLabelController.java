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
    private String fileName = ".";
    private Iterator<Item> itemList;
    private File outFile;
    private PdfWriter pdfWriter;
    private static int printCount = 1;

    private ItemLabelController() {

    }

    private static ItemLabelController instance;

    @Override
    public ItemLabelController getInstance() {
	if (instance == null)
	    instance = new ItemLabelController();
	return instance;
    }

    @Override
    public void createDocument(Iterator<Item> itemList) {
	try {
	    this.itemList = itemList;
	    Document document = new Document(PageSize.LETTER);
	    generateFileName();
	    pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(
		    outFile));
	    document.open();
	    formatDocument(document);
	    document.close();
	    displayDocument();
	} catch (FileNotFoundException | DocumentException e) {
            ExceptionHandler.TO_USER.reportException(e, "Can't print bar code labels");
            ExceptionHandler.TO_LOG.reportException(e, "Can't print bar code labels");
	}
    }

    @Override
    public void formatDocument(Document document) {
	try {
	    PdfPTable table = new PdfPTable(COLUMNS);
	    if (itemList.hasNext()) {
		generateBarCode(itemList.next(), table);
	    }
	    document.add(table);
	} catch (DocumentException e) {
            ExceptionHandler.TO_USER.reportException(e, "Can;t print bar code labels");
            ExceptionHandler.TO_LOG.reportException(e, "Can't print bar code labels");
	}
    }

    @Override
    public void generateBarCode(Item item, PdfPTable table) {
	BarcodeEAN barCode = new BarcodeEAN();
	barCode.setCodeType(Barcode.UPCA);
	barCode.setCode(item.getBarCode().getValue());
	barCode.setAltText(item.getProduct().getDescription());
	Image img = barCode.createImageWithBarcode(
		pdfWriter.getDirectContent(), null, null);
	PdfPCell cell = new PdfPCell(img);
	table.addCell(cell);
    }

    @Override
    public void generateFileName() {
	Date date = DateUtils.removeTimeFromDate(DateUtils.currentDate());
	String dateString = DateUtils.formatDate(date);
	outFile = new File(fileName, "printouts\\labels\\" + dateString + "_"
		+ printCount + ".pdf");
	printCount++;
    }

    @Override
    public void displayDocument() {
	try {
	    Desktop.getDesktop().open(new File(fileName));
	} catch (IOException e) {
            ExceptionHandler.TO_USER.reportException(e, "Can't print bar code labels");
            ExceptionHandler.TO_LOG.reportException(e, "Can't print bar code labels");
	}
    }

}
