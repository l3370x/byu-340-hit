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
public enum ProductManager {
    INSTANCE;
    
    private Collection<Product> Products;
    
    public Product newProduct(BarCodeImplementation barcode, String description) throws HITException{
        return null;
    }
    
    public Iterator<Product> getProducts(){
        return null;
    }
    
    public Iterator<Product> getProducts(StorageUnit SU){
        return null;
    }
    
    public Iterator<Product> getProducts(Container container){
        return null;
    }
    
    public void addProduct(Product product, Container container) throws HITException{
        
    }
    
    public void transferProduct(Container from, Container to, Product product) throws HITException{
        
    }
    
    public void remove(Product product, Container container) throws HITException{
        
    }
}
