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
public enum ItemManager {
    INSTANCE;
    
    private Collection<Item> Items;
    
    public Item newItem(Product product){
        return null;
    }
    
    public Iterator<Item> getItems(Product product){
        return null;
    } 
    
    public void addItem(Item item, Container container) throws HITException{
        
    }
    
    public void transferItem(Item item, Container from, Container to) throws HITException{
        
    }
    
    public void removeItem(Item item, Container container) throws HITException{
        
    }
}
