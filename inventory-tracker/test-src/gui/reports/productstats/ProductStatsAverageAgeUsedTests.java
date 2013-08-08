package gui.reports.productstats;

import core.model.Item;
import junit.framework.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.Collections;

import static gui.reports.productstats.ProductStatsCalculator.Factory.newProductStatsCalculator;

/**
 * @author Cameron Jones (originator)
 * @author Keith McQueen (refactoring)
 */
public class ProductStatsAverageAgeUsedTests extends ProductStatsCalculatorTests {
    @Test
    public void testCalculateAverageAgeUsed1Month() {
        this.testCalculateAverageAgeUsed(1);
    }

    @Test
    public void testCalculateAverageAgeUsed3Month() {
        this.testCalculateAverageAgeUsed(3);
    }

    @Test
    public void testCalculateAverageAgeUsed6Month() {
        this.testCalculateAverageAgeUsed(6);
    }

    @Test
    public void testCalculateAverageAgeUsed9Month() {
        this.testCalculateAverageAgeUsed(9);
    }

    @Test
    public void testCalculateAverageAgeUsed12Month() {
        this.testCalculateAverageAgeUsed(12);
    }

    @Test
    public void testCalculateAverageAgeUsed24Month() {
        this.testCalculateAverageAgeUsed(24);
    }

    private void testCalculateAverageAgeUsed(int months) {
        cal.add(Calendar.MONTH, -months);
        calc = newProductStatsCalculator(cal.getTime(), list.returnCurrent(),
                list.returnRemoved());
        out.println("Average Age Used: " + months + " month = "
                + calc.averageAgeUsed());
    }

    @Test
    public void testCalculateAverageAgeUsedWithNoUsedItems() {
        cal.add(Calendar.MONTH, -1);
        calc = newProductStatsCalculator(cal.getTime(), list.returnCurrent(),
                Collections.<Item>emptyList());
        Assert.assertEquals(0.0, calc.averageAgeUsed());
    }
}
