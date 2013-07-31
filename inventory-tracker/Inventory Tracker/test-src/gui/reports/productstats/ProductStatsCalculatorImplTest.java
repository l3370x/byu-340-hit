package gui.reports.productstats;

import static org.junit.Assert.*;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Observer;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import core.model.BarCode;
import core.model.Product;
import core.model.ProductContainer;
import core.model.Quantity;
import core.model.StorageUnit;
import core.model.exception.HITException;

public class ProductStatsCalculatorImplTest {

	ProductStatsCalculator calc = ProductStatsCalculator.Factory
			.newProductStatsCalculator();
	ListOfItems list = new ListOfItems();
	Calendar cal = Calendar.getInstance();
	Product product = new MockProduct();

	static PrintStream out;

	@SuppressWarnings("serial")
	class MockProduct implements Product {

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
		public Date getCreationDate() {
			Calendar c = Calendar.getInstance();
			c.add(Calendar.MONTH, -18);
			return c.getTime();
		}

		@Override
		public BarCode getBarCode() {

			return null;
		}

		@Override
		public String getDescription() {

			return null;
		}

		@Override
		public void setDescription(String description) {

		}

		@Override
		public Quantity getSize() {

			return null;
		}

		@Override
		public void setSize(Quantity quantity) {

		}

		@Override
		public void setCreationDate(Date d) {

		}

		@Override
		public int getShelfLifeInMonths() {

			return 0;
		}

		@Override
		public void setShelfLifeInMonths(int shelfLife) throws HITException {

		}

		@Override
		public int get3MonthSupplyQuota() {

			return 0;
		}

		@Override
		public void set3MonthSupplyQuota(int quota) throws HITException {

		}

		@Override
		public Iterable<StorageUnit> getStorageUnits() {

			return null;
		}

		@SuppressWarnings("rawtypes")
		@Override
		public Iterable<ProductContainer> getProductContainers() {

			return null;
		}

		@SuppressWarnings("rawtypes")
		@Override
		public ProductContainer getProductContainer(StorageUnit unit) {

			return null;
		}

		@Override
		public void addObs(Observer o) {

		}

	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		out = new PrintStream(new FileOutputStream("output.txt"));
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		list = new ListOfItems();
		cal.setTime(new Date());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCalculateCurrentSupply1Month() {
		cal.add(Calendar.MONTH, -1);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Current Supply: 1 month = "
				+ calc.calculateCurrentSupply());
	}

	@Test
	public void testCalculateCurrentSupply3Month() {
		cal.add(Calendar.MONTH, -3);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Current Supply: 3 month = "
				+ calc.calculateCurrentSupply());
	}

	@Test
	public void testCalculateCurrentSupply6Month() {
		cal.add(Calendar.MONTH, -6);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Current Supply: 6 month = "
				+ calc.calculateCurrentSupply());
	}

	@Test
	public void testCalculateCurrentSupply9Month() {
		cal.add(Calendar.MONTH, -9);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Current Supply: 9 month = "
				+ calc.calculateCurrentSupply());
	}

	@Test
	public void testCalculateCurrentSupply12Month() {
		cal.add(Calendar.MONTH, -12);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Current Supply: 12 month = "
				+ calc.calculateCurrentSupply());
	}

	@Test
	public void testCalculateCurrentSupply24Month() {
		cal.add(Calendar.MONTH, -24);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Current Supply: 24 month = "
				+ calc.calculateCurrentSupply());
	}

	@Test
	public void testCalculateAverageSupply1Month() {
		cal.add(Calendar.MONTH, -1);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Average Supply: 1 month = "
				+ calc.calculateAverageSupply());
	}

	@Test
	public void testCalculateAverageSupply3Month() {
		cal.add(Calendar.MONTH, -3);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Average Supply: 3 month = "
				+ calc.calculateAverageSupply());
	}

	@Test
	public void testCalculateAverageSupply6Month() {
		cal.add(Calendar.MONTH, -6);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Average Supply: 6 month = "
				+ calc.calculateAverageSupply());
	}

	@Test
	public void testCalculateAverageSupply9Month() {
		cal.add(Calendar.MONTH, -9);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Average Supply: 9 month = "
				+ calc.calculateAverageSupply());
	}

	@Test
	public void testCalculateAverageSupply12Month() {
		cal.add(Calendar.MONTH, -12);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Average Supply: 12 month = "
				+ calc.calculateAverageSupply());
	}

	@Test
	public void testCalculateAverageSupply24Month() {
		cal.add(Calendar.MONTH, -24);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Average Supply: 24 month = "
				+ calc.calculateAverageSupply());
	}

	@Test
	public void testCalculateMinimumSupply1Month() {
		cal.add(Calendar.MONTH, -1);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Minimum Supply: 1 month = "
				+ calc.calculateMinimumSupply());
	}

	@Test
	public void testCalculateMinimumSupply3Month() {
		cal.add(Calendar.MONTH, -3);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Minimum Supply: 3 month = "
				+ calc.calculateMinimumSupply());
	}

	@Test
	public void testCalculateMinimumSupply6Month() {
		cal.add(Calendar.MONTH, -6);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Minimum Supply: 6 month = "
				+ calc.calculateMinimumSupply());
	}

	@Test
	public void testCalculateMinimumSupply9Month() {
		cal.add(Calendar.MONTH, -9);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Minimum Supply: 9 month = "
				+ calc.calculateMinimumSupply());
	}

	@Test
	public void testCalculateMinimumSupply12Month() {
		cal.add(Calendar.MONTH, -12);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Minimum Supply: 12 month = "
				+ calc.calculateMinimumSupply());
	}

	@Test
	public void testCalculateMinimumSupply24Month() {
		cal.add(Calendar.MONTH, -24);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Minimum Supply: 24 month = "
				+ calc.calculateMinimumSupply());
	}

	@Test
	public void testCalculateMaximumSupply1Month() {
		cal.add(Calendar.MONTH, -1);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Maximum Supply: 1 month = "
				+ calc.calculateMaximumSupply());
	}

	@Test
	public void testCalculateMaximumSupply3Month() {
		cal.add(Calendar.MONTH, -3);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Maximum Supply: 3 month = "
				+ calc.calculateMaximumSupply());
	}

	@Test
	public void testCalculateMaximumSupply6Month() {
		cal.add(Calendar.MONTH, -6);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Maximum Supply: 6 month = "
				+ calc.calculateMaximumSupply());
	}

	@Test
	public void testCalculateMaximumSupply9Month() {
		cal.add(Calendar.MONTH, -9);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Maximum Supply: 9 month = "
				+ calc.calculateMaximumSupply());
	}

	@Test
	public void testCalculateMaximumSupply12Month() {
		cal.add(Calendar.MONTH, -12);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Maximum Supply: 12 month = "
				+ calc.calculateMaximumSupply());
	}

	@Test
	public void testCalculateMaximumSupply24Month() {
		cal.add(Calendar.MONTH, -24);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Maximum Supply: 24 month = "
				+ calc.calculateMaximumSupply());
	}

	@Test
	public void testCalculateItemsUsed1Month() {
		cal.add(Calendar.MONTH, -1);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Items used: 1 month = " + calc.calculateItemsUsed());
	}

	@Test
	public void testCalculateItemsUsed3Month() {
		cal.add(Calendar.MONTH, -3);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Items used: 3 month = " + calc.calculateItemsUsed());
	}

	@Test
	public void testCalculateItemsUsed6Month() {
		cal.add(Calendar.MONTH, -6);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Items used: 6 month = " + calc.calculateItemsUsed());
	}

	@Test
	public void testCalculateItemsUsed9Month() {
		cal.add(Calendar.MONTH, -9);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Items used: 9 month = " + calc.calculateItemsUsed());
	}

	@Test
	public void testCalculateItemsUsed12Month() {
		cal.add(Calendar.MONTH, -12);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Items used: 12 month = " + calc.calculateItemsUsed());
	}

	@Test
	public void testCalculateItemsUsed24Month() {
		cal.add(Calendar.MONTH, -24);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Items used: 24 month = " + calc.calculateItemsUsed());
	}

	@Test
	public void testCalculateItemsAdded1Month() {
		cal.add(Calendar.MONTH, -1);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Items added: 1 month = " + calc.calculateItemsAdded());
	}

	@Test
	public void testCalculateItemsAdded3Month() {
		cal.add(Calendar.MONTH, -3);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Items added: 3 month = " + calc.calculateItemsAdded());
	}

	@Test
	public void testCalculateItemsAdded6Month() {
		cal.add(Calendar.MONTH, -6);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Items added: 6 month = " + calc.calculateItemsAdded());
	}

	@Test
	public void testCalculateItemsAdded9Month() {
		cal.add(Calendar.MONTH, -9);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Items added: 9 month = " + calc.calculateItemsAdded());
	}

	@Test
	public void testCalculateItemsAdded12Month() {
		cal.add(Calendar.MONTH, -12);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Items added: 12 month = " + calc.calculateItemsAdded());
	}

	@Test
	public void testCalculateItemsAdded24Month() {
		cal.add(Calendar.MONTH, -24);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Items added: 24 month = " + calc.calculateItemsAdded());
	}

	@Test
	public void testCalculateAverageAgeUsed1Month() {
		cal.add(Calendar.MONTH, -1);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Average Age Used: 1 month = "
				+ calc.calculateAverageAgedCurrent());
	}

	@Test
	public void testCalculateAverageAgeUsed3Month() {
		cal.add(Calendar.MONTH, -3);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Average Age Used: 3 month = "
				+ calc.calculateAverageAgedCurrent());
	}

	@Test
	public void testCalculateAverageAgeUsed6Month() {
		cal.add(Calendar.MONTH, -6);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Average Age Used: 6 month = "
				+ calc.calculateAverageAgedCurrent());
	}

	@Test
	public void testCalculateAverageAgeUsed9Month() {
		cal.add(Calendar.MONTH, -9);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Average Age Used: 9 month = "
				+ calc.calculateAverageAgedCurrent());
	}

	@Test
	public void testCalculateAverageAgeUsed12Month() {
		cal.add(Calendar.MONTH, -12);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Average Age Used: 12 month = "
				+ calc.calculateAverageAgedCurrent());
	}

	@Test
	public void testCalculateAverageAgeUsed24Month() {
		cal.add(Calendar.MONTH, -24);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Average Age Used: 24 month = "
				+ calc.calculateAverageAgedCurrent());
	}

	@Test
	public void testCalculateMaximumAgeUsed1Month() {
		cal.add(Calendar.MONTH, -1);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Maximum Age Used: 1 month = "
				+ calc.calculateMaximumAgeUsed());
	}

	@Test
	public void testCalculateMaximumAgeUsed3Month() {
		cal.add(Calendar.MONTH, -3);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Maximum Age Used: 3 month = "
				+ calc.calculateMaximumAgeUsed());
	}

	@Test
	public void testCalculateMaximumAgeUsed6Month() {
		cal.add(Calendar.MONTH, -6);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Maximum Age Used: 6 month = "
				+ calc.calculateMaximumAgeUsed());
	}

	@Test
	public void testCalculateMaximumAgeUsed9Month() {
		cal.add(Calendar.MONTH, -9);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Maximum Age Used: 9 month = "
				+ calc.calculateMaximumAgeUsed());
	}

	@Test
	public void testCalculateMaximumAgeUsed12Month() {
		cal.add(Calendar.MONTH, -12);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Maximum Age Used: 12 month = "
				+ calc.calculateMaximumAgeUsed());
	}

	@Test
	public void testCalculateMaximumAgeUsed24Month() {
		cal.add(Calendar.MONTH, -24);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Maximum Age Used: 24 month = "
				+ calc.calculateMaximumAgeUsed());
	}

	@Test
	public void testCalculateAverageAgedCurrent1Month() {
		cal.add(Calendar.MONTH, -1);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Average Age Current: 1 month = "
				+ calc.calculateAverageAgedCurrent());
	}

	@Test
	public void testCalculateAverageAgedCurrent3Month() {
		cal.add(Calendar.MONTH, -3);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Average Age Current: 3 month = "
				+ calc.calculateAverageAgedCurrent());
	}

	@Test
	public void testCalculateAverageAgedCurrent6Month() {
		cal.add(Calendar.MONTH, -6);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Average Age Current: 6 month = "
				+ calc.calculateAverageAgedCurrent());
	}

	@Test
	public void testCalculateAverageAgedCurrent9Month() {
		cal.add(Calendar.MONTH, -9);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Average Age Current: 9 month = "
				+ calc.calculateAverageAgedCurrent());
	}

	@Test
	public void testCalculateAverageAgedCurrent12Month() {
		cal.add(Calendar.MONTH, -12);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Average Age Current: 12 month = "
				+ calc.calculateAverageAgedCurrent());
	}

	@Test
	public void testCalculateAverageAgedCurrent24Month() {
		cal.add(Calendar.MONTH, -24);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Average Age Current: 24 month = "
				+ calc.calculateAverageAgedCurrent());
	}

	@Test
	public void testCalculateMaximumAgeCurrent1Month() {
		cal.add(Calendar.MONTH, -1);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Maximum Age Current: 1 month = "
				+ calc.calculateMaximumAgeCurrent());
	}

	@Test
	public void testCalculateMaximumAgeCurrent3Month() {
		cal.add(Calendar.MONTH, -3);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Maximum Age Current: 3 month = "
				+ calc.calculateMaximumAgeCurrent());
	}

	@Test
	public void testCalculateMaximumAgeCurrent6Month() {
		cal.add(Calendar.MONTH, -6);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Maximum Age Current: 6 month = "
				+ calc.calculateMaximumAgeCurrent());
	}

	@Test
	public void testCalculateMaximumAgeCurrent9Month() {
		cal.add(Calendar.MONTH, -9);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Maximum Age Current: 9 month = "
				+ calc.calculateMaximumAgeCurrent());
	}

	@Test
	public void testCalculateMaximumAgeCurrent12Month() {
		cal.add(Calendar.MONTH, -12);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Maximum Age Current: 12 month = "
				+ calc.calculateMaximumAgeCurrent());
	}

	@Test
	public void testCalculateMaximumAgeCurrent24Month() {
		cal.add(Calendar.MONTH, -24);
		calc.setValues(cal.getTime(), list.returnCurrent(),
				list.returnRemoved(), product);
		out.println("Maximum Age Current: 24 month = "
				+ calc.calculateMaximumAgeCurrent());
	}

	// @Test
	// public void testCalculateDayDifference() {
	// fail("Not yet implemented");
	// }
	//
	// @Test
	// public void testGetEarliestDate() {
	// fail("Not yet implemented");
	// }
	//
	// @Test
	// public void testGetLatestDate() {
	// fail("Not yet implemented");
	// }

}
