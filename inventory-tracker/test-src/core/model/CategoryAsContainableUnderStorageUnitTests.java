package core.model;

import core.model.exception.HITException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kmcqueen
 */
public class CategoryAsContainableUnderStorageUnitTests 
extends AbstractContainmentTests<StorageUnit, Category> {

    @Override
    protected void doAddContentToContainer(StorageUnit container, Category content) throws HITException {
        content.putIn(container);
    }

    @Override
    protected void doRemoveContentFromContainer(StorageUnit container, Category content) throws HITException {
        content.removeFrom(container);
    }

    @Override
    protected StorageUnit createContainer(Object arg) {
        try {
            return StorageUnit.Factory.newInstance("Storage Unit");
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