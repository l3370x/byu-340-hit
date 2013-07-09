package core.model;

import core.model.exception.HITException;
import core.model.exception.Severity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * The {@code ProductImpl} class is the default implementation of the 
 * {@link Product} interface.  The constructor(s) are (mostly) hidden.  To get
 * a Product instance use the {@link Product.Factory}.
 * 
 * 
 * @author kemcqueen
 */
class ProductImpl extends AbstractContainable<ProductContainer> implements Product {
    private Map<StorageUnit, ProductContainer> containers = new HashMap();
    private BarCode barcode;
    private Date creationDate;
    private String description;
    private Quantity size;
    private int shelfLife;
    private int quota;
    
    @Override
    public void putIn(final ProductContainer container) throws HITException {
        if (container == null){
            throw new HITException(Severity.ERROR, "Null container");
        }
        else if(container instanceof Category){
            StorageUnit unit = ((Category)container).getStorageUnit();
            if(containers.containsKey(unit)){
                ProductContainer old = containers.get(unit);
                transfer(old,container);
                return;
            }
            containers.put(((Category)container).getStorageUnit(), container);
        }
        else if(container instanceof StorageUnit){            
            StorageUnit unit = ((StorageUnit)container);
            if(containers.containsKey(unit)){
                ProductContainer old = containers.get(unit);
                transfer(old,container);
                return;
            }
            containers.put((StorageUnit)container, container);
        }
        else{
            throw new HITException(Severity.ERROR, "Container not Storage Unit"
                    + "or Category");
        }
        super.putIn(container);
    }

    @Override
    public void removeFrom(final ProductContainer container) throws HITException {
        if(container instanceof Category){
            containers.remove(((Category)container).getStorageUnit());
        }
        else if(container instanceof StorageUnit){
            containers.remove((StorageUnit)container);
        }
        else{
            throw new HITException(Severity.ERROR, "Container not Storage Unit"
                    + "or Category");
        }
        super.removeFrom(container);
    }
   
    @Override
    public boolean isContainedIn(final ProductContainer container) {
        super.isContainedIn(container);
        for(ProductContainer myContainer : containers.values()){
            if(container.equals(myContainer)){
                return true;
            }
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
        return containers.keySet();
    }

    @Override
    public Iterable<ProductContainer> getProductContainers() {
        assert true;
        return containers.values();
    }

    @Override
    public ProductContainer getProductContainer(StorageUnit unit) throws HITException{
        if (unit == null){
            throw new HITException(Severity.ERROR, "Null unit");
        }
        else if(!containers.containsKey(unit)){
            throw new HITException(Severity.ERROR, "This product doesn't exist in"
                    + "the given storage unit");
        }
        return containers.get(unit);
    }
    
}
