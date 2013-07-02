package core.model;

/**
 * The <code>MultiContainable</code> interface defines the contract (extended
 * from {@link Containable}) for an object that can be contained in multiple
 * containers.  Clients of this interface should note that the 
 * {@link Containable#getContainer() getContainer} method is inherited but it 
 * should not be invoked.
 * 
 * @author kemcqueen
 */
public interface MultiContainable<T extends Container> extends Containable<T> {
    /**
     * Get all the containers that currently contain this object.
     * 
     * @return all the containers that currently contain this object
     */
    Iterable<Container> getContainers();
    
    
    /**
     * Override of {@link Containable#getContainer()}.
     * 
     * @throws UnsupportedOperationException
     */
    @Override
    T getContainer();
}
