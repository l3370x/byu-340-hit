package core.model;

import static core.model.StorageUnit.Factory.newStorageUnit;
import static core.model.Category.Factory.newCategory;
import static core.model.Product.Factory.newProduct;
import static core.model.Item.Factory.newItem;
import static core.model.BarCode.generateItemBarCode;
import core.model.exception.HITException;
import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kmcqueen
 */
public class InventoryManagerTests extends AbstractContainmentTests<InventoryManager, StorageUnit>{

    @Override
    @Test
    public void testAddWithNonAddableContent() throws HITException {
        // do nothing (disable this test)
    }
    
    @Test
    public void testInventoryManagerGetsNotifiedOfProductAddedToStorageUnit() throws HITException {
        // create the inventory manager
        InventoryManager inventory = this.createContainer(null);
        
        // create a storage unit
        StorageUnit unit = this.createContent("Storage Unit");
        
        // add the storage unit to the inventory
        this.addContainableToContainer(inventory, unit);
        
        // create a product
        Product product = newProduct(generateItemBarCode(), "Test Product");
        
        // add the product to the storage unit
        unit.addProduct(product);
        
        // make sure the the product is in the inventory manager
        this.assertInventoryManagerContainsProduct(inventory, product, true);
    }
    
    @Test
    public void testInventoryManagerGetsNotifiedOfProductAddedToCategory() throws HITException {
        // create the inventory manager
        InventoryManager inventory = this.createContainer(null);
        
        // create a storage unit
        StorageUnit unit = this.createContent("Storage Unit");
        
        // add the storage unit to the inventory
        this.addContainableToContainer(inventory, unit);
        
        // create a category
        Category category = newCategory("Category");
        
        // add the category to the storage unit
        unit.add(category);
        
        // make sure the category got added to the storage unit?
        
        // create a product
        Product product = newProduct(generateItemBarCode(), "Test Product");
        
        // add the product to the storage unit
        category.addProduct(product);
        
        // make sure the the product is in the inventory manager
        this.assertInventoryManagerContainsProduct(inventory, product, true);
    }

    @Test
    public void testInventoryManagerGetsNotifiedOfProductRemovedFromStorageUnit() throws HITException {
        // create the inventory manager
        InventoryManager inventory = this.createContainer(null);
        
        // create a storage unit
        StorageUnit unit = this.createContent("Storage Unit");
        
        // add the storage unit to the inventory
        this.addContainableToContainer(inventory, unit);
        
        // create a product
        Product product = newProduct(generateItemBarCode(), "Test Product");
        
        // add the product to the storage unit
        unit.addProduct(product);
        
        // make sure the the product is in the inventory manager
        this.assertInventoryManagerContainsProduct(inventory, product, true);
        
        // now remove the product
        unit.removeProduct(product);
        
        // make sure the product is *not* in the inventory manager
        this.assertInventoryManagerContainsProduct(inventory, product, false);
    }
    
    @Test
    public void testInventoryManagerGetsNotifiedOfProductRemovedFromCategory() throws HITException {
        // create the inventory manager
        InventoryManager inventory = this.createContainer(null);
        
        // create a storage unit
        StorageUnit unit = this.createContent("Storage Unit");
        
        // add the storage unit to the inventory
        this.addContainableToContainer(inventory, unit);
        
        // create a category
        Category category = newCategory("Category");
        
        // add the category to the storage unit
        unit.add(category);
        
        // make sure the category got added to the storage unit?
        
        // create a product
        Product product = newProduct(generateItemBarCode(), "Test Product");
        
        // add the product to the storage unit
        category.addProduct(product);
        
        // make sure the the product is in the inventory manager
        this.assertInventoryManagerContainsProduct(inventory, product, true);
        
        // now remove the product
        category.removeProduct(product);
        
        // make sure the product is *not* in the inventory manaager
        this.assertInventoryManagerContainsProduct(inventory, product, false);
    }

    @Test
    public void testInventoryManagerGetsNotifiedOfItemAddedToStorageUnit() throws HITException {
        // create the inventory manager
        InventoryManager inventory = this.createContainer(null);
        
        // create a storage unit
        StorageUnit unit = this.createContent("Storage Unit");
        
        // add the storage unit to the inventory
        this.addContainableToContainer(inventory, unit);
        
        // create a product
        Product product = newProduct(generateItemBarCode(), "Test Product");
        Item item = newItem(product, new Date(), null);
        
        // add the product to the storage unit
        unit.addItem(item);
        
        // make sure the the product is in the inventory manager
        this.assertInventoryManagerContainsProduct(inventory, product, true);
        this.assertInventoryManagerContainsItem(inventory, item, true);
    }
    
    @Test
    public void testInventoryManagerGetsNotifiedOfItemAddedToCategory() throws HITException {
        // create the inventory manager
        InventoryManager inventory = this.createContainer(null);
        
        // create a storage unit
        StorageUnit unit = this.createContent("Storage Unit");
        
        // add the storage unit to the inventory
        this.addContainableToContainer(inventory, unit);
        
        // create a category
        Category category = newCategory("Category");
        
        // add the category to the storage unit
        unit.add(category);
        
        // make sure the category got added to the storage unit?
        
        // create a product
        Product product = newProduct(generateItemBarCode(), "Test Product");
        Item item = newItem(product, new Date(), null);
        
        // add the product to the storage unit
        category.addItem(item);
        
        // make sure the the product is in the inventory manager
        this.assertInventoryManagerContainsProduct(inventory, product, true);
        this.assertInventoryManagerContainsItem(inventory, item, true);
    }

    @Test
    public void testInventoryManagerGetsNotifiedOfItemRemovedFromStorageUnit() throws HITException {
        // create the inventory manager
        InventoryManager inventory = this.createContainer(null);
        
        // create a storage unit
        StorageUnit unit = this.createContent("Storage Unit");
        
        // add the storage unit to the inventory
        this.addContainableToContainer(inventory, unit);
        
        // create a product
        Product product = newProduct(generateItemBarCode(), "Test Product");
        Item item = newItem(product, new Date(), null);
        
        // add the product to the storage unit
        unit.addItem(item);
        
        // make sure the the product is in the inventory manager
        this.assertInventoryManagerContainsProduct(inventory, product, true);
        this.assertInventoryManagerContainsItem(inventory, item, true);
        
        // now remove the item from the storage unit
        unit.removeItem(item);
        
        // make sure the product+item are *not* in the inventory manager
        this.assertInventoryManagerContainsItem(inventory, item, false);
        //this.assertInventoryManagerContainsProduct(inventory, product, false);
    }
    
    @Test
    public void testInventoryManagerGetsNotifiedOfItemRemovedFromCategory() throws HITException {
        // create the inventory manager
        InventoryManager inventory = this.createContainer(null);
        
        // create a storage unit
        StorageUnit unit = this.createContent("Storage Unit");
        
        // add the storage unit to the inventory
        this.addContainableToContainer(inventory, unit);
        
        // create a category
        Category category = newCategory("Category");
        
        // add the category to the storage unit
        unit.add(category);
        
        // make sure the category got added to the storage unit?
        
        // create a product
        Product product = newProduct(generateItemBarCode(), "Test Product");
        Item item = newItem(product, new Date(), null);
        
        // add the product to the storage unit
        category.addItem(item);
        
        // make sure the the product is in the inventory manager
        this.assertInventoryManagerContainsProduct(inventory, product, true);
        this.assertInventoryManagerContainsItem(inventory, item, true);
        
        // now remove the item from the storage unit
        category.removeItem(item);
        
        // make sure the product+item are *not* in the inventory manager
        this.assertInventoryManagerContainsItem(inventory, item, false);
        //this.assertInventoryManagerContainsProduct(inventory, product, false);
    }

    @Override
    protected InventoryManager createContainer(Object arg) {
        // just for the sake of testing, go directly to the implementation
        return new InventoryManagerImpl();
    }

    @Override
    protected StorageUnit createContent(Object arg) {
        try {
            return newStorageUnit(String.valueOf(arg));
        } catch (HITException ex) {
            fail(ex.getMessage());
            return null;
        }
    }

    private void assertInventoryManagerContainsProduct(InventoryManager inventory, Product product, boolean shouldContain) {
        boolean found = false;
        for (Product p : inventory.getProducts()) {
            if (p.equals(product)) {
                found = true;
                break;
            }
        }
        
        assertTrue(found == shouldContain);
    }
    
    private void assertInventoryManagerContainsItem(InventoryManager inventory, Item item, boolean shouldContain) {
        boolean found = false;
        for (Item i : inventory.getItems()) {
            if (i.equals(item)) {
                found = true;
                break;
            }
        }
        
        assertTrue(found == shouldContain);
    }
    
}