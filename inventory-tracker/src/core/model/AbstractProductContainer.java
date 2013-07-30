package core.model;

import common.Visitable;
import core.model.exception.ExceptionHandler;
import core.model.exception.HITException;
import core.model.exception.HITException.Severity;

import java.util.*;

import static core.model.ModelNotification.ChangeType.*;

/**
 * The {@code AbstractProductContainer} class is the base class for {@link ProductContainer}
 * implementations.
 *
 * @author kemcqueen
 * @invariant getName() != null
 */
public abstract class AbstractProductContainer<T extends Containable>
        extends AbstractContainer<T> implements ProductContainer<T> {

    private final Map<String, T> contentsByString = new HashMap<>();
    private ItemCollection items;
    private ProductCollection products;

    private String name;

    protected AbstractProductContainer(String name) {
        this.name = name;

        this.initCollections(new ItemCollection(this), new ProductCollection(this));
    }

    protected AbstractProductContainer(String name,
                                       ItemCollection item, ProductCollection products) {
        this.name = name;

        this.initCollections(item, products);
    }

    private void initCollections(ItemCollection items, ProductCollection products) {
        this.items = items;
        this.items.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                if (arg instanceof ModelNotification) {
                    final ModelNotification notification =
                            (ModelNotification) arg;
                    switch (notification.getChangeType()) {
                        case CONTENT_ADDED:
                            notifyObservers(new ModelNotification(ITEM_ADDED,
                                    AbstractProductContainer.this,
                                    notification.getContent()));
                            break;

                        case CONTENT_REMOVED:
                            notifyObservers(new ModelNotification(ITEM_REMOVED,
                                    AbstractProductContainer.this,
                                    notification.getContent()));
                            break;

                        case CONTENT_UPDATED:
                            notifyObservers(new ModelNotification(ITEM_UPDATED,
                                    AbstractProductContainer.this,
                                    notification.getContent()));

                        default:
                            break;
                    }
                }
            }
        });

        this.products = products;
        this.products.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                if (arg instanceof ModelNotification) {
                    final ModelNotification notification =
                            (ModelNotification) arg;
                    switch (notification.getChangeType()) {
                        case CONTENT_ADDED:
                            notifyObservers(new ModelNotification(PRODUCT_ADDED,
                                    AbstractProductContainer.this,
                                    notification.getContent()));
                            break;

                        case CONTENT_REMOVED:
                            notifyObservers(new ModelNotification(PRODUCT_REMOVED,
                                    AbstractProductContainer.this,
                                    notification.getContent()));
                            break;


                        case CONTENT_UPDATED:
                            notifyObservers(new ModelNotification(PRODUCT_UPDATED,
                                    AbstractProductContainer.this,
                                    notification.getContent()));

                        default:
                            break;
                    }
                }
            }
        });
    }

    protected void loadObject(Container c) {
        initCollections(this.items, this.products);
        for (Item i : this.items.getItems()) {
            i.addObs(this.items);
        }
    }

    protected void loadInvMan(AbstractProductContainer i) throws HITException {
        this.items = i.items;
        this.products = i.products;
    }

    @Override
    public void addItem(Item item) throws HITException {
        if (null == item) {
            throw new HITException(Severity.INFO, "Item must not be null");
        }

        // add the product if necessary
        this.addProduct(item.getProduct());

        // (try to) add the item
        this.items.add(item);
    }

    @Override
    public void addProduct(Product product) throws HITException {
        if (null == product) {
            throw new HITException(Severity.INFO, "Product must not be null");
        }

        if (false == this.canAddProduct(product)) {
            return;
        }

        // check to see if the product is already contained with this container's storage unit
        // if so, do a transfer, otherwise just add it
        StorageUnit mySU = this.getStorageUnit();
        for (StorageUnit unit : product.getStorageUnits()) {
            // I'm doing indentity equality here on purpose
            if (unit == mySU) {
                // transfer the product from the container within this storage unit to the this
                product.transfer(product.getProductContainer(unit), this);
                return;
            }
        }

        // just add the product here as a new product in this branch
        this.doAddProduct(product);
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
    public int getItemCount() {
        return this.items.getItemCount();
    }

    @Override
    public Iterable<Item> getItems(Product product) {
        return this.items.getItems(product);
    }

    @Override
    public int getItemCount(Product product) {
        return this.items.getItemCount(product);
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

        this.name = name;
    }

    @Override
    public Comparator<T> getComparator() {
        return new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {
                return o1.toString().compareTo(o2.toString());
            }
        };
    }

    @Override
    public String toString() {
        return this.getName();
    }

    @Override
    protected boolean isAddable(T content) {
        assert null != content;

        return false == this.contains(content) &&
                false == this.contentsByString.containsKey(content.toString());
    }

    @Override
    protected void doAdd(T content) throws HITException {
        assert null != content;
        this.contentsByString.put(content.toString(), content);
    }

    @Override
    protected boolean isRemovable(T content) {
        return false == this.canAdd(content);
    }

    @Override
    protected void doRemove(T content) throws HITException {
        assert null != content;
        this.contentsByString.remove(content.toString());
    }

    @Override
    protected void contentWasUpdated(final T content) {
        assert null != content;

        // if the content was changed, it is likely that the key by which it
        // is stored has changed, so we'll need to re-key the content

        // if the content is still properly keyed, then we don't really need to
        // do anything
        if (content == this.contentsByString.get(content.toString())) {
            return;
        }

        // if we don't have the content, then we don't need to do anything
        if (false == this.contentsByString.containsValue(content)) {
            return;
        }

        // see if we can find the content in the values, if so, remove it
        Iterator<T> iter = this.contentsByString.values().iterator();
        while (iter.hasNext()) {
            if (content == iter.next()) {
                iter.remove();
                break;
            }
        }
        try {
            // add the content back in so it will be properly keyed
            this.doAdd(content);
            this.requiresSort(true);
        } catch (HITException ex) {
            ExceptionHandler.TO_LOG.reportException(ex, "Unable to update content");
        }
    }

    @Override
    public <V extends Visitable> Iterable<V> getNextToBeVisited() {
        return (Iterable<V>) this.getContents();
    }

    protected void doAddProduct(Product product) throws HITException {
        this.products.add(product);
    }

    protected void doAddItem(Item item) throws HITException {
        this.items.add(item);
    }

    @Override
    public boolean containsItem(Item item) {
        return this.items.contains(item);
    }

    @Override
    public boolean containsProduct(Product product) {
        return this.products.contains(product);
    }

    @Override
    public Item getItem(BarCode barCode) {
        return this.items.getItem(barCode);
    }
}
