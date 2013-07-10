package core.model;

import core.model.exception.HITException;
import core.model.exception.Severity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The {@code AbstractContainer} class is an abstract implementation of the
 * {@link Container} interface intended as a base class for concrete containers.
 * 
 * @invariant size() >= 0
 * 
 * @author kemcqueen
 */
abstract class AbstractContainer<T extends Containable> implements Container<T> {
    private final List<T> contents = new ArrayList<>();
    //private final Container<T> proxy;
    /*
    protected AbstractContainer(Container<T> proxy) {
        this.proxy = proxy;
    }
    
    protected AbstractContainer() {
        this.proxy = null;
    }
    */
    
    @Override
    public final Iterable<T> getContents() {
        return Collections.unmodifiableList(this.contents);
    }
    
    @Override
    public final boolean canAdd(T content) {
        if (null == content) {
            return false;
        }
        
        return this.isAddable(content);
    }

    @Override
    public final void add(T content) throws HITException {
        if (false == this.canAdd(content)) {
            if (this.contains(content)) {
                throw new HITException(Severity.INFO, 
                        "Container already contains content: " + content);
            }

            throw new HITException(Severity.ERROR, 
                    "Unable to add content: " + content);
        }
        
        // pass to subclass for overriding behavior
        this.doAdd(content);
        
        // put it in the contents
        this.contents.add(content);
        
        // notify the content that it has been added to this container (or proxy)
        //content.wasAddedTo(null != this.proxy ? this.proxy : this);
        content.wasAddedTo(this);
    }
    
    @Override
    public final boolean canRemove(T content) {
        if (null == content) {
            return false;
        }
        
        return this.isRemovable(content);
    }

    @Override
    public final void remove(T content) throws HITException {
        if (false == this.canRemove(content)) {
            if (false == this.contains(content)) {
                throw new HITException(Severity.INFO, 
                        content + " is not contained in this container");
            } else {
                throw new HITException(Severity.ERROR, 
                        "Can't remove content (" + content +") from this container");
            }
        }

        // pass to the subclass for overriding behavior
        this.doRemove(content);
        
        // remove it from the contents
        this.contents.remove(content);
        
        // notify the content that it has been removed from this container (or proxy)
        //content.wasRemovedFrom(null != this.proxy ? this.proxy : this); 
        content.wasRemovedFrom(this); 
    }

    @Override
    public final boolean contains(T content) {
        final int i = this.contents.indexOf(content);
        if (i >= 0) {
            return true;
        }
        
        return false;
    }

    @Override
    public final boolean hasContent() {
        return this.size() > 0;
    }

    @Override
    public final int size() {
        return this.contents.size();
    }

    /**
     * This method is called by the {@link #canAdd(core.model.Containable)} 
     * method after it has performed its initial checks;
     * 
     * @param content the content to check
     * 
     * @return {@code true} if the given content can be added to this container,
     * {@code false} otherwise
     */
    protected abstract boolean isAddable(T content);
    
    /**
     * This method is called by the {@link #add(core.model.Containable)} method
     * after it has performed its initial checks.
     * 
     * @param content the content to be added
     * 
     * @throws HITException if the content could not be added for any reason
     */
    protected abstract void doAdd(T content) throws HITException;
    
    /**
     * This method is called by the {@link #canRemove(core.model.Containable)} 
     * method after it has performed its initial checks;
     * 
     * @param content the content to check
     * 
     * @return {@code true} if the given content can be removed from this 
     * container, {@code false} otherwise
     */
    protected abstract boolean isRemovable(T content);

    /**
     * This method is called by the {@link #remove(core.model.Containable)} 
     * method after it has performed its initial checks.
     * 
     * @param content the content to be removed
     * 
     * @throws HITException if the content could not be removed for any reason
     */
    protected abstract void doRemove(T content) throws HITException;
}
