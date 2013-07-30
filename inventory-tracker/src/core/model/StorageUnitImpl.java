package core.model;

import core.model.exception.HITException;
import core.model.exception.HITException.Severity;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static core.model.AbstractContainable.verifyContains;
import static core.model.AbstractContainable.verifyDoesNotContain;

//import sun.tools.tree.ThisExpression;

/**
 * The {@code StorageUnitImpl} class is the default implementation of the {@link StorageUnit}
 * interface. The constructor(s) for this class are hidden. To get a StorageUnit instance, you must
 * use the {@link StorageUnit.Factory}.
 *
 * @author kemcqueen
 * @invariant name != null
 */
class StorageUnitImpl extends AbstractProductContainer<Category> implements
        StorageUnit {
    private InventoryManager container;

    StorageUnitImpl(String name) {
        super(name);
    }

    @Override
    public void wasAddedTo(InventoryManager container) throws HITException {
        verifyContains(container, this);

        this.container = container;

        this.addObserver(container);
    }

    @Override
    public void wasRemovedFrom(InventoryManager container) throws HITException {
        verifyDoesNotContain(container, this);

        this.container = null;

        this.deleteObserver(container);
    }

    @Override
    public void transfer(InventoryManager from, InventoryManager to)
            throws HITException {
        throw new HITException(Severity.ERROR, "Can't transfer a storage unit");
    }

    @Override
    public InventoryManager getContainer() {
        assert true;

        return this.container;
    }

    @Override
    public StorageUnit getStorageUnit() {
        assert true;
        return this;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }

    private void readObject(ObjectInputStream in) throws IOException,
            ClassNotFoundException, HITException {
        in.defaultReadObject();
        super.loadObject(this);
        InventoryManager.Factory.getInventoryManager().add(this);
    }
}
