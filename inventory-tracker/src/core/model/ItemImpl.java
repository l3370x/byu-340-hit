package core.model;

import core.model.exception.HITException;
import java.util.Date;

import java.util.*;

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
	private Date entryDate;
	private BarCode barcode;
	private Date exitDate;
	private Date expirationDate;
	private Product product;
	
    ItemImpl(Date entryDate, Date expirationDate, Product product, BarCode barcode) {
    	this.product = product;
    	this.expirationDate = expirationDate;
    	this.entryDate = entryDate;
    	this.barcode = barcode;
    }

    @Override
    public Product getProduct() {
        return this.product;
    }
    
    
    @Override
    public BarCode getBarCode() {
        return this.barcode;
    }
    
    
    @Override
    public Date getEntryDate() {
        return this.entryDate;
    }
    

    @Override
    public Date getExitDate() {
        return this.exitDate;
    }
    
    @Override
    public void setExitDate(Date d) {
    	this.exitDate = d;
    }

    @Override
    public Date getExpirationDate() {
        return this.expirationDate;
    }

    @Override
    public void transfer(ProductContainer from, ProductContainer to) throws HITException {
        from.removeItem(this);
        to.addItem(this);
    }

    @Override
    public String toString() {
        return this.getBarCode().toString();
    }

    @Override
    protected void verifyContainedIn(ProductContainer container) throws HITException {
        if (null == container) {
            throw new HITException(HITException.Severity.WARNING, 
                    "Container must not be null");
        }
        
        if (false == container.containsItem(this)) {
            throw new HITException(HITException.Severity.WARNING, 
                    "Container (" + container +") does not contain this item");
        }
    }

    @Override
    protected void verifyNotContainedIn(ProductContainer container) throws HITException {
        if (null == container) {
            throw new HITException(HITException.Severity.WARNING, 
                    "Container must not be null");
        }
        
        if (this.getContainer() != container) {
            throw new HITException(HITException.Severity.WARNING, 
                    "Container (" + container + ") is not the current container");
        }
        
        if (container.containsItem(this)) {
            throw new HITException(HITException.Severity.WARNING, 
                    "Container (" + container + ") still contains this item");
        }
    }
    
}
