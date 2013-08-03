package gui.reports.productstats;

import java.util.Calendar;
import java.util.Date;
import java.util.Observer;

import core.model.BarCode;
import core.model.Item;
import core.model.ItemCollection;
import core.model.Product;
import core.model.ProductContainer;
import core.model.exception.HITException;

public class ListOfItems {
	ItemCollection currentItems = new ItemCollection(null);
	ItemCollection removedItems = new ItemCollection(null);
	
	//1: added today
	//2: added yesterday
	//3: added 1 week ago
	//4: added 2 weeks ago
	//5: added 1 month ago
	//6: added 1 year ago
	//7: added today, removed today
	//8: added 3 weeks ago, removed 1 week ago
	//9: added 1 year ago, removed yesterday
	//10: added 6 months ago, removed 3 months ago
	//11: added 6 weeks ago, removed 6 weeks ago
	//12: added yesterday, removed today

	public ListOfItems() {
		try {
			currentItems.add(new MockItem1());
			currentItems.add(new MockItem2());
			currentItems.add(new MockItem3());
			currentItems.add(new MockItem4());
			currentItems.add(new MockItem5());
			currentItems.add(new MockItem6());

			removedItems.add(new MockItem7());
			removedItems.add(new MockItem8());
			removedItems.add(new MockItem9());
			removedItems.add(new MockItem10());
			removedItems.add(new MockItem11());
			removedItems.add(new MockItem12());
		} catch (HITException e) {
			e.printStackTrace();
		}
	}

	public Iterable<Item> returnCurrent() {
		return currentItems.getContents();
	}

	public Iterable<Item> returnRemoved() {
		return removedItems.getContents();
	}

	@SuppressWarnings("serial")
	// Mock Item classes
	// 1-6 current items, 7-12 removed items
	// 1: added today
	class MockItem1 implements Item {
		@Override
		public Date getEntryDate() {
			return new Date();
		}

		@SuppressWarnings("rawtypes")
		@Override
		public void wasAddedTo(ProductContainer container) throws HITException {

		}

		@SuppressWarnings("rawtypes")
		@Override
		public void wasRemovedFrom(ProductContainer container)
				throws HITException {

		}

		@SuppressWarnings("rawtypes")
		@Override
		public void transfer(ProductContainer from, ProductContainer to)
				throws HITException {

		}

		@SuppressWarnings("rawtypes")
		@Override
		public ProductContainer getContainer() {

			return null;
		}

		@Override
		public Product getProduct() {

			return null;
		}

		@Override
		public BarCode getBarCode() {

			return BarCode.getBarCodeFor("100000000001");
		}

		@Override
		public Date getExitDate() {

			return null;
		}

		@Override
		public void setExitDate(Date d) {

		}

		@Override
		public Date getExpirationDate() {

			return null;
		}

		@Override
		public void setEntryDate(Date date) {

		}

		@Override
		public void addObs(Observer o) {

		}
	}

	@SuppressWarnings("serial")
	// 2: added one day ago
	class MockItem2 implements Item {
		@Override
		public Date getEntryDate() {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.DATE, -1);
			return cal.getTime();
		}

		@SuppressWarnings("rawtypes")
		@Override
		public void wasAddedTo(ProductContainer container) throws HITException {

		}

		@SuppressWarnings("rawtypes")
		@Override
		public void wasRemovedFrom(ProductContainer container)
				throws HITException {

		}

		@SuppressWarnings("rawtypes")
		@Override
		public void transfer(ProductContainer from, ProductContainer to)
				throws HITException {

		}

		@SuppressWarnings("rawtypes")
		@Override
		public ProductContainer getContainer() {

			return null;
		}

		@Override
		public Product getProduct() {

			return null;
		}

		@Override
		public BarCode getBarCode() {

			return BarCode.getBarCodeFor("100000000002");
		}

		@Override
		public Date getExitDate() {

			return null;
		}

		@Override
		public void setExitDate(Date d) {

		}

		@Override
		public Date getExpirationDate() {

			return null;
		}

		@Override
		public void setEntryDate(Date date) {

		}

		@Override
		public void addObs(Observer o) {

		}
	}

	// 3: added one week ago
	@SuppressWarnings("serial")
	class MockItem3 implements Item {
		@Override
		public Date getEntryDate() {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.WEEK_OF_YEAR, -1);
			return cal.getTime();
		}

		@SuppressWarnings("rawtypes")
		@Override
		public void wasAddedTo(ProductContainer container) throws HITException {

		}

		@SuppressWarnings("rawtypes")
		@Override
		public void wasRemovedFrom(ProductContainer container)
				throws HITException {

		}

		@SuppressWarnings("rawtypes")
		@Override
		public void transfer(ProductContainer from, ProductContainer to)
				throws HITException {

		}

		@SuppressWarnings("rawtypes")
		@Override
		public ProductContainer getContainer() {

			return null;
		}

		@Override
		public Product getProduct() {

			return null;
		}

		@Override
		public BarCode getBarCode() {

			return BarCode.getBarCodeFor("100000000003");
		}

		@Override
		public Date getExitDate() {

			return null;
		}

		@Override
		public void setExitDate(Date d) {

		}

		@Override
		public Date getExpirationDate() {

			return null;
		}

		@Override
		public void setEntryDate(Date date) {

		}

		@Override
		public void addObs(Observer o) {

		}
	}

	// 4: added two weeks ago
	@SuppressWarnings("serial")
	class MockItem4 implements Item {
		@Override
		public Date getEntryDate() {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.WEEK_OF_YEAR, -2);
			return cal.getTime();
		}

		@SuppressWarnings("rawtypes")
		@Override
		public void wasAddedTo(ProductContainer container) throws HITException {

		}

		@SuppressWarnings("rawtypes")
		@Override
		public void wasRemovedFrom(ProductContainer container)
				throws HITException {

		}

		@SuppressWarnings("rawtypes")
		@Override
		public void transfer(ProductContainer from, ProductContainer to)
				throws HITException {

		}

		@SuppressWarnings("rawtypes")
		@Override
		public ProductContainer getContainer() {

			return null;
		}

		@Override
		public Product getProduct() {

			return null;
		}

		@Override
		public BarCode getBarCode() {

			return BarCode.getBarCodeFor("100000000004");
		}

		@Override
		public Date getExitDate() {

			return null;
		}

		@Override
		public void setExitDate(Date d) {

		}

		@Override
		public Date getExpirationDate() {

			return null;
		}

		@Override
		public void setEntryDate(Date date) {

		}

		@Override
		public void addObs(Observer o) {

		}
	}

	// 5: added one month ago
	@SuppressWarnings("serial")
	class MockItem5 implements Item {
		@Override
		public Date getEntryDate() {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.MONTH, -1);
			return cal.getTime();
		}

		@SuppressWarnings("rawtypes")
		@Override
		public void wasAddedTo(ProductContainer container) throws HITException {

		}

		@SuppressWarnings("rawtypes")
		@Override
		public void wasRemovedFrom(ProductContainer container)
				throws HITException {

		}

		@SuppressWarnings("rawtypes")
		@Override
		public void transfer(ProductContainer from, ProductContainer to)
				throws HITException {

		}

		@SuppressWarnings("rawtypes")
		@Override
		public ProductContainer getContainer() {

			return null;
		}

		@Override
		public Product getProduct() {

			return null;
		}

		@Override
		public BarCode getBarCode() {

			return BarCode.getBarCodeFor("100000000005");
		}

		@Override
		public Date getExitDate() {

			return null;
		}

		@Override
		public void setExitDate(Date d) {

		}

		@Override
		public Date getExpirationDate() {

			return null;
		}

		@Override
		public void setEntryDate(Date date) {

		}

		@Override
		public void addObs(Observer o) {

		}
	}

	// 6: added one year ago
	@SuppressWarnings("serial")
	class MockItem6 implements Item {
		@Override
		public Date getEntryDate() {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.YEAR, -1);
			return cal.getTime();
		}

		@SuppressWarnings("rawtypes")
		@Override
		public void wasAddedTo(ProductContainer container) throws HITException {

		}

		@SuppressWarnings("rawtypes")
		@Override
		public void wasRemovedFrom(ProductContainer container)
				throws HITException {

		}

		@SuppressWarnings("rawtypes")
		@Override
		public void transfer(ProductContainer from, ProductContainer to)
				throws HITException {

		}

		@SuppressWarnings("rawtypes")
		@Override
		public ProductContainer getContainer() {

			return null;
		}

		@Override
		public Product getProduct() {

			return null;
		}

		@Override
		public BarCode getBarCode() {

			return BarCode.getBarCodeFor("100000000006");
		}

		@Override
		public Date getExitDate() {

			return null;
		}

		@Override
		public void setExitDate(Date d) {

		}

		@Override
		public Date getExpirationDate() {

			return null;
		}

		@Override
		public void setEntryDate(Date date) {

		}

		@Override
		public void addObs(Observer o) {

		}
	}

	// Removed Items
	// 7: added today, removed today
	@SuppressWarnings("serial")
	class MockItem7 implements Item {
		@Override
		public Date getEntryDate() {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			return cal.getTime();
		}

		@Override
		public Date getExitDate() {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			return cal.getTime();
		}

		@SuppressWarnings("rawtypes")
		@Override
		public void wasAddedTo(ProductContainer container) throws HITException {

		}

		@SuppressWarnings("rawtypes")
		@Override
		public void wasRemovedFrom(ProductContainer container)
				throws HITException {

		}

		@SuppressWarnings("rawtypes")
		@Override
		public void transfer(ProductContainer from, ProductContainer to)
				throws HITException {

		}

		@SuppressWarnings("rawtypes")
		@Override
		public ProductContainer getContainer() {

			return null;
		}

		@Override
		public Product getProduct() {

			return null;
		}

		@Override
		public BarCode getBarCode() {

			return BarCode.getBarCodeFor("100000000007");
		}

		@Override
		public void setExitDate(Date d) {

		}

		@Override
		public Date getExpirationDate() {

			return null;
		}

		@Override
		public void setEntryDate(Date date) {

		}

		@Override
		public void addObs(Observer o) {

		}
	}

	// 8: Added 3 weeks ago, removed 1 week ago
	@SuppressWarnings("serial")
	class MockItem8 implements Item {
		@Override
		public Date getEntryDate() {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.WEEK_OF_YEAR, -3);
			return cal.getTime();
		}

		@Override
		public Date getExitDate() {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.WEEK_OF_YEAR, -1);
			return cal.getTime();
		}

		@SuppressWarnings("rawtypes")
		@Override
		public void wasAddedTo(ProductContainer container) throws HITException {

		}

		@SuppressWarnings("rawtypes")
		@Override
		public void wasRemovedFrom(ProductContainer container)
				throws HITException {

		}

		@SuppressWarnings("rawtypes")
		@Override
		public void transfer(ProductContainer from, ProductContainer to)
				throws HITException {

		}

		@SuppressWarnings("rawtypes")
		@Override
		public ProductContainer getContainer() {

			return null;
		}

		@Override
		public Product getProduct() {

			return null;
		}

		@Override
		public BarCode getBarCode() {

			return BarCode.getBarCodeFor("100000000008");
		}

		@Override
		public void setExitDate(Date d) {

		}

		@Override
		public Date getExpirationDate() {

			return null;
		}

		@Override
		public void setEntryDate(Date date) {

		}

		@Override
		public void addObs(Observer o) {

		}
	}

	// 9: added one year ago. Removed yesterday
	@SuppressWarnings("serial")
	class MockItem9 implements Item {
		@Override
		public Date getEntryDate() {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.YEAR, -1);
			return cal.getTime();
		}

		@Override
		public Date getExitDate() {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.DATE, -1);
			return cal.getTime();
		}

		@SuppressWarnings("rawtypes")
		@Override
		public void wasAddedTo(ProductContainer container) throws HITException {

		}

		@SuppressWarnings("rawtypes")
		@Override
		public void wasRemovedFrom(ProductContainer container)
				throws HITException {

		}

		@SuppressWarnings("rawtypes")
		@Override
		public void transfer(ProductContainer from, ProductContainer to)
				throws HITException {

		}

		@SuppressWarnings("rawtypes")
		@Override
		public ProductContainer getContainer() {

			return null;
		}

		@Override
		public Product getProduct() {

			return null;
		}

		@Override
		public BarCode getBarCode() {

			return BarCode.getBarCodeFor("100000000009");
		}

		@Override
		public void setExitDate(Date d) {

		}

		@Override
		public Date getExpirationDate() {

			return null;
		}

		@Override
		public void setEntryDate(Date date) {

		}

		@Override
		public void addObs(Observer o) {

		}
	}

	// 10: added six months ago, removed 3 months ago
	@SuppressWarnings("serial")
	class MockItem10 implements Item {
		@Override
		public Date getEntryDate() {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.MONTH, -6);
			return cal.getTime();
		}

		@Override
		public Date getExitDate() {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.MONTH, -3);
			return cal.getTime();
		}

		@SuppressWarnings("rawtypes")
		@Override
		public void wasAddedTo(ProductContainer container) throws HITException {

		}

		@SuppressWarnings("rawtypes")
		@Override
		public void wasRemovedFrom(ProductContainer container)
				throws HITException {

		}

		@SuppressWarnings("rawtypes")
		@Override
		public void transfer(ProductContainer from, ProductContainer to)
				throws HITException {

		}

		@SuppressWarnings("rawtypes")
		@Override
		public ProductContainer getContainer() {

			return null;
		}

		@Override
		public Product getProduct() {

			return null;
		}

		@Override
		public BarCode getBarCode() {

			return BarCode.getBarCodeFor("100000000010");
		}

		@Override
		public void setExitDate(Date d) {

		}

		@Override
		public Date getExpirationDate() {

			return null;
		}

		@Override
		public void setEntryDate(Date date) {

		}

		@Override
		public void addObs(Observer o) {

		}
	}

	// 11: Added and removed 6 weeks ago
	@SuppressWarnings("serial")
	class MockItem11 implements Item {
		@Override
		public Date getEntryDate() {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.WEEK_OF_YEAR, -6);
			return cal.getTime();
		}

		@Override
		public Date getExitDate() {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.WEEK_OF_YEAR, -6);
			return cal.getTime();
		}

		@SuppressWarnings("rawtypes")
		@Override
		public void wasAddedTo(ProductContainer container) throws HITException {

		}

		@SuppressWarnings("rawtypes")
		@Override
		public void wasRemovedFrom(ProductContainer container)
				throws HITException {

		}

		@SuppressWarnings("rawtypes")
		@Override
		public void transfer(ProductContainer from, ProductContainer to)
				throws HITException {

		}

		@SuppressWarnings("rawtypes")
		@Override
		public ProductContainer getContainer() {

			return null;
		}

		@Override
		public Product getProduct() {

			return null;
		}

		@Override
		public BarCode getBarCode() {

			return BarCode.getBarCodeFor("100000000011");
		}

		@Override
		public void setExitDate(Date d) {

		}

		@Override
		public Date getExpirationDate() {

			return null;
		}

		@Override
		public void setEntryDate(Date date) {

		}

		@Override
		public void addObs(Observer o) {

		}
	}

	// 12: added yesterday, removed today
	@SuppressWarnings("serial")
	class MockItem12 implements Item {
		@Override
		public Date getEntryDate() {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.DATE, -1);
			return cal.getTime();
		}

		@Override
		public Date getExitDate() {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			return cal.getTime();
		}

		@SuppressWarnings("rawtypes")
		@Override
		public void wasAddedTo(ProductContainer container) throws HITException {

		}

		@SuppressWarnings("rawtypes")
		@Override
		public void wasRemovedFrom(ProductContainer container)
				throws HITException {

		}

		@SuppressWarnings("rawtypes")
		@Override
		public void transfer(ProductContainer from, ProductContainer to)
				throws HITException {

		}

		@SuppressWarnings("rawtypes")
		@Override
		public ProductContainer getContainer() {

			return null;
		}

		@Override
		public Product getProduct() {

			return null;
		}

		@Override
		public BarCode getBarCode() {

			return BarCode.getBarCodeFor("100000000012");
		}

		@Override
		public void setExitDate(Date d) {

		}

		@Override
		public Date getExpirationDate() {

			return null;
		}

		@Override
		public void setEntryDate(Date date) {

		}

		@Override
		public void addObs(Observer o) {

		}
	}
}
