package core.model;

import core.model.exception.HITException;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@code ProductContainer} class is a concrete container class that manages
 * a collection of {@link Product} instances.
 * 
 * @author kemcqueen
 */
class ProductCollection extends AbstractContainer<Product> {
    private final Map<String, Product> productsByDescription = new HashMap<>();

    @Override
    protected void doAdd(Product product) throws HITException {
        this.productsByDescription.put(product.getDescription(), product);
    }

    @Override
    protected void doRemove(Product product) throws HITException {
        this.productsByDescription.remove(product.getDescription());
    }

    @Override
    public boolean canAdd(Product product) {
        return false == this.productsByDescription.containsKey(product.getDescription());
    }

    @Override
    public boolean canRemove(Product product) {
        return false == this.canAdd(product);
    }
}
