package core.model;

import core.model.exception.HITException;

import java.io.Serializable;

/**
 * The {@code Containable} interface defines the contract for an object that can be stored in a
 * {@link Container}.
 *
 * @author kemcqueen
 * @invariant ??
 */
public interface Containable<T extends Container> extends Serializable {
    /**
     * Notify this {@code Containable} that it has been added to the given container.  This allows
     * the containable to perform any necessary housekeeping.
     *
     * @param container the container to which this containable has been added
     * @throws HITException if this containable was not actually added to the given container
     * @pre container != null && container.contains(this)
     * @post this.getContainer() == container && this.isContainedIn(container) == true
     */
    void wasAddedTo(T container) throws HITException;


    /**
     * Notify this {@code Containable} that it has been removed from the given container.  This
     * allows the containable to perform any necessary housekeeping.
     *
     * @param container the container from which this item has been removed
     * @throws HITException if this containable was not actually removed from the given container
     * @pre container != null && container == getContainer() && false == container.contains(this)
     * @post getContainer() == null
     */
    void wasRemovedFrom(T container) throws HITException;


    /**
     * Transfer this item from the first container to the second container. This is a convenience
     * method and is equivalent to calling {@link #wasRemovedFrom(core.model.Container)} followed by
     * {@link #wasAddedTo(core.model.Container)}.
     *
     * @param from the container that currently stores this item
     * @param to   the container that will store this item
     * @throws HITException if this item could not be transferred from the original container to the
     *                      new container for any reason
     * @pre from != NULL, to != NULL
     * @post Containable.getContainer() == to
     */
    void transfer(T from, T to) throws HITException;


    /**
     * Get the container that currently stores this containable.
     *
     * @return the container that currently stores this item (may be null if this item has not yet
     *         been stored, or has been removed)
     * @pre none
     */
    T getContainer();
}
