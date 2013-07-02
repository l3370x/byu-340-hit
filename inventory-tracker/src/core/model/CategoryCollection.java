package core.model;

import core.model.exception.HITException;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@code CategoryContainer} class is a concrete container class that 
 * manages a collection of {@link Category} instances.
 * 
 * @author kemcqueen
 */
class CategoryCollection extends AbstractContainer<Category> {
    private final Map<String, Category> categoriesByName = new HashMap<>();

    @Override
    protected void doAdd(Category category) throws HITException {
        this.categoriesByName.put(category.getName(), category);
    }

    @Override
    protected void doRemove(Category category) throws HITException {
        this.categoriesByName.remove(category.getName());
    }

    @Override
    public boolean canAdd(Category category) {
        return false == this.categoriesByName.containsKey(category.getName());
    }

    @Override
    public boolean canRemove(Category category) {
        return false == this.canAdd(category);
    }
}
