package core.model;

import core.model.exception.HITException;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@code ItemContainer} class is a concrete container class that manages
 * a collection of {@link Item} instances.
 * 
 * @author kemcqueen
 */
class ItemCollection extends AbstractProductContainerProxy<Item> {
    private Map<BarCode, Item> itemsByBarCode = new HashMap<>();
    
    ItemCollection(ProductContainer delegate) {
        super(delegate);
    }

    @Override
    protected void doAdd(Item item) throws HITException {
        assert null != item;
        
        this.itemsByBarCode.put(item.getBarCode(), item);
    }

    @Override
    protected void doRemove(Item item) throws HITException {
        assert null != item;
        
        this.itemsByBarCode.remove(item.getBarCode());
    }

    @Override
    protected boolean isAddable(Item item) {
        assert null != item;
        
        return false == this.itemsByBarCode.containsKey(item.getBarCode());
    }

    @Override
    protected boolean isRemovable(Item item) {
        return false == this.canAdd(item);
    }
}
