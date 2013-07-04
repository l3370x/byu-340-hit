package core.model;

import core.model.exception.HITException;

/**
 * The {@code ProductContainer} class defines the contract for a class that can
 * manage a collection of {@link Product} and {@link Item} instances.
 * 
 * 
 * @author kemcqueen
 */
public interface ProductContainer<T extends Containable> extends Container<T> {

    /**
     * Add the given {@link Item} instance to this product container.
     * 
     * @pre item != null
     * 
     * @post getItems contains item
     * @post all items of the same product in this storage unit will be 
     * transfered to this container along with the product 
     *
     * @param item the item to be added
     *
     * @throws HITException if the item could not be added for any reason
     */
    void addItem(Item item) throws HITException;

    /**
     * Add the given {@link Product} instance to this product container.
     * 
     * @pre product != null
     * 
     * @post getProducts contains product
     * @post product will only exist in this container of its Storage Unit
     * @post all items of product in this storage unit will be 
     * transfered to this container
     *
     * @param product the product to be added
     *
     * @throws HITException if the product could not be added for any reason
     */
    void addProduct(Product product) throws HITException;

    /**
     * Determine whether the given {@link Item} instance can be added to this
     * product container.
     * 
     * @pre item != null
     * 
     * @post returnval == false if inventoryManager, true otherwise
     *
     * @param item the candidate item
     *
     * @return {@code true} if the item can be added, {@code false}
     * otherwise
     */
    boolean canAddItem(Item item);

    /**
     * Determine whether the given {@link Product} instance can be added to this
     * product container.
     * 
     * @pre product != null
     * 
     * @post returnval == false if inventoryManager, true otherwise
     *
     * @param product the candidate product
     *
     * @return {@code true} if the product can be added, {@code false}
     * otherwise
     */
    boolean canAddProduct(Product product);

    /**
     * Determine whether the given {@link Item} instance can be removed from
     * this product container.
     * 
     * @pre item != null
     * 
     * @post retval true if item exists in container else false
     *
     * @param item the candidate item
     *
     * @return {@code true} if the item can be removed, {@code false}
     * otherwise
     */
    boolean canRemoveItem(Item item);

    /**
     * Determine whether the given {@link Product} instance can be removed from
     * this product container.
     * 
     * @pre product != null
     * 
     * @post retval true if product exists in container else false
     *
     * @param product the candidate product
     *
     * @return {@code true} if the product can be removed, {@code false}
     * otherwise
     */
    boolean canRemoveProduct(Product product);

    /**
     * Get the items contained in this product container.
     * 
     * @pre none
     * 
     * @post retval == iterator for all items in tree if storage unit and all 
     * in container otherwise.
     * @post retval == null if no items in conainer;
     *
     * @return the {@link Item}s contained in this product container
     */
    Iterable<Item> getItems();
    
    /**
     * Get the collection of {@link Item} instances contained in this product
     * container belonging to the given product.
     * 
     * @pre product != null
     * @pre product.getContainer() == this
     * 
     * @post retval != null
     * 
     * @param product the product for which to retrieve items
     * 
     * @return a collection of items for the given product
     */
    Iterable<Item> getItems(Product product);

    /**
     * Get the name of this product container.
     * 
     * @pre none
     * 
     * @post retval == String name
     *
     * @return the name of this product container
     */
    String getName();
    
    /**
     * Set the name of this product container to the given name.  The name must
     * not be {@code null} and must not be empty.
     * 
     * @pre null != name && false == name.isEmpty()
     * @pre name != getName() of any sibling
     * 
     * @post getName() == name
     * 
     * @param name the new name for this product container
     * 
     * @throws HITException 
     */
    void setName(String name) throws HITException;

    /**
     * Get the products contained in this product container.
     * 
     * @pre none
     * 
     * @post retval == null if no products, iterable otherwise 
     *
     * @return the {@link Product}s contained in this product container
     */
    Iterable<Product> getProducts();

    /**
     * Remove the given {@link Item} instance from this product container.
     * 
     * @pre item != null
     * 
     * @post getItems !contain item
     *
     * @param item the item to be removed
     *
     * @throws HITException if the item could not be removed for any reason
     */
    void removeItem(Item item) throws HITException;

    /**
     * Remove the given {@link Product} instance from this product container.
     * 
     * @pre product != null
     * 
     * @post getProducts !contain product
     *
     * @param product the product to be removed
     *
     * @throws HITException if the product could not be removed for any reason
     */
    void removeProduct(Product product) throws HITException;
    
}
