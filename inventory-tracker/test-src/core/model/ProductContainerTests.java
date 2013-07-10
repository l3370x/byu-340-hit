package core.model;

import core.model.exception.HITException;
import static core.model.Product.Factory.newProduct;
import static core.model.BarCode.generateItemBarCode;
import java.util.Date;
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
        
        assertProductAdded(container, product);
    }
    
    @Test (expected = HITException.class)
    public void testAddDuplicateProduct() throws HITException {
        // create the product container
        TestProductContainer container = new TestProductContainer();
        
        BarCode code = BarCode.generateItemBarCode();
        
        // create the (duplicate) products
        Product product1 = newProduct(code, "Test Product");
        Product product2 = newProduct(code, "Test Product");
        
        // add the first product
        container.addProduct(product1);
        
        assertProductAdded(container, product1);
        
        // add the second product
        container.addProduct(product2);
    }
    
    @Test (expected = HITException.class)
    public void testAddNullProduct() throws HITException {
        // create the product container
        TestProductContainer container = new TestProductContainer();
        
        // add the product to the product container
        container.addProduct(null);
    }
    
    @Test
    public void testAddProductToStorageUnit() {
        //
    }
    
    @Test 
    public void testRemoveProduct() throws HITException {
        // create the product container
        TestProductContainer container = new TestProductContainer();
        
        // create a product
        Product product = newProduct(generateItemBarCode(), "Test Product");
        
        // add the product to the product container
        container.addProduct(product);
        
        // make sure it was added
        assertProductAdded(container, product);
        
        // remove the product
        container.removeProduct(product);
        
        // make sure it was removed
        assertProductRemoved(container, product);
    }
    
    @Test (expected = HITException.class)
    public void testRemoveRemovedProduct() throws HITException {
        // create the product container
        TestProductContainer container = new TestProductContainer();
        
        // create a product
        Product product = newProduct(generateItemBarCode(), "Test Product");
        
        // add the product to the product container
        container.addProduct(product);
        
        // make sure it was added
        assertProductAdded(container, product);
        
        // remove the product
        container.removeProduct(product);
        
        // make sure it was removed
        assertProductRemoved(container, product);
        
        // remove the product again
        container.removeProduct(product);
    }
    
    @Test (expected = HITException.class)
    public void testRemoveNullProduct() throws HITException {
        // create the product container
        TestProductContainer container = new TestProductContainer();
        
        // remove the product
        container.removeProduct(null);
    }
    
    @Test
    public void testAddItem() throws HITException {
        // create the container
        TestProductContainer container = new TestProductContainer();
        
        // create the product
        Product product = newProduct(generateItemBarCode(), "Test Product");
        
        // create the item
        Item item = Item.Factory.newItem(product, new Date(), new Date());
        
        // add the item
        container.addItem(item);
        
        // make sure the product was added
        assertProductAdded(container, product);
        
        // make sure the item was added
        assertItemAdded(container, item);
    }
    
    @Test (expected = HITException.class)
    public void testAddDuplicateItem() throws HITException {
        // create the container
        TestProductContainer container = new TestProductContainer();
        
        // create the product
        Product product = newProduct(generateItemBarCode(), "Test Product");
        
        // create the item
        Item item = Item.Factory.newItem(product, new Date(), new Date());
        
        // add the item
        container.addItem(item);
        
        // make sure the product was added
        assertProductAdded(container, product);
        
        // make sure the item was added
        assertItemAdded(container, item);
        
        // add the item again
        container.addItem(item);
    }
    
    @Test (expected = HITException.class)
    public void testAddNullItem() throws HITException {
        // create the container
        TestProductContainer container = new TestProductContainer();
        
        // add the item
        container.addItem(null);
    }
    
    @Test
    public void testRemoveItem() throws HITException {
        // create the container
        TestProductContainer container = new TestProductContainer();
        
        // create the product
        Product product = newProduct(generateItemBarCode(), "Test Product");
        
        // create the item
        Item item = Item.Factory.newItem(product, new Date(), new Date());
        
        // add the item
        container.addItem(item);
        
        // make sure the product was added
        assertProductAdded(container, product);
        
        // make sure the item was added
        assertItemAdded(container, item);
        
        // remove the product
        container.removeItem(item);
        
        // make sure it was removed
        assertItemRemoved(container, item);
    }
    
    @Test (expected = HITException.class)
    public void testRemoveRemovedItem() throws HITException {
        // create the container
        TestProductContainer container = new TestProductContainer();
        
        // create the product
        Product product = newProduct(generateItemBarCode(), "Test Product");
        
        // create the item
        Item item = Item.Factory.newItem(product, new Date(), new Date());
        
        // add the item
        container.addItem(item);
        
        // make sure the product was added
        assertProductAdded(container, product);
        
        // make sure the item was added
        assertItemAdded(container, item);
        
        // remove the product
        container.removeItem(item);
        
        // make sure it was removed
        assertItemRemoved(container, item);
        
        // remove the item again
        container.removeItem(item);
    }
    
    @Test (expected = HITException.class)
    public void testRemoveNullItem() throws HITException {
        TestProductContainer container = new TestProductContainer();
        
        container.removeItem(null);
    }
    
    static void checkProducts(
            ProductContainer container, Product product, boolean shouldBeInProducts) {
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

    static void checkItems(
            ProductContainer container, Item item, boolean shouldBeInItems) {
        for (Item i : (Iterable<Item>) container.getItems()) {
            if (i == item) {
                if (shouldBeInItems) {
                    return;
                }
                
                fail();
            }
        }
        
        if (shouldBeInItems) {
            fail();
        }
    }

    static void assertProductAdded(ProductContainer container, Product product) {
        // make sure the product was added to the product container
        checkProducts(container, product, true);
        
        // make sure the product can be removed
        assertTrue(container.canRemoveProduct(product));
        
        // make sure the product can't be added
        assertFalse(container.canAddProduct(product));
        
        //assertTrue(product.isContainedIn(container));
    }
    
    static void assertProductRemoved(ProductContainer container, Product product) {
        // make sure the product was added to the product container
        checkProducts(container, product, false);
        
        // make sure the product can be removed
        assertFalse(container.canRemoveProduct(product));
        
        // make sure the product can't be added
        assertTrue(container.canAddProduct(product));
    }

    static void assertItemAdded(ProductContainer container, Item item) {
        // make sure the product was added to the product container
        checkItems(container, item, true);
        
        // make sure the product can be removed
        assertTrue(container.canRemoveItem(item));
        
        // make sure the product can't be added
        assertFalse(container.canAddItem(item));
        
        //assertTrue(product.isContainedIn(container));
    }
    
    static void assertItemRemoved(ProductContainer container, Item item) {
        // make sure the product was added to the product container
        checkItems(container, item, false);
        
        // make sure the product can be removed
        assertFalse(container.canRemoveItem(item));
        
        // make sure the product can't be added
        assertTrue(container.canAddItem(item));
    }
}