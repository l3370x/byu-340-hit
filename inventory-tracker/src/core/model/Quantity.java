package core.model;

/**
 * The {@code Quantity} class is used to measure a product.
 * 
 * @author kemcqueen
 */
public class Quantity {
   private final float value;
   private final QuantityUnit unit;
   
   
   /**
    * Create a new Quantity with the given value and unit.
    * 
    * @param value the value (must be >= 0.0f)
    * @param unit the unit
    */
   public Quantity(float value, QuantityUnit unit) {
       this.value = value;
       this.unit = unit;
   }
   
   
   /**
    * Get the value (amount) of this quantity.
    * 
    * @return  the value of this quantity
    */
   public float getValue() {
       return this.value;
   }
   
   
   /**
    * Get the unit of this quantity
    * 
    * @return the unit of this quantity
    */
   public QuantityUnit getUnit() {
       return this.unit;
   }
}
