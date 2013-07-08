package core.model;

import core.model.exception.HITException;
import core.model.exception.Severity;
import java.util.HashMap;
import java.util.Map;

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
    private Map<String, Category> categoriesByName = new HashMap<>();
    private ProductContainer<Category> container;
    private StorageUnit storageUnit;
    
    /**
     * Hidden (mostly) construct
     */
    CategoryImpl(String name) {
        super(name);
    }

    @Override
    protected void doAdd(Category category) throws HITException {
        this.categoriesByName.put(category.getName(), category);
    }

    @Override
    protected void doRemove(Category category) throws HITException {
        this.categoriesByName.remove(category.getName());
    }

    @Override
    public boolean canAdd(Category category) {
        return false == this.contains(category) && 
                false == this.categoriesByName.containsKey(category.getName());
    }

    @Override
    public boolean canRemove(Category category) {
        return this.contains(category);
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
        return this.storageUnit;
    }

    @Override
    public void putIn(ProductContainer<Category> container) throws HITException {
        this.container = container;
        container.add(this);
        
        if (container instanceof StorageUnit) {
            this.storageUnit = (StorageUnit) container;
        }
    }

    @Override
    public void transfer(ProductContainer<Category> from, ProductContainer<Category> to) throws HITException {
        throw new HITException(Severity.INFO, 
                "Categories (or product groups) are not transferable");
    }

    @Override
    public void removeFrom(ProductContainer<Category> container) throws HITException {
        if (container == this.getContainer()) {
            this.container = null;
            this.storageUnit = null;
        }
        container.remove(this);
    }

    @Override
    public ProductContainer<Category> getContainer() {
        return this.container;
    }

    @Override
    public boolean isContainedIn(ProductContainer<Category> container) {
        return container == this.container && container.contains(this);
    }
}
