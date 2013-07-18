package core.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.awt.event.ItemListener;
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
import core.model.BarCode;
import core.model.Item;
import core.model.Product;

public class ItemLabelControllerTest {
    
    static List<Item> itemList = new ArrayList<Item>();

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
	Product product1 = core.model.Product.Factory.newProduct(new BarCode(
		"0014700011105"), "Salerno Butter Cookies");
	Product product2 = core.model.Product.Factory.newProduct(new BarCode(
		"0758108021563"), "KIDNEY BEANS");
	Product product3 = core.model.Product.Factory.newProduct(new BarCode(
		"0012044371503"), "Old Spice Aftershave Original");
	Product product4 = core.model.Product.Factory.newProduct(new BarCode(
		"0077260008343"), "I/O HLLW CHOC BUNNY 24PK");

	Calendar cal = Calendar.getInstance();
	Date currentDate = DateUtils.currentDate();
	cal.setTime(currentDate);
	cal.add(Calendar.MONTH, 3);
	Date expirationDate = cal.getTime();
	for (int i = 0; i < 4; i++) {
	    itemList.add(core.model.Item.Factory.newItem(product1, currentDate,
		    expirationDate));
	}
	cal.setTime(currentDate);
	cal.add(Calendar.YEAR, 2);
	expirationDate = cal.getTime();
	for (int i = 0; i < 3; i++) {
	    itemList.add(core.model.Item.Factory.newItem(product2, currentDate,
		    expirationDate));
	}
	for (int i = 0; i < 2; i++) {
	    itemList.add(core.model.Item.Factory.newItem(product3, currentDate,
		    null));
	}
	itemList.add(core.model.Item.Factory.newItem(product4, currentDate, expirationDate));
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
    public void testGetInstance() {
	ItemLabelController ilcOne = ItemLabelController.getInstance();
	String stringOne = ilcOne.toString();
	ItemLabelController ilcTwo = ItemLabelController.getInstance();
	String stringTwo = ilcTwo.toString();
	assertEquals(stringOne, stringTwo);
    }

    @Test
    public void testCreateDocument() {
	Iterator<Item> iter = itemList.iterator();
	ItemLabelController.getInstance().createDocument(iter);
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
