/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.model;

import core.model.exception.HITException;
import core.model.exception.HITException.Severity;

/**
 * The {@code StorageUnit}> class represents an enclosed area where items may be stored (e.g. a
 * room, closet, pantry, or cupboard).
 *
 * @author kemcqueen
 */
public interface StorageUnit extends ProductContainer<Category>, Containable<InventoryManager> {

    /**
     * The static {@code StorageUnit.Factory} class is used to generate valid StorageUnit instances
     * for use in the system.
     */
    public static class Factory {
        /**
         * Get a new {@link StorageUnit} instance with the given name.
         *
         * @param name the name of the new storage unit
         * @return a new storage unit with the given name
         * @throws HITException if the storage unit could not be created for any reason
         * @pre name != null
         * @pre name != getName() of any sibling Container
         * @post retval != null
         */
        public static StorageUnit newStorageUnit(String name) throws HITException {
            if (name == null) {
                throw new HITException(Severity.WARNING,
                        "Storage Unit name must not be null");
            }

            if (name.isEmpty()) {
                throw new HITException(Severity.WARNING,
                        "Storage Unit name must not be Empty");
            }

            return new StorageUnitImpl(name);
        }
    }
}
