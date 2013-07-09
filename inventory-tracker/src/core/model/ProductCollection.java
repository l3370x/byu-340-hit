package core.model;

import core.model.exception.HITException;
import core.model.exception.Severity;
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
        if(product == null){
            throw new HITException(Severity.ERROR, "Null Product");
        }
        else if(product.getDescription() == null 
                || product.getDescription().equals("")){
            throw new HITException(Severity.ERROR, "Invalid Product Description");
        }
        if(this.canAdd(product)){
            this.productsByDescription.put(product.getDescription(), product);
        }
    }

    @Override
    protected void doRemove(Product product) throws HITException {
        if(product == null){
            throw new HITException(Severity.ERROR, "Null Product");
        }
        else if(product.getDescription() == null 
                || product.getDescription().equals("")){
            throw new HITException(Severity.ERROR, "Invalid Product Description");
        }
        if(this.canRemove(product)){
            this.productsByDescription.remove(product.getDescription());
        }
    }

    @Override
    protected boolean isAddable(Product product) {
        return false == this.productsByDescription.containsKey(product.getDescription());
    }

    @Override
    protected boolean isRemovable(Product product) {
        return false == this.canAdd(product);
    }
}
