package persistence;

/**
 * Created with IntelliJ IDEA. User: kmcqueen Date: 8/3/13 Time: 9:45 PM To change this template use
 * File | Settings | File Templates.
 */
public class ProductContainerDAO extends AbstractDAO {

    public static final String TABLE_NAME = "PRODUCT_CONTAINER";
    public static final String COL_ID = "ID";
    public static final String COL_NAME = "NAME";
    public static final String COL_IS_STORAGE_UNIT = "IS_STORAGE_UNIT";
    public static final String COL_3_MO_SUPPLY_AMT = "_3_MONTH_SUPPLY_AMT";
    public static final String COL_3_MO_SUPPLY_UNITS = "_3_MONTH_SUPPLY_UNIT";
    public static final String COL_PARENT = "PARENT_CONTAINER";

    private static final String[] COLUMN_NAMES = new String[]{
            COL_ID,
            COL_NAME,
            COL_IS_STORAGE_UNIT,
            COL_3_MO_SUPPLY_AMT,
            COL_3_MO_SUPPLY_UNITS,
            COL_PARENT
    };

    private static final String[] KEY_COLUMN_NAMES = new String[]{COL_ID};

    @Override
    public String[] getColumnNames() {
        return COLUMN_NAMES;
    }

    @Override
    public String[] getKeyColumnNames() {
        return KEY_COLUMN_NAMES;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
}
