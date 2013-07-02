package core.model;

import core.model.exception.HITException;

/**
 * The {@code ProductContainer} class defines the contract for a class that can
 * manage a collection of {@link Product} and {@link Item} instances.
 * 
 * @invariant ???
 * 
 * @author kemcqueen
 */
public interface ProductContainer<T extends Containable> extends Container<T> {

    /**
     * Add the given {@link Item} instance to this product container.
     * 
     * @pre ???
     * 
     * @post ???
     *
     * @param item the item to be added
     *
     * @throws HITException if the item could not be added for any reason
     */
    void addItem(Item item) throws HITException;

    /**
     * Add the given {@link Product} instance to this product container.
     * 
     * @pre ???
     * 
     * @post ???
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
     * @pre ???
     * 
     * @post ???
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
     * @pre ???
     * 
     * @post ???
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
     * @pre ???
     * 
     * @post ???
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
     * @pre ???
     * 
     * @post ???
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
     * @pre ???
     * 
     * @post ???
     *
     * @return the {@link Item}s contained in this product container
     */
    Iterable<Item> getItems();
    
    /**
     * Get the collection of {@link Item} instances contained in this product
     * container belonging to the given product.
     * 
     * @pre ???
     * 
     * @post ???
     * 
     * @param product the product for which to retrieve items
     * 
     * @return a collection of items for the given product
     */
    Iterable<Item> getItems(Product product);

    /**
     * Get the name of this product container.
     * 
     * @pre ???
     * 
     * @post ???
     *
     * @return the name of this product container
     */
    String getName();
    
    /**
     * Set the name of this product container to the given name.  The name must
     * not be {@code null} and must not be empty.
     * 
     * @pre null != name && false == name.isEmpty()
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
     * @pre ???
     * 
     * @post ???
     *
     * @return the {@link Product}s contained in this product container
     */
    Iterable<Product> getProducts();

    /**
     * Remove the given {@link Item} instance from this product container.
     * 
     * @pre ???
     * 
     * @post ???
     *
     * @param item the item to be removed
     *
     * @throws HITException if the item could not be added for any reason
     */
    void removeItem(Item item) throws HITException;

    /**
     * Remove the given {@link Product} instance from this product container.
     * 
     * @pre ???
     * 
     * @post ???
     *
     * @param product the product to be removed
     *
     * @throws HITException if the product could not be added for any reason
     */
    void removeProduct(Product product) throws HITException;
    
}
