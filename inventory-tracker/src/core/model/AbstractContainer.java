package core.model;

import core.model.exception.HITException;
import core.model.exception.HITException.Severity;
import static core.model.ModelNotification.ChangeType.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Observable;

/**
 * The {@code AbstractContainer} class is an abstract implementation of the
 * {@link Container} interface intended as a base class for concrete containers.
 * 
 * @invariant size() >= 0
 * 
 * @author kemcqueen
 */
abstract class AbstractContainer<T extends Containable> extends Observable implements Container<T> {

    private final List<T> contents = new ArrayList<>();
    private boolean requiresSort;
    
    @Override
    public final Iterable<T> getContents() {
        if (this.requiresSort) {
            Comparator<T> comparator = this.getComparator();
            if (null != comparator) {
                Collections.sort(this.contents, comparator);
            }
            this.requiresSort = false;
        }
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
        this.requiresSort = true;
        
        // perform any necessary follow-up after adding content
        this.didAdd(content);
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
            }
            
            throw new HITException(Severity.ERROR, 
                    "Can't remove content (" + content +") from this container");
        }

        // pass to the subclass for overriding behavior
        this.doRemove(content);
        
        // remove it from the contents
        this.contents.remove(content);
        
        // perform any necessary follow-up after removing content
        this.didRemove(content); 
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

    @Override
    public void update(Observable o, Object arg) {
        this.notifyObservers(arg);
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

    /**
     * This method is called after content has been added to allow for any kind
     * of specific follow-up tasks.
     * 
     * @param content the content that was added
     * 
     * @throws HITException if the follow-up task(s) fail(s) for any reason
     */
    protected void didAdd(T content) throws HITException {
        // notify the content that it has been added to this container
        content.wasAddedTo(this);
        
        // notify observers that content has been added
        this.notifyObservers(new ModelNotification(CONTENT_ADDED, content));
        
        // start observing changes to the content
        if (content instanceof Observable) {
            ((Observable) content).addObserver(this);
        }
    }

    /**
     * This method is called after content has been removed to allow for any 
     * kind of specific follow-up tasks.
     * 
     * @param content the content that was removed
     * 
     * @throws HITException if the follow-up task(s) fail(s) for any reason
     */
    protected void didRemove(T content) throws HITException {
        // notify the content that it has been removed from this container
        content.wasRemovedFrom(this);
        
        // notify observers that content has been added
        this.notifyObservers(new ModelNotification(CONTENT_REMOVED, content));
        
        // *stop* observing changes to the content
        if (content instanceof Observable) {
            ((Observable) content).deleteObserver(this);
        }
    }

    @Override
    public void notifyObservers(Object arg) {
        this.setChanged();
        
        super.notifyObservers(arg);
    }
}
