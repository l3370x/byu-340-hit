package gui.reports.productstats;

import org.junit.Test;

import java.util.Calendar;

import static gui.reports.productstats.ProductStatsCalculator.Factory.newProductStatsCalculator;

/**
 * @author Cameron Jones (originator)
 * @author Keith McQueen (refactoring)
 */
public class ProductStatsCurrentAverageAgeTests extends ProductStatsCalculatorTests {
    @Test
    public void testCalculateAverageAgedCurrent1Month() {
        this.testCalculateAverageAge(1);
    }

    @Test
    public void testCalculateAverageAgedCurrent3Month() {
        this.testCalculateAverageAge(3);
    }

    @Test
    public void testCalculateAverageAgedCurrent6Month() {
        this.testCalculateAverageAge(6);
    }

    @Test
    public void testCalculateAverageAgedCurrent9Month() {
        this.testCalculateAverageAge(9);
    }

    @Test
    public void testCalculateAverageAgedCurrent12Month() {
        this.testCalculateAverageAge(12);
    }

    @Test
    public void testCalculateAverageAgedCurrent24Month() {
        this.testCalculateAverageAge(24);
    }

    private void testCalculateAverageAge(int months) {
        cal.add(Calendar.MONTH, -months);
        calc = newProductStatsCalculator(cal.getTime(), list.returnCurrent(),
                list.returnRemoved());
        out.println("Average Age Current: " + months + " month = "
                + calc.averageAgedCurrent());
    }
}
