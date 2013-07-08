package core.model;

import core.model.exception.HITException;
import java.io.OutputStream;

/**
 * The {@code BarCodeImpl} class is the default implementation of the 
 * {@link BarCode} interface
 * @author Andrew
 */
public class BarCode {
    private final String value;
    
    /**
     * Create a new bar code with the given value.
     * 
     * @pre value != null
     * 
     * @post BarCode.value == value
     * 
     * @param value the value of the bar code
     * 
     * @throws HITException if the bar code could not be created for any reason.
     */
    public BarCode(String value) throws HITException {
        this.value = value;
    }

    /**
     * Get the string value of the bar code.
     * 
     * @pre 
     * 
     * @post return != null
     * 
     * @return the bar code value
     */
    public String getValue() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Print this bar code to the given stream.
     * 
     * @pre null != stream
     * 
     * @post this bar code has been written to the given stream
     * 
     * @param stream the stream to which to write this bar code
     * 
     * @throws HITException if this bar code could not be written to the given
     * stream for any reason.
     */
    public void print(OutputStream stream) throws HITException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
