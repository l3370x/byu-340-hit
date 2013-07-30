package core.model;

import common.Visitable;
import core.model.exception.HITException;

/**
 * The {@code ProductContainer} interface defines the contract for a class that can manage a
 * collection of {@link Product} and {@link Item} instances.
 *
 * @author kemcqueen
 */
public interface ProductContainer<T extends Containable> extends Container<T>, Visitable {

    /**
     * Add the given {@link Item} instance to this product container.
     *
     * @param item the item to be added
     * @throws HITException if the item could not be added for any reason
     * @pre item != null
     * @post getItems contains item
     * @post all items of the same product in this storage unit will be transfered to this container
     * along with the product
     */
    void addItem(Item item) throws HITException;

    /**
     * Add the given {@link Product} instance to this product container.
     *
     * @param product the product to be added
     * @throws HITException if the product could not be added for any reason
     * @pre product != null
     * @post getProducts contains product
     * @post product will only exist in this container of its Storage Unit
     * @post all items of product in this storage unit will be transfered to this container
     */
    void addProduct(Product product) throws HITException;

    /**
     * Determine whether the given {@link Item} instance can be added to this product container.
     *
     * @param item the candidate item
     * @return {@code true} if the item can be added, {@code false} otherwise
     * @pre item != null
     * @post returnval == false if inventoryManager, true otherwise
     */
    boolean canAddItem(Item item);

    /**
     * Determine whether the given {@link Product} instance can be added to this product container.
     *
     * @param product the candidate product
     * @return {@code true} if the product can be added, {@code false} otherwise
     * @pre product != null
     * @post returnval == false if inventoryManager, true otherwise
     */
    boolean canAddProduct(Product product);

    /**
     * Determine whether the given {@link Item} instance can be removed from this product
     * container.
     *
     * @param item the candidate item
     * @return {@code true} if the item can be removed, {@code false} otherwise
     * @pre item != null
     * @post retval true if item exists in container else false
     */
    boolean canRemoveItem(Item item);

    /**
     * Determine whether the given {@link Product} instance can be removed from this product
     * container.
     *
     * @param product the candidate product
     * @return {@code true} if the product can be removed, {@code false} otherwise
     * @pre product != null
     * @post retval true if product exists in container else false
     */
    boolean canRemoveProduct(Product product);

    /**
     * Get the items contained in this product container.
     *
     * @return the {@link Item}s contained in this product container
     * @pre none
     * @post retval == iterator for all items in tree if storage unit and all in container
     * otherwise.
     * @post retval == null if no items in conainer;
     */
    Iterable<Item> getItems();

    /**
     * Get the number of {@link Item} instances stored in this product container.
     *
     * @return the number of items in this product container
     */
    int getItemCount();

    /**
     * Get the collection of {@link Item} instances contained in this product container belonging to
     * the given product.
     *
     * @param product the product for which to retrieve items
     * @return a collection of items for the given product
     * @pre product != null
     * @pre product.getContainer() == this
     * @post retval != null
     */
    Iterable<Item> getItems(Product product);

    /**
     * Get the number of {@link Item} instances contained in this product container belonging to the
     * given product.
     *
     * @param product the product for which to retrieve the count of items
     * @return the number of items in this product container associated with the given product.
     */
    int getItemCount(Product product);

    /**
     * Get the name of this product container.
     *
     * @return the name of this product container
     * @pre none
     * @post retval == String name
     */
    String getName();

    /**
     * Set the name of this product container to the given name.  The name must not be {@code null}
     * and must not be empty.
     *
     * @param name the new name for this product container
     * @throws HITException
     * @pre null != name && false == name.isEmpty()
     * @pre name != getName() of any sibling
     * @post getName() == name
     */
    void setName(String name) throws HITException;

    /**
     * Get the products contained in this product container.
     *
     * @return the {@link Product}s contained in this product container
     * @pre none
     * @post retval == null if no products, iterable otherwise
     */
    Iterable<Product> getProducts();

    /**
     * Get the {@link Product} for the given bar code (if it exists).
     *
     * @param barCode the bar code for which to return the product
     * @return the product whose bar code matches the given bar code, if found; {@code null}
     *         otherwise
     */
    Product getProduct(BarCode barCode);

    /**
     * Remove the given {@link Item} instance from this product container.
     *
     * @param item the item to be removed
     * @throws HITException if the item could not be removed for any reason
     * @pre item != null
     * @post getItems !contain item
     */
    void removeItem(Item item) throws HITException;

    /**
     * Remove the given {@link Product} instance from this product container.
     *
     * @param product the product to be removed
     * @throws HITException if the product could not be removed for any reason
     * @pre product != null
     * @post getProducts !contain product
     */
    void removeProduct(Product product) throws HITException;

    /**
     * Get the {@link StorageUnit} under which this product container may be found.
     *
     * @return the storage unit under which this category may be found
     * @pre
     * @post return != null
     */
    StorageUnit getStorageUnit();

    boolean containsItem(Item item);

    boolean containsProduct(Product product);

    Item getItem(BarCode barCode);
}
