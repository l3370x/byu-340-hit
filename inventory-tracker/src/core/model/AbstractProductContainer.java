package core.model;

import core.model.exception.HITException;
import core.model.exception.HITException.Severity;
import static core.model.ModelNotification.ChangeType.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
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
    
    private final Container<Item> items;
    private final Container<Product> products;
    
    private Map<Product, Set<Item>> itemsByProduct = new HashMap<>();
        
    private String name;
    
    protected AbstractProductContainer(String name) {
        this.name = name;
        
        this.items = new ItemCollection(this);
        this.items.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                if (arg instanceof ModelNotification) {
                    final ModelNotification notification = 
                            (ModelNotification) arg;
                    switch (notification.getChangeType()) {
                        case CONTENT_ADDED:
                            notifyObservers(new ModelNotification(ITEM_ADDED, 
                                    notification.getPayload()));
                            break;
                            
                        case CONTENT_REMOVED:
                            notifyObservers(new ModelNotification(ITEM_REMOVED, 
                                    notification.getPayload()));
                            break;
                            
                        default:
                            break;
                    }
                }
            }
        });
        
        this.products = new ProductCollection(this);
        this.products.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                if (arg instanceof ModelNotification) {
                    final ModelNotification notification = 
                            (ModelNotification) arg;
                    switch (notification.getChangeType()) {
                        case CONTENT_ADDED:
                            notifyObservers(new ModelNotification(PRODUCT_ADDED, 
                                    notification.getPayload()));
                            break;
                            
                        case CONTENT_REMOVED:
                            notifyObservers(new ModelNotification(PRODUCT_REMOVED, 
                                    notification.getPayload()));
                            break;
                            
                        default:
                            break;
                    }
                }
            }
        });
    }
    
    protected AbstractProductContainer(String name, 
            Container<Item> itemCollection, 
            Container<Product> productCollection) {
        this.name = name;
        
        this.items = itemCollection;
        this.products = productCollection;
    }
    
    @Override
    public void addItem(Item item) throws HITException {
        // (try to) add the item
        this.items.add(item);
        
        // add the product if necessary
        if (this.products.canAdd(item.getProduct())) {
            this.products.add(item.getProduct());
        }
        
        // add the item to the items-by-product index
        this.addItemToProductIndex(item);
    }

    @Override
    public void addProduct(Product product) throws HITException {
        this.products.add(product);
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
    public Product getProduct(BarCode barCode) {
        for (Product product : this.getProducts()) {
            if (product.getBarCode().equals(barCode)) {
                return product;
            }
        }
        
        return null;
    }

    @Override
    public void removeItem(Item item) throws HITException {
        this.items.remove(item);
        this.removeItemFromProductIndex(item);
    }

    @Override
    public void removeProduct(Product product) throws HITException {
        this.products.remove(product);
    }
    
    @Override
    public void setName(String name) throws HITException {
        if (null == name || name.isEmpty()) {
            throw new HITException(Severity.WARNING, "Name must not be empty");
        }
        
        String oldName = this.getName();
        
        this.name = name;
        
        // TODO changing the name may cause integrity violations
    }

    protected void removeItemFromProductIndex(Item item) throws HITException {
        assert null != item;
        
        final Product product = item.getProduct();

        Set<Item> productItems = this.itemsByProduct.get(product);
        if (null == productItems) {
            throw new HITException(Severity.WARNING, 
                    "Unable to remove item: item's product not found");
        }

        productItems.remove(item);
    }

    protected void addItemToProductIndex(Item item) {
        assert null != item;
        
        final Product product = item.getProduct();
        
        Set<Item> productItems = this.itemsByProduct.get(product);
        if (null == productItems) {
            productItems = new HashSet<>();
            this.itemsByProduct.put(product, productItems);
        }
        
        productItems.add(item);
    }
}
