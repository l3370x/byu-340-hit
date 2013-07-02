/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.model;

import core.model.exception.HITException;
import java.util.Collection;
import java.util.Iterator;

/**
 *
 * @author Andrew
 */
public enum StorageUnitManager {
    INSTANCE;
    
    private Collection<StorageUnit> StorageUnits;
    
    
    public StorageUnit newStorageUnit(String name) throws HITException{
        return null;
    }
    public void removeStorageUnit(StorageUnit SU) throws HITException{}
    
    public StorageUnit renameStorageUnit(StorageUnit SU, String name){
        return null;
    }
    
    public Integer size(){
        return null;
    }
    
    public Iterator<StorageUnit> getStorageUnits(){
        return null;
    }
    
    public StorageUnit getActiveStorageUnit(){
        return null;
    }
}
