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
	
    ItemImpl() {
    }

    @Override
    public Product getProduct() {
        return this.product;
    }
    
    public void setProduct(Product p) {
    	this.product = p;
    }

    @Override
    public BarCode getBarCode() {
        return this.barcode;
    }
    
    public void setBarCode(BarCode b) {
    	this.barcode = b;
    }

    @Override
    public Date getEntryDate() {
        return this.entryDate;
    }
    
    public void setEntryDate(Date d) {
    	this.entryDate = d;
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
    public void setExpirationDate(Date d) {
    	this.expirationDate = d;
    }
    
}
