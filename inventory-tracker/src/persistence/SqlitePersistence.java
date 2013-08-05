/**
 * 
 */
package persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import core.model.Category;
import core.model.InventoryManager;
import core.model.Item;
import core.model.ModelNotification;
import core.model.Product;
import core.model.ProductContainer;
import core.model.Quantity;
import core.model.Quantity.Units;
import core.model.StorageUnit;
import core.model.exception.HITException;

/**
 * @author Aaron
 * 
 */
public class SqlitePersistence implements Persistence {

	private SqlitePersistence() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.PersistenceManager#load()
	 */
	@Override
	public void load() throws HITException {
		
		// holds added containers in order to add new categories to existing
		// product containers
		Map<Integer, ProductContainer> addedContainers = new HashMap<Integer, ProductContainer>();

		// holds unadded containers to add later (this is necessary if a
		// category is to be loaded without the parent being in memory yet.
		List<UnaddedCategoryWrapper> unaddedContainers = new ArrayList<UnaddedCategoryWrapper>();

		// add all product containers
		ProductContainerDAO dao = new ProductContainerDAO();
		Iterable<DataTransferObject> data = dao.getAll();
		for (DataTransferObject dto : data) {
			if (((String) dto.getValue(ProductContainerDAO.COL_IS_STORAGE_UNIT))
					.equals("true")) {
				StorageUnit su = addStorageUnitFromDTO(dto);
				addedContainers.put((int) dto.getValue(ProductContainerDAO.COL_ID), su);
			} else {
				Category c = newCategoryFromDTO(dto);

				// check if parent exists
				int parentKey = (int) dto.getValue(ProductContainerDAO.COL_PARENT);
				int cID = (int) dto.getValue(ProductContainerDAO.COL_ID);
				if (addedContainers.containsKey(parentKey)) {
					addedContainers.get(parentKey).add(c);
					addedContainers.put(cID, c);
				} else {
					unaddedContainers.add(new UnaddedCategoryWrapper(c, cID, parentKey));
				}
			}
		}

		// add remaining unadded containers
		while (!unaddedContainers.isEmpty()) {
			for (UnaddedCategoryWrapper cw : unaddedContainers) {
				if (addedContainers.containsKey(cw.parentID)) {
					addedContainers.get(cw.parentID).add(cw.c);
					addedContainers.put(cw.myID, cw.c);
					unaddedContainers.remove(cw);
					break;
				}
			}
		}

		// add products
		// TODO add products
		// add items
		// TODO add items

		// add persistence observer to invMan
		InventoryManager.Factory.getInventoryManager().addObserver(this);

	}

	private Category newCategoryFromDTO(DataTransferObject dto)
			throws HITException {
		String name = (String) dto.getValue(ProductContainerDAO.COL_NAME);
		// create new category
		Category c = Category.Factory.newCategory(name);

		// set values from dto
		float value = Float.valueOf(String.valueOf(dto
				.getValue(ProductContainerDAO.COL_3_MO_SUPPLY_AMT)));
		Units unit = Units.valueOf((String) dto
				.getValue(ProductContainerDAO.COL_3_MO_SUPPLY_UNITS));
		Quantity q = new Quantity(value, unit);
		c.set3MonthSupplyQuantity(q);
		return c;
	}

	private StorageUnit addStorageUnitFromDTO(DataTransferObject dto)
			throws HITException {
		String name = (String) dto.getValue(ProductContainerDAO.COL_NAME);
		StorageUnit su = StorageUnit.Factory.newStorageUnit(name);
		InventoryManager.Factory.getInventoryManager().add(su);
		return su;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.PersistenceManager#save()
	 */
	@Override
	public void save() throws HITException {

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
	
	class UnaddedCategoryWrapper {
		public Category c;
		public int myID;
		public int parentID;

		public UnaddedCategoryWrapper(Category c, int myID, int parentID) {
			this.c = c;
			this.myID = myID;
			this.parentID = parentID;
		}
	}

}
