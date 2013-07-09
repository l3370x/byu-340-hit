package core.model;

import core.model.exception.HITException;
import core.model.exception.Severity;

/**
 * The {@code Quantity} class is used to measure a product.  This class is 
 * immutable and therefore thread-safe.
 * 
 * @invariant value != null
 * @invariant unit != null
 * 
 * @author kemcqueen
 */
public class Quantity {
   private final float value;
   private final QuantityUnit unit;
   
   
   /**
    * Create a new Quantity with the given value and unit.
    * 
    * @pre float >= 0.0f && unit != null
    * 
    * @post getValue() == value && getUnit() == unit
    * 
    * @param value the value (must be >= 0.0f)
    * @param unit the unit
    */
   public Quantity(float value, QuantityUnit unit) throws HITException{
       if(value < 0.0){
           throw new HITException(Severity.WARNING, "Value must be greater than"
                   + " 0");
       }
       else if (unit == null){
           throw new HITException(Severity.WARNING, "Unit cannot be null");
       }
       this.value = value;
       this.unit = unit;
   }
   
   
   /**
    * Get the value (amount) of this quantity.
    * 
    * @pre none
    * 
    * @return  the value of this quantity
    */
   public float getValue() {
       assert true;
       return this.value;
   }
   
   
   /**
    * Get the unit of this quantity
    * 
    * @pre none
    * 
    * @return the unit of this quantity
    */
   public QuantityUnit getUnit() {
       assert true;
       return this.unit;
   }
}
