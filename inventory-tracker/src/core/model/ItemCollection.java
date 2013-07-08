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
    protected void doAdd(Item item) throws HITException {
    }

    @Override
    protected void doRemove(Item item) throws HITException {
    }

    @Override
    public boolean canAdd(Item item) {
        return true;
    }

    @Override
    public boolean canRemove(Item item) {
        return true;
    }
}
