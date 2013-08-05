package core.model;

import core.model.exception.ExceptionHandler;
import core.model.exception.HITException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

/**
 * The {@code InventoryManagerImpl} class is the default (and sole) implementation of the {@link
 * InventoryManager} interface.  The constructor is hidden, so the only way to get an instance is
 * through the {@link InventoryManager.Factory}.
 *
 * @author kemcqueen
 */
class InventoryManagerImpl extends AbstractProductContainer<StorageUnit>
        implements InventoryManager {

    private Date lastReportRun = null;

    private final ItemCollection removedItems = new ItemCollection(null) {
        @Override
        protected void didAdd(Item content) throws HITException {
        }

        @Override
        protected void didRemove(Item content) throws HITException {
        }

        @Override
        public synchronized void addObserver(Observer o) {
        }
    };

    /**
     * Hidden (mostly) constructor.
     */
    InventoryManagerImpl() {
        super("Storage Units", new ItemCollection(null) {
                    @Override
                    protected void didAdd(Item content) throws HITException {
                    }

                    @Override
                    protected void didRemove(Item content) throws HITException {
                    }

                    @Override
                    public synchronized void addObserver(Observer o) {
                    }
                }, new ProductCollection(null) {
                    @Override
                    protected void didAdd(Product content) throws HITException {
                    }

                    @Override
                    protected void didRemove(Product content) throws HITException {
                    }

                    @Override
                    public synchronized void addObserver(Observer o) {
                    }
                }
        );
    }

    @Override
    public Iterable<Item> getRemovedItems() {
        return this.removedItems.getContents();
    }

    @Override
    public Iterable<Item> getRemovedItems(Product product) {
        return this.removedItems.getItems(product);
    }

    @Override
    public void saveRemovedItem(Item item) throws HITException {
        assert null != item;
        assert null == item.getContainer();

        // set the item's exit date
        item.setExitDate(new Date());

        // put the removed item with the others
        this.removedItems.add(item);
    }

    @Override
    public void deleteRemovedItem(Item item) throws HITException {
        this.removedItems.remove(item);

        item.setExitDate(null);
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

        // call the super, so that notifications will propagate to my observers
        super.update(o, arg);
    }


    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }

    private void readObject(ObjectInputStream in) throws IOException,
            ClassNotFoundException, HITException {
        in.defaultReadObject();
        InventoryManager.Factory.getInventoryManager().load(this);
    }


    @Override
    public void load(AbstractProductContainer i) throws HITException {
        super.loadInvMan(i);
    }

    /**
     * @return the lastReportRun
     */
    public Date getLastReportRun() {
        return lastReportRun;
    }
    
    public boolean idExistsInTree(int id){
    	return true;
    }

    /**
     * @param lastReportRun the lastReportRun to set
     */
    public void setLastReportRun(Date lastReportRun) {
        this.lastReportRun = lastReportRun;
    }
}
