/**
 *
 */
package persistence;

import core.model.*;
import core.model.Quantity.Units;
import core.model.exception.HITException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Aaron
 */
public class SqlitePersistence implements Persistence {
    Map<Integer, ProductContainer> addedContainers;

    private SqlitePersistence() {

    }

    /*
     * (non-Javadoc)
     *
     * @see persistence.PersistenceManager#load()
     */
    @Override
    public void load() throws HITException {

        try {
            addContainers();
            addProducts();
            // add products
            // TODO add products
            // add items
            // TODO add items

        } catch (Exception e) {
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

    private void addProducts() throws HITException {
        // add products


        //First get all the products:

        ProductDAO productDAO = new ProductDAO();
        Iterable<DataTransferObject> products = productDAO.getAll();

        //Hold Products by barcode

        Map<String, Product> productsToAdd = new HashMap<String, Product>();

        for (DataTransferObject product : products) {
            productsToAdd.put(((String) product.getValue(ProductDAO.COL_BARCODE)), getProductFromDTO(product));
        }

        //Fetch Data from the Product_ProductContainer DAO

        ProductPCDAO productPCDAO = new ProductPCDAO();
        Iterable<DataTransferObject> productPC = productPCDAO.getAll();

        //Create a list of products Storage unit ID wise

        Map<Integer, List<String>> productsSortedSU = new HashMap<Integer, List<String>>();

        for (DataTransferObject productPC1 : productPC) {
            String productCode = (String) productPC1.getValue(ProductPCDAO.COL_PRODUCT_ID);
            Integer key = Integer.parseInt((String) productPC1.getValue(ProductPCDAO.COL_PRODUCT_CONTAINER_ID));
            if (productsSortedSU.containsKey(key)) {

                productsSortedSU.get(productPC1.getValue(ProductPCDAO.COL_PRODUCT_CONTAINER_ID)).add(productCode);
            } else {
                List<String> newList = new ArrayList<String>();
                newList.add(productCode);
                productsSortedSU.put(key, newList);

            }
        }

        //Now go through the list and add the products to the storage units

        for (Integer suID : productsSortedSU.keySet()) {
            List<String> pList = productsSortedSU.get(suID);

            for (String pCode : pList) {
                addedContainers.get(suID).addProduct(productsToAdd.get(pCode));
            }
        }


    }


    private Product getProductFromDTO(DataTransferObject dto) throws HITException {

        BarCode barcode = BarCode.getBarCodeFor(((String) dto.getValue(ProductDAO.COL_BARCODE)));
        Product p = Product.Factory.newProduct(barcode, ((String) dto.getValue(ProductDAO.COL_DESCRIPTION)));
        int quota = Integer.parseInt((String) dto.getValue(ProductDAO.COL_3_MONTH_SUPPLY));
        p.set3MonthSupplyQuota(quota);

        Date date = new Date();
        try {
            date = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(ProductDAO.COL_CREATE_DATE);
        } catch (ParseException e) {
        }

        p.setCreationDate(date);
        int shelfLife = Integer.parseInt((String) dto.getValue(ProductDAO.COL_SHELF_LIFE_MONTHS));
        p.setShelfLifeInMonths(shelfLife);

        Units unit = Units.valueOf((String) dto
                .getValue(ProductDAO.COL_SIZE_UNIT));

        Quantity quantity = new Quantity(Float.parseFloat((String) dto.getValue(ProductDAO.COL_SIZE_AMT)), unit);

        p.setSize(quantity);

        return p;
    }


    private void getDTOFromProduct(Product product) {
        DataTransferObject productDTO = new DataTransferObject();

        productDTO.setValue(ProductDAO.COL_3_MONTH_SUPPLY, Integer.toString(product.get3MonthSupplyQuota()));
        productDTO.setValue(ProductDAO.COL_BARCODE, product.getBarCode().toString());
        productDTO.setValue(ProductDAO.COL_CREATE_DATE, product.getCreationDate().toString());


    }

    private void productAddDAO(Product product) {
        //DataTransferObject productDTO = getDTOFromProduct(product);
    }

    private void resetDatabase() throws SQLException {
        deleteDatabaseFile();
        Connection connection = null;
        try {
            connection = TransactionManager.Factory.getTransactionManager().beginTransaction();
        } catch (HITException e) {
        } finally {
            connection.close();
        }

    }

    private void deleteDatabaseFile() {
        String DB_FILE = "inventory-tracker/db/hit.sqlite";
        try {
            Files.deleteIfExists(Paths.get(DB_FILE));
        } catch (IOException e1) {
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
                productAddDAO((Product) payload);
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
