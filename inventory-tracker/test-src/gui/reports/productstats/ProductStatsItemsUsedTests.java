package gui.reports.productstats;

import junit.framework.Assert;
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
        this.testCalculateItemsUsed(1, 4);
    }

    @Test
    public void testCalculateItemsUsed3Month() {
        this.testCalculateItemsUsed(3, 6);
    }

    @Test
    public void testCalculateItemsUsed6Month() {
        this.testCalculateItemsUsed(6, 6);
    }

    @Test
    public void testCalculateItemsUsed9Month() {
        this.testCalculateItemsUsed(9, 6);
    }

    @Test
    public void testCalculateItemsUsed12Month() {
        this.testCalculateItemsUsed(12, 6);
    }

    @Test
    public void testCalculateItemsUsed24Month() {
        this.testCalculateItemsUsed(24, 6);
    }

    private void testCalculateItemsUsed(int m, int expected) {
        cal.add(Calendar.MONTH, -m);
        calc = newProductStatsCalculator(cal.getTime(), list.returnCurrent(),
                list.returnRemoved());
        out.println("Items used: " + m + " month = " + calc.itemsUsed());

        Assert.assertEquals(expected, calc.itemsUsed());
    }
}
