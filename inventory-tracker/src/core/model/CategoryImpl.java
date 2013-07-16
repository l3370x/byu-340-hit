package core.model;

import core.model.exception.HITException;
import core.model.exception.HITException.Severity;
import static core.model.AbstractContainable.*;
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
    private Quantity threeMonthSupply;
    
    /**
     * Hidden (mostly) construct
     */
    CategoryImpl(String name) {
        super(name);
    }

    @Override
    protected void doAdd(Category category) throws HITException {
        assert null != category;
        this.categoriesByName.put(category.getName(), category);
    }

    @Override
    protected void doRemove(Category category) throws HITException {
        assert null != category;
        
        this.categoriesByName.remove(category.getName());
    }

    @Override
    protected boolean isAddable(Category category) {
        assert null != category;
        
        return false == this.contains(category) && 
                false == this.categoriesByName.containsKey(category.getName());
    }

    @Override
    protected boolean isRemovable(Category category) {
        return false == this.canAdd(category);
    }

    @Override
    public Quantity get3MonthSupplyQuantity() {
        return this.threeMonthSupply;
    }

    @Override
    public void set3MonthSupplyQuantity(Quantity quantity) {
        this.threeMonthSupply = quantity;
    }

    @Override
    public StorageUnit getStorageUnit() {
        return this.storageUnit;
    }

    @Override
    public void wasAddedTo(ProductContainer<Category> container) throws HITException {
        verifyContains(container, this);

        this.container = container;
        this.storageUnit = container.getStorageUnit();
        
        this.addObserver(container);
    }

    @Override
    public void wasRemovedFrom(ProductContainer<Category> container) throws HITException {
        verifyDoesNotContain(container, this);
        
        this.container = null;
        this.storageUnit = null;
        
        this.deleteObserver(container);
    }

    @Override
    public void transfer(ProductContainer<Category> from, ProductContainer<Category> to) throws HITException {
        throw new HITException(Severity.INFO, 
                "Categories (or product groups) are not transferable");
    }

    @Override
    public ProductContainer<Category> getContainer() {
        return this.container;
    }
}
