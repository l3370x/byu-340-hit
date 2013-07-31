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
}
