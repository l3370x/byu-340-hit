package core.model;

import core.model.exception.HITException;
import java.io.OutputStream;

/**
 * The {@code Printable} interface defines the contract for an object that can
 * be printed to some stream.
 * 
 * @invariant
 * 
 * @author kemcqueen
 */
public interface Printable {
    /**
     * Print this object to the given output stream.
     * 
     * @pre null != stream
     * 
     * @post this object has been written to the given stream
     * 
     * @param stream the stream to which to print
     * 
     * @throws HITException if this object could not be printed to the given 
     * output stream for any reason
     */
    void print(OutputStream stream) throws HITException;
}
