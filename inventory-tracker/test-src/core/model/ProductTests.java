/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.model;

import core.model.exception.HITException;
import core.model.exception.Severity;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Andrew
 */
public class ProductTests {
    @Test (expected = HITException.class)
    public void testProductCreationError() throws HITException{
            Product product = Product.Factory.newProduct(null);
    }
    
    @Test (expected = HITException.class)
    public void testProductCreationErrorDesc() throws HITException{
            Product product = Product.Factory.newProduct(
                    BarCode.generateItemBarCode(),null);
    }
    
    @Test (expected = HITException.class)
    public void testProductCreationErrorDesc2() throws HITException{
            Product product = Product.Factory.newProduct(
                    BarCode.generateItemBarCode(), "");
    }
    
    @Test
    public void testProductCreationValidBarcode() throws HITException{
        BarCode barCode = BarCode.generateItemBarCode();
        Product product = Product.Factory.newProduct(barCode);
        assertNotNull(product);
        assertEquals(product.getBarCode(), barCode);
    }
    
    @Test
    public void testProductCreationValidDescription() throws HITException{
        BarCode barCode = BarCode.generateItemBarCode();
        Product product = Product.Factory.newProduct(barCode, "Test");
        assertNotNull(product);
        assertEquals(product.getBarCode(), barCode);
        assertEquals(product.getDescription(), "Test");
    }
    
}
