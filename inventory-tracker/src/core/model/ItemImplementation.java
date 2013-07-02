/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.model;

import core.model.exception.HITException;
import java.util.Date;

/**
 *
 * @author Andrew
 */
public class ItemImplementation implements Item{

    @Override
    public Product getProduct() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public BarCode getBarCode() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Date getEntryDate() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Date getExitDate() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Date getExpirationDate() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void putIn(Container<Item> container) throws HITException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void transfer(Container<Item> from, Container<Item> to) throws HITException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeFrom(Container<Item> container) throws HITException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Container<Item> getContainer() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isContainedIn(Container<Item> container) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
