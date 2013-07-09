package core.model;

import core.model.exception.HITException;
import core.model.exception.Severity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * The {@code ProductImpl} class is the default implementation of the 
 * {@link Product} interface.  The constructor(s) are (mostly) hidden.  To get
 * a Product instance use the {@link Product.Factory}.
 * 
 * 
 * @author kemcqueen
 */
class ProductImpl extends AbstractContainable<ProductContainer> implements Product {
    private List<Category> categories = new ArrayList();
    private List<StorageUnit> units = new ArrayList();
    private BarCode barcode;
    private Date creationDate;
    private String description;
    private Quantity size;
    private int shelfLife;
    private int quota;
    
    private boolean isContainedInUnit(StorageUnit unit){
        Iterator unitIterator = units.iterator();
        while(unitIterator.hasNext()){
            if(unit.equals(unitIterator.next())){
                return true;
            }
        }
        return false;
    }
    
    private ProductContainer findProductContainerInUnit(StorageUnit unit){
        Iterator containerIterator = categories.iterator();
        while(containerIterator.hasNext()){
            Category category = (Category)containerIterator.next();
            if(unit.equals(category.getStorageUnit())){
                return category;
            }
        }
        return unit;
    }
    
    @Override
    public void putIn(final ProductContainer container) throws HITException {
        if(container instanceof Category){
            StorageUnit unit = ((Category)container).getStorageUnit();
            if(isContainedInUnit(unit)){
                ProductContainer old = findProductContainerInUnit(unit);
                transfer(old,container);
                return;
            }
            categories.add((Category)container);
            units.add(((Category)container).getStorageUnit());
        }
        else if(container instanceof StorageUnit){
            units.add((StorageUnit)container);
        }
        else{
            throw new HITException(Severity.ERROR, "Container not Storage Unit"
                    + "or Category");
        }
        super.putIn(container);
    }

    @Override
    public void transfer(final ProductContainer from, final ProductContainer to) 
            throws HITException {
        super.removeFrom(from);
        super.putIn(to);
        this.removeFrom(from);
        this.putIn(to);
    }

    @Override
    public void removeFrom(final ProductContainer container) throws HITException {
        if(container instanceof Category){
            categories.remove((Category)container);
            units.remove(((Category)container).getStorageUnit());
        }
        else if(container instanceof StorageUnit){
            units.remove((StorageUnit)container);
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
        Iterator containerIterator = categories.iterator();
        while(containerIterator.hasNext()){
            if(container.equals(containerIterator.next())){
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
        return units;
    }

    @Override
    public Iterable<Category> getCategories() {
        assert true;
        return Collections.unmodifiableList(this.categories);
    }

    @Override
    public ProductContainer getProductContainer(StorageUnit unit) throws HITException{
        if(!units.contains(unit)){
            throw new HITException(Severity.ERROR, "This product doesn't exist "
                    + "the given storage unit");
        }
        Iterator categoryIterator = categories.iterator();
        while(categoryIterator.hasNext()){
            Category category = (Category) categoryIterator.next();
            if(unit.equals(category.getStorageUnit())){
                return category;
            }
        }
        return unit;
    }
    
}
