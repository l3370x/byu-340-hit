package gui.reports.productstats;

import org.junit.Test;

import java.util.Calendar;

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
                + calc.averageAgedCurrent());
    }
}
