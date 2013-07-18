/**
 *
 */
package gui.common;

import core.model.Quantity.Units;
import core.model.exception.HITException;
import core.model.exception.HITException.Severity;

/**
 * Class to enable simple conversion between the gui's and model's units.
 *
 * @author aaron
 *
 */
public class UnitController {

    /**
     *
     * @param su	The SizeUnits to convert from
     * @return	The Units that corresponds to the given SizeUnits
     * @throws HITException if the conversion failed
     */
    public static Units sizeUnitsToUnits(SizeUnits su) throws HITException {
        switch (su) {
            case Pounds:
                return Units.POUNDS;
            case Ounces:
                return Units.OUNCES;
            case Grams:
                return Units.GRAMS;
            case Kilograms:
                return Units.KILOGRAMS;
            // Volume units
            case Gallons:
                return Units.GALLONS;
            case Quarts:
                return Units.QUARTS;
            case Pints:
                return Units.PINTS;
            case FluidOunces:
                return Units.FLUID_OUNCES;
            case Liters:
                return Units.LITERS;
            // Count units
            case Count:
                return Units.COUNT;
        }
        throw new HITException(Severity.WARNING, "Couldn't convert from Quantity.Units to SizeUnits.");
    }

    /**
     *
     * @param u	The Units to convert from
     * @return	The SizeUnits equivalent from the given Units
     * @throws HITException if the conversion failed.
     */
    public static SizeUnits unitsToSizeUnits(Units u) throws HITException {
        switch (u) {
            case COUNT:
                return SizeUnits.Count;
            case POUNDS:
                return SizeUnits.Pounds;
            case OUNCES:
                return SizeUnits.Ounces;
            case GRAMS:
                return SizeUnits.Grams;
            case KILOGRAMS:
                return SizeUnits.Kilograms;
            case GALLONS:
                return SizeUnits.Gallons;
            case QUARTS:
                return SizeUnits.Quarts;
            case PINTS:
                return SizeUnits.Pints;
            case FLUID_OUNCES:
                return SizeUnits.FluidOunces;
            case LITERS:
                return SizeUnits.Liters;
        }
        throw new HITException(Severity.WARNING, "Couldn't convert from SizeUnits to Quantity.Units.");
    }
}
