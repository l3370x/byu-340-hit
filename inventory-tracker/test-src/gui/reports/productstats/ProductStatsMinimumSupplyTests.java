package gui.reports.productstats;

import org.junit.Test;

import core.model.ItemCollection;

import java.util.Calendar;

import static gui.reports.productstats.ProductStatsCalculator.Factory.newProductStatsCalculator;
import static org.junit.Assert.assertEquals;

/**
 * @author Cameron Jones (originator)
 * @author Keith McQueen (refactoring)
 * @author Rohit
 */
public class ProductStatsMinimumSupplyTests extends ProductStatsCalculatorTests {
    @Test
    public void testCalculateMinimumSupply1Month() {
        this.testCalculateMinimumSupply(1, 3);
    }

    @Test
    public void testCalculateMinimumSupply3Month() {
        this.testCalculateMinimumSupply(3, 2);
    }

    @Test
    public void testCalculateMinimumSupply6Month() {
        this.testCalculateMinimumSupply(6, 2);
    }

    @Test
    public void testCalculateMinimumSupply9Month() {
        this.testCalculateMinimumSupply(9, 2);
    }

    @Test
    public void testCalculateMinimumSupply12Month() {
        this.testCalculateMinimumSupply(12, 2);
    }

    @Test
    public void testCalculateMinimumSupply24Month() {
        this.testCalculateMinimumSupply(24, 0);
    }
    
    @Test
    public void testCalculateMinimumSupply3MonthWithZeroItems() {
    	ItemCollection currentItems = new ItemCollection(null);
    	
    	cal.add(Calendar.MONTH, -3);
        calc = newProductStatsCalculator(cal.getTime(), currentItems.getContents(),
                list.returnRemoved());
        
        assertEquals(calc.minimumSupply(),0);
    	
    }
    

    private void testCalculateMinimumSupply(int months, int expected) {
        cal.add(Calendar.MONTH, -months);
        calc = newProductStatsCalculator(cal.getTime(), list.returnCurrent(),
                list.returnRemoved());
        int x = calc.minimumSupply();
        assertEquals(x,expected);
    	
       // out.println("Minimum Supply: " + months + " month = "
              //  + calc.minimumSupply());
    }
}
