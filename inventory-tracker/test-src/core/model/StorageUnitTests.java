/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.model;

import core.model.exception.HITException;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Andrew
 */
public class StorageUnitTests {
    
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
    
}
