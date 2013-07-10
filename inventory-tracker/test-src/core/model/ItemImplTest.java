/**
 * 
 */
package core.model;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import core.model.exception.HITException;

/**
 * @author aaron
 *
 */
public class ItemImplTest {

	/**
	 * Test method for {@link core.model.ItemImpl#ItemImpl(java.util.Date, java.util.Date, core.model.Product, core.model.BarCode)}.
	 * @throws HITException 
	 */
	@Test
	public void testItemImpl() throws HITException {
		Date d = new Date();
		BarCode b = BarCode.generateItemBarCode();
		BarCode b2 = BarCode.generateItemBarCode();
		Product p = Product.Factory.newProduct(b2);
		Item i = new ItemImpl(d, d, p, b);
	}

	/**
	 * Test method for {@link core.model.ItemImpl#getProduct()}.
	 * @throws HITException 
	 */
	@Test
	public void testGetProduct() throws HITException {
		Date d = new Date();
		BarCode b = BarCode.generateItemBarCode();
		BarCode b2 = BarCode.generateItemBarCode();
		Product p = Product.Factory.newProduct(b2);
		Item i = new ItemImpl(d, d, p, b);
		i.getProduct();
	}

	/**
	 * Test method for {@link core.model.ItemImpl#getBarCode()}.
	 * @throws HITException 
	 */
	@Test
	public void testGetBarCode() throws HITException {
		Date d = new Date();
		BarCode b = BarCode.generateItemBarCode();
		BarCode b2 = BarCode.generateItemBarCode();
		Product p = Product.Factory.newProduct(b2);
		Item i = new ItemImpl(d, d, p, b);
		i.getBarCode();
	}

	/**
	 * Test method for {@link core.model.ItemImpl#getEntryDate()}.
	 * @throws HITException 
	 */
	@Test
	public void testGetEntryDate() throws HITException {
		Date d = new Date();
		BarCode b = BarCode.generateItemBarCode();
		BarCode b2 = BarCode.generateItemBarCode();
		Product p = Product.Factory.newProduct(b2);
		Item i = new ItemImpl(d, d, p, b);
		i.getEntryDate();
	}

	/**
	 * Test method for {@link core.model.ItemImpl#getExitDate()}.
	 * @throws HITException 
	 */
	@Test
	public void testGetExitDate() throws HITException {
		Date d = new Date();
		BarCode b = BarCode.generateItemBarCode();
		BarCode b2 = BarCode.generateItemBarCode();
		Product p = Product.Factory.newProduct(b2);
		Item i = new ItemImpl(d, d, p, b);
		i.getExitDate();
	}

	/**
	 * Test method for {@link core.model.ItemImpl#setExitDate(java.util.Date)}.
	 * @throws HITException 
	 */
	@Test
	public void testSetExitDate() throws HITException {
		Date d = new Date();
		BarCode b = BarCode.generateItemBarCode();
		BarCode b2 = BarCode.generateItemBarCode();
		Product p = Product.Factory.newProduct(b2);
		Date d2 = new Date(20000);
		Item i = new ItemImpl(d, d, p, b);
		i.setExitDate(d2);
	}

	/**
	 * Test method for {@link core.model.ItemImpl#getExpirationDate()}.
	 * @throws HITException 
	 */
	@Test
	public void testGetExpirationDate() throws HITException {
		Date d = new Date();
		BarCode b = BarCode.generateItemBarCode();
		BarCode b2 = BarCode.generateItemBarCode();
		Product p = Product.Factory.newProduct(b2);
		Item i = new ItemImpl(d, d, p, b);
		i.getExpirationDate();
	}

	/**
	 * Test method for {@link core.model.AbstractContainable#wasAddedTo(core.model.Container)}.
	 */
	@Test
	public void testWasAddedTo() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link core.model.AbstractContainable#wasRemovedFrom(core.model.Container)}.
	 */
	@Test
	public void testWasRemovedFrom() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link core.model.AbstractContainable#transfer(core.model.Container, core.model.Container)}.
	 */
	@Test
	public void testTransfer() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link core.model.AbstractContainable#getContainer()}.
	 */
	@Test
	public void testGetContainer() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link core.model.AbstractContainable#isContainedIn(core.model.Container)}.
	 */
	@Test
	public void testIsContainedIn() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link core.model.AbstractContainable#verifyAddedTo(core.model.Container, core.model.Containable)}.
	 */
	@Test
	public void testVerifyAddedTo() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link core.model.AbstractContainable#verifyRemovedFrom(core.model.Container, core.model.Containable)}.
	 */
	@Test
	public void testVerifyRemovedFrom() {
		fail("Not yet implemented"); // TODO
	}

}
