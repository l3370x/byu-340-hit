/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.model;

import core.model.exception.HITException;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Andrew
 */
public class ProductCollectionTests {
    Product product1;
    Product product2;
    Product product3;
    Product product4;
    
    
    @Before
    public void setup() throws HITException{
        product1 = Product.Factory.newProduct(BarCode.generateItemBarCode());
        product2 = Product.Factory.newProduct(BarCode.generateItemBarCode());
        product3 = Product.Factory.newProduct(BarCode.generateItemBarCode());
        product4 = Product.Factory.newProduct(BarCode.generateItemBarCode());
    }
    @Test (expected = HITException.class)
    public void testProductCreationError() throws HITException{
            Product product = Product.Factory.newProduct(null);
    }
    
    @Test (expected = HITException.class)
    public void testProductCreationErrorDesc() throws HITException{
            Product product = Product.Factory.newProduct(
                    BarCode.generateItemBarCode(),null);
    }

    @Test
    public void testDoAdd() throws HITException{
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
