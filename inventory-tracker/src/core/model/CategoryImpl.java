package core.model;

import core.model.exception.HITException;

/**
 * The {@code CategoryImpl} class is the default implementation of the 
 * {@link Category} interface.  The constructor for this class is (mostly)
 * hidden.  To get an instance of {@link Category} you must use the 
 * {@link Category.Factory}.
 * 
 * @invariant getStorageUnit() != null
 * 
 * @author kemcqueen
 */
class CategoryImpl extends AbstractProductContainer<Category> implements Category {
    /**
     * Hidden (mostly) construct
     */
    CategoryImpl(String name) {
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
    public boolean canAdd(Category content) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean canRemove(Category content) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Quantity get3MonthSupplyQuantity() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void set3MonthSupplyQuantity(Quantity quantity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public StorageUnit getStorageUnit() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void putIn(Container<Category> container) throws HITException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void transfer(Container<Category> from, Container<Category> to) throws HITException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeFrom(Container<Category> container) throws HITException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Container<Category> getContainer() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isContainedIn(Container<Category> container) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
