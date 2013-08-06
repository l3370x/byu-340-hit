package gui.reports.productstats;

import org.junit.Test;

import java.util.Calendar;

import static gui.reports.productstats.ProductStatsCalculator.Factory.newProductStatsCalculator;

/**
 * @author Cameron Jones (originator)
 * @author Keith McQueen (refactoring)
 */
public class ProductStatsMinimumSupplyTests extends ProductStatsCalculatorTests {
    @Test
    public void testCalculateMinimumSupply1Month() {
        this.testCalculateMinimumSupply(1);
    }

    @Test
    public void testCalculateMinimumSupply3Month() {
        this.testCalculateMinimumSupply(3);
    }

    @Test
    public void testCalculateMinimumSupply6Month() {
        this.testCalculateMinimumSupply(6);
    }

    @Test
    public void testCalculateMinimumSupply9Month() {
        this.testCalculateMinimumSupply(9);
    }

    @Test
    public void testCalculateMinimumSupply12Month() {
        this.testCalculateMinimumSupply(12);
    }

    @Test
    public void testCalculateMinimumSupply24Month() {
        this.testCalculateMinimumSupply(24);
    }

    private void testCalculateMinimumSupply(int months) {
        cal.add(Calendar.MONTH, -months);
        calc = newProductStatsCalculator(cal.getTime(), list.returnCurrent(),
                list.returnRemoved());
        out.println("Minimum Supply: " + months + " month = "
                + calc.minimumSupply());
    }
}
