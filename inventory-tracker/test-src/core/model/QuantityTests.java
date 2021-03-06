/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.model;

import core.model.exception.HITException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Andrew
 */
public class QuantityTests {
    
    @Test (expected = HITException.class)
    public void testBadValue() throws HITException{
        float num = (float) -0.1;
        Quantity quant = new Quantity(num, Quantity.Units.PINTS);
    }
    
    @Test (expected = HITException.class)
    public void testBadQuant() throws HITException{
        float num = (float) 0.1;
        Quantity quant = new Quantity(num, null);
    }
    
    @Test 
    public void testValid() throws HITException{
        float num = (float) 0.1;
        Quantity quant = new Quantity(num, Quantity.Units.COUNT);
        assertNotNull(quant);
        assertEquals(quant.getValue(), num, 2);
        assertEquals(quant.getUnits(), Quantity.Units.COUNT);
    }
}
