/**
 * 
 */
package core.model;

import static org.junit.Assert.*;
import core.model.exception.HITException;
import org.junit.Test;
import core.model.InventoryManager;
import core.model.PersistenceManager;
import core.model.StorageUnit;
/**
 * @author aaron
 *
 */
public class PersistenceManagerTest {

	/**
	 * Test method for {@link core.model.PersistenceManager#save()}.
	 * @throws HITException 
	 */
	@Test
	public void testSave() throws HITException {
		InventoryManager i = InventoryManager.Factory.getInventoryManager();
		StorageUnit s = StorageUnit.Factory.newInstance("su1");
		i.add(s);
		System.out.println("before save " + i.getContents().iterator().next().getName());
		PersistenceManager.INSTANCE.save();
	}

	/**
	 * Test method for {@link core.model.PersistenceManager#load()}.
	 * @throws HITException 
	 */
	@Test
	public void testLoad() throws HITException {
		InventoryManager i = InventoryManager.Factory.getInventoryManager();
		System.out.println(i.getName());
		assertTrue("make sure the InventoryManager is blank before loading.",i.getContents().iterator().hasNext() == false);
		PersistenceManager.INSTANCE.load();
		System.out.println(i.getName());
		System.out.println(i.getContents().iterator().hasNext());
		assertTrue("make sure first storage unit is named su1.",i.getContents().iterator().hasNext() == true);
		assertTrue("test3",i.getContents().iterator().next().getName() != null);
	}

	/**
	 * Test method for {@link core.model.PersistenceManager#update()}.
	 */
	@Test
	public void testUpdate() {
		fail("Not yet implemented"); // TODO
	}

}
