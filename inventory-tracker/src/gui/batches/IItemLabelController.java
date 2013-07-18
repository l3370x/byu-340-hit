package gui.batches;

import java.util.Iterator;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;

import core.model.Item;

public interface IItemLabelController {
    
<<<<<<< HEAD
=======
    public IItemLabelController getInstance();
    
>>>>>>> 7544444f8bbd91e2a38a71dae1adbff6eab42380
    public void createDocument(Iterator<Item> itemList);
    
    public void formatDocument(Document document);
    
    public void generateBarCode(Item item, PdfPTable table);
    
    public void generateFileName();
    
    public void displayDocument();
    
}
