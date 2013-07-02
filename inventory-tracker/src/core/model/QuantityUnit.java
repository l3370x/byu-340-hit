package core.model;

/**
 * The {@code QuantityUnit} enumeration is used to designate the unit of 
 * measurement for a particular quantity.
 * 
 * @author kemcqueen
 */
public enum QuantityUnit {
    /**
     * Unit count (as in "12 cans")
     */
    COUNT,
    
    /**
     * Weight in pounds (lbs) (as in "1.5 lbs")
     */
    POUNDS,
    
    /**
     * Weight in ounces (oz) (as in "6 oz")
     */
    OUNCES,
    
    /**
     * Mass in grams (g) (as in "675 g")
     */
    GRAMS,
    
    /**
     * Mass in kilograms (kg) (as in "1.2 kg")
     */
    KILOGRAMS,
    
    /**
     * Volume in gallons (gal) (as in "1 gal")
     */
    GALLONS,
    
    /**
     * Volume in quarts (qt) (as in "4 qts = 1 gal")
     */
    QUARTS,
    
    /**
     * Volume in pints (pt) (as in "0.5 pt")
     */
    PINTS,
    
    /**
     * Volume in fluid ounces (fl oz) (as in "12 fl oz")
     */
    FLUID_OUNCES,
    
    /**
     * Volume in liters/litres (lit) (as in "2 liters")
     */
    LITERS;
}
