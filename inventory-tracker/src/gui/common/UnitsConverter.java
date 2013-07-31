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

    private static final Map<String, SizeUnits> SIZE_UNITS_BY_STRING = new HashMap<>();

    static {
        SIZE_UNITS_BY_STRING.put("count", SizeUnits.Count);
        SIZE_UNITS_BY_STRING.put("fluid ounces", SizeUnits.FluidOunces);
        SIZE_UNITS_BY_STRING.put("gallons", SizeUnits.Gallons);
        SIZE_UNITS_BY_STRING.put("grams", SizeUnits.Grams);
        SIZE_UNITS_BY_STRING.put("kilograms", SizeUnits.Kilograms);
        SIZE_UNITS_BY_STRING.put("liters", SizeUnits.Liters);
        SIZE_UNITS_BY_STRING.put("ounces", SizeUnits.Ounces);
        SIZE_UNITS_BY_STRING.put("pints", SizeUnits.Pints);
        SIZE_UNITS_BY_STRING.put("pounds", SizeUnits.Pounds);
        SIZE_UNITS_BY_STRING.put("quarts", SizeUnits.Quarts);
    }

    private static final Map<SizeUnits, String> STRING_BY_SIZE_UNITS = new HashMap<>();

    static {
        STRING_BY_SIZE_UNITS.put(SizeUnits.Count, "count");
        STRING_BY_SIZE_UNITS.put(SizeUnits.FluidOunces, "fluid ounces");
        STRING_BY_SIZE_UNITS.put(SizeUnits.Gallons, "gallons");
        STRING_BY_SIZE_UNITS.put(SizeUnits.Grams, "grams");
        STRING_BY_SIZE_UNITS.put(SizeUnits.Kilograms, "kilograms");
        STRING_BY_SIZE_UNITS.put(SizeUnits.Liters, "liters");
        STRING_BY_SIZE_UNITS.put(SizeUnits.Ounces, "ounces");
        STRING_BY_SIZE_UNITS.put(SizeUnits.Pints, "pints");
        STRING_BY_SIZE_UNITS.put(SizeUnits.Pounds, "pounds");
        STRING_BY_SIZE_UNITS.put(SizeUnits.Quarts, "quarts");
    }

    private static final Map<Units, UnitType> SIZE_UNITS_BY_TYPE = new HashMap<>();

    static {
        SIZE_UNITS_BY_TYPE.put(Units.COUNT, UnitType.Neither);
        SIZE_UNITS_BY_TYPE.put(Units.FLUID_OUNCES, UnitType.Volume);
        SIZE_UNITS_BY_TYPE.put(Units.GALLONS, UnitType.Volume);
        SIZE_UNITS_BY_TYPE.put(Units.GRAMS, UnitType.Weight);
        SIZE_UNITS_BY_TYPE.put(Units.KILOGRAMS, UnitType.Weight);
        SIZE_UNITS_BY_TYPE.put(Units.LITERS, UnitType.Volume);
        SIZE_UNITS_BY_TYPE.put(Units.OUNCES, UnitType.Weight);
        SIZE_UNITS_BY_TYPE.put(Units.PINTS, UnitType.Volume);
        SIZE_UNITS_BY_TYPE.put(Units.POUNDS, UnitType.Weight);
        SIZE_UNITS_BY_TYPE.put(Units.QUARTS, UnitType.Volume);
    }


    private static final Map<Units, Map<Units,  Double>> CONVERSION_BY_UNITS = new HashMap<>();
    static {
    	
    	CONVERSION_BY_UNITS.put(Units.FLUID_OUNCES, new HashMap<Units, Double>());
    	CONVERSION_BY_UNITS.get(Units.FLUID_OUNCES).put(Units.GALLONS, 0.007813);
    	CONVERSION_BY_UNITS.get(Units.FLUID_OUNCES).put(Units.LITERS, 0.02957);
    	CONVERSION_BY_UNITS.get(Units.FLUID_OUNCES).put(Units.PINTS, 0.0625);
    	CONVERSION_BY_UNITS.get(Units.FLUID_OUNCES).put(Units.QUARTS, 0.03125);
    	
    	CONVERSION_BY_UNITS.put(Units.GALLONS, new HashMap<Units, Double>());
    	CONVERSION_BY_UNITS.get(Units.GALLONS).put(Units.FLUID_OUNCES, 128.0);
    	CONVERSION_BY_UNITS.get(Units.GALLONS).put(Units.LITERS, 3.785);
    	CONVERSION_BY_UNITS.get(Units.GALLONS).put(Units.PINTS, 8.0);
    	CONVERSION_BY_UNITS.get(Units.GALLONS).put(Units.QUARTS, 4.0);
    	
    	CONVERSION_BY_UNITS.put(Units.GRAMS, new HashMap<Units, Double>());
    	CONVERSION_BY_UNITS.get(Units.GRAMS).put(Units.KILOGRAMS, 0.001);
    	CONVERSION_BY_UNITS.get(Units.GRAMS).put(Units.OUNCES, 0.03527);
    	CONVERSION_BY_UNITS.get(Units.GRAMS).put(Units.POUNDS, 0.0022);
    	
    	CONVERSION_BY_UNITS.put(Units.KILOGRAMS, new HashMap<Units, Double>());
    	CONVERSION_BY_UNITS.get(Units.KILOGRAMS).put(Units.GRAMS, 1000.0);
    	CONVERSION_BY_UNITS.get(Units.KILOGRAMS).put(Units.OUNCES, 35.37296);
    	CONVERSION_BY_UNITS.get(Units.KILOGRAMS).put(Units.POUNDS, 2.20462);
    	
    	CONVERSION_BY_UNITS.put(Units.LITERS, new HashMap<Units, Double>());
    	CONVERSION_BY_UNITS.get(Units.LITERS).put(Units.FLUID_OUNCES, 33.814);
    	CONVERSION_BY_UNITS.get(Units.LITERS).put(Units.GALLONS, 0.2642);
    	CONVERSION_BY_UNITS.get(Units.LITERS).put(Units.PINTS, 2.1134);
    	CONVERSION_BY_UNITS.get(Units.LITERS).put(Units.QUARTS, 1.0567);
    	
    	CONVERSION_BY_UNITS.put(Units.OUNCES, new HashMap<Units, Double>());
    	CONVERSION_BY_UNITS.get(Units.OUNCES).put(Units.KILOGRAMS, 0.02835);
    	CONVERSION_BY_UNITS.get(Units.OUNCES).put(Units.GRAMS, 28.34952);
    	CONVERSION_BY_UNITS.get(Units.OUNCES).put(Units.POUNDS, 0.0625);
    	
    	CONVERSION_BY_UNITS.put(Units.PINTS, new HashMap<Units, Double>());
    	CONVERSION_BY_UNITS.get(Units.PINTS).put(Units.LITERS, 0.4732);
    	CONVERSION_BY_UNITS.get(Units.PINTS).put(Units.FLUID_OUNCES, 16.0);
    	CONVERSION_BY_UNITS.get(Units.PINTS).put(Units.GALLONS, 0.125);
    	CONVERSION_BY_UNITS.get(Units.PINTS).put(Units.QUARTS, 0.5);
    	
    	CONVERSION_BY_UNITS.put(Units.POUNDS, new HashMap<Units, Double>());
    	CONVERSION_BY_UNITS.get(Units.POUNDS).put(Units.KILOGRAMS, 0.45359);
    	CONVERSION_BY_UNITS.get(Units.POUNDS).put(Units.GRAMS, 453.59237);
    	CONVERSION_BY_UNITS.get(Units.POUNDS).put(Units.OUNCES, 16.0);
    	
    	CONVERSION_BY_UNITS.put(Units.QUARTS, new HashMap<Units, Double>());
    	CONVERSION_BY_UNITS.get(Units.QUARTS).put(Units.LITERS,  0.9464);
    	CONVERSION_BY_UNITS.get(Units.QUARTS).put(Units.FLUID_OUNCES, 32.0);
    	CONVERSION_BY_UNITS.get(Units.QUARTS).put(Units.GALLONS, 0.25);
    	CONVERSION_BY_UNITS.get(Units.QUARTS).put(Units.PINTS, 2.0);
    }
   
    public static enum UnitType {

        Weight("weight"), Volume("volume"), Neither("none");

        private final String label;

        private UnitType(String label) {
            this.label = label;
        }

        public String getLabel() {
            return this.label;
        }
    }
   
    public static UnitType unitsToUnitType(Units u) throws HITException {
        return SIZE_UNITS_BY_TYPE.get(u);
    }

    /**
     * 
     * @param su
     *            The SizeUnits to convert from
     * @return The Units that corresponds to the given SizeUnits
     * @throws HITException
     *             if the conversion failed
     */
    public static Units sizeUnitsToUnits(SizeUnits su) throws HITException {
        return UNITS_BY_SIZE_UNITS.get(su);
    }

    /**
     * 
     * @param u
     *            The Units to convert from
     * @return The SizeUnits equivalent from the given Units
     * @throws HITException
     *             if the conversion failed.
     */
    public static SizeUnits unitsToSizeUnits(Units u) throws HITException {
        return SIZE_UNITS_BY_UNITS.get(u);
    }

    /**
     * 
     * @param value
     *            The string to convert from
     * @return The Units that corresponds to the given string
     * @throws HITException
     *             if the conversion failed
     */
    public static SizeUnits stringToUnits(String value) throws HITException {
        return SIZE_UNITS_BY_STRING.get(value.toLowerCase());
    }

    /**
     * 
     * @param value
     *            The Units to convert from
     * @return The string that corresponds to the given Units
     * @throws HITException
     *             if the conversion failed
     */
    public static String unitsToString(SizeUnits value) throws HITException {
        return STRING_BY_SIZE_UNITS.get(value);
    }
    
    /**
     * 
     * @param from
     *            The Units to convert from
     *@param to
     *			The Units to convert to            
     * @return The conversion unit between the two
     * @throws HITException
     *             if the conversion failed
     */
    public static double unitsToConstant(Units from, Units to) throws HITException {
        return CONVERSION_BY_UNITS.get(from).get(to);
    }
}
