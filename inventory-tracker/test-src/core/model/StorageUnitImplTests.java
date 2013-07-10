/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.model;

import core.model.exception.HITException;
import core.model.exception.Severity;
import org.junit.Test;

/**
 *
 * @author Andrew
 */
public class StorageUnitImplTests {
    
    @Test
    public void testDoAdd() throws HITException{
        StorageUnitImpl unit1 = (StorageUnitImpl)StorageUnit.Factory.newStorageUnit("unit1");
        Category cat1 = Category.Factory.newCategory("cat1");
        unit1.doAdd(cat1);
    }
    
//    @Override
//    protected void doAdd(Category content) throws HITException {
//        if (content == null){
//            throw new HITException(Severity.ERROR, "Null category");
//        }
//        else if(!canAdd(content)){
//            throw new HITException(Severity.ERROR, "Can't add the category");
//        }
//        else{
//            this.add(content);
//        }
//    }
//
//    @Override
//    protected void doRemove(Category content) throws HITException {
//        if (content == null){
//            throw new HITException(Severity.ERROR, "Null category");
//        }
//        else if(!canRemove(content)){
//            throw new HITException(Severity.ERROR, "Can't add the category");
//        }
//        else{
//            this.remove(content);
//        }
//    }
//
//    @Override
//    protected boolean isAddable(Category content) {
//        assert true;
//        for(Category category : this.getContents()){
//            if(content.getName().equals(
//                category.getName())){
//                return false;
//            }
//        }
//        return !this.contains(content);
//    }
//
//    @Override
//    protected boolean isRemovable(Category content) {
//        assert true;
//        return this.contains(content);
//    }
//
//    @Override
//    public void putIn(InventoryManager container) throws HITException {
//        if(container == null){
//            throw new HITException(Severity.ERROR, "Null manager");
//        }
//        else if(!container.canAdd(this)){
//            throw new HITException(Severity.WARNING, "Can't add to manager");
//        }
//        container.add(this);
//    }
//
//    @Override
//    public void transfer(InventoryManager from, InventoryManager to) throws HITException {
//        throw new HITException(Severity.ERROR, "Can't transfer a storage unit");
//    }
//
//    @Override
//    public void removeFrom(InventoryManager container) throws HITException {
//        if(container == null){
//            throw new HITException(Severity.ERROR, "Null manager");
//        }
//        else if(!container.canRemove(this)){
//            throw new HITException(Severity.WARNING, "Can't remove from manager");
//        }
//        container.remove(this);
//    }
//
//    @Override
//    public InventoryManager getContainer() {
//        assert true;
//        return InventoryManager.Factory.getInventoryManager();
//    }
//
//    @Override
//    public boolean isContainedIn(InventoryManager container) {
//        assert true;
//        if(container.contains(this)){
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    public StorageUnit getStorageUnit() {
//        assert true;
//        return this;
//    }
}
