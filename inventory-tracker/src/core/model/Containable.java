package core.model;

import core.model.exception.HITException;
import java.io.Serializable;

/**
 * The {@code Containable} interface defines the contract for an object that can
 * be stored in a {@link Container}.
 * 
 * @invariant ??
 * 
 * @author kemcqueen
 */
public interface Containable<T extends Container> extends Serializable {
    /**
     * Notify this {@code Containable} that it has been added to the given
     * container.  This allows the containable to perform any necessary 
     * housekeeping.
     * 
     * @param container the container to which this containable has been added
     * 
     * @pre container != null && container.contains(this)
     * 
     * @post this.getContainer() == container && this.isContainedIn(container) == true
     * 
     * @throws HITException if this containable was not actually added to the 
     * given container
     */
    void wasAddedTo(T container) throws HITException;
    
    
    /**
     * Notify this {@code Containable} that it has been removed from the given
     * container.  This allows the containable to perform any necessary
     * housekeeping.
     * 
     * @param container the container from which this item has been removed
     * 
     * @pre container != null && container == getContainer() 
     * && false == container.contains(this)
     * 
     * @post getContainer() == null
     * 
     * @throws HITException if this containable was not actually removed from 
     * the given container
     */
    void wasRemovedFrom(T container) throws HITException;
    
    
    /**
     * Transfer this item from the first container to the second container.
     * This is a convenience method and is equivalent to calling 
     * {@link #wasRemovedFrom(core.model.Container)} followed by 
     * {@link #wasAddedTo(core.model.Container)}.
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
     * Get the container that currently stores this containable.
     * 
     * @pre none
     * 
     * @return the container that currently stores this item (may be null if
     * this item has not yet been stored, or has been removed)
     */
    T getContainer();
}
