package core.model;

import static org.junit.Assert.assertEquals;

import static core.model.Product.Factory.newProduct;
import static core.model.Item.Factory.newItem;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import common.util.DateUtils;
import gui.batches.ItemLabelController;

public class ItemLabelControllerTest {
    
    static List<Item> itemList = new ArrayList<>();

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
	Product product1 = newProduct(BarCode.getBarCodeFor("0014700011105"), 
                "Salerno Butter Cookies");
	Product product2 = newProduct(BarCode.getBarCodeFor("0758108021563"), 
                "KIDNEY BEANS");
	Product product3 = newProduct(BarCode.getBarCodeFor("0012044371503"), 
                "Old Spice Aftershave Original");
	Product product4 = newProduct(BarCode.getBarCodeFor("0077260008343"), 
                "I/O HLLW CHOC BUNNY 24PK");

	Calendar cal = Calendar.getInstance();
	Date currentDate = DateUtils.currentDate();
	cal.setTime(currentDate);
	cal.add(Calendar.MONTH, 3);
	for (int i = 0; i < 4; i++) {
	    itemList.add(newItem(product1, currentDate));
	}
	cal.setTime(currentDate);
	cal.add(Calendar.YEAR, 2);
	for (int i = 0; i < 3; i++) {
	    itemList.add(newItem(product2, currentDate));
	}
	for (int i = 0; i < 2; i++) {
	    itemList.add(newItem(product3, currentDate));
	}
	itemList.add(newItem(product4, currentDate));
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
	itemList.clear();
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testCreateDocument() {
	ItemLabelController.createDocument(itemList.toArray(new Item[itemList.size()]));
    }
/*
    @Test
    public void testFormatDocument() {
	fail("Not yet implemented");
    }

    @Test
    public void testGenerateBarCode() {
	fail("Not yet implemented");
    }

    @Test
    public void testGenerateFileName() {
	fail("Not yet implemented");
    }

    @Test
    public void testDisplayDocument() {
	fail("Not yet implemented");
    }
*/
}
