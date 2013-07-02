package core.model;

import java.io.Serializable;

/**
 * The {@code Container} interface defines the contract for an object that
 * may contain a collection of {@link Containable} objects.
 * 
 * @invariant ???
 * 
 * @author kemcqueen
 */
public interface Container<T extends Containable> extends Serializable {
    /**
     * Get all of the objects contained by this container.
     * 
     * @pre ???
     * 
     * @post ???
     * 
     * @return all the objects contained by this container
     */
    Iterable<T> getContents();
    
    
    /**
     * Add the given object to this container.
     * 
     * @pre ???
     * 
     * @post ???
     * 
     * @param content the object to be added
     */
    void add(T content);
    
    
    /**
     * Determine if the given object may be added to this container.
     * 
     * @pre ???
     * 
     * @post ???
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
     * @pre ???
     * 
     * @post ???
     * 
     * @param content the object to be removed
     */
    void remove(T content);
    
    
    /**
     * Determine if the given object may be removed from this container.
     * 
     * @pre ???
     * 
     * @post ???
     * 
     * @param content the candidate
     * 
     * @return {@code true} if the given object may be removed from this
     * container, {@code false} otherwise
     */
    boolean canRemove(T content);
    
    
    /**
     * Determine if the given object is in this container.
     * 
     * @pre ???
     * 
     * @post ???
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
     * @pre ???
     * 
     * @post ???
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
     * @pre ???
     * 
     * @post ???
     * 
     * @return a positive integer representing the number of objects in this
     * container (if {@link #hasContent} returns {@code true}), or 0 (if
     * {@link #hasContents} returns {@code false})
     */
    int size();
}
