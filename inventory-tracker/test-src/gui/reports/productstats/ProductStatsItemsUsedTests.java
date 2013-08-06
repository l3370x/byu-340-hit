package gui.reports.productstats;

import org.junit.Test;

import java.util.Calendar;

import static gui.reports.productstats.ProductStatsCalculator.Factory.newProductStatsCalculator;

/**
 * @author Cameron Jones (originator)
 * @author Keith McQueen (refactoring)
 */
public class ProductStatsItemsUsedTests extends ProductStatsCalculatorTests {
    @Test
    public void testCalculateItemsUsed1Month() {
        this.testCalculateItemsUsed(1);
    }

    @Test
    public void testCalculateItemsUsed3Month() {
        this.testCalculateItemsUsed(3);
    }

    @Test
    public void testCalculateItemsUsed6Month() {
        this.testCalculateItemsUsed(6);
    }

    @Test
    public void testCalculateItemsUsed9Month() {
        this.testCalculateItemsUsed(9);
    }

    @Test
    public void testCalculateItemsUsed12Month() {
        this.testCalculateItemsUsed(12);
    }

    @Test
    public void testCalculateItemsUsed24Month() {
        this.testCalculateItemsUsed(24);
    }

    private void testCalculateItemsUsed(int m) {
        cal.add(Calendar.MONTH, -m);
        calc = newProductStatsCalculator(cal.getTime(), list.returnCurrent(),
                list.returnRemoved());
        out.println("Items used: " + m + " month = " + calc.itemsUsed());
    }
}
