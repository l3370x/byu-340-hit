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
    public void wasAddedTo(final T container) throws HITException {
        verifyContains(container, this);
        
        this.container = container;
    }

    @Override
    public void wasRemovedFrom(final T container) throws HITException {
        verifyDoesNotContain(container, this);
        
        this.container = null;
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
        
        from.remove(this);
        to.add(this);
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

    static <C extends Containable> void verifyContains
            (final Container<C> container, C content) throws HITException {
        if (null == container) {
            throw new HITException(Severity.WARNING, 
                    "Container must not be null");
        }
        
        if (false == container.contains(content)) {
            throw new HITException(Severity.WARNING, 
                    "Container (" + container +") does not contain this containable");
        }
    }

    static <C extends Containable> void verifyDoesNotContain(final Container<C> container, C content) throws HITException {
        if (null == container) {
            throw new HITException(Severity.WARNING, 
                    "Container must not be null");
        }
        
        if (content.getContainer() != container) {
            throw new HITException(Severity.WARNING, 
                    "Container (" + container + ") is not the current container");
        }
        
        if (container.contains(content)) {
            throw new HITException(Severity.WARNING, 
                    "Container (" + container + ") still contains this containable");
        }
    }
    
}
