package gui.reports.productstats;

import org.junit.Test;

import java.util.Calendar;

import static gui.reports.productstats.ProductStatsCalculator.Factory.newProductStatsCalculator;

/**
 * @author Cameron Jones (originator)
 * @author Keith McQueen (refactoring)
 */
public class ProductStatsMaximumSupplyTests extends ProductStatsCalculatorTests {
    @Test
    public void testCalculateMaximumSupply1Month() {
        this.testCalculateMaxSupply(1);
    }

    @Test
    public void testCalculateMaximumSupply3Month() {
        this.testCalculateMaxSupply(3);
    }

    @Test
    public void testCalculateMaximumSupply6Month() {
        this.testCalculateMaxSupply(6);
    }

    @Test
    public void testCalculateMaximumSupply9Month() {
        this.testCalculateMaxSupply(9);
    }

    @Test
    public void testCalculateMaximumSupply12Month() {
        this.testCalculateMaxSupply(12);
    }

    @Test
    public void testCalculateMaximumSupply24Month() {
        this.testCalculateMaxSupply(24);
    }

    private void testCalculateMaxSupply(int months) {
        cal.add(Calendar.MONTH, -months);
        calc = newProductStatsCalculator(cal.getTime(), list.returnCurrent(),
                list.returnRemoved());
        out.println("Maximum Supply: " + months + " month = "
                + calc.maximumSupply());
    }
}
