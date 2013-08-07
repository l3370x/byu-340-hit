package gui.reports.productstats;

import org.junit.*;

import java.util.Calendar;

import static gui.reports.productstats.ProductStatsCalculator.Factory.newProductStatsCalculator;
import static org.junit.Assert.*;

/**
 * @author Cameron Jones (originator)
 * @author Keith McQueen (refactoring)
 */
public class ProductStatsCurrentSupplyTests extends ProductStatsCalculatorTests {
    @Test
    public void testCalculateCurrentSupply1Month() {
        int result = this.testCalculateCurrentSupply(1);
        assertEquals(6, result);
    }

    @Test
    public void testCalculateCurrentSupply3Month() {
    	int result = this.testCalculateCurrentSupply(3);
    	assertEquals(6, result);
    }

    @Test
    public void testCalculateCurrentSupply6Month() {
    	int result = this.testCalculateCurrentSupply(6);
    	assertEquals(6, result);
    }

    @Test
    public void testCalculateCurrentSupply9Month() {
    	int result = this.testCalculateCurrentSupply(9);
    	assertEquals(6, result);
    }

    @Test
    public void testCalculateCurrentSupply12Month() {
    	int result = this.testCalculateCurrentSupply(12);
    	assertEquals(6, result);
    }

    @Test
    public void testCalculateCurrentSupply24Month() {
    	int result = this.testCalculateCurrentSupply(24);
    	assertEquals(6, result);
    }

    private int testCalculateCurrentSupply(int months) {
        cal.add(Calendar.MONTH, -months);
        calc = newProductStatsCalculator(cal.getTime(), list.returnCurrent(),
                list.returnRemoved());
        return calc.currentSupply();
    }
}
