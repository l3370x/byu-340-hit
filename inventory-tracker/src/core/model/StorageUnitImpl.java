package core.model;

import core.model.exception.HITException;
import core.model.exception.Severity;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@code StorageUnitImpl} class is the default implementation of the 
 * {@link StorageUnit} interface.  The constructor(s) for this class are hidden.
 * To get a StorageUnit instance, you must use the {@link StorageUnit.Factory}.
 * 
 * @invariant name != null
 * 
 * @author kemcqueen
 */
class StorageUnitImpl extends AbstractProductContainer<Category> implements StorageUnit {
    private Map<String, Category> categoriesByName = new HashMap<>();
    private InventoryManager container;
    
    StorageUnitImpl(String name) {
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
        
        return false == this.categoriesByName.containsKey(category.getName());
    }

    @Override
    protected boolean isRemovable(Category category) {
        return false == this.canAdd(category);
    }

    @Override
    public void wasAddedTo(InventoryManager container) throws HITException {
        AbstractContainable.verifyAddedTo(container, this);
        
        this.container = container;
    }

    @Override
    public void wasRemovedFrom(InventoryManager container) throws HITException {
        AbstractContainable.verifyRemovedFrom(container, this);
        
        this.container = container;
    }

    @Override
    public void transfer(InventoryManager from, InventoryManager to) throws HITException {
        throw new HITException(Severity.ERROR, "Can't transfer a storage unit");
    }

    @Override
    public InventoryManager getContainer() {
        assert true;
        
        return this.container;
    }

    @Override
    public boolean isContainedIn(InventoryManager container) {
        if (null == container) {
            return false;
        }
        
        return container.contains(this);
    }

    @Override
    public StorageUnit getStorageUnit() {
        assert true;
        
        return this;
    }
    
}
