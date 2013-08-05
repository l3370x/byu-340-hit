/**
 * 
 */
package persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.*;

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
	Map<Integer, ProductContainer> addedContainers;

	private SqlitePersistence() {

	}

	@Override
	public void load() throws HITException {

		try {
			addContainers();

			// add products
			// TODO add products
			// add items
			// TODO add items

		} catch (Exception e) {
			e.printStackTrace();  // TODO delete this later
			System.out.println("Database not found/corrupted.  Starting over.");
			try {
				resetDatabase();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		}
		// add persistence observer to invMan
		InventoryManager.Factory.getInventoryManager().addObserver(this);

	}

	private void resetDatabase() throws SQLException {
		deleteDatabaseFile();
		Connection connection = null;
		Statement statement = null;
		try {
			connection = TransactionManager.Factory.getTransactionManager().beginTransaction();
			// Open the file that is the first
			// command line parameter
			String BASE_FILE_NAME = System.getProperty("user.dir");
			String path = BASE_FILE_NAME.replace("build/", "");
	        File inFile = new File(path + "/inventory-tracker/db/hit-createdblines.sql");
			FileInputStream fstream = new FileInputStream(inFile);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				// Print the content on the console
				System.out.println(strLine);
				statement = connection.createStatement();
				statement.executeUpdate(strLine);
			}
			TransactionManager.Factory.getTransactionManager().endTransaction(connection, true);
			// Close the input stream
			in.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}

	}

	private void deleteDatabaseFile() {
		String DB_FILE = "inventory-tracker/db/hit.sqlite";
		try {
			Files.deleteIfExists(Paths.get(DB_FILE));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void addContainers() throws HITException {
		// holds added containers in order to add new categories to existing
		// product containers
		addedContainers = new HashMap<Integer, ProductContainer>();

		// holds unadded containers to add later (this is necessary if a
		// category is to be loaded without the parent being in memory yet.
		List<UnaddedCategoryWrapper> unaddedContainers = new ArrayList<UnaddedCategoryWrapper>();

		// add all product containers
		ProductContainerDAO dao = new ProductContainerDAO();
		Iterable<DataTransferObject> data = dao.getAll();
		for (DataTransferObject dto : data) {
			if (((String) dto.getValue(ProductContainerDAO.COL_IS_STORAGE_UNIT)).equals("true")) {
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

	}

	private Category newCategoryFromDTO(DataTransferObject dto) throws HITException {
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

	private StorageUnit addStorageUnitFromDTO(DataTransferObject dto) throws HITException {
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
		ModelNotification notification = (ModelNotification) arg;
		Object payload = notification.getContent();
		switch (notification.getChangeType()) {
		case ITEM_ADDED:
			System.out.println("item added " + ((Item) payload).getProduct().getDescription());
			break;

		case ITEM_REMOVED:
			System.out.println("item removed " + ((Item) payload).getProduct().getDescription());
			break;

		case PRODUCT_ADDED:
			System.out.println("product added " + ((Product) payload).getDescription());
			break;

		case PRODUCT_REMOVED:
			System.out.println("product removed " + ((Product) payload).getDescription());
			break;
		case CONTENT_ADDED:
			System.out.println("container added " + ((ProductContainer) payload).getName());
			break;
		case CONTENT_REMOVED:
			System.out.println("container removed " + ((ProductContainer) payload).getName());
			break;
		case CONTENT_UPDATED:
			System.out.println("container updated " + ((ProductContainer) payload).getName());
			break;
		case ITEM_UPDATED:
			System.out.println("item updated " + ((Item) payload).getProduct().getDescription());
			break;
		case PRODUCT_UPDATED:
			System.out.println("product updated " + ((Product) payload).getDescription());
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
