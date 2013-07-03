package core.model;

import core.model.exception.HITException;

/**
 * The {@code AbstractContainable} class is the base class for implementations
 * of the {@link Containable} interface.
 * 
 * @invariant ?
 * 
 * @author kemcqueen
 */
class AbstractContainable<T extends Container> implements Containable<T> {
    @Override
    public void putIn(T container) throws HITException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void transfer(T from, T to) throws HITException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeFrom(T container) throws HITException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public T getContainer() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isContainedIn(T container) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
