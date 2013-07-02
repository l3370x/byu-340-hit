/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.model;

import core.model.exception.HITException;
import java.io.OutputStream;

/**
 *
 * @author Andrew
 */
public class BarCodeImplementation implements BarCode{

    @Override
    public String getValue() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void print(OutputStream stream) throws HITException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
