package persistence;

public class ItemDAO extends AbstractDAO{

	public static final String TABLE_NAME = "ITEM";
    public static final String COL_BARCODE = "BARCODE";
    public static final String COL_PROD_BARCODE = "PROD_BARCODE";
    public static final String COL_ENTRY_DATE = "ENTRY_DATE";
    public static final String COL_PRODUCT_CONTAINER = "PRODUCT_CONTAINER";
    public static final String COL_REMOVED_DATE = "REMOVED_DATE";
    
    private static final String[] COLUMN_NAMES = new String[]{
    	COL_BARCODE,
    	COL_PROD_BARCODE,
    	COL_ENTRY_DATE,
    	COL_PRODUCT_CONTAINER,
    	COL_REMOVED_DATE
    };
    
    private static final String[] COLUMN_KEY_NAMES = new String[]{
    	COL_BARCODE
    };
    
	@Override
	public String[] getColumnNames() {
		return COLUMN_NAMES;
	}

	@Override
	public String getTableName() {
		return TABLE_NAME;
	}

	@Override
	public String[] getKeyColumnNames() {
		return COLUMN_KEY_NAMES;
	}

}
