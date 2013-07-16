package gui.batches;

import java.util.List;

import core.model.Item;

public interface IItemLabelController {
    
    public void createDocument(List<Item> itemList);
    
    public void formatDocument();
    
    public void generateBarCode();
    
    public void generateFileName();
    
    public void displayDocument();
    
}
