package core.model;

import java.util.Date;

/**
 * The {@code ItemImpl} class is the default implementation of the {@link Item}
 * interface.  The constructor(s(s) are (mostly) hidden.  To get an Item 
 * instance you must use the {@link Item.Factory}.
 * 
 * @invariant ?
 * 
 * @author kemcqueen
 */
class ItemImpl extends AbstractContainable<ProductContainer> implements Item {
    ItemImpl() {
    }

    @Override
    public Product getProduct() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BarCode getBarCode() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Date getEntryDate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Date getExitDate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Date getExpirationDate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
