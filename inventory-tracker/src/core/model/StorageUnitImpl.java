package core.model;

import core.model.exception.HITException;

/**
 * The {@code StorageUnitImpl} class is the default implementation of the 
 * {@link StorageUnit} interface.  The constructor(s) for this class are hidden.
 * To get a StorageUnit instance, you must use the {@link StorageUnit.Factory}.
 * 
 * @invariant name != null
 * 
 * @author kemcqueen
 */
class StorageUnitImpl extends AbstractProductContainer<Category> implements StorageUnit {
    StorageUnitImpl(String name) {
        super(name);
    }

    @Override
    protected void doAdd(Category content) throws HITException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void doRemove(Category content) throws HITException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected boolean isAddable(Category content) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected boolean isRemovable(Category content) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void putIn(InventoryManager container) throws HITException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void transfer(InventoryManager from, InventoryManager to) throws HITException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeFrom(InventoryManager container) throws HITException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public InventoryManager getContainer() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isContainedIn(InventoryManager container) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
