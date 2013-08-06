package gui.reports.productstats;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import java.io.PrintStream;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA. User: kmcqueen Date: 8/6/13 Time: 3:10 PM To change this template use
 * File | Settings | File Templates.
 */
public class ProductStatsCalculatorTests {
    protected static PrintStream out;
    protected ProductStatsCalculator calc;
    protected ListOfItems list = new ListOfItems();
    protected Calendar cal = Calendar.getInstance();

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        //out = new PrintStream(new FileOutputStream("output.txt"));
        out = System.out;
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        list = new ListOfItems();
        cal.setTime(new Date());
    }

    @After
    public void tearDown() throws Exception {
    }
}
