package gui.reports.productstats;

import org.junit.Test;

import java.util.Calendar;

import static gui.reports.productstats.ProductStatsCalculator.Factory.newProductStatsCalculator;
import static org.junit.Assert.*;

/**
 * @author Cameron Jones (originator)
 * @author Keith McQueen (refactoring)
 */
public class ProductStatsAverageSupplyTests extends ProductStatsCalculatorTests {
    @Test
    public void testCalculateAverageSupply1Month() {
        double result = this.testCalculateAverageSupply(1);
        assertTrue(4.226 == result);
    }

    @Test
    public void testCalculateAverageSupply3Month() {
    	double result = this.testCalculateAverageSupply(3);
        assertTrue(2.761 == result);
    }

    @Test
    public void testCalculateAverageSupply6Month() {
    	double result = this.testCalculateAverageSupply(6);
        assertTrue(2.873 == result);
    }

    @Test
    public void testCalculateAverageSupply9Month() {
    	double result = this.testCalculateAverageSupply(9);
        assertTrue(2.579 == result);
    }

    @Test
    public void testCalculateAverageSupply12Month() {
    	double result = this.testCalculateAverageSupply(12);
        assertTrue(2.427 == result);
    }

    @Test
    public void testCalculateAverageSupply24Month() {
    	double result = this.testCalculateAverageSupply(24);
        assertTrue(1.212 == result);
    }

    private double testCalculateAverageSupply(int months) {
        cal.add(Calendar.MONTH, -months);
        calc = newProductStatsCalculator(cal.getTime(), list.returnCurrent(),
                list.returnRemoved());
        return calc.averageSupply();
    }
}
