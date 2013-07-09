package core.model;

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
    
}
