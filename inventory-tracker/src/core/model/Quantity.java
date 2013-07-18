package core.model;

import core.model.exception.HITException;
import core.model.exception.HITException.Severity;
import java.io.Serializable;
/**
 * The {@code Quantity} class is used to measure a product. This class is
 * immutable and therefore thread-safe.
 *
 * @invariant value != null
 * @invariant units != null
 *
 * @author kemcqueen
 */
public class Quantity implements Serializable {

    private final float value;
    private final Units units;

    /**
     * Create a new Quantity with the given value and units.
     *
     * @pre float >= 0.0f && units != null
     *
     * @post getValue() == value && getUnits() == units
     *
     * @param value the value (must be >= 0.0f)
     * @param units the units
     */
    public Quantity(float value, Units unit) throws HITException {
        if (value < 0.0) {
            throw new HITException(Severity.WARNING, "Value must be greater than"
                    + " 0");
        } else if (unit == null) {
            throw new HITException(Severity.WARNING, "Unit cannot be null");
        }
        this.value = value;
        this.units = unit;
    }

    /**
     * Get the value (amount) of this quantity.
     *
     * @pre none
     *
     * @return the value of this quantity
     */
    public float getValue() {
        assert true;
        return this.value;
    }

    /**
     * Get the units of this quantity
     *
     * @pre none
     *
     * @return the units of this quantity
     */
    public Units getUnits() {
        assert true;
        return this.units;
    }

    @Override
    public String toString() {
    	if(value == (int) value)
    		return String.format("%d %s", (int)value, units.getLabel());
    	return String.format("%s %s", value, units.toString());
    }

    /**
     * The {@code Quantity.Unit} enumeration is used to designate the unit of 
     * measurement for a particular quantity.
     */
    public static enum Units {

        /**
         * Units count (as in "12 cans")
         */
        COUNT("count"),
        /**
         * Weight in pounds (lbs) (as in "1.5 lbs")
         */
        POUNDS("pounds"),
        /**
         * Weight in ounces (oz) (as in "6 oz")
         */
        OUNCES("ounces"),
        /**
         * Mass in grams (g) (as in "675 g")
         */
        GRAMS("grams"),
        /**
         * Mass in kilograms (kg) (as in "1.2 kg")
         */
        KILOGRAMS("kilograms"),
        /**
         * Volume in gallons (gal) (as in "1 gal")
         */
        GALLONS("gallons"),
        /**
         * Volume in quarts (qt) (as in "4 qts = 1 gal")
         */
        QUARTS("quarts"),
        /**
         * Volume in pints (pt) (as in "0.5 pt")
         */
        PINTS("pints"),
        /**
         * Volume in fluid ounces (fl oz) (as in "12 fl oz")
         */
        FLUID_OUNCES("fluid ounces"),
        /**
         * Volume in liters/litres (lit) (as in "2 liters")
         */
        LITERS("liters");
        
        private final String label;
        
        private Units(String label) {
            this.label = label;
        }

        public String getLabel() {
            return this.label;
        }
    }
}
