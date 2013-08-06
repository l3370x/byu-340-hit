package gui.reports.productstats;

import org.junit.Test;

import java.util.Calendar;

import static gui.reports.productstats.ProductStatsCalculator.Factory.newProductStatsCalculator;

/**
 * @author Cameron Jones
 * @author Keith McQueen
 */
public class ProductStatsItemsAdded extends ProductStatsCalculatorTests {
    @Test
    public void testCalculateItemsAdded1Month() {
        this.testCalculateItemsAdded(1);
    }

    @Test
    public void testCalculateItemsAdded3Month() {
        this.testCalculateItemsAdded(3);
    }

    @Test
    public void testCalculateItemsAdded6Month() {
        this.testCalculateItemsAdded(6);
    }

    @Test
    public void testCalculateItemsAdded9Month() {
        this.testCalculateItemsAdded(9);
    }

    @Test
    public void testCalculateItemsAdded12Month() {
        this.testCalculateItemsAdded(12);
    }

    @Test
    public void testCalculateItemsAdded24Month() {
        this.testCalculateItemsAdded(24);
    }

    private void testCalculateItemsAdded(int m) {
        cal.add(Calendar.MONTH, -m);
        calc = newProductStatsCalculator(cal.getTime(), list.returnCurrent(),
                list.returnRemoved());
        out.println("Items added: " + m + " month = " + calc.itemsAdded());
    }
}
