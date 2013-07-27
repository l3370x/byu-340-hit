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
        this.sortContentsIfNecessary();
        
        return Collections.unmodifiableList(new ArrayList<>(this.contents));
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
        this.requiresSort(true);
        
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
    public int indexOf(T content) {
        this.sortContentsIfNecessary();
        
        return this.contents.indexOf(content);
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
        if (arg instanceof ModelNotification) {
            this.handleModelNotification((ModelNotification) arg);
        }
        
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
        this.updateAddedContent(content);
        
        // notify observers that content has been added
        this.notifyObservers(new ModelNotification(CONTENT_ADDED, this, content));
        
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
        this.updateRemovedContent(content);
        
        // notify observers that content has been added
        this.notifyObservers(new ModelNotification(CONTENT_REMOVED, this, content));
        
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

    private void sortContentsIfNecessary() {
        if (this.requiresSort) {
            Comparator<T> comparator = this.getComparator();
            if (null != comparator) {
                Collections.sort(this.contents, comparator);
            }
            this.requiresSort(false);
        }
    }

    private void handleModelNotification(ModelNotification notification) {
        assert null != notification;
        
        if (this != notification.getContainer()) {
            return;
        }
        
        switch (notification.getChangeType()) {
            case CONTENT_ADDED:
                this.contentWasAdded((T) notification.getContent());
                break;
                
            case CONTENT_REMOVED:
                this.contentWasRemoved((T) notification.getContent());
                break;
                
            case CONTENT_UPDATED:
                this.contentWasUpdated((T) notification.getContent());
                break;
        }
    }
    
    /**
     * Notifies this container that the given content was added to this 
     * container.  This method differs from the 
     * {@link #didAdd(core.model.Containable)} method in that this method is 
     * called as a result of receiving a {@code CONTENT_ADDED} update message.
     * This implementation does nothing.  Subclasses should not really need to
     * override this method as they will most likely override the {@code didAdd}
     * method instead.
     * 
     * @param content the content that was added
     */
    protected void contentWasAdded(T content) {
        // no op
    }
    
    /**
     * Notifies this container that the given content was removed from this 
     * container.  This method differs from the 
     * {@link #didRemove(core.model.Containable)} method in that this method is 
     * called as a result of receiving a {@code CONTENT_REMOVED} update message.
     * This implementation does nothing.  Subclasses should not really need to
     * override this method as they will most likely override the 
     * {@code didRemove} method instead.
     * 
     * @param content the content that was removed
     */
    protected void contentWasRemoved(T content) {
        // no op
    }
    
    /**
     * Notifies this container that the given content has been updated.  This 
     * indicates that some internal state has changed which this container needs
     * to be aware of.  This implementation does nothing, leaving it to
     * subclasses to override as necessary.
     * 
     * @param content the content that was updated
     */
    protected void contentWasUpdated(T content) {
        
    }

    protected void requiresSort(boolean requiresSort) {
        this.requiresSort = requiresSort;
    }

    /**
     * This method is called after the given content was added, in order to notify the content that
     * it has been added to this container.  Implementations should call the content's 
     * {@link Containable#wasAddedTo(core.model.Container) wasAddedTo} method.
     * 
     * @param content the content that was just added
     * 
     * @throws HITException 
     */
    protected void updateAddedContent(T content) throws HITException {
        // notify the content that it has been added to this container
        content.wasAddedTo(this);
    }

    /**
     * This method is called after the given content was removed, in order to notify the content 
     * that it has been removed from this container.  Implementations should call the content's 
     * {@link Containable#wasRemovedFrom(core.model.Container) wasRemovedFrom} method.
     * 
     * @param content the content that was just removed
     * 
     * @throws HITException 
     */
    protected void updateRemovedContent(T content) throws HITException {
        // notify the content that it has been removed from this container
        content.wasRemovedFrom(this);
    }
}
