package core.model;

/**
 * The {@code InventoryManager} interface defines the contract for a class that
 * can manage an inventory of products and items stored in various product 
 * containers.  This class acts as the root node of the inventory tree (forest).
 * 
 * @invariant TODO
 * 
 * @author kemcqueen
 */
public interface InventoryManager extends ProductContainer<StorageUnit> {
    /**
     * Get all the items in the inventory that have been removed.
     * 
     * @pre TODO
     * 
     * @post TODO
     * 
     * @return a collection of items that have been removed from the system
     */
    Iterable<Item> getRemovedItems();

    /**
     * The {@code InventoryManager.Factory} class is used to get the singleton
     * instance of the {@link InventoryManager}.
     * 
     * @invariant TODO
     */
    public static class Factory {
        private static final InventoryManager INSTANCE  = 
                new InventoryManagerImpl();
        
        /**
         * Get the {@link InventoryManager} instance.
         * 
         * @pre TODO
         * 
         * @post TODO
         * 
         * @return the inventory manager
         */
        public static InventoryManager getInventoryManager() {
            return INSTANCE;
        }
    }
}
