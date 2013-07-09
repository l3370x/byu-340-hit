/**
 * 
 */
package core.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import core.model.exception.HITException;
import org.junit.Test;
/**
 * @author aaron
 *
 */
public class PersistenceManagerTest {

	
	@Test
	public void testSaveAndLoad() throws HITException {
		testSave();
		testLoad();
	}
	
	
	/**
	 * Test method for {@link core.model.PersistenceManager#save()}.
	 * @throws HITException 
	 */
	public void testSave() throws HITException {
		InventoryManager i = InventoryManager.Factory.getInventoryManager();
		StorageUnit s1 = StorageUnit.Factory.newStorageUnit("su1");
		Product p1 = Product.Factory.newProduct(BarCode.generateItemBarCode(), "product 1");
		Item i1 = Item.Factory.newInstance(p1, new Date(), new Date());
		
		// s1.addItem(i1);
		// s1.addProduct(p1);
		
		StorageUnit s2 = StorageUnit.Factory.newStorageUnit("su2");
		i.add(s1);
		i.add(s2);
		// System.out.println("before save " + i.getContents().iterator().next().getName());
		PersistenceManager.INSTANCE.save();
	}

	/**
	 * Test method for {@link core.model.PersistenceManager#load()}.
	 * @throws HITException 
	 */
	public void testLoad() throws HITException {
		// clear all storageUnits before load
		InventoryManager i = InventoryManager.Factory.getInventoryManager();
		Iterator<StorageUnit> it = i.getContents().iterator();
		List<StorageUnit> units = new ArrayList<StorageUnit>();
		while (it.hasNext()) {
			units.add(it.next());
		}
		for (StorageUnit s : units) {
			i.remove(s);
		}
		units.clear();
		
		assertTrue("make sure the InventoryManager is blank before loading.",i.getContents().iterator().hasNext() == false);
		assertTrue("make sure List units is clear", units.isEmpty() == true);
		PersistenceManager.INSTANCE.load();
		
		// make list of all storage units
		it = i.getContents().iterator();
		while (it.hasNext()) {
			units.add(it.next());
		}
		
		assertTrue("make sure first storage unit is named su1.",units.get(0).getName().compareTo("su1") == 0);
		assertTrue("make sure second storage unit is named su2.",units.get(1).getName().compareTo("su2") == 0);
	}

	/**
	 * Test method for {@link core.model.PersistenceManager#update()}.
	 */
	@Test
	public void testUpdate() {
		fail("Not yet implemented"); // TODO
	}

}
