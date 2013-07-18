package core.model;

import static org.junit.Assert.assertEquals;

import static core.model.Product.Factory.newProduct;
import static core.model.Item.Factory.newItem;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
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
	Date expirationDate = cal.getTime();
	for (int i = 0; i < 4; i++) {
	    itemList.add(newItem(product1, currentDate, expirationDate));
	}
	cal.setTime(currentDate);
	cal.add(Calendar.YEAR, 2);
	expirationDate = cal.getTime();
	for (int i = 0; i < 3; i++) {
	    itemList.add(newItem(product2, currentDate, expirationDate));
	}
	for (int i = 0; i < 2; i++) {
	    itemList.add(newItem(product3, currentDate, null));
	}
	itemList.add(newItem(product4, currentDate, expirationDate));
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

    //@Test
    public void testGetInstance() {
	ItemLabelController ilcOne = new ItemLabelController();
	String stringOne = ilcOne.toString();
	ItemLabelController ilcTwo = new ItemLabelController();
	String stringTwo = ilcTwo.toString();
	assertEquals(stringOne, stringTwo);
    }

    @Test
    public void testCreateDocument() {
	new ItemLabelController().createDocument(itemList.iterator());
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
