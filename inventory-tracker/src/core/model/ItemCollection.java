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
    protected boolean isAddable(Item item) {
        return true;
    }

    @Override
    protected boolean isRemovable(Item item) {
        return true;
    }
}
