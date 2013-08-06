package gui.reports.productstats;

import org.junit.Test;

import java.util.Calendar;

import static gui.reports.productstats.ProductStatsCalculator.Factory.newProductStatsCalculator;

/**
 * @author Cameron Jones (originator)
 * @author Keith McQueen (refactoring)
 */
public class ProductStatsAverageSupplyTests extends ProductStatsCalculatorTests {
    @Test
    public void testCalculateAverageSupply1Month() {
        this.testCalculateAverageSupply(1);
    }

    @Test
    public void testCalculateAverageSupply3Month() {
        this.testCalculateAverageSupply(3);
    }

    @Test
    public void testCalculateAverageSupply6Month() {
        this.testCalculateAverageSupply(6);
    }

    @Test
    public void testCalculateAverageSupply9Month() {
        this.testCalculateAverageSupply(9);
    }

    @Test
    public void testCalculateAverageSupply12Month() {
        this.testCalculateAverageSupply(12);
    }

    @Test
    public void testCalculateAverageSupply24Month() {
        this.testCalculateAverageSupply(24);
    }

    private void testCalculateAverageSupply(int months) {
        cal.add(Calendar.MONTH, -months);
        calc = newProductStatsCalculator(cal.getTime(), list.returnCurrent(),
                list.returnRemoved());
        out.println("Average Supply: " + months + " month = "
                + calc.averageSupply());
    }
}
