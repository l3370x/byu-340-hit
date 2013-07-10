/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.model;

import core.model.exception.HITException;
import core.model.exception.Severity;
import java.util.Iterator;

/**
 * The {@code StorageUnit}> class represents an enclosed area where items
 * may be stored (e.g. a room, closet, pantry, or cupboard).
 * 
 * @author kemcqueen
 */
public interface StorageUnit extends ProductContainer<Category>, Containable<InventoryManager> {

    /**
     * The static {@code StorageUnit.Factory} class is used to generate valid
     * StorageUnit instances for use in the system.
     */
    public static class Factory {
        /**
         * Get a new {@link StorageUnit} instance with the given name.
         * 
         * @pre name != null
         * @pre name != getName() of any sibling Container
         * 
         * @post retval != null
         * 
         * @param name the name of the new storage unit
         * 
         * @return a new storage unit with the given name
         * 
         * @throws HITException if the storage unit could not be created for
         * any reason
         */
        public static StorageUnit newStorageUnit(String name) throws HITException {
            if(name == null){
                throw new HITException(Severity.WARNING, "Name cannot be null");
            }
            else if(name.isEmpty()){
                throw new HITException(Severity.WARNING, "Name cannot be Empty");
            }
            InventoryManager manager = InventoryManager.Factory.getInventoryManager();
            Iterable<StorageUnit> units = manager.getContents();
            for (StorageUnit unit : units){
                if(name.equals(unit.getName())){
                    throw new HITException(Severity.WARNING, "There is already a"
                            + " Storage Unit named " + name);
                }
            }
            return new StorageUnitImpl(name);
        }
    }
}
