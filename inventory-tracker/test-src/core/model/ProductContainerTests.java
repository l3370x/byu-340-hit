package core.model;

import core.model.exception.HITException;
import static core.model.Product.Factory.newProduct;
import static core.model.BarCode.generateItemBarCode;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kmcqueen
 */
public class ProductContainerTests {
    @Test
    public void testAddProduct() throws HITException {
        // create the product container
        TestProductContainer tpc = new TestProductContainer();
        
        // create a product
        Product product = newProduct(generateItemBarCode(), "Test Product");
        
        // add the product to the product container
        tpc.addProduct(product);
        
        // make sure the product was added to the product container
        checkProducts(tpc, product, true);
        
        // make sure the product can be removed
        assertTrue(tpc.canRemoveProduct(product));
        
        // make sure the product can't be added
        assertFalse(tpc.canAddProduct(product));
    }
    
    private static void checkProducts(ProductContainer container, Product product, boolean shouldBeInProducts) {
        for (Product p : container.getProducts()) {
            if (p == product) {
                if (shouldBeInProducts) {
                    return;
                }
                
                fail();
            }
        }
        
        if (shouldBeInProducts) {
            fail();
        }
    }
}