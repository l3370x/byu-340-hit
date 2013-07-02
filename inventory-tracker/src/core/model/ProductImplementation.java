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
public class ProductImplementation implements Product{

    @Override
    public Date getCreationDate() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public BarCode getBarCode() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getDescription() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Quantity getSize() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getShelfLifeInMonths() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setShelfLifeInMonths(int shelfLife) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int get3MonthSupplyQuota() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void set3MonthSupplyQuota(int quota) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Iterable<Container> getContainers() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Container<Product> getContainer() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void putIn(Container<Product> container) throws HITException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void transfer(Container<Product> from, Container<Product> to) throws HITException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeFrom(Container<Product> container) throws HITException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isContainedIn(Container<Product> container) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
