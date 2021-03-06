package core.model;

import core.model.exception.HITException;
import core.model.exception.HITException.Severity;

import java.util.Date;
import java.util.Observer;

/**
 * The {@code Product} interface defines the contract for an object that describes a particular
 * class of product.  For example: <ul> <li>Town House Light Buttery Crackers, 13 oz., Bar code
 * 030100169079</li> <li>Colgate Toothpaste - Cavity Protection, 6.40 oz., Bar code
 * 035000509000</li> <li>Alcon Lens Drops, 0.17 fl. oz., Bar code 3000650192057</li> </ul>
 *
 * @author kemcqueen
 * @invariant barCode != null
 */
public interface Product extends Containable<ProductContainer> {
    /**
     * Returns the date (and time) this product was added to the system.
     *
     * @return the date (and time) this product was added to the system
     * @pre none
     * @post retval != null
     */
    Date getCreationDate();

    /**
     * Get the bar code associated with this product (assigned by the manufacturer).
     *
     * @return the bar code associated with this product
     * @pre none
     * @post retval != null
     */
    BarCode getBarCode();


    /**
     * Get the textual description of this product.
     *
     * @return the textual description of this product
     * @pre none
     * @post retval == string description
     */
    String getDescription();

    void setDescription(String description);

    /**
     * Get the size of this product expressed as a {@link Quantity}.  For example:
     * <p/>
     * <ul> <li>13 oz.</li> <li>5 lbs</li> <li>0.17 fl. oz.</li> </ul>
     *
     * @return the size of this product
     * @pre none
     * @post retval != null
     */
    Quantity getSize();

    void setSize(Quantity quantity);

    public void setCreationDate(Date d);

    /**
     * Get the shelf life of this product measured in months.  A value of zero (0) means
     * "unspecified."
     *
     * @return the shelf life of this product measured in months (or 0 if not specified)
     * @pre none
     * @post retval >= 0
     */
    int getShelfLifeInMonths();


    /**
     * Set the shelf life of this product measured in months.  A value of zero (0) (the default)
     * means "unspecified."
     *
     * @param shelfLife the shelf life of this product measured in months (>= 0)
     * @pre shelfLife >= 0
     * @post getShelfLifeInMonths() == shelfLife
     */
    void setShelfLifeInMonths(int shelfLife) throws HITException;

    /**
     * Get the number of this product required for a three (3) month supply. A value of zero (0)
     * means "unspecified."
     *
     * @return the number of this product required for a 3 month supply (or 0 if not specified)
     * @pre none
     * @post retval >= 0
     */
    int get3MonthSupplyQuota();


    /**
     * Set the number of this product required for a three (3) month supply.  A value of zero (0)
     * means "unspecified."
     *
     * @param quota the number of this product required for a 3-month supply (>= 0)
     * @pre quota >= 0
     * @post get3MonthSupplyQuota() == quota
     */
    void set3MonthSupplyQuota(int quota) throws HITException;


    /**
     * Get all the storage units where this product is located.
     *
     * @return all the storage units where this product is located
     * @pre none
     * @post retval != null ?? Product doesn't exist if not in any Storage Unit
     */
    Iterable<StorageUnit> getStorageUnits();


    /**
     * Get all the categories to which this product has been assigned.
     *
     * @return all the categories to which this product has been assigned
     * @pre none
     * @post retval == iterable if exists in any category, and null if not
     */
    Iterable<ProductContainer> getProductContainers();


    /**
     * Get the {@link ProductContainer} containing this product within the given storage unit.
     *
     * @param unit the storage unit that contains this product
     * @return the product container that actually contains this product in the given storage unit
     *         (may be the storage unit itself)
     * @pre unit != null
     * @pre unit exists in getStorageUnits()
     * @post retval != null
     */
    ProductContainer getProductContainer(StorageUnit unit);

    void addObs(Observer o);


    /**
     * The static {@code Product.Factory} class is used to generate valid Product instances for use
     * in the system.
     */
    public static class Factory {
        /**
         * Get a new {@link Product} instance for use in the system based on the given
         * manufacturer's bar code (UPC).
         *
         * @param barCode the manufacturer's bar code (UPC)
         * @return a new product based on the given bar code
         * @throws HITException if the product could not be created for any reason
         * @pre barCode != null
         * @post retval != null
         * @post getBarcode() = barCode
         */
        public static Product newProduct(BarCode barCode) throws HITException {
            if (barCode == null) {
                throw new HITException(Severity.WARNING, "Barcode cannot be null");
            }

            // get the description for the given bar code (look on the internet)
            String description = getDescriptionFor(barCode);

            return newProduct(barCode, description);
        }


        /**
         * Get a new {@link Product} instance for use in the system based on the given
         * manufacturer's bar code (UPC) and description.
         *
         * @param barCode     the manufacturer's bar code (UPC)
         * @param description the description of the product
         * @return a new product based on the given bar code and description
         * @throws HITException if the product could not be created for any reason
         * @pre barCode != null
         * @pre description != null
         * @post retval != null
         * @post getBarcode() = barCode
         * @post getDescription() = description
         */
        public static Product newProduct(BarCode barCode, String description) throws HITException {
            if (barCode == null) {
                throw new HITException(Severity.WARNING, "Barcode cannot be null");
            } else if (description == null || description.isEmpty()) {
                throw new HITException(Severity.WARNING, "Invalid Description");
            }

            return new ProductImpl(barCode, description);
        }

        private static String getDescriptionFor(BarCode barCode) {
            return "TODO";
        }
    }

}
