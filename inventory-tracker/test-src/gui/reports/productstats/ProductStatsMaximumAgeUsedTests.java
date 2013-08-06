package gui.reports.productstats;

import org.junit.Test;

import java.util.Calendar;

import static gui.reports.productstats.ProductStatsCalculator.Factory.newProductStatsCalculator;

/**
 * @author Cameron Jones
 * @author Keith McQueen
 */
public class ProductStatsMaximumAgeUsedTests extends ProductStatsCalculatorTests {
    @Test
    public void testCalculateMaximumAgeUsed1Month() {
        this.testCalculateMaxAgeUsed(1);
    }

    @Test
    public void testCalculateMaximumAgeUsed3Month() {
        this.testCalculateMaxAgeUsed(3);
    }

    @Test
    public void testCalculateMaximumAgeUsed6Month() {
        this.testCalculateMaxAgeUsed(6);
    }

    @Test
    public void testCalculateMaximumAgeUsed9Month() {
        this.testCalculateMaxAgeUsed(9);
    }

    @Test
    public void testCalculateMaximumAgeUsed12Month() {
        this.testCalculateMaxAgeUsed(12);
    }

    @Test
    public void testCalculateMaximumAgeUsed24Month() {
        this.testCalculateMaxAgeUsed(24);
    }

    private void testCalculateMaxAgeUsed(int months) {
        cal.add(Calendar.MONTH, -months);
        calc = newProductStatsCalculator(cal.getTime(), list.returnCurrent(),
                list.returnRemoved());
        out.println("Maximum Age Used: " + months + " month = "
                + calc.maximumAgeUsed());
    }
}
