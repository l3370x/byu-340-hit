package gui.batches;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.istack.internal.logging.Logger;

import common.util.DateUtils;
import core.model.Item;

public class ItemLabelControler implements IItemLabelController {

    private Iterator<Item> itemList;
    private String fileName = ".";
    private File outFile;
    private static int COLUMNS = 4;

    @Override
    public void createDocument(Iterator<Item> itemList) {
	try {
	    this.itemList = itemList;
	    Document document = new Document(PageSize.LETTER);
	    generateFileName();
	    PdfWriter.getInstance(document, new FileOutputStream(outFile));
	    document.open();
	    formatDocument(document);
	    document.close();
	    displayDocument();
	} catch (FileNotFoundException | DocumentException e) {
	    Logger.getLogger(getClass()).logException(e, Level.SEVERE);
	    e.printStackTrace();
	}
    }

    @Override
    public void formatDocument(Document document) {
	PdfPTable table = new PdfPTable(COLUMNS);
	int row = 1;
	while(itemList.hasNext()) {
	    
	}
    }

    @Override
    public void generateBarCode(Item item) {
	// TODO Auto-generated method stub

    }

    @Override
    public void generateFileName() {
	Date date = DateUtils.removeTimeFromDate(DateUtils.currentDate());
	String dateString = DateUtils.formatDate(date);
	outFile = new File(fileName, "printouts\\labels\\" + dateString + ".pdf");
    }

    @Override
    public void displayDocument() {
	try {
	    Desktop.getDesktop().open(new File(fileName));
	} catch (IOException e) {
	    Logger.getLogger(getClass()).logException(e, Level.SEVERE);
	    e.printStackTrace();
	}
    }

}
