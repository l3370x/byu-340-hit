/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.model;

import core.model.exception.HITException;
import static core.model.ProductContainerTests.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert.*;

/**
 *
 * @author Andrew
 */
public class ProductImplTests {
    
    private StorageUnit unit1;
    private StorageUnit unit2;
    private Category cat1;
    private Category cat2;
    private Category cat3;
    private Category cat4;
    
    @Before
    public void setUp() throws HITException{
        unit1 = StorageUnit.Factory.newStorageUnit("unit1");
        unit2 = StorageUnit.Factory.newStorageUnit("unit2");
        cat1 = Category.Factory.newCategory("cat1");
        unit1.add(cat1);
        cat2 = Category.Factory.newCategory("cat2");
        unit2.add(cat2);
        cat3 = Category.Factory.newCategory("cat3");
        unit2.add(cat3);
        cat4 = Category.Factory.newCategory("cat4");
        cat3.add(cat4);
    }
    
    @Test
    public void testWasAddedTo() throws HITException{
        Product prod1 = Product.Factory.newProduct(BarCode.generateItemBarCode());
        unit1.addProduct(prod1);

        assertProductAdded(unit1, prod1);
    }
    
    @Test
    public void testTransferProduct() throws HITException {
        Product prod1 = Product.Factory.newProduct(BarCode.generateItemBarCode());
        unit1.addProduct(prod1);

        assertProductAdded(unit1, prod1);
        
        prod1.transfer(unit1, unit2);
        
        assertProductRemoved(unit1, prod1);
        assertProductAdded(unit2, prod1);
    }
    
//    @Override
//    public void putIn(final ProductContainer container) throws HITException {
//        StorageUnit unit = container.getStorageUnit();
//        if (this.isContainedInUnit(unit)) {
//            this.transfer(this.containersByStorageUnit.get(unit), container);
//            return;
//        }
//        
//        super.putIn(container);
//        
//        this.containersByStorageUnit.put(container.getStorageUnit(), container);
//    }
//
//    /*
//    @Override
//    public void transfer(final ProductContainer from, final ProductContainer to) 
//            throws HITException {
//        super.removeFrom(from);
//        super.putIn(to);
//        this.removeFrom(from);
//        this.putIn(to);
//    }
//    */
//
//    @Override
//    public void removeFrom(final ProductContainer container) throws HITException {
//        super.removeFrom(container);
//
//        this.containersByStorageUnit.remove(container.getStorageUnit());
//    }
//   
//    @Override
//    public boolean isContainedIn(final ProductContainer container) {
//        // if the super says I'm contained in the container then it must be true
//        if (super.isContainedIn(container)) {
//            return true;
//        }
//        
//        // check to see if the container is one of my categories
//        if (this.containersByStorageUnit.containsValue(container)) {
//            return true;
//        }
//
//        return false;
//    }
//
//    @Override
//    public Date getCreationDate() {
//        assert true;
//        return this.creationDate;
//    }
//
//    @Override
//    public BarCode getBarCode() {
//        assert true;
//        return this.barcode;
//    }
//
//    @Override
//    public String getDescription() {
//        assert true;
//        return this.description;
//    }
//
//    @Override
//    public Quantity getSize() {
//        assert true;
//        return this.size;
//    }
//
//    @Override
//    public int getShelfLifeInMonths() {
//        assert true;
//        return this.shelfLife;
//    }
//
//    @Override
//    public void setShelfLifeInMonths(int shelfLife) throws HITException{
//        if (shelfLife < 0){
//            throw new HITException(Severity.ERROR, 
//                    "Shelf Life must be greater than or equal to 0");
//        }
//        else{
//            this.shelfLife = shelfLife;
//        }
//    }
//
//    @Override
//    public int get3MonthSupplyQuota() {
//        assert true;
//        return this.quota;
//    }
//
//    @Override
//    public void set3MonthSupplyQuota(int quota) throws HITException{
//        if (shelfLife < 0){
//            throw new HITException(Severity.ERROR, 
//                    "Shelf Life must be greater than or equal to 0");
//        }
//        else {
//            this.quota = quota;
//        }
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public Iterable<StorageUnit> getStorageUnits() {
//        assert true;
//        return Collections.unmodifiableSet(this.containersByStorageUnit.keySet());
//    }
//
//    @Override
//    public Iterable<ProductContainer> getProductContainers() {
//        assert true;
//        return Collections.unmodifiableCollection(this.containersByStorageUnit.values());
//    }
//
//    @Override
//    public ProductContainer getProductContainer(StorageUnit unit) throws HITException{
//        ProductContainer container = this.containersByStorageUnit.get(unit);
//        if(null == container){
//            throw new HITException(Severity.ERROR, 
//                    "This product doesn't exist in the given storage unit");
//        }
//
//        return container;
//    }
}
