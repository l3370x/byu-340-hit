package core.model;

import core.model.exception.HITException;
import static core.model.BarCode.generateItemBarCode;
import static core.model.Category.Factory.newCategory;
import static core.model.Item.Factory.newItem;
import static core.model.Product.Factory.newProduct;
import static core.model.ProductContainerTests.*;
import static core.model.StorageUnit.Factory.newStorageUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author kmcqueen
 */
public class ContainableTransferTests {
    private InventoryManagerImpl inventory;
    private StorageUnit su1;
    private StorageUnit su2;
    private Category cat1_1;
    private Category cat1_2;
    private Category cat2_1;
    private Category cat2_2;
    
    @Before
    public void initializeProductContainers() throws HITException {
        // create the Inventory Manager
        // not using the factory here on purpose
        this.inventory = new InventoryManagerImpl();
        
        // create storage units and add them to the inventory manager
        this.inventory.add(this.su1 = newStorageUnit("SU1"));
        this.inventory.add(this.su2 = newStorageUnit("SU2"));
        
        // create categories and add them to the storage units
        this.su1.add(this.cat1_1 = newCategory("CAT1-1"));
        this.su1.add(this.cat1_2 = newCategory("CAT1-2"));
        this.su2.add(this.cat2_1 = newCategory("CAT2-1"));
        this.su2.add(this.cat2_2 = newCategory("CAT2-2"));
    }
    
    @Test
    public void testTransferItemFromOneStorageUnitToAnother() throws HITException {
        // create and add product/item to storage unit 1
        Product product = newProduct(generateItemBarCode(), "P1");
        Item item = newItem(product, new Date());
        this.su1.addItem(item);
        
        // make sure the product and item are in SU1
        assertProductAdded(this.su1, product);
        assertItemAdded(this.su1, item);
        
        // transfer the item from SU1 to SU2
        item.transfer(this.su1, this.su2);
        
        // make sure the product and item are in SU2
        assertProductAdded(this.su2, product);
        assertItemAdded(this.su2, item);
        
        // make sure the item was removed from SU1
        assertItemRemoved(this.su1, item);
        
        // make sure the product is also still in SU1
        assertProductAdded(this.su1, product);
        
        // make sure that the product knows it's in 2 places
        List<StorageUnit> units = 
                new ArrayList<>(Arrays.asList(new StorageUnit[] { this.su1, this.su2 }));
        for (StorageUnit su : product.getStorageUnits()) {
            assertTrue(units.remove(su));
        }
        assertTrue(units.isEmpty());
    }
    
    @Test
    public void testTransferItemFromStorageUnitToCategory() throws HITException {
        // create and add product/item to storage unit 1
        Product product = newProduct(generateItemBarCode(), "P1");
        Item item = newItem(product, new Date());
        this.su1.addItem(item);
        
        // make sure the product and item are in SU1
        assertProductAdded(this.su1, product);
        assertItemAdded(this.su1, item);
        
        // transfer the item from SU1 to CAT1-1
        item.transfer(this.su1, this.cat1_1);
        
        // make sure the product and item are in CAT1-1
        assertProductAdded(this.cat1_1, product);
        assertItemAdded(this.cat1_1, item);
        
        // make sure the prodcut and item were removed from SU1
        assertProductRemoved(this.su1, product);
        assertItemRemoved(this.su1, item);

        // make sure that the product knows it's in 2 places
        List<StorageUnit> units = 
                new ArrayList<>(Arrays.asList(new StorageUnit[] { this.su1, this.su2 }));
        for (StorageUnit su : product.getStorageUnits()) {
            assertTrue(units.remove(su));
        }
        assertTrue(units.contains(this.su2));
    }
    
    @Test
    public void testTransferAllItemsFromStorageUnitToCategory() throws HITException {
        // create and add product/item to storage unit 1
        Product product = newProduct(generateItemBarCode(), "P1");
        Item item = newItem(product, new Date());
        this.su1.addItem(item);
        
        // make sure the product and item are in SU1
        assertProductAdded(this.su1, product);
        assertItemAdded(this.su1, item);
        
        // now create and add a bunch more items to the storage unit
        int numAddlItems = 10;
        for (int i = 0; i < numAddlItems; i++) {
            Item newItem = newItem(product, new Date());
            this.su1.addItem(newItem);
            assertItemAdded(this.su1, newItem);
        }
        
        // make sure SU1 has the right count of items
        assertEquals(numAddlItems + 1, this.su1.getItemCount(product));
        
        // transfer the item from SU1 to CAT1-1
        item.transfer(this.su1, this.cat1_1);
        
        // make sure the product and item are in CAT1-1
        assertProductAdded(this.cat1_1, product);
        assertItemAdded(this.cat1_1, item);
        
        // make sure the prodcut and item were removed from SU1
        assertProductRemoved(this.su1, product);
        assertItemRemoved(this.su1, item);

        // make sure that the product knows it's in 2 places
        List<StorageUnit> units = 
                new ArrayList<>(Arrays.asList(new StorageUnit[] { this.su1, this.su2 }));
        for (StorageUnit su : product.getStorageUnits()) {
            assertTrue(units.remove(su));
        }
        assertTrue(units.contains(this.su2));
        
        // make sure *all* of the items were transferred
        assertEquals(0, this.su1.getItemCount(product));
        assertEquals(numAddlItems + 1, this.cat1_1.getItemCount(product));
    }
}