package core.model;

import core.model.exception.HITException;
import core.model.exception.Severity;

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
        if (null == container) {
            throw new HITException(Severity.WARNING, 
                    "Container to be added to must not be null");
        }
        
        this.container = container;
        container.add(this);
    }

    @Override
    public void transfer(final T from, final T to) throws HITException {
        if (null == from) {
            throw new HITException(Severity.WARNING,
                    "Container transferring from must not be null");
        }
        if (null == to) {
            throw new HITException(Severity.WARNING,
                    "Container transferring to must not be null");
        }
        
        this.removeFrom(from);
        this.putIn(to);
    }

    @Override
    public void removeFrom(final T container) throws HITException {
        if (null == container) {
            throw new HITException(Severity.WARNING, 
                    "Container removing from must not be null");
        }
        
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
        if (null == container) {
            return false;
        }
        
        return container == this.container && container.contains(this);
    }
    
}
