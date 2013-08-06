package gui.reports.productstats;

import org.junit.Test;

import java.util.Calendar;

import static gui.reports.productstats.ProductStatsCalculator.Factory.newProductStatsCalculator;

/**
 * @author Cameron Jones (originator)
 * @author Keith McQueen (refactoring)
 */
public class ProductStatsMaximumCurrentAgeTests extends ProductStatsCalculatorTests {

    @Test
    public void testCalculateMaximumAgeCurrent1Month() {
        this.testCalculateMaxAgeCurrent(1);
    }

    @Test
    public void testCalculateMaximumAgeCurrent3Month() {
        this.testCalculateMaxAgeCurrent(3);
    }

    @Test
    public void testCalculateMaximumAgeCurrent6Month() {
        this.testCalculateMaxAgeCurrent(6);
    }

    @Test
    public void testCalculateMaximumAgeCurrent9Month() {
        this.testCalculateMaxAgeCurrent(9);
    }

    @Test
    public void testCalculateMaximumAgeCurrent12Month() {
        this.testCalculateMaxAgeCurrent(12);
    }

    @Test
    public void testCalculateMaximumAgeCurrent24Month() {
        this.testCalculateMaxAgeCurrent(24);
    }

    private void testCalculateMaxAgeCurrent(int months) {
        cal.add(Calendar.MONTH, -months);
        calc = newProductStatsCalculator(cal.getTime(), list.returnCurrent(),
                list.returnRemoved());
        out.println("Maximum Age Current: " + months + " month = "
                + calc.maximumAgeCurrent());
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
