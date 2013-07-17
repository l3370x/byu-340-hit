package core.model;

import core.model.exception.HITException;
import core.model.exception.HITException.Severity;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Observer;

/**
 * The {@code Container} interface defines the contract for an object that
 * may contain a collection of {@link Containable} objects.
 * 
 * @invariant size() >= 0
 * 
 * @author kemcqueen
 */
public interface Container<T extends Containable> extends Serializable, Observer {
    /**
     * Get all of the objects contained by this container.
     * 
     * @pre
     * 
     * @post each item in returned Iterable<T> isContained in Container, length
     * of returned Iterable<T> == Container.size
     * 
     * @return all the objects contained by this container
     */
    Iterable<T> getContents();
    
    
    /**
     * Add the given object to this container.
     * 
     * @pre canAdd(content)
     * 
     * @post this.contains(content) == true && this.size() == old(this.size() + 1) 
     * && content.getContainer() == this && content.isContainedIn(this) == true
     * 
     * @param content the object to be added
     * 
     * @throws HITException if the content could not be added for any reason.
     * The severity of the thrown exception will be {@link Severity#INFO if the
     * content has already been added to this container.
     */
    void add(T content) throws HITException;
    
    
    /**
     * Determine if the given object may be added to this container.
     * 
     * @pre content != null
     * 
     * @param content the candidate
     * 
     * @return {@code true} if the given object may be added to this 
     * container, {@code false} otherwise
     */
    boolean canAdd(T content);
    
    
    /**
     * Remove the given object from this container.
     * 
     * @pre canRemove(content) == true
     * 
     * @post this.contains(content) == false && this.size() == old.size() - 1 && 
     * null == content.getContainer() && content.isContainedIn(this) == false
     * 
     * @param content the object to be removed
     */
    void remove(T content) throws HITException;
    
    
    /**
     * Determine if the given object can be removed from this container.
     * 
     * @pre content != null
     * 
     * @param content the candidate
     * 
     * @return {@code true} if the given object can be removed from this
     * container, {@code false} otherwise
     */
    boolean canRemove(T content);
    
    
    /**
     * Determine if the given object is in this container.
     * 
     * @pre content != null
     * 
     * @param content the candidate
     * 
     * @return {@code true} if the given object is contained in this
     * container, {@code false} otherwise
     */
    boolean contains(T content);
    
    
    /**
     * Determine if this container has any content.
     * 
     * @pre none
     * 
     * @return {@code true} if this container has contents, 
     * {@code false} otherwise
     */
    boolean hasContent();
    
    
    /**
     * Get the number of objects in this container.  If {@link #hasContent()}
     * returns {@code true} then this method should return a positive
     * integer, otherwise it should return zero (0).
     * 
     * @pre none
     * 
     * @return a positive integer representing the number of objects in this
     * container (if {@link #hasContent} returns {@code true}), or 0 (if
     * {@link #hasContents} returns {@code false})
     */
    int size();
    
    
    /**
     * Add the given {@code Observer} to this container such that it will be 
     * notified of changes to this container.  The observer will be notified 
     * when contents are added to or removed from this container.
     * 
     * @param observer the observer to be added
     */
    void addObserver(Observer observer);
    
    
    /**
     * Remove the given {@code Observer} from this container such that it will
     * no longer be notified of changes to this container.
     * 
     * @param observer the observer to be removed
     */
    void deleteObserver(Observer observer);
    
    
    /**
     * Get a {@link Comparator} capable of sorting the contents of this 
     * container.  The comparator will be used to sort the contents before
     * returning them from the {@link #getContents()} method as needed.  If the
     * contents should not be sorted this method should return a {@code null}
     * value.
     * 
     * @return a comparator instance capable of sorting the contents, or null if
     * the contents should not be sorted
     */
    Comparator<T> getComparator();
    
    
    /**
     * Return the index (position) of the given content within this container.
     * The returned position is relative to the other content of this container
     * when sorted by the container's {@link #getComparator() comparator}.
     * 
     * @param content the content whose position should be found
     * 
     * @return the relative position of the given content within the sorted
     * contents of this container, or -1 if the content is not in this container
     */
    int indexOf(T content);
}
