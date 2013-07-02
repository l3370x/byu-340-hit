package core.model;

import core.model.exception.HITException;
import java.util.Date;

/**
 * The {@code Product} interface defines the contract for an object that
 * describes a particular class of product.  For example:
 * <ul>
 * <li>Town House Light Buttery Crackers, 13 oz., Bar code 030100169079</li>
 * <li>Colgate Toothpaste - Cavity Protection, 6.40 oz., Bar code 035000509000</li>
 * <li>Alcon Lens Drops, 0.17 fl. oz., Bar code 3000650192057</li>
 * </ul>
 * 
 * @author kemcqueen
 */
public interface Product extends MultiContainable<Container<Product>> {
    /**
     * Returns the date (and time) this product was added to the system.
     * 
     * @return the date (and time) this product was added to the system
     */
    Date getCreationDate();
    
    /**
     * Get the bar code associated with this product (assigned by the 
     * manufacturer).
     * 
     * @return the bar code associated with this product
     */
    BarCode getBarCode();
    
    
    /**
     * Get the textual description of this product.
     * 
     * @return the textual description of this product
     */
    String getDescription();
    
    
    /**
     * Get the size of this product expressed as a {@link Quantity}.  For 
     * example:
     * 
     * <ul>
     * <li>13 oz.</li>
     * <li>5 lbs</li>
     * <li>0.17 fl. oz.</li>
     * </ul>
     * 
     * @return the size of this product
     */
    Quantity getSize();
    
    
    /**
     * Get the shelf life of this product measured in months.  A value of zero
     * (0) means "unspecified."
     * 
     * @return the shelf life of this product measured in months (or 0 if not
     * specified)
     */
    int getShelfLifeInMonths();
    
    
    /**
     * Set the shelf life of this product measured in months.  A value of zero
     * (0) (the default) means "unspecified."
     * 
     * @pre the shelf life should be >= 0
     * 
     * @param shelfLife the shelf life of this product measured in months (>= 0)
     */
    void setShelfLifeInMonths(int shelfLife);
    
    
    /**
     * Get the number of this product required for a three (3) month supply. A
     * value of zero (0) means "unspecified."
     * 
     * @return the number of this product required for a 3 month supply (or 0 if
     * not specified)
     */
    int get3MonthSupplyQuota();
    
    
    /**
     * Set the number of this product required for a three (3) month supply.  A
     * value of zero (0) means "unspecified."
     * 
     * @pre the quota should be >= 0
     * 
     * @param quota the number of this product required for a 3-month supply 
     * (>= 0)
     */
    void set3MonthSupplyQuota(int quota);
    
    
    /**
     * The static {@code Product.Factory} class is used to generate valid
     * Product instances for use in the system.
     */
    public static class Factory {
        /**
         * Get a new {@link Product} instance for use in the system based on the
         * given manufacturer's bar code (UPC).
         * 
         * @param barCode the manufacturer's bar code (UPC)
         * 
         * @return a new product based on the given bar code
         * 
         * @throws HITException if the product could not be created for any 
         * reason
         */
        public static Product newInstance(BarCode barCode) throws HITException {
            // TODO implement
            return null;
        }
        
        
        /**
         * Get a new {@link Product} instance for use in the system based on the
         * given manufacturer's bar code (UPC) and description.
         * 
         * @param barCode the manufacturer's bar code (UPC)
         * @param description the description of the product
         * 
         * @return a new product based on the given bar code and description
         * 
         * @throws HITException if the product could not be created for any
         * reason
         */
        public static Product newInstance(BarCode barCode, String description) throws HITException {
            // TODO implement
            return null;
        }
    }
}
