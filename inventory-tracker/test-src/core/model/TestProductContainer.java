package core.model;

import core.model.exception.HITException;

/**
 *
 * @author kmcqueen
 */
public class TestProductContainer extends AbstractProductContainer<TestContainable> {
    private final TestContainer containerImpl = new TestContainer();
    
    public TestProductContainer() {
        this("Test Product Container");
    }
    
    public TestProductContainer(String name) {
        super(name);
    }

    @Override
    protected boolean isAddable(TestContainable content) {
        return this.containerImpl.isAddable(content);
    }

    @Override
    protected void doAdd(TestContainable content) throws HITException {
        this.containerImpl.doAdd(content);
    }

    @Override
    protected boolean isRemovable(TestContainable content) {
        return this.containerImpl.isRemovable(content);
    }

    @Override
    protected void doRemove(TestContainable content) throws HITException {
        this.containerImpl.doRemove(content);
    }

    @Override
    public StorageUnit getStorageUnit() {
        return null;
    }
    
}
