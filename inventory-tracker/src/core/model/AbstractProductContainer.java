package core.model;

import core.model.exception.HITException;
import core.model.exception.Severity;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The {@code AbstractProductContainer} class is the base class for 
 * {@link ProductContainer} implementations.
 * 
 * @invariant getName() != null
 * 
 * @author kemcqueen
 */
public abstract class AbstractProductContainer<T extends Containable> 
    extends AbstractContainer<T> implements ProductContainer<T> {
    
    private final Container<Item> items = new ItemCollection();
    private Map<Product, Set<Item>> itemsByProduct = new HashMap<>();
    
    private final Container<Product> products = new ProductCollection();
    
    private String name;
    
    protected AbstractProductContainer(String name) {
        this.name = name;
    }

    @Override
    public void addItem(Item item) throws HITException {
        if (this.canAddItem(item)) {
            this.items.add(item);
        
            this.addItemToProductIndex(item);
        }
    }

    @Override
    public void addProduct(Product product) throws HITException {
        if (this.canAddProduct(product)) {
            this.products.add(product);
        }
    }

    @Override
    public boolean canAddItem(Item item) {
        return this.items.canAdd(item);
    }

    @Override
    public boolean canAddProduct(Product product) {
        return this.products.canAdd(product);
    }

    @Override
    public boolean canRemoveItem(Item item) {
        return this.items.canRemove(item);
    }

    @Override
    public boolean canRemoveProduct(Product product) {
        return this.products.canRemove(product);
    }

    @Override
    public Iterable<Item> getItems() {
        return this.items.getContents();
    }

    @Override
    public Iterable<Item> getItems(Product product) {
        Set<Item> itemSet = this.itemsByProduct.get(product);
        if (null == itemSet) {
            return Collections.emptySet();
        }
        
        return Collections.unmodifiableSet(itemSet);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Iterable<Product> getProducts() {
        return this.products.getContents();
    }

    @Override
    public void removeItem(Item item) throws HITException {
        if (this.canRemoveItem(item)) {
            this.items.remove(item);

            this.removeItemFromProductIndex(item);
        }
    }

    @Override
    public void removeProduct(Product product) throws HITException {
        if (this.canRemoveProduct(product)) {
            this.products.remove(product);
        }
    }

    @Override
    public void setName(String name) throws HITException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    protected void removeItemFromProductIndex(Item item) throws HITException {
        final Product product = item.getProduct();

        Set<Item> productItems = this.itemsByProduct.get(product);
        if (null == productItems) {
            throw new HITException(Severity.WARNING, 
                    "Unable to remove item: item's product not found");
        }

        productItems.remove(item);
    }

    protected void addItemToProductIndex(Item item) {
        final Product product = item.getProduct();
        
        Set<Item> productItems = this.itemsByProduct.get(product);
        if (null == productItems) {
            productItems = new HashSet<>();
            this.itemsByProduct.put(product, productItems);
        }
        
        productItems.add(item);
    }

}
