package core.model;

import core.model.exception.HITException;

/**
 * The {@code TestContainable} class is an implementation of the {@link Containable}
 * interface for testing purposes.
 * 
 * @author kmcqueen
 */
public class TestContainable extends AbstractContainable<TestContainer> {
    private final boolean addable;
    
    /**
     * Create a new {@code TestContainaable} instance that is (or is not) addable
     * to a {@link Containable) instance according to the given "addable" value.
     * 
     * @param addable value that indicates whether or not this containable can or
     * can not be added to a container
     */
    public TestContainable(boolean addable) {
        this.addable = addable;
    }
    
    /**
     * Determine if this {@link Containable} can be added to a {@link Container}.
     * 
     * @return {@code true} if this containable can be added to a container, 
     * {@code false} otherwise
     */
    public boolean isAddable() {
        return this.addable;
    }

    @Override
    protected void verifyContainedIn(TestContainer container) throws HITException {
        verifyContains(container, this);
    }

    @Override
    protected void verifyNotContainedIn(TestContainer container) throws HITException {
        verifyDoesNotContain(container, this);
    }
}
