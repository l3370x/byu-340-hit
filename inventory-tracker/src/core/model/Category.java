package core.model;

import core.model.exception.HITException;

/**
 *
 * @author kemcqueen
 */
public interface Category extends Containable<Container<Category>>, StorageUnit {
    /**
     * Get the total amount of products in this category required for a three 
     * (3) month supply. For example:
     * <ul>
     * <li>A value of "100 count" means that we must have at least 100 of the
     * products in this category to have a 3-month supply.</li>
     * <li>A value of "500 lbs" means that we must have at least 500 pounds of
     * the products in this category to have a 3-month supply.</li>
     * <li>A value of "48 quarts" means that we must have at least 48 quarts of
     * the products in this category to have a 3-month supply.</li>
     * </ul>
     * A value of zero (0) means "unspecified."  
     * 
     * @return the quantity of products in this category required for a 3 month 
     * supply (a quantity with value of 0 means "unspecified")
     */
    Quantity get3MonthSupplyQuantity();
    
    
    /**
     * Set the quantity of products in this category required for a three (3) 
     * month supply.  A quantity with a value of zero (0) means "unspecified."
     * 
     * @pre ???
     * 
     * @post ???
     * 
     * @param quantity the quantity of products in this category required for a 
     * 3-month supply (the value of the quantity should be >= 0)
     */
    void set3MonthSupplyQuantity(Quantity quantity);

    
    /**
     * The static {@code Category.Factory} class is used to generate valid
     * Category instances for use in the system.
     */
    public static class Factory {
        /**
         * Get a new {@link Category} instance with the given name.
         * 
         * @param name the name of the new category
         * 
         * @return a new category instance
         * 
         * @throws HITException if the category could not be created for any
         * reason
         */
        public static Category newInstance(String name) throws HITException {
            // TODO implement
            return null;
        }
    }
}
