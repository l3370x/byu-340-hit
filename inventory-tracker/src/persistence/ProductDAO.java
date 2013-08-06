/**
 * 
 */
package persistence;

/**
 * @author rfun11
 *
 */
public class ProductDAO extends AbstractDAO {

    public static final String TABLE_NAME = "PRODUCT";
    public static final String COL_BARCODE = "BARCODE";
    public static final String COL_DESCRIPTION = "DESCRIPTION";
    public static final String COL_SIZE_AMT = "SIZE_AMT";
    public static final String COL_SIZE_UNIT = "SIZE_AMT";
    public static final String COL_SHELF_LIFE_MONTHS = "SHELF_LINE_MONTHS";
    public static final String COL_CREATE_DATE = "CREATE_DATE";
    public static final String COL_3_MONTH_SUPPLY = "_3_MONTH_SUPPLY";

    private static final String[] COLUMN_NAMES = new String[]{
        COL_BARCODE,
        COL_DESCRIPTION,
        COL_SIZE_AMT,
        COL_SIZE_UNIT,
        COL_SHELF_LIFE_MONTHS,
        COL_CREATE_DATE,
        COL_3_MONTH_SUPPLY
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
