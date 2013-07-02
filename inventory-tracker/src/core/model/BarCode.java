package core.model;

import core.model.exception.HITException;

/**
 * The {@code BarCode} class is used to uniquely identify products and items in
 * the system.  A product's bar code (UPC) is assigned by the manufacturer.  An
 * item's bar code is assigned by the system at the time the item is entered
 * into the system.
 * 
 * @author kemcqueen
 */
public interface BarCode extends Printable {
    /**
     * Get the value of this bar code.
     * 
     * @return the value of this bar code
     */
    String getValue();
    
    
    /**
     * The {@code BarCode.Factory} class is used to generate valid bar code
     * instances for use within the system.
     */
    public static class Factory {
        /**
         * Get a new {@link BarCode} instance with the given value.  This is 
         * intended for creating an instance with a manufacturer's UPC.
         * 
         * @param value the value of the bar code
         * 
         * @return a new bar code with the given value
         * 
         * @throws HITException if the bar code could not be created for any
         * reason
         */
        public static BarCode newInstance(String value) throws HITException {
            // TODO implement
            return null;
        }
        
        
        /**
         * Get a new {@link BarCode} instance.  This is intended for generating
         * bar codes for items entered into the system.
         * 
         * @return a new bar code with an internally-generated value
         * 
         * @throws HITException if the bar code could not be created for any 
         * reason
         */
        public static BarCode newInstance() throws HITException {
            // TODO implement
            return null;
        }
    }
}
