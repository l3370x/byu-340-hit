package core.model;

import core.model.exception.HITException;

/**
 * The {@code Containable} interface defines the contract for an object that can
 * be stored in a {@link Container}.
 * 
 * @invariant TODO
 * 
 * @author kemcqueen
 */
public interface Containable<T extends Container> {
    /**
     * Put this item in the given container.
     * 
     * @param container the target container to which this item will be added
     * 
     * @pre container != NULL
     * 
     * @post Containable.getContainer() == container
     * 
     * @throws HITException if this object could not be added to the given 
     * container for any reason
     */
    void putIn(T container) throws HITException;
    
    
    /**
     * Transfer this item from the first container to the second container.
     * This is a convenience method and is equivalent to calling 
     * {@link #removeFrom(core.model.Container)} followed by 
     * {@link #putIn(core.model.Container)}.
     * 
     * @param from the container that currently stores this item
     * @param to the container that will store this item
     * 
     * @pre from != NULL, to != NULL
     * 
     * @post Containable.getContainer() == to
     * 
     * @throws HITException if this item could not be transferred from the
     * original container to the new container for any reason
     */
    void transfer(T from, T to) throws HITException;
    
    
    /**
     * Remove this item from the given container.
     * 
     * @param container the container from which this item is to be removed
     * 
     * @pre container != NULL
     * 
     * @post container.contains(Containable) == False
     * 
     * @throws HITException if this item could not be removed from the given
     * container for any reason
     */
    void removeFrom(T container) throws HITException;
    
    
    /**
     * Get the container that currently stores this item.
     * 
     * @pre Containable has been saved
     * 
     * @post return Container contains(Containable)
     * 
     * @return the container that currently stores this item (may be null if
     * this item has not yet been stored, or has been removed)
     */
    T getContainer();
    
    
    /**
     * Determine if this item is stored in the given container.
     * 
     * @pre container != NULL
     * 
     * @post if container.contains(Containable) then true else false
     * 
     * @param container the container in which this item may be stored
     * 
     * @return {@code true} if this item is stored in the given container,
     * {@code false} otherwise
     */
    boolean isContainedIn(T container);
}
