package persistence;

import core.model.exception.HITException;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static persistence.ProductContainerDAO.*;

/**
 * Created with IntelliJ IDEA. User: kmcqueen Date: 8/3/13 Time: 11:06 PM To change this template
 * use File | Settings | File Templates.
 */
public class ProductContainerDAOTests {
    @Before
    public void clearProductContainers() throws HITException {
        new ProductContainerDAO().deleteAll();
    }

    @Test
    public void testInsertProductContainer() throws HITException {
        // create the DAO
        ProductContainerDAO dao = new ProductContainerDAO();

        // create the DTO
        DataTransferObject dtoIn = new DataTransferObject();
        dtoIn.setValue(COL_NAME, "My Storage Unit");
        dtoIn.setValue(COL_IS_STORAGE_UNIT, Boolean.TRUE);
        dtoIn.setValue(COL_3_MO_SUPPLY_AMT, 1.03);

        // insert the data
        dao.insert(dtoIn);

        // select it back out and make sure it matches
        //Iterable<DataTransferObject> data = dao.getAll();
        Iterable<DataTransferObject> data = dao.get(dtoIn);
        for (DataTransferObject dtoOut : data) {
            assertEquals(dtoIn.getValue(COL_NAME), dtoOut.getValue(COL_NAME));
            assertTrue(Boolean.valueOf(String.valueOf(dtoOut.getValue(COL_IS_STORAGE_UNIT))));
        }

        // update the row
        for (DataTransferObject dtoUpdate : data) {
            dtoUpdate.setValue(COL_NAME, "Updated Name");
            dtoUpdate.setValue(COL_3_MO_SUPPLY_AMT, 12.5);
            dtoUpdate.setValue(COL_3_MO_SUPPLY_UNITS, "Ounces");

            dao.update(dtoUpdate);
        }

        // select it back out and make sure it matches
        data = dao.getAll();
        for (DataTransferObject dtoOut : data) {
            assertEquals("Updated Name", dtoOut.getValue(COL_NAME));
            assertEquals(12.5, dtoOut.getValue(COL_3_MO_SUPPLY_AMT));
            assertEquals("Ounces", dtoOut.getValue(COL_3_MO_SUPPLY_UNITS));
        }
    }
}
