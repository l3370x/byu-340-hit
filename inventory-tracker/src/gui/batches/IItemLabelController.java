package gui.batches;

import java.util.Iterator;


import core.model.Item;

public interface IItemLabelController {
    
    public void createDocument(Iterator<Item> itemList);
}
