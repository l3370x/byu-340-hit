package core.model;

import core.model.exception.HITException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Observable;
import java.util.Set;

/**
 * The {@code ItemContainer} class is a concrete container class that manages a collection of
 * {@link Item} instances.
 *
 * @author kemcqueen
 */
public class ItemCollection extends AbstractProductContainerProxy<Item> {

    private Map<BarCode, Item> itemsByBarCode = new HashMap<>();
    private Map<Product, Set<Item>> itemsByProduct = new HashMap<>();

    public ItemCollection(ProductContainer delegate) {
        super(delegate);
    }

    @Override
    protected void doAdd(Item item) throws HITException {
        assert null != item;

        this.itemsByBarCode.put(item.getBarCode(), item);

        Set<Item> items = this.itemsByProduct.get(item.getProduct());
        if (null == items) {
            items = new HashSet<>();
            this.itemsByProduct.put(item.getProduct(), items);
        }
        items.add(item);
    }

    @Override
    protected void doRemove(Item item) throws HITException {
        assert null != item;

        this.itemsByBarCode.remove(item.getBarCode());

        Set<Item> items = this.itemsByProduct.get(item.getProduct());
        if (null != items) {
            items.remove(item);
        }
    }

    @Override
    protected boolean isAddable(Item item) {
        assert null != item;

        return false == this.itemsByBarCode.containsKey(item.getBarCode());
    }

    @Override
    protected boolean isRemovable(Item item) {
        return false == this.canAdd(item);
    }

    @Override
    public Comparator<Item> getComparator() {
        return null;
    }

    @Override
    public Iterable<Item> getItems(Product product) {
        Set<Item> items = this.itemsByProduct.get(product);
        if (null == items) {
            return Collections.emptySet();
        }
        
        return Collections.unmodifiableSet(new HashSet<>(items));
    }

    @Override
    public int getItemCount(Product product) {
        Set<Item> items = this.itemsByProduct.get(product);
        
        return null != items ? items.size() : 0;
    }

    @Override
    public int getItemCount() {
        return this.size();
    }

    @Override
    public Item getItem(BarCode barCode) {
        return this.itemsByBarCode.get(barCode);
    }
}
