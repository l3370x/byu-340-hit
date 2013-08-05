/**
 * 
 */
package persistence;

import static persistence.ProductContainerDAO.COL_IS_STORAGE_UNIT;
import static persistence.ProductContainerDAO.COL_NAME;

import java.util.Observable;

import core.model.InventoryManager;
import core.model.Item;
import core.model.ModelNotification;
import core.model.Product;
import core.model.ProductContainer;
import core.model.Quantity;
import core.model.StorageUnit;
import core.model.exception.ExceptionHandler;
import core.model.exception.HITException;
import core.model.Category;

/**
 * @author Aaron
 * 
 */
public class SqlitePersistence implements Persistence {

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.PersistenceManager#save()
	 */
	@Override
	public void save() throws HITException {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.PersistenceManager#load()
	 */
	@Override
	public void load() throws HITException {
		InventoryManager i = InventoryManager.Factory.getInventoryManager();

		// add persistence observer to invMan
		i.addObserver(this);

		ProductContainerDAO dao = new ProductContainerDAO();
		Iterable<DataTransferObject> data = dao.getAll();
		for (DataTransferObject obj : data) {
			String name = (String) obj.getValue(ProductContainerDAO.COL_NAME);
			if (((String) obj.getValue(ProductContainerDAO.COL_IS_STORAGE_UNIT))
					.equals("true")) {
				// add storage Unit
				StorageUnit su = StorageUnit.Factory.newStorageUnit(name);
				i.add(su);
				System.out.println(obj.getValues());
			} else {
				// add category
				Category c = Category.Factory.newCategory(name);
				int parentKey = (int) obj
						.getValue(ProductContainerDAO.COL_PARENT);
				// Quantity q = new Quantity(value, unit)
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.PersistenceManager#update()
	 */
	@Override
	public void update() throws HITException {
		// TODO Auto-generated method stub

	}

	private SqlitePersistence() {

	}

	public static class Factory {
		private static final SqlitePersistence INSTANCE = new SqlitePersistence();

		/**
		 * Get the {@link SqlitePersistence} instance.
		 * 
		 * @return the persistence manager
		 * @pre
		 * @post return != null
		 */
		public static SqlitePersistence getPersistenceManager() {
			return INSTANCE;
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if (false == arg instanceof ModelNotification) {
			return;
		}
		System.out.println("sqlite persistence update");
		ModelNotification notification = (ModelNotification) arg;
		Object payload = notification.getContent();
		switch (notification.getChangeType()) {
		case ITEM_ADDED:
			System.out.println("item added "
					+ ((Item) payload).getProduct().getDescription());
			break;

		case ITEM_REMOVED:
			System.out.println("item removed "
					+ ((Item) payload).getProduct().getDescription());
			break;

		case PRODUCT_ADDED:
			System.out.println("product added "
					+ ((Product) payload).getDescription());
			break;

		case PRODUCT_REMOVED:
			System.out.println("product removed "
					+ ((Product) payload).getDescription());
			break;
		case CONTENT_ADDED:
			System.out.println("container added "
					+ ((ProductContainer) payload).getName());
			break;
		case CONTENT_REMOVED:
			System.out.println("container removed "
					+ ((ProductContainer) payload).getName());
			break;
		case CONTENT_UPDATED:
			System.out.println("container updated "
					+ ((ProductContainer) payload).getName());
			break;
		case ITEM_UPDATED:
			System.out.println("item updated "
					+ ((Item) payload).getProduct().getDescription());
			break;
		case PRODUCT_UPDATED:
			System.out.println("product updated "
					+ ((Product) payload).getDescription());
			break;
		default:
			break;

		}

	}

}
