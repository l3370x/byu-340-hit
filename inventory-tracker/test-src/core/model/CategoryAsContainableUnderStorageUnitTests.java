package core.model;

import core.model.exception.HITException;
import core.model.exception.Severity;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kmcqueen
 */
public class CategoryAsContainableUnderStorageUnitTests 
extends AbstractContainmentTests<StorageUnit, Category> {
    
    @Test(expected = HITException.class)
    @Override
    public void testAddWithNonAddableContent() throws HITException {
        StorageUnit container = this.createContainer("Pantry");
        Category content = this.createContent("Soup");
        Category content2 = this.createContent("Soup");
        
        try {
            this.addContainableToContainer(container, content);
            this.addContainableToContainer(container, content2);
        } catch (HITException e) {
            // we are expecting an exception with severity error
            if (Severity.ERROR == e.getSeverity()) {
                throw e;
            } else {
                fail();
            }
        }
    }    

    @Override
    protected void doAddContentToContainer(
            StorageUnit container, Category content) throws HITException {
        content.putIn(container);
    }

    @Override
    protected void doRemoveContentFromContainer(
            StorageUnit container, Category content) throws HITException {
        content.removeFrom(container);
    }

    @Override
    protected StorageUnit createContainer(Object arg) {
        try {
            return StorageUnit.Factory.newStorageUnit("Storage Unit");
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