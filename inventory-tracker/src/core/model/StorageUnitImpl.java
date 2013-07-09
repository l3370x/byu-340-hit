package core.model;

import core.model.exception.HITException;
import core.model.exception.Severity;
import java.util.HashMap;
import java.util.Iterator;
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
    
    StorageUnitImpl(String name) {
        super(name);
    }
    

    @Override
    protected void doAdd(Category content) throws HITException {
        if (content == null){
            throw new HITException(Severity.ERROR, "Null category");
        }
        else if(!canAdd(content)){
            throw new HITException(Severity.ERROR, "Can't add the category");
        }
        else{
            this.add(content);
        }
    }

    @Override
    protected void doRemove(Category content) throws HITException {
        if (content == null){
            throw new HITException(Severity.ERROR, "Null category");
        }
        else if(!canRemove(content)){
            throw new HITException(Severity.ERROR, "Can't add the category");
        }
        else{
            this.remove(content);
        }
    }

    @Override
<<<<<<< HEAD
    protected boolean isAddable(Category content) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected boolean isRemovable(Category content) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
=======
    public boolean canAdd(Category content) {
        assert true;
        Iterator categoryIterator = this.getContents().iterator();
        while(categoryIterator.hasNext()){
            if(content.getName().equals(
                ((Category)categoryIterator.next()).getName())){
                return false;
            }
        }
        return !this.contains(content);
    }

    @Override
    public boolean canRemove(Category content) {
        assert true;
        return this.contains(content);
>>>>>>> Product through Storage Unit Implementation
    }

    @Override
    public void putIn(InventoryManager container) throws HITException {
        if(container == null){
            throw new HITException(Severity.ERROR, "Null manager");
        }
        else if(!container.canAdd(this)){
            throw new HITException(Severity.WARNING, "Can't add to manager");
        }
        container.add(this);
    }

    @Override
    public void transfer(InventoryManager from, InventoryManager to) throws HITException {
        throw new HITException(Severity.ERROR, "Can't transfer a storage unit");
    }

    @Override
    public void removeFrom(InventoryManager container) throws HITException {
        if(container == null){
            throw new HITException(Severity.ERROR, "Null manager");
        }
        else if(!container.canRemove(this)){
            throw new HITException(Severity.WARNING, "Can't remove from manager");
        }
        container.remove(this);
    }

    @Override
    public InventoryManager getContainer() {
        assert true;
        return InventoryManager.Factory.getInventoryManager();
    }

    @Override
    public boolean isContainedIn(InventoryManager container) {
        assert true;
        if(container.contains(this)){
            return true;
        }
        return false;
    }
    
}
