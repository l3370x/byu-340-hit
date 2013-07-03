package core.model;

import core.model.exception.HITException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    public void add(T content) {
        if (this.canAdd(content)) {
            try {
                this.doAdd(content);
                this.contents.add(content);
            } catch (HITException ex) {
                Logger.getLogger(AbstractContainer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void remove(T content) {
        if (this.canRemove(content)) {
            try {
                this.doRemove(content);
                this.contents.remove(content);
            } catch (HITException ex) {
                Logger.getLogger(AbstractContainer.class.getName()).log(Level.SEVERE, null, ex);
            }
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
