package core.model;

import core.model.exception.HITException;
import core.model.exception.Severity;

/**
 * The {@code ItemContainer} class is a concrete container class that manages
 * a collection of {@link Item} instances.
 * 
 * @author kemcqueen
 */
class ItemCollection extends AbstractContainer<Item> {
    @Override
    protected void doAdd(Item item) throws HITException {
    	if (item == null) {
    		throw new HITException(Severity.INFO,"Can't add a null item.");
    	}
    	item.getContainer().addItem(item); // TODO is this correct?
    }

    @Override
    protected void doRemove(Item item) throws HITException {
    	if (item == null) {
    		throw new HITException(Severity.INFO,"Can't add a null item.");
    	} else if( this.contains(item) == false) {
    		throw new HITException(Severity.INFO, "Item isn't contained in collection");
    	}
    	item.getContainer().removeItem(item);  // TODO is this correct?
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
