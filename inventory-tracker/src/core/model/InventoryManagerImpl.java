package core.model;

import core.model.exception.HITException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

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
    private final Container<Item> removedItems = new ItemCollection(null);
    
    Map<String, StorageUnit> storageUnitsByName = new HashMap<>();
    
    /**
     * Hidden (mostly) constructor.
     * 
     * @see InventoryManger.Factory
     */
    InventoryManagerImpl() {
        super ("Storage Units", new ItemCollection(null){
            @Override
            protected void didAdd(Item content) throws HITException {
            }

            @Override
            protected void didRemove(Item content) throws HITException {
            }
        }, new ProductCollection(null){
            @Override
            protected void didAdd(Product content) throws HITException {
            }

            @Override
            protected void didRemove(Product content) throws HITException {
            }
        });
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
    protected boolean isAddable(StorageUnit unit) {
        return false == this.storageUnitsByName.containsKey(unit.getName());
    }

    @Override
    protected boolean isRemovable(StorageUnit unit) {
        return false == this.canAdd(unit);
    }
    
    @Override
    public Iterable<Item> getRemovedItems() {
        return this.removedItems.getContents();
    }
    
    @Override
    public void removeItem(Item item) throws HITException {
        super.removeItem(item);
        
        item.setExitDate(new Date());
        
        this.removedItems.add(item);
    }
    
    @Override
    public boolean canRemoveItem(Item item) {
        return super.canRemoveItem(item) && this.removedItems.canAdd(item);
    }

    @Override
    public StorageUnit getStorageUnit() {
        throw new UnsupportedOperationException("InventoryManager does not belong to a StorageUnit");
    }

    @Override
    public void update(Observable o, Object arg) {
        if (false == arg instanceof ModelNotification) {
            return;
        }
        
        ModelNotification notification = (ModelNotification) arg;
        Object payload = notification.getPayload();
        
        try {
            switch (notification.getChangeType()) {
                case ITEM_ADDED:
                    this.addItem((Item) payload);
                    break;

                case ITEM_REMOVED:
                    this.removeItem((Item) payload);
                    break;

                case PRODUCT_ADDED:
                    this.addProduct((Product) payload);
                    break;

                case PRODUCT_REMOVED:
                    this.removeProduct((Product) payload);
                    break;
            }
        } catch (HITException ex) {
            // TODO what do I do with this exception
        }
    }
    
    
}
