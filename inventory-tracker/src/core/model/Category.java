package core.model;

import core.model.exception.HITException;
import core.model.exception.HITException.Severity;

/**
 * The {@code Category} interface defines the contract for an object that describes a group of
 * related products.  A category may also contain other categories in the manner of a tree of
 * product groups.
 *
 * @author kemcqueen
 * @invariant getStorageUnit() != null
 */
public interface Category extends Containable<ProductContainer<Category>>,
        ProductContainer<Category> {
    /**
     * Get the total amount of products in this category required for a three (3) month supply. For
     * example: <ul> <li>A value of "100 count" means that we must have at least 100 of the products
     * in this category to have a 3-month supply.</li> <li>A value of "500 lbs" means that we must
     * have at least 500 pounds of the products in this category to have a 3-month supply.</li>
     * <li>A value of "48 quarts" means that we must have at least 48 quarts of the products in this
     * category to have a 3-month supply.</li> </ul> A value of zero (0) means "unspecified."
     *
     * @return the quantity of products in this category required for a 3 month supply (a quantity
     *         with value of 0 means "unspecified")
     * @pre
     * @post return != null
     */
    Quantity get3MonthSupplyQuantity();


    /**
     * Set the quantity of products in this category required for a three (3) month supply.  A
     * quantity with a value of zero (0) means "unspecified."
     *
     * @param quantity the quantity of products in this category required for a 3-month supply (the
     *                 value of the quantity should be >= 0)
     * @pre quantity != null
     * @post Category.get3MonthSupplyQuantity() == quantity
     */
    void set3MonthSupplyQuantity(Quantity quantity);


    /**
     * The static {@code Category.Factory} class is used to generate valid Category instances for
     * use in the system.
     */
    public static class Factory {
        /**
         * Get a new {@link Category} instance with the given name.
         *
         * @param name the name of the new category
         * @return a new category instance
         * @throws HITException if the category could not be created for any reason
         * @pre name != null && name.isEmpty() == false
         * @post return != null
         */
        public static Category newCategory(String name) throws HITException {
            if (null == name) {
                throw new HITException(Severity.WARNING,
                        "Category name must not be null");
            }
            if (true == name.isEmpty()) {
                throw new HITException(Severity.WARNING,
                        "Category name must not be empty");
            }

            return new CategoryImpl(name);
        }
    }
}
