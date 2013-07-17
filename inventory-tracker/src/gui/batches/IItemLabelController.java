package gui.batches;

import java.util.Iterator;

import com.itextpdf.text.Document;

import core.model.Item;

public interface IItemLabelController {
    
    public void createDocument(Iterator<Item> itemList);
    
    public void formatDocument(Document document);
    
    public void generateBarCode(Item item);
    
    public void generateFileName();
    
    public void displayDocument();
    
}
