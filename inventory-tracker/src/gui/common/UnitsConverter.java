package gui.common;

import core.model.Quantity.Units;
import core.model.exception.HITException;
import java.util.HashMap;
import java.util.Map;

/**
 * Class to enable simple conversion between the gui's and model's units.
 *
 * @author aaron
 *
 */
public class UnitsConverter {

    private static final Map<Units, SizeUnits> SIZE_UNITS_BY_UNITS = new HashMap<>();

    static {
        SIZE_UNITS_BY_UNITS.put(Units.COUNT, SizeUnits.Count);
        SIZE_UNITS_BY_UNITS.put(Units.FLUID_OUNCES, SizeUnits.FluidOunces);
        SIZE_UNITS_BY_UNITS.put(Units.GALLONS, SizeUnits.Gallons);
        SIZE_UNITS_BY_UNITS.put(Units.GRAMS, SizeUnits.Grams);
        SIZE_UNITS_BY_UNITS.put(Units.KILOGRAMS, SizeUnits.Kilograms);
        SIZE_UNITS_BY_UNITS.put(Units.LITERS, SizeUnits.Liters);
        SIZE_UNITS_BY_UNITS.put(Units.OUNCES, SizeUnits.Ounces);
        SIZE_UNITS_BY_UNITS.put(Units.PINTS, SizeUnits.Pints);
        SIZE_UNITS_BY_UNITS.put(Units.POUNDS, SizeUnits.Pounds);
        SIZE_UNITS_BY_UNITS.put(Units.QUARTS, SizeUnits.Quarts);
    }
    
    private static final Map<SizeUnits, Units> UNITS_BY_SIZE_UNITS = new HashMap<>();

    static {
        UNITS_BY_SIZE_UNITS.put(SizeUnits.Count, Units.COUNT);
        UNITS_BY_SIZE_UNITS.put(SizeUnits.FluidOunces, Units.FLUID_OUNCES);
        UNITS_BY_SIZE_UNITS.put(SizeUnits.Gallons, Units.GALLONS);
        UNITS_BY_SIZE_UNITS.put(SizeUnits.Grams, Units.GRAMS);
        UNITS_BY_SIZE_UNITS.put(SizeUnits.Kilograms, Units.KILOGRAMS);
        UNITS_BY_SIZE_UNITS.put(SizeUnits.Liters, Units.LITERS);
        UNITS_BY_SIZE_UNITS.put(SizeUnits.Ounces, Units.OUNCES);
        UNITS_BY_SIZE_UNITS.put(SizeUnits.Pints, Units.PINTS);
        UNITS_BY_SIZE_UNITS.put(SizeUnits.Pounds, Units.POUNDS);
        UNITS_BY_SIZE_UNITS.put(SizeUnits.Quarts, Units.QUARTS);
    }

    /**
     *
     * @param su The SizeUnits to convert from
     * @return	The Units that corresponds to the given SizeUnits
     * @throws HITException if the conversion failed
     */
    public static Units sizeUnitsToUnits(SizeUnits su) throws HITException {
        return UNITS_BY_SIZE_UNITS.get(su);
    }

    /**
     *
     * @param u	The Units to convert from
     * @return	The SizeUnits equivalent from the given Units
     * @throws HITException if the conversion failed.
     */
    public static SizeUnits unitsToSizeUnits(Units u) throws HITException {
        return SIZE_UNITS_BY_UNITS.get(u);
    }
}
