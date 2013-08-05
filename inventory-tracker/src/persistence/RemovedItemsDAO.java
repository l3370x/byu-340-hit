package persistence;

public class RemovedItemsDAO extends AbstractDAO {

    public static final String TABLE_NAME = "ITEM";
    public static final String COL_ID = "ID";
    public static final String COL_BARCODE = "BARCODE";
    public static final String COL_PROD_BARCODE = "PROD_BARCODE";
    public static final String COL_ENTRY_DATE = "ENTRY_DATE";
    public static final String COL_PRODUCT_CONTAINER = "PRODUCT_CONTAINER";
    public static final String COL_REMOVED_DATE = "REMOVED_DATE";

    private static final String[] COLUMN_NAMES = new String[]{
            COL_ID,
            COL_BARCODE,
            COL_PROD_BARCODE,
            COL_ENTRY_DATE,
            COL_PRODUCT_CONTAINER,
            COL_REMOVED_DATE
    };

    @Override
    public String[] getColumnNames() {
        return COLUMN_NAMES;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
}
