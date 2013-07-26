package core.model;

import core.model.exception.HITException;

/**
 * The {@code InventoryManager} interface defines the contract for a class that
 * can manage an inventory of products and items stored in various product 
 * containers.  This class acts as the root node of the inventory tree (forest).
 * 
 * @invariant ?
 * 
 * @author kemcqueen
 */
public interface InventoryManager extends ProductContainer<StorageUnit> {
    /**
     * Get all the items in the inventory that have been removed.
     * 
     * @pre removed items exist
     * 
     * @post return != null
     * 
     * @return a collection of items that have been removed from the system
     */
    Iterable<Item> getRemovedItems();

    /**
     * Get all items in the inventory that have been removed that are associated with the given product.
     *
     * @param product the product associated with the removed items
     *
     * @return a collection of items that have been removed from the system
     */
    Iterable<Item> getRemovedItems(Product product);

    /**
     * Save a removed item for tracking within the system.  This should be called after an item has been removed from
     * the system to ensure that the system tracks the item's removal (for reporting purposes).
     *
     * {@pre null != item && null == item.getContainer()}
     *
     * @param item the item that has been removed
     *
     * @throws HITException if the removed item could not be saved for any reason
     */
    void saveRemovedItem(Item item) throws HITException;

    /**
     * Delete this item from the tracking of removed items.  This method should only be used when an item that has been
     * removed from the system no longer needs to be tracked.  At this point, the removed item can not be recovered and
     * no record of the item's existence within the system will be preserved.
     *
     * @param item the item to be deleted from the system
     *
     * @throws HITException if the item could not be deleted for any reason
     */
    void deleteRemovedItem(Item item) throws HITException;

    /**
     * The {@code InventoryManager.Factory} class is used to get the singleton
     * instance of the {@link InventoryManager}.
     * 
     * @invariant ?
     */
    public static class Factory {
        private static final InventoryManager INSTANCE  = 
                new InventoryManagerImpl();
        
        /**
         * Get the {@link InventoryManager} instance.
         * 
         * @pre 
         * 
         * @post return != null
         * 
         * @return the inventory manager
         */
        public static InventoryManager getInventoryManager() {
            return INSTANCE;
        }
    }
}
