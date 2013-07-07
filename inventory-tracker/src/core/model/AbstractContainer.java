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
    
    @Override
    public Iterable<T> getContents() {
        return Collections.unmodifiableList(this.contents);
    }

    @Override
    public void add(T content) throws HITException {
        if (false == this.canAdd(content)) {
            if (this.contains(content)) {
                throw new HITException(Severity.INFO, 
                        content + " has already been added to this container");
            } else {
                throw new HITException(Severity.ERROR, 
                        "Can't add content (" + content + ") to this container");
            }
        }
        
        // pass to subclass for overriding behavior
        this.doAdd(content);
        
        // put it in the contents
        this.contents.add(content);
        
        try {
            content.putIn(this);
        } catch (HITException e) {
            
        }
    }

    @Override
    public void remove(T content) throws HITException {
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
        
        try {
            content.removeFrom(this); 
        } catch (HITException e) {
            
        }
    }

    @Override
    public boolean contains(T content) {
        final int i = this.contents.indexOf(content);
        if (i >= 0) {
            return true;
        }
        
        return false;
    }

    @Override
    public boolean hasContent() {
        return this.size() > 0;
    }

    @Override
    public int size() {
        return this.contents.size();
    }

    protected abstract void doAdd(T content) throws HITException;
    protected abstract void doRemove(T content) throws HITException;
}
