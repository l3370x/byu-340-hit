/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.model;

import core.model.exception.HITException;

/**
 *
 * @author Andrew
 */
public class StorageUnitImplementation implements StorageUnit{

    @Override
    public String getName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Iterable<Product> getProducts() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addProduct(Product product) throws HITException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean canAddProduct(Product product) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeProduct(Product product) throws HITException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean canRemoveProduct(Product product) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Iterable<Item> getItems() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addItem(Item item) throws HITException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean canAddItem(Item item) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeItem(Item item) throws HITException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean canRemoveItem(Item item) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Iterable<Category> getCategories() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addCategory(Category category) throws HITException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean canAddCategory(Category category) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeCategory(Category category) throws HITException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean canRemoveCategory(Category product) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Iterable<Category> getContents() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void add(Category content) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean canAdd(Category content) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void remove(Category content) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean canRemove(Category content) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean contains(Category content) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean hasContent() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int size() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
