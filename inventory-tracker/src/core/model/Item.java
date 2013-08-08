package core.model;

import core.model.exception.HITException;
import core.model.exception.HITException.Severity;

import java.util.Date;
import java.util.Observer;

import static core.model.BarCode.generateItemBarCode;
import static core.model.BarCode.getBarCodeFor;

/**
 * Instances of the {@code Item} class represent physical items of a particular {@link Product}. An
 * item corresponds to a physical container with a bar code on it. For example, a case of soda might
 * contain 24 cans of Diet Coke. In this case, the Product is "Diet Coke, 12 fl. oz.", while each
 * physical can is a distinct item.
 *
 * @author kemcqueen
 * @invariant entryDate != null
 * @invariant product != null
 */
public interface Item extends Containable<ProductContainer> {
    /**
     * Get the {@link Product} of which this item is an instance.
     *
     * @return this item's product
     * @pre
     * @post return Product contains item
     */
    Product getProduct();

    /**
     * Get the bar code assigned to this item. This bar code is generated by the inventory tracking
     * system and is distinct from the associated product's bar code which was assigned by the
     * product manufacturer.
     *
     * @return the bar code assigned to this individual item
     * @pre
     * @post return != null
     */
    BarCode getBarCode();

    /**
     * Get the date (and time) this item was entered into the system.
     *
     * @return the date (and time) this item was entered into the system
     * @pre
     * @post return != null
     */
    Date getEntryDate();

    /**
     * Get the date (and time) this item was removed from the system.
     *
     * @return the date (and time) this item was removed from the system
     * @pre
     * @post return != null
     */
    Date getExitDate();

    /**
     * Set the date (and time) this item was removed from the system.
     *
     * @pre d != null
     * @post d.getExitDate = d
     */
    void setExitDate(Date d);

    /**
     * Get the date on which this item will expire. This value is calculated based on this item's
     * entry date and the product's shelf life.
     *
     * @return the date on which this item will expire
     * @pre
     * @post return != null
     */
    Date getExpirationDate();

    /**
     * Set the date on which this item was entered.
     *
     * @param date the date to set
     * @pre
     * @post entry date is set
     */
    void setEntryDate(Date date);

    void addObs(Observer o);

    /**
     * The static {@code Item.Factory} class is used to generate valid Item instances for use in the
     * system.
     */
    public static class Factory {
        /**
         * Get a new {@link Item} instance based on the given {@link Product}.
         *
         * @param product   the product for which to create a new item
         * @param entryDate the date the item was entered into the system
         * @return a new item instance based on the given product
         * @throws HITException if the item could not be created for any reason
         * @pre product != null, entryDate != null, expirationDate != null
         * @post product.contains(Item)
         */
        public static Item newItem(Product product, Date entryDate) throws HITException {
            return newItem(product, entryDate, generateItemBarCode().getValue());
        }

        public static Item newItem(Product product, Date entryDate, String barcode)
                throws HITException {
            if (product == null) {
                throw new HITException(Severity.WARNING,
                        "Product must not be null");
            }

            if (entryDate == null) {
                throw new HITException(Severity.WARNING, "Entry date must not be null");
            }

            return new ItemImpl(entryDate, product, getBarCodeFor(barcode));
        }
    }
}
