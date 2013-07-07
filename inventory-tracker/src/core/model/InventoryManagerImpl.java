package core.model;

import core.model.exception.HITException;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@code InventoryManagerImpl} class is the default (and sole) 
 * implementation of the {@link InventoryManager} interface.  The
 * constructor is hidden, so the only way to get an instance is through the
 * {@link InventoryManager.Factory}.
 * 
 * @invariant ?
 * 
 * @author kemcqueen
 */
class InventoryManagerImpl extends AbstractProductContainer<StorageUnit> implements InventoryManager {
    private final Container<Item> removedItems = new ItemCollection();
    
    Map<String, StorageUnit> storageUnitsByName = new HashMap<>();
    
    /**
     * Hidden (mostly) constructor.
     * 
     * @see InventoryManger.Factory
     */
    InventoryManagerImpl() {
        super ("Storage Units");
    }

    @Override
    protected void doAdd(StorageUnit unit) throws HITException {
        this.storageUnitsByName.put(unit.getName(), unit);
    }

    @Override
    protected void doRemove(StorageUnit unit) throws HITException {
        this.storageUnitsByName.remove(unit.getName());
    }

    @Override
    public boolean canAdd(StorageUnit unit) {
        return false == this.storageUnitsByName.containsKey(unit.getName());
    }

    @Override
    public boolean canRemove(StorageUnit unit) {
        return false == this.canAdd(unit);
    }
    
    @Override
    public Iterable<Item> getRemovedItems() {
        return this.removedItems.getContents();
    }
    
    @Override
    public void removeItem(Item item) throws HITException {
        super.removeItem(item);
        
        // TODO set the exit date on the item
        
        this.removedItems.add(item);
    }
    
    @Override
    public boolean canRemoveItem(Item item) {
        return super.canRemoveItem(item) && this.removedItems.canAdd(item);
    }
}
