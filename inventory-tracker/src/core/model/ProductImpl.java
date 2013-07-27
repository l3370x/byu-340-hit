package core.model;

import core.model.exception.HITException;
import core.model.exception.HITException.Severity;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Observer;

/**
 * The {@code ProductImpl} class is the default implementation of the {@link Product} interface. The
 * constructor(s) are (mostly) hidden. To get a Product instance use the {@link Product.Factory}.
 *
 * @author kemcqueen
 */
class ProductImpl extends AbstractContainable<ProductContainer> implements Product {

    private Map<StorageUnit, ProductContainer> containersByStorageUnit = new HashMap<>();
    private BarCode barcode;
    private Date creationDate;
    private String description;
    private Quantity size;
    private int shelfLife;
    private int quota;

    ProductImpl(BarCode barCode, String description) {
        this.barcode = barCode;
        this.description = description;
        this.creationDate = new Date();
    }

    @Override
    public void wasAddedTo(final ProductContainer container) throws HITException {
        super.wasAddedTo(container);

        this.containersByStorageUnit.put(container.getStorageUnit(), container);
    }

    @Override
    public void wasRemovedFrom(final ProductContainer container) throws HITException {
        super.wasRemovedFrom(container);

        this.containersByStorageUnit.remove(container.getStorageUnit());
    }

    @Override
    public void transfer(ProductContainer from, ProductContainer to) throws HITException {
        // remove this product from the "old" container
        from.removeProduct(this);
        
        // transfer all of the items associated with this product to the new container
        for (Item item : (Iterable<Item>) from.getItems(this)) {
            item.transfer(from, to);
        }
        
        to.addProduct(this);
    }

    @Override
    public Date getCreationDate() {
        assert true;
        return this.creationDate;
    }

    @Override
    public BarCode getBarCode() {
        assert true;
        return this.barcode;
    }

    @Override
    public String getDescription() {
        assert true;
        return this.description;
    }

    @Override
    public Quantity getSize() {
        assert true;
        return this.size;
    }

    @Override
    public void setSize(Quantity size) {
        this.size = size;
    }

    @Override
    public int getShelfLifeInMonths() {
        assert true;
        return this.shelfLife;
    }

    @Override
    public void setShelfLifeInMonths(int shelfLife) throws HITException {
        if (shelfLife < 0) {
            throw new HITException(Severity.ERROR,
                    "Shelf Life must be greater than or equal to 0");
        } else {
            this.shelfLife = shelfLife;
        }
    }

    @Override
    public void setDescription(String description) {
		this.description = description;
	}

	@Override
    public int get3MonthSupplyQuota() {
        assert true;
        return this.quota;
    }

    @Override
    public void set3MonthSupplyQuota(int quota) throws HITException {
        if (shelfLife < 0) {
            throw new HITException(Severity.ERROR,
                    "Shelf Life must be greater than or equal to 0");
        } else {
            this.quota = quota;
        }
        //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Date getCreationDate(Date d) throws HITException {
        return new Date();//smallest date.
    }

    @Override
    public Iterable<StorageUnit> getStorageUnits() {
        assert true;
        return Collections.unmodifiableSet(this.containersByStorageUnit.keySet());
    }

    @Override
    public Iterable<ProductContainer> getProductContainers() {
        assert true;
        return Collections.unmodifiableCollection(this.containersByStorageUnit.values());
    }

    @Override
    public ProductContainer getProductContainer(StorageUnit unit) {
        ProductContainer container = this.containersByStorageUnit.get(unit);
        /*
        if (null == container) {
            throw new HITException(Severity.ERROR,
                    "This product doesn't exist in the given storage unit");
        }
        */

        return container;
    }

    @Override
    public String toString() {
        return this.description;
    }

    @Override
    protected void verifyContainedIn(ProductContainer container) throws HITException {
        if (null == container) {
            throw new HITException(HITException.Severity.WARNING, 
                    "Container must not be null");
        }
        
        if (false == container.containsProduct(this)) {
            throw new HITException(HITException.Severity.WARNING, 
                    "Container (" + container +") does not contain this product");
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
        
        if (container.containsProduct(this)) {
            throw new HITException(HITException.Severity.WARNING, 
                    "Container (" + container + ") still contains this product");
        }

    }
    
    @Override
    public void addObs(Observer o) {
    	super.addObserver(o);
    }
    
    //private void readObject(ObjectInputStream in) throws IOException {
    //	this.addObserver(getContainer());
    //}
}
