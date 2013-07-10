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
        TestProductContainer container = new TestProductContainer();
        
        // create a product
        Product product = newProduct(generateItemBarCode(), "Test Product");
        
        // add the product to the product container
        container.addProduct(product);
        
        this.assertProductAdded(container, product);
    }
    
    @Test
    public void testAddDuplicateProduct() throws HITException {
        // create the product container
        TestProductContainer container = new TestProductContainer();
        
        BarCode code = BarCode.generateItemBarCode();
        
        // create the (duplicate) products
        Product product1 = newProduct(code, "Test Product");
        Product product2 = newProduct(code, "Test Product");
        
        // add the first product
        container.addProduct(product1);
        this.assertProductAdded(container, product1);
        
        // add the second product
        container.addProduct(product2);
    }
    
    private static void checkProducts(ProductContainer container, Product product, boolean shouldBeInProducts) {
        for (Product p : (Iterable<Product>) container.getProducts()) {
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

    void assertProductAdded(TestProductContainer container, Product product) {
        // make sure the product was added to the product container
        checkProducts(container, product, true);
        
        // make sure the product can be removed
        assertTrue(container.canRemoveProduct(product));
        
        // make sure the product can't be added
        assertFalse(container.canAddProduct(product));
    }
}