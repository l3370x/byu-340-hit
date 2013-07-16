/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.model;

import static core.model.Category.Factory.newCategory;
import static core.model.StorageUnit.Factory.newStorageUnit;
import core.model.exception.HITException;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Andrew
 */
public class StorageUnitTests extends AbstractContainmentTests<StorageUnit, Category> {

    @Override
    @Test
    public void testAddWithNonAddableContent() throws HITException {
        // do nothing (disable this test)
    }
    
    @Test (expected = HITException.class)
    public void testStorageUnitCreationError() throws HITException{
        StorageUnit unit = StorageUnit.Factory.newStorageUnit(null);
    }
    
    @Test (expected = HITException.class)
    public void testStorageUnitCreationError2() throws HITException{
        StorageUnit unit = StorageUnit.Factory.newStorageUnit("");
    }
       
    @Test
    public void testStorageUnitCreationValid() throws HITException{
        StorageUnit unit = StorageUnit.Factory.newStorageUnit("unit1");
        assertNotNull(unit);
        assertEquals(unit.getName(), "unit1");
    }
    
    @Test
    public void testDoAdd() throws HITException{
        StorageUnit unit1 = (StorageUnitImpl) newStorageUnit("unit1");
        Category cat1 = newCategory("cat1");
        unit1.add(cat1);
    }

    @Override
    protected StorageUnit createContainer(Object arg) {
        try {
            return newStorageUnit(String.valueOf(arg));
        } catch (HITException ex) {
            fail(ex.getMessage());
            return null;
        }
    }

    @Override
    protected Category createContent(Object arg) {
       return CategoryAsContainerTests.constructCategory(arg);
    }
}
