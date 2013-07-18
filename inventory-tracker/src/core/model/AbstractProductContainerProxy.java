package core.model;

import core.model.exception.HITException;

/**
 * The {@code AbstractProductContainerProxy} class is an abstract class that 
 * acts as a proxy for a {@link ProductContainer}.  All ProductContainer 
 * operations will be forwarded to a ProductContainer instance that was given at
 * construction time.
 * 
 * @author kmcqueen
 */
abstract class AbstractProductContainerProxy<T extends Containable> 
extends AbstractContainer<T> implements ProductContainer<T> {
    private final ProductContainer delegate;
    
    protected AbstractProductContainerProxy(ProductContainer delegate) {
        this.delegate = delegate;
    }

    @Override
    public void addItem(Item item) throws HITException {
        this.delegate.addItem(item);
    }

    @Override
    public void addProduct(Product product) throws HITException {
        this.delegate.addProduct(product);
    }

    @Override
    public boolean canAddItem(Item item) {
        return this.delegate.canAddItem(item);
    }

    @Override
    public boolean canAddProduct(Product product) {
        return this.delegate.canAddProduct(product);
    }

    @Override
    public boolean canRemoveItem(Item item) {
        return this.delegate.canRemoveItem(item);
    }

    @Override
    public boolean canRemoveProduct(Product product) {
        return this.delegate.canRemoveProduct(product);
    }

    @Override
    public Iterable<Item> getItems() {
        return this.delegate.getItems();
    }

    @Override
    public Iterable<Item> getItems(Product product) {
        return this.delegate.getItems(product);
    }

    @Override
    public String getName() {
        return this.delegate.getName();
    }

    @Override
    public Iterable<Product> getProducts() {
        return this.delegate.getProducts();
    }

    @Override
    public StorageUnit getStorageUnit() {
        return this.delegate.getStorageUnit();
    }

    @Override
    public void removeItem(Item item) throws HITException {
        this.delegate.removeItem(item);
    }

    @Override
    public void removeProduct(Product product) throws HITException {
        this.delegate.removeProduct(product);
    }

    @Override
    public void setName(String name) throws HITException {
        this.delegate.setName(name);
    }
    
    @Override
    public Product getProduct(BarCode barCode) {
        return this.delegate.getProduct(barCode);
    }

    @Override
    public int getItemCount(Product product) {
        return this.delegate.getItemCount(product);
    }

}
