/**
 *
 */
package persistence;

import common.util.DbUtils;
import core.model.*;
import core.model.Quantity.Units;
import core.model.exception.HITException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import static core.model.InventoryManager.Factory.getInventoryManager;

/**
 * @author Aaron
 */
public class SqlitePersistence implements Persistence {
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

    Map<Integer, ProductContainer> addedContainers;
    Map<String, Product> productsToAdd;

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
            if (((int) dto.getValue(ProductContainerDAO.COL_IS_STORAGE_UNIT)) == 1) {
                StorageUnit su = addStorageUnitFromDTO(dto);
                addedContainers.put(
                        (int) dto.getValue(ProductContainerDAO.COL_ID), su);
            } else {
                Category c = newCategoryFromDTO(dto);

                // check if parent exists
                int parentKey = (int) dto
                        .getValue(ProductContainerDAO.COL_PARENT);
                int cID = (int) dto.getValue(ProductContainerDAO.COL_ID);
                if (addedContainers.containsKey(parentKey)) {
                    addedContainers.get(parentKey).add(c);
                    addedContainers.put(cID, c);
                } else {
                    unaddedContainers.add(new UnaddedCategoryWrapper(c, cID,
                            parentKey));
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

    private void resetDatabase() throws SQLException {
        deleteDatabaseFile();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = TransactionManager.Factory.getTransactionManager()
                    .beginTransaction();

            String BASE_FILE_NAME = System.getProperty("user.dir");
            String path = BASE_FILE_NAME.replace("build/", "");
            File inFile = new File(path
                    + "/inventory-tracker/db/hit-createdblines.sql");
            FileInputStream fstream = new FileInputStream(inFile);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                // System.out.println(strLine);
                statement = connection.createStatement();
                statement.executeUpdate(strLine);
            }
            TransactionManager.Factory.getTransactionManager().endTransaction(
                    connection, true);
            in.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.safeClose(connection);
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
            ItemDAO itemDAO = new ItemDAO();
            Iterable<DataTransferObject> data = itemDAO.getAll();
            for (DataTransferObject dto : data) {
                addItemFromDTO(dto);
            }

        } catch (Exception e) {
            // e.printStackTrace();
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

        // First get all the products:

        ProductDAO productDAO = new ProductDAO();
        Iterable<DataTransferObject> products = productDAO.getAll();

        // Hold Products by barcode

        productsToAdd = new HashMap<String, Product>();

        for (DataTransferObject product : products) {
            productsToAdd.put(
                    ((String) product.getValue(ProductDAO.COL_BARCODE)),
                    getProductFromDTO(product));
        }

        // Fetch Data from the Product_ProductContainer DAO

        ProductPCDAO productPCDAO = new ProductPCDAO();
        Iterable<DataTransferObject> productPC = productPCDAO.getAll();

        // Create a list of products Storage unit ID wise

        Map<Integer, List<String>> productsSortedSU = new HashMap<Integer, List<String>>();

        for (DataTransferObject productPC1 : productPC) {
            String productCode = (String) productPC1
                    .getValue(ProductPCDAO.COL_PRODUCT_ID);
            Integer key = (Integer) productPC1
                    .getValue(ProductPCDAO.COL_PRODUCT_CONTAINER_ID);
            if (productsSortedSU.containsKey(key)) {

                productsSortedSU
                        .get(productPC1
                                .getValue(ProductPCDAO.COL_PRODUCT_CONTAINER_ID))
                        .add(productCode);
            } else {
                List<String> newList = new ArrayList<String>();
                newList.add(productCode);
                productsSortedSU.put(key, newList);

            }
        }

        // Now go through the list and add the products to the storage units

        for (Integer suID : productsSortedSU.keySet()) {
            List<String> pList = productsSortedSU.get(suID);

            for (String pCode : pList) {
                addedContainers.get(suID).addProduct(productsToAdd.get(pCode));
            }
        }

    }

    private Product getProductFromDTO(DataTransferObject dto)
            throws HITException {

        BarCode barcode = BarCode.getBarCodeFor(((String) dto
                .getValue(ProductDAO.COL_BARCODE)));
        Product p = Product.Factory.newProduct(barcode,
                ((String) dto.getValue(ProductDAO.COL_DESCRIPTION)));
        int quota = (int) dto.getValue(ProductDAO.COL_3_MONTH_SUPPLY);
        p.set3MonthSupplyQuota(quota);

        Date date = new Date((long) dto.getValue(ProductDAO.COL_CREATE_DATE));

        p.setCreationDate(date);
        int shelfLife = (int) dto.getValue(ProductDAO.COL_SHELF_LIFE_MONTHS);
        p.setShelfLifeInMonths(shelfLife);

        Units unit = Units.valueOf((String) dto
                .getValue(ProductDAO.COL_SIZE_UNIT));

        Quantity quantity = new Quantity(Float.valueOf(String
                .valueOf((Double) dto.getValue(ProductDAO.COL_SIZE_AMT))), unit);

        p.setSize(quantity);

        return p;
    }

    private DataTransferObject getDTOFromProduct(Product product) {
        DataTransferObject productDTO = new DataTransferObject();

        productDTO.setValue(ProductDAO.COL_3_MONTH_SUPPLY,
                product.get3MonthSupplyQuota());
        productDTO.setValue(ProductDAO.COL_BARCODE, product.getBarCode()
                .toString());
        productDTO.setValue(ProductDAO.COL_CREATE_DATE,
                product.getCreationDate());
        productDTO.setValue(ProductDAO.COL_DESCRIPTION,
                product.getDescription());
        productDTO.setValue(ProductDAO.COL_SIZE_AMT, product.getSize()
                .getValue());
        productDTO.setValue(ProductDAO.COL_SIZE_UNIT, product.getSize()
                .getUnits().toString());
        productDTO.setValue(ProductDAO.COL_SHELF_LIFE_MONTHS,
                product.getShelfLifeInMonths());

        return productDTO;

    }

    private void productAddDAO(Product product) {
        DataTransferObject productDTO = getDTOFromProduct(product);
        ProductDAO productDAO = new ProductDAO();
        // Make new product_product_containerDTO

        DataTransferObject ppcDTO = new DataTransferObject();
        ppcDTO.setValue(ProductPCDAO.COL_PRODUCT_ID, product.getBarCode()
                .toString());

        for (int pcKey : addedContainers.keySet()) {
            ProductContainer ps = addedContainers.get(pcKey);

            if (ps == product.getContainer()) {
                ppcDTO.setValue(ProductPCDAO.COL_PRODUCT_CONTAINER_ID, pcKey);
            }
        }

        ProductPCDAO productPCDAO = new ProductPCDAO();
        try {
            boolean doAdd = true;
            for (DataTransferObject pdto : productDAO.getAll()) {
                if (((String) pdto.getValue(ProductDAO.COL_BARCODE))
                        .equals(product.getBarCode().toString()))
                    doAdd = false;
            }
            if (doAdd)
                productDAO.insert(productDTO);
            productPCDAO.insert(ppcDTO);
        } catch (HITException e) {
            e.printStackTrace();
        }

    }

    private void productRemoveDAO(ModelNotification arg) {
        Product product = (Product) arg.getContent();
        ProductContainer pcFromNotification = (ProductContainer) arg
                .getContainer();
        try {

            DataTransferObject ppcDTO = new DataTransferObject();
            ppcDTO.setValue(ProductPCDAO.COL_PRODUCT_ID, product.getBarCode().getValue());

            for (int pcKey : this.addedContainers.keySet()) {
                if (this.addedContainers.get(pcKey) == pcFromNotification) {
                    ppcDTO.setValue(ProductPCDAO.COL_PRODUCT_CONTAINER_ID, pcKey);
                    break;
                }
            }

            // First check if this product does not exist in any other container

            ProductPCDAO productPCDAO = new ProductPCDAO();
            productPCDAO.delete(ppcDTO);

            // if there are no more instances of this product left,
            // then we should delete it altogether

            /* --- DON'T DO THIS - IT THROWS AN EXCEPTION FOR SOME REASON ABOUT BREAKING THE FK ---
            ppcDTO = new DataTransferObject();
            ppcDTO.setValue(ProductPCDAO.COL_PRODUCT_ID, product.getBarCode().toString());

            Iterator<DataTransferObject> results = productPCDAO.get(ppcDTO).iterator();
            if (false == results.hasNext()) {
                DataTransferObject productDTO = new DataTransferObject();
                productDTO.setValue(ProductDAO.COL_BARCODE, product.getBarCode().getValue());
                new ProductDAO().delete(productDTO);
            }
            */
        } catch (HITException e) {
            e.printStackTrace();
        }
    }

    private void productUpdateDAO(Product product) {
        try {
            DataTransferObject productDTO = getDTOFromProduct(product);
            ProductDAO productDAO = new ProductDAO();
            productDAO.update(productDTO);
        } catch (HITException e) {
            e.printStackTrace();
        }
    }

    private DataTransferObject getDTOFromItem(ModelNotification notification) { // Eric
        DataTransferObject itemDTO = new DataTransferObject();
        ProductContainer pcFromNotification = (ProductContainer) notification
                .getContainer();
        Item item = (Item) notification.getContent();
        itemDTO.setValue(ItemDAO.COL_BARCODE, item.getBarCode().toString());
        itemDTO.setValue(ItemDAO.COL_ENTRY_DATE, item.getEntryDate());
        itemDTO.setValue(ItemDAO.COL_PROD_BARCODE, item.getProduct()
                .getBarCode().toString());

        // find the parent container in the addedContainers map
        int parentContainerID = 0;
        for (Map.Entry<Integer, ProductContainer> entry : this.addedContainers
                .entrySet()) {
            if (entry.getValue().equals(item.getContainer())) {
                parentContainerID = entry.getKey();
            }
        }
        itemDTO.setValue(ItemDAO.COL_PRODUCT_CONTAINER, parentContainerID);
        //itemDTO.setValue(ItemDAO.COL_REMOVED_DATE, item.getExpirationDate());

        return itemDTO;
    }

    private Item getItemFromDTO(DataTransferObject object) {
        try {
            ProductDAO pDAO = new ProductDAO();
            DataTransferObject dto = new DataTransferObject();
            dto.setValue(ProductDAO.COL_BARCODE,
                    object.getValue(ItemDAO.COL_PROD_BARCODE));
            Iterable<DataTransferObject> results = pDAO.get(dto);
            Product product = null;
            for (DataTransferObject obj : results) {
                product = this.getProductFromDTO(obj);
                break;
            }
            Date date = new Date((long) object.getValue(ItemDAO.COL_ENTRY_DATE));

            Item item = Item.Factory.newItem(product, date);
            return item;
        } catch (HITException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public void setLastReportRun(Date lastReportRun) throws HITException {
        RemovedItemsDAO dao = new RemovedItemsDAO();
        DataTransferObject dto = new DataTransferObject();
        dto.setValue(RemovedItemsDAO.COL_ID, 1);
        dto.setValue(RemovedItemsDAO.COL_LAST_REPORT_RUN,
                lastReportRun.toString());
        if (!dao.getAll().iterator().hasNext())
            dao.insert(dto);
        else
            dao.update(dto);
    }

    @Override
    public Date getLastReportRun() throws HITException {
        RemovedItemsDAO dao = new RemovedItemsDAO();
        Date toReturn = null;
        for (DataTransferObject obj : dao.getAll()) {
            toReturn = new Date(
                    (String) obj.getValue(RemovedItemsDAO.COL_LAST_REPORT_RUN));
            // System.out.println("I GOT THIS FROM THE DB: "
            // + (String) obj.getValue(RemovedItemsDAO.COL_LAST_REPORT_RUN));
        }
        return toReturn;
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

    private void addItemFromDTO(DataTransferObject dto) throws HITException {

        String productBarCode = (String) dto.getValue(ItemDAO.COL_PROD_BARCODE);
        String itemBarCode = (String) dto.getValue(ItemDAO.COL_BARCODE);
        Product product = productsToAdd.get(productBarCode);
        Date date = new Date((long) dto.getValue(ItemDAO.COL_ENTRY_DATE));

        Item item = Item.Factory.newItem(product, date, itemBarCode);
        boolean hasExitDate = false;
        if (dto.getValue(ItemDAO.COL_REMOVED_DATE) != null) {
            if (!(dto.getValue(ItemDAO.COL_REMOVED_DATE)).equals(-1)) {
                Date exitdate = new Date(
                        (long) dto.getValue(ItemDAO.COL_REMOVED_DATE));
                item.setExitDate(exitdate);
                hasExitDate = true;
            }
        }
        int productContainerID = (Integer) dto
                .getValue(ItemDAO.COL_PRODUCT_CONTAINER);

        if (!hasExitDate) {
            addedContainers.get(productContainerID).addItem(item);
        } else {
            //addedContainers.get(productContainerID).addItem(item);
            getInventoryManager().saveRemovedItem(item);
            //addedContainers.get(productContainerID).removeItem(item);
        }
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
                // System.out.println("item added "
                //        + ((Item) payload).getProduct().getDescription());
                this.itemAdded(notification);

                break;

            case ITEM_REMOVED:
                // System.out.println("item removed "
                //        + ((Item) payload).getProduct().getDescription());
                this.itemRemoved(notification);
                break;

            case ITEM_UPDATED:
                // System.out.println("item updated "
                //        + ((Item) payload).getProduct().getDescription());
                this.itemUpdated(notification);
                break;

            case PRODUCT_ADDED:
                productAddDAO((Product) payload);
                // System.out.println("product added "
                //        + ((Product) payload).getDescription());
                break;

            case PRODUCT_REMOVED:
                productRemoveDAO((ModelNotification) arg);
                // System.out.println("product removed "
                //        + ((Product) payload).getDescription());
                break;
            case CONTENT_ADDED:
                // System.out.println("container added "
                //        + ((ProductContainer) payload).getName());
                this.contentAdded(notification);
                break;
            case CONTENT_REMOVED:
                // System.out.println("container removed "
                //        + ((ProductContainer) payload).getName());
                this.contentRemoved(notification);
                break;
            case CONTENT_UPDATED:
                // System.out.println("container updated "
                //       + ((ProductContainer) payload).getName());
                this.contentUpdated(notification);
                break;
            case PRODUCT_UPDATED:
                productUpdateDAO((Product) payload);
                //System.out.println("product updated "
                //        + ((Product) payload).getDescription());
                break;
            default:
                break;

        }

    }

    private void contentUpdated(ModelNotification notification) {
        ProductContainer pc = (ProductContainer) notification.getContent();
        DataTransferObject dto = getDTOFromProductContainer(pc);
        ProductContainerDAO dao = new ProductContainerDAO();
        dto.setValue(ProductContainerDAO.COL_ID, getIDFromAddedContainers(pc));
        try {
            dao.update(dto);
        } catch (HITException e) {
            e.printStackTrace();
        }
    }

    private void contentRemoved(ModelNotification notification) {
        ProductContainer pc = (ProductContainer) notification.getContent();
        DataTransferObject dto = getDTOFromProductContainer(pc);
        ProductContainerDAO dao = new ProductContainerDAO();
        int myID = getIDFromAddedContainers(pc);
        dto.setValue(ProductContainerDAO.COL_ID, myID);
        try {
            dao.delete(dto);
            addedContainers.remove(myID);
        } catch (HITException e) {
            e.printStackTrace();
        }
    }

    private void contentAdded(ModelNotification notification) {
        ProductContainer pc = (ProductContainer) notification.getContent();
        DataTransferObject dto = getDTOFromProductContainer(pc);
        ProductContainerDAO dao = new ProductContainerDAO();
        try {
            dao.insert(dto);
            Iterable<DataTransferObject> results = dao.get(dto);
            for (DataTransferObject o : results) {
                this.addedContainers.put(
                        (int) o.getValue(ProductContainerDAO.COL_ID), pc);
                break;
            }
        } catch (HITException e) {
            e.printStackTrace();
        }
    }

    private DataTransferObject getDTOFromProductContainer(ProductContainer pc) {
        DataTransferObject pcDTO = new DataTransferObject();

        pcDTO.setValue(ProductContainerDAO.COL_NAME, pc.getName());

        if (true == pc instanceof StorageUnit) {
            pcDTO.setValue(ProductContainerDAO.COL_IS_STORAGE_UNIT, true);
            return pcDTO;
        } else if (true == pc instanceof Category) {
            pcDTO.setValue(ProductContainerDAO.COL_IS_STORAGE_UNIT, false);
            pcDTO.setValue(ProductContainerDAO.COL_3_MO_SUPPLY_AMT,
                    ((Category) pc).get3MonthSupplyQuantity().getValue());
            pcDTO.setValue(ProductContainerDAO.COL_3_MO_SUPPLY_UNITS,
                    ((Category) pc).get3MonthSupplyQuantity().getUnits());
            ProductContainer parent = ((Category) pc).getContainer();
            for (Map.Entry<Integer, ProductContainer> entry : this.addedContainers
                    .entrySet()) {
                if (entry.getValue().equals(parent)) {
                    pcDTO.setValue(ProductContainerDAO.COL_PARENT,
                            entry.getKey());
                }
            }
            return pcDTO;
        }

        return null;
    }

    private void itemUpdated(ModelNotification notification) {
        try {
            DataTransferObject dtoFromItem = getDTOFromItem(notification);
            ItemDAO iDAO = new ItemDAO();
            iDAO.update(dtoFromItem);
        } catch (HITException e) {
            e.printStackTrace();
        }
    }

    private void itemRemoved(ModelNotification notification) {
        try {
            DataTransferObject dtoFromItem = getDTOFromItem(notification);
            ProductContainer pcFromNotification = (ProductContainer) notification
                    .getContainer();

            ItemDAO iDAO = new ItemDAO();
            // instead of deleting, set removed date to current date.
            // iDAO.delete(dtoFromItem);
            dtoFromItem.setValue(ItemDAO.COL_PRODUCT_CONTAINER,
                    getIDFromAddedContainers(pcFromNotification));
            dtoFromItem.setValue(ItemDAO.COL_REMOVED_DATE, new Date());
            iDAO.update(dtoFromItem);
        } catch (HITException e) {
            e.printStackTrace();
        }
    }

    private int getIDFromAddedContainers(ProductContainer pc) {
        for (int pcKey : addedContainers.keySet()) {
            ProductContainer ps = addedContainers.get(pcKey);
            if (ps == pc) {
                return pcKey;
            }
        }
        return -1;
    }

    private void itemAdded(ModelNotification notification) {
        DataTransferObject dtoFromItem = getDTOFromItem(notification);
        ProductContainer pcFromNotification = (ProductContainer) notification
                .getContainer();
        ItemDAO iDAO = new ItemDAO();
        try {
            iDAO.insert(dtoFromItem);
        } catch (HITException e) {
            if (e.getMessage().contains("column barcode is not unique")) {
                dtoFromItem.setValue(ItemDAO.COL_REMOVED_DATE, -1);
                try {
                    dtoFromItem.setValue(ItemDAO.COL_PRODUCT_CONTAINER,
                            getIDFromAddedContainers(pcFromNotification));
                    iDAO.update(dtoFromItem);
                } catch (HITException e1) {
                    e1.printStackTrace();
                }
            } else {
                e.printStackTrace();
            }
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

}
