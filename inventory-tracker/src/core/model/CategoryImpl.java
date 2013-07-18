package core.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import core.model.exception.HITException;
import core.model.exception.HITException.Severity;
import static core.model.AbstractContainable.*;

/**
 * The {@code CategoryImpl} class is the default implementation of the
 * {@link Category} interface. The constructor for this class is (mostly)
 * hidden. To get an instance of {@link Category} you must use the
 * {@link Category.Factory}.
 * 
 * @invariant getStorageUnit() != null
 * 
 * @author kemcqueen
 */
class CategoryImpl extends AbstractProductContainer<Category> implements
		Category {
	private ProductContainer<Category> container;
	private StorageUnit storageUnit;
	private Quantity threeMonthSupply;

	/**
	 * Hidden (mostly) construct
	 */
	CategoryImpl(String name) {
		super(name);
	}

	@Override
	public Quantity get3MonthSupplyQuantity() {
		return this.threeMonthSupply;
	}

	@Override
	public void set3MonthSupplyQuantity(Quantity quantity) {
		this.threeMonthSupply = quantity;
	}

	@Override
	public StorageUnit getStorageUnit() {
		return this.storageUnit;
	}

	@Override
	public void wasAddedTo(ProductContainer<Category> container)
			throws HITException {
		verifyContains(container, this);

		this.container = container;
		this.storageUnit = container.getStorageUnit();

		this.addObserver(container);
	}

	@Override
	public void wasRemovedFrom(ProductContainer<Category> container)
			throws HITException {
		verifyDoesNotContain(container, this);

		this.container = null;
		this.storageUnit = null;

		this.deleteObserver(container);
	}

	@Override
	public void transfer(ProductContainer<Category> from,
			ProductContainer<Category> to) throws HITException {
		throw new HITException(Severity.INFO,
				"Categories (or product groups) are not transferable");
	}

	@Override
	public ProductContainer<Category> getContainer() {
		return this.container;
	}

	private void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();
		for (Category c : this.getContents()) {
			out.writeObject(c);
		}
	}

	private void readObject(ObjectInputStream in) throws IOException,
			ClassNotFoundException, HITException {
		in.defaultReadObject();
		this.addObserver(container);
	}
}
