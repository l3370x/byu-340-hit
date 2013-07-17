package gui.batches;

import java.util.Iterator;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;

import core.model.Item;

public interface IItemLabelController {
    
    public ItemLabelController getInstance();
    
    public void createDocument(Iterator<Item> itemList);
    
    public void formatDocument(Document document);
    
    public void generateBarCode(Item item, PdfPTable table);
    
    public void generateFileName();
    
    public void displayDocument();
    
}
