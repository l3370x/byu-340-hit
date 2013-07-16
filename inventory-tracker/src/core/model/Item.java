package core.model;

import core.model.exception.HITException;
import core.model.exception.HITException.Severity;

import java.util.Date;

/**
 * Instances of the {@code Item} class represent physical items of a particular
 * {@link Product}.  An item corresponds to a physical container with a bar code
 * on it.  For example, a case of soda might contain 24 cans of Diet Coke.  In
 * this case, the Product is "Diet Coke, 12 fl. oz.", while each physical can is
 * a distinct item.
 * 
 * @invariant entryDate != null
 * @invariant product != null
 * 
 * @author kemcqueen
 */
public interface Item extends Containable<ProductContainer> {
    /**
     * Get the {@link Product} of which this item is an instance.
     * 
     * @pre 
     * 
     * @post return Product contains item
     * 
     * @return this item's product
     */
    Product getProduct();
    
    /**
     * Get the bar code assigned to this item.  This bar code is generated by
     * the inventory tracking system and is distinct from the associated
     * product's bar code which was assigned by the product manufacturer.
     * 
     * @pre 
     * 
     * @post return != null
     * 
     * @return the bar code assigned to this individual item
     */
    BarCode getBarCode();
    
    /**
     * Get the date (and time) this item was entered into the system.
     * 
     * @pre 
     * 
     * @post return != null
     * 
     * @return the date (and time) this item was entered into the system
     */
    Date getEntryDate();
    
    /**
     * Get the date (and time) this item was removed from the system.
     * 
     * @pre 
     * 
     * @post return != null
     * 
     * @return the date (and time) this item was removed from the system
     */
    Date getExitDate();
    void setExitDate(Date d); //TODO docuentation
    
    /**
     * Get the date on which this item will expire.  This value is calculated
     * based on this item's entry date and the product's shelf life.
     * 
     * @pre 
     * 
     * @post return != null
     * 
     * @return the date on which this item will expire
     */
    Date getExpirationDate();
    
    /**
     * The static {@code Item.Factory} class is used to generate valid Item
     * instances for use in the system.
     */
    public static class Factory {
        /**
         * Get a new {@link Item} instance based on the given {@link Product}.
         * 
         * @pre product != null, entryDate != null, expirationDate != null
         * 
         * @post product.contains(Item)
         * 
         * @param product the product for which to create a new item
         * @param entryDate the date the item was entered into the system
         * 
         * @return a new item instance based on the given product
         * 
         * @throws HITException if the item could not be created for any reason
         */
        public static Item newItem(Product product, Date entryDate, Date expirationDate) throws HITException {
            if (product == null) {
            	throw new HITException(Severity.WARNING, "Can't add item to null product.");
            } 
            
            if (entryDate == null) {
            	throw new HITException(Severity.WARNING, "Date missing.");
            }
            
            Item item = new ItemImpl(entryDate, 
                    expirationDate, product, BarCode.generateItemBarCode());

            return item;
        }
    }
}
