package gui.reports.productstats;

import org.junit.Test;

import java.util.Calendar;

import static gui.reports.productstats.ProductStatsCalculator.Factory.newProductStatsCalculator;

/**
 * @author Cameron Jones (originator)
 * @author Keith McQueen (refactoring)
 */
public class ProductStatsCurrentSupplyTests extends ProductStatsCalculatorTests {
    @Test
    public void testCalculateCurrentSupply1Month() {
        this.testCalculateCurrentSupply(1);
    }

    @Test
    public void testCalculateCurrentSupply3Month() {
        this.testCalculateCurrentSupply(3);
    }

    @Test
    public void testCalculateCurrentSupply6Month() {
        this.testCalculateCurrentSupply(6);
    }

    @Test
    public void testCalculateCurrentSupply9Month() {
        this.testCalculateCurrentSupply(9);
    }

    @Test
    public void testCalculateCurrentSupply12Month() {
        this.testCalculateCurrentSupply(12);
    }

    @Test
    public void testCalculateCurrentSupply24Month() {
        this.testCalculateCurrentSupply(24);
    }

    private void testCalculateCurrentSupply(int months) {
        cal.add(Calendar.MONTH, -months);
        calc = newProductStatsCalculator(cal.getTime(), list.returnCurrent(),
                list.returnRemoved());
        out.println("Current Supply: " + months + " month = "
                + calc.currentSupply());
    }
}
