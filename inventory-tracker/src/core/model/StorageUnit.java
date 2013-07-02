/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.model;

import core.model.exception.HITException;

/**
 * The {@code StorageUnit}> class represents an enclosed area where items
 * may be stored (e.g. a room, closet, pantry, or cupboard).
 * 
 * @author kemcqueen
 */
public interface StorageUnit extends Container<Category> {
    /**
     * Get the name of this storage unit.
     * 
     * @return the name of this storage unit
     */
    String getName();
    
    
    /**
     * Get the products contained in this storage unit.
     * 
     * @return the {@link Product}s contained in this storage unit
     */
    Iterable<Product> getProducts();
    
    
    /**
     * Add the given {@link Product} instance to this storage unit.
     * 
     * @param product the product to be added
     * 
     * @throws HITException if the product could not be added for any reason
     */
    void addProduct(Product product) throws HITException;
    
    
    /**
     * Determine whether the given {@link Product} instance can be added to this
     * storage unit.
     * 
     * @param product the candidate product
     * 
     * @return {@code true} if the product can be added, {@code false}
     * otherwise
     */
    boolean canAddProduct(Product product);
    
    
    /**
     * Remove the given {@link Product} instance from this storage unit.
     * 
     * @param product the product to be removed
     * 
     * @throws HITException if the product could not be added for any reason
     */
    void removeProduct(Product product) throws HITException;
    
    
    /**
     * Determine whether the given {@link Product} instance can be removed from
     * this storage unit.
     * 
     * @param product the candidate product
     * 
     * @return {@code true} if the product can be removed, {@code false} 
     * otherwise
     */
    boolean canRemoveProduct(Product product);
    
    
    /**
     * Get the items contained in this storage unit.
     * 
     * @return the {@link Item}s contained in this storage unit
     */
    Iterable<Item> getItems();
    
    
    /**
     * Add the given {@link Item} instance to this storage unit.
     * 
     * @param item the item to be added
     * 
     * @throws HITException if the item could not be added for any reason
     */
    void addItem(Item item) throws HITException;
    
    
    /**
     * Determine whether the given {@link Item} instance can be added to this
     * storage unit.
     * 
     * @param item the candidate item
     * 
     * @return {@code true} if the item can be added, {@code false}
     * otherwise
     */
    boolean canAddItem(Item item);
    
    
    /**
     * Remove the given {@link Item} instance from this storage unit.
     * 
     * @param item the item to be removed
     * 
     * @throws HITException if the item could not be added for any reason
     */
    void removeItem(Item item) throws HITException;
    
    
    /**
     * Determine whether the given {@link Item} instance can be removed from
     * this storage unit.
     * 
     * @param item the candidate item
     * 
     * @return {@code true} if the item can be removed, {@code false} 
     * otherwise
     */
    boolean canRemoveItem(Item item);
    
    
    /**
     * Get the categories contained in this storage unit.
     * 
     * @return the {@link Category} instances contained in this storage unit
     */
    Iterable<Category> getCategories();
    
    
    /**
     * Add the given {@link Category} instance to this storage unit.
     * 
     * @param category the category to be added
     * 
     * @throws HITException if the category could not be added for any reason
     */
    void addCategory(Category category) throws HITException;
    
    
    /**
     * Determine whether the given {@link Category} instance can be added to 
     * this storage unit.
     * 
     * @param category the candidate category
     * 
     * @return {@code true} if the category can be added, {@code false}
     * otherwise
     */
    boolean canAddCategory(Category category);
    
    
    /**
     * Remove the given {@link Category} instance from this storage unit.
     * 
     * @param category the category to be removed
     * 
     * @throws HITException if the category could not be added for any reason
     */
    void removeCategory(Category category) throws HITException;
    
    
    /**
     * Determine whether the given {@link Category} instance can be removed from
     * this storage unit.
     * 
     * @param category the candidate category
     * 
     * @return {@code true} if the category can be removed, {@code false} 
     * otherwise
     */
    boolean canRemoveCategory(Category product);
    
    
    /**
     * The static {@code StorageUnit.Factory} class is used to generate valid
     * StorageUnit instances for use in the system.
     */
    public static class Factory {
        /**
         * Get a new {@link StorageUnit} instance with the given name.
         * 
         * @param name the name of the new storage unit
         * 
         * @return a new storage unit with the given name
         * 
         * @throws HITException if the storage unit could not be created for
         * any reason
         */
        public static StorageUnit newInstance(String name) throws HITException {
            // TODO implement
            return null;
        }
    }
}
