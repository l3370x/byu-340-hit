package core.model;

import core.model.exception.HITException;

/**
 * The {@code ItemContainer} class is a concrete container class that manages
 * a collection of {@link Item} instances.
 * 
 * @author kemcqueen
 */
class ItemCollection extends AbstractContainer<Item> {

    @Override
    protected void doAdd(Item content) throws HITException {
    }

    @Override
    protected void doRemove(Item content) throws HITException {
    }

    @Override
    public boolean canAdd(Item content) {
        return true;
    }

    @Override
    public boolean canRemove(Item content) {
        return true;
    }
}
