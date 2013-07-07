package core.model;

import core.model.exception.HITException;

/**
 * The {@code AbstractContainable} class is the base class for implementations
 * of the {@link Containable} interface.
 * 
 * @invariant ?
 * 
 * @author kemcqueen
 */
class AbstractContainable<T extends Container> implements Containable<T> {
    private T container;
    
    @Override
    public void putIn(final T container) throws HITException {
        this.container = container;
        container.add(this);
    }

    @Override
    public void transfer(final T from, final T to) throws HITException {
        this.removeFrom(from);
        this.putIn(to);
    }

    @Override
    public void removeFrom(final T container) throws HITException {
        if (container == this.getContainer()) {
            this.container = null;
        }
        container.remove(this);
    }

    @Override
    public T getContainer() {
        return this.container;
    }

    @Override
    public boolean isContainedIn(final T container) {
        return container == this.container && container.contains(this);
    }
    
}
