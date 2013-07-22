package core.model;

import core.model.exception.ExceptionHandler;
import core.model.exception.HITException;
import static core.model.ModelNotification.ChangeType.*;

import java.util.Date;
import java.util.Observable;
import java.util.Observer;

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
class InventoryManagerImpl extends AbstractProductContainer<StorageUnit> 
implements InventoryManager {
    private final Container<Item> removedItems = new ItemCollection(null);
    
    /**
     * Hidden (mostly) constructor.
     * 
     * @see InventoryManger.Factory
     */
    InventoryManagerImpl() {
        super ("Storage Units", new ItemCollection(null){
            @Override
            protected void didAdd(Item content) throws HITException {
                //this.notifyObservers(new ModelNotification(CONTENT_ADDED, this, content));
            }

            @Override
            protected void didRemove(Item content) throws HITException {
                //this.notifyObservers(new ModelNotification(CONTENT_REMOVED, this, content));
            }

            @Override
            public synchronized void addObserver(Observer o) {
                // do nothing
            }
        }, new ProductCollection(null){
            @Override
            protected void didAdd(Product content) throws HITException {
                //this.notifyObservers(new ModelNotification(CONTENT_ADDED, this, content));
            }

            @Override
            protected void didRemove(Product content) throws HITException {
                //this.notifyObservers(new ModelNotification(CONTENT_REMOVED, this, content));
            }

            @Override
            public synchronized void addObserver(Observer o) {
                // do nothing
            }
        });
    }

    @Override
    public Iterable<Item> getRemovedItems() {
        return this.removedItems.getContents();
    }
    
    @Override
    public void removeItem(Item item) throws HITException {
        super.removeItem(item);
        
        item.setExitDate(new Date());
        
        //this.removedItems.add(item);
    }
    
    @Override
    public boolean canRemoveItem(Item item) {
        return super.canRemoveItem(item) && this.removedItems.canAdd(item);
    }

    @Override
    public void addProduct(Product product) throws HITException {
        this.doAddProduct(product);
    }

    @Override
    public void addItem(Item item) throws HITException {
        this.doAddItem(item);
    }

    @Override
    public StorageUnit getStorageUnit() {
        return null;
    }

    @Override
    public void update(Observable o, Object arg) {
        // call the super first, so that notifications will propagate to my 
        // observers
        super.update(o, arg);
        
        if (false == arg instanceof ModelNotification) {
            return;
        }
        
        ModelNotification notification = (ModelNotification) arg;
        Object payload = notification.getContent();
        
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
            switch (ex.getSeverity()) {
                case ERROR:
                case WARNING:
                    ExceptionHandler.TO_LOG.reportException(ex, "");
                    break;
            }
        }
    }
}
