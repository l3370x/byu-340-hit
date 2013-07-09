package core.model;

import core.model.exception.HITException;
import core.model.exception.Severity;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@code ProductImpl} class is the default implementation of the 
 * {@link Product} interface.  The constructor(s) are (mostly) hidden.  To get
 * a Product instance use the {@link Product.Factory}.
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

    ProductImpl(BarCode barCode, String description){
        this.barcode = barCode;
        this.description = description;
    }
    
    private boolean isContainedInUnit(StorageUnit unit){
        return this.containersByStorageUnit.containsKey(unit);
    }
    
    @Override
    public void putIn(final ProductContainer container) throws HITException {
        StorageUnit unit = container.getStorageUnit();
        if (this.isContainedInUnit(unit)) {
            this.transfer(this.containersByStorageUnit.get(unit), container);
            return;
        }
        
        super.putIn(container);
        
        this.containersByStorageUnit.put(container.getStorageUnit(), container);
    }

    /*
    @Override
    public void transfer(final ProductContainer from, final ProductContainer to) 
            throws HITException {
        super.removeFrom(from);
        super.putIn(to);
        this.removeFrom(from);
        this.putIn(to);
    }
    */

    @Override
    public void removeFrom(final ProductContainer container) throws HITException {
        super.removeFrom(container);

        this.containersByStorageUnit.remove(container.getStorageUnit());
    }
   
    @Override
    public boolean isContainedIn(final ProductContainer container) {
        // if the super says I'm contained in the container then it must be true
        if (super.isContainedIn(container)) {
            return true;
        }
        
        // check to see if the container is one of my categories
        if (this.containersByStorageUnit.containsValue(container)) {
            return true;
        }

        return false;
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
    public int getShelfLifeInMonths() {
        assert true;
        return this.shelfLife;
    }

    @Override
    public void setShelfLifeInMonths(int shelfLife) throws HITException{
        if (shelfLife < 0){
            throw new HITException(Severity.ERROR, 
                    "Shelf Life must be greater than or equal to 0");
        }
        else{
            this.shelfLife = shelfLife;
        }
    }

    @Override
    public int get3MonthSupplyQuota() {
        assert true;
        return this.quota;
    }

    @Override
    public void set3MonthSupplyQuota(int quota) throws HITException{
        if (shelfLife < 0){
            throw new HITException(Severity.ERROR, 
                    "Shelf Life must be greater than or equal to 0");
        }
        else {
            this.quota = quota;
        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    public ProductContainer getProductContainer(StorageUnit unit) throws HITException{
        ProductContainer container = this.containersByStorageUnit.get(unit);
        if(null == container){
            throw new HITException(Severity.ERROR, 
                    "This product doesn't exist in the given storage unit");
        }

        return container;
    }
    
}
