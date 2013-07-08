package core.model;

import core.model.exception.HITException;

/**
 * The {@code TestContainer} class is an implementation of the {@link Container}
 * interface for testing purposes.
 * 
 * @author kmcqueen
 */
public class TestContainer extends AbstractContainer<TestContainable> {
    @Override
    protected void doAdd(TestContainable content) throws HITException {
    }

    @Override
    protected void doRemove(TestContainable content) throws HITException {
    }

    @Override
    protected boolean isAddable(TestContainable content) {
        return true == content.isAddable() && false == this.contains(content);
    }

    @Override
    protected boolean isRemovable(TestContainable content) {
        return true == this.contains(content);
    }
}
