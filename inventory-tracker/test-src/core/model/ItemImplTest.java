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
		if(!i.getProduct().equals(p)){
			fail();
		}
		if(!i.getEntryDate().equals(d)){
			fail();
		}
		if(!i.getExpirationDate().equals(d)){
			fail();
		}
		if(!i.getBarCode().equals(b)){
			fail();
		}
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
		if(!i.getProduct().equals(p)){
			fail();
		}
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
		if(!i.getBarCode().equals(b)){
			fail();
		}
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
		if(!i.getEntryDate().equals(d)){
			fail();
		}
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
		i.setExitDate(d);
		if(!i.getExitDate().equals(d)){
			fail();
		}
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
		if(!i.getExpirationDate().equals(d)) {
			fail();
		}
	}

}
