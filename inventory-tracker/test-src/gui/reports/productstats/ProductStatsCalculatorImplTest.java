package gui.reports.productstats;

import org.junit.*;

import java.io.PrintStream;
import java.util.Calendar;
import java.util.Date;

import static gui.reports.productstats.ProductStatsCalculator.Factory.newProductStatsCalculator;

public class ProductStatsCalculatorImplTest {

    ProductStatsCalculator calc;
    ListOfItems list = new ListOfItems();
    Calendar cal = Calendar.getInstance();

    static PrintStream out;

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

    @Test
    public void testCalculateItemsUsed1Month() {
        this.testCalculateItemsUsed(1);
    }

    @Test
    public void testCalculateItemsUsed3Month() {
        this.testCalculateItemsUsed(3);
    }

    @Test
    public void testCalculateItemsUsed6Month() {
        this.testCalculateItemsUsed(6);
    }

    @Test
    public void testCalculateItemsUsed9Month() {
        this.testCalculateItemsUsed(9);
    }

    @Test
    public void testCalculateItemsUsed12Month() {
        this.testCalculateItemsUsed(12);
    }

    @Test
    public void testCalculateItemsUsed24Month() {
        this.testCalculateItemsUsed(24);
    }

    private void testCalculateItemsUsed(int m) {
        cal.add(Calendar.MONTH, -m);
        calc = newProductStatsCalculator(cal.getTime(), list.returnCurrent(),
                list.returnRemoved());
        out.println("Items used: " + m + " month = " + calc.itemsUsed());
    }

    @Test
    public void testCalculateItemsAdded1Month() {
        this.testCalculateItemsAdded(1);
    }

    @Test
    public void testCalculateItemsAdded3Month() {
        this.testCalculateItemsAdded(3);
    }

    @Test
    public void testCalculateItemsAdded6Month() {
        this.testCalculateItemsAdded(6);
    }

    @Test
    public void testCalculateItemsAdded9Month() {
        this.testCalculateItemsAdded(9);
    }

    @Test
    public void testCalculateItemsAdded12Month() {
        this.testCalculateItemsAdded(12);
    }

    @Test
    public void testCalculateItemsAdded24Month() {
        this.testCalculateItemsAdded(24);
    }

    private void testCalculateItemsAdded(int m) {
        cal.add(Calendar.MONTH, -m);
        calc = newProductStatsCalculator(cal.getTime(), list.returnCurrent(),
                list.returnRemoved());
        out.println("Items added: " + m + " month = " + calc.itemsAdded());
    }

    @Test
    public void testCalculateAverageAgeUsed1Month() {
        this.testCalculateAverageAgeUsed(1);
    }

    @Test
    public void testCalculateAverageAgeUsed3Month() {
        this.testCalculateAverageAgeUsed(3);
    }

    @Test
    public void testCalculateAverageAgeUsed6Month() {
        this.testCalculateAverageAgeUsed(6);
    }

    @Test
    public void testCalculateAverageAgeUsed9Month() {
        this.testCalculateAverageAgeUsed(9);
    }

    @Test
    public void testCalculateAverageAgeUsed12Month() {
        this.testCalculateAverageAgeUsed(12);
    }

    @Test
    public void testCalculateAverageAgeUsed24Month() {
        this.testCalculateAverageAgeUsed(24);
    }

    private void testCalculateAverageAgeUsed(int months) {
        cal.add(Calendar.MONTH, -months);
        calc = newProductStatsCalculator(cal.getTime(), list.returnCurrent(),
                list.returnRemoved());
        out.println("Average Age Used: " + months + " month = "
                + calc.averageAgedCurrent());
    }

    @Test
    public void testCalculateMaximumAgeUsed1Month() {
        this.testCalculateMaxAgeUsed(1);
    }

    @Test
    public void testCalculateMaximumAgeUsed3Month() {
        this.testCalculateMaxAgeUsed(3);
    }

    @Test
    public void testCalculateMaximumAgeUsed6Month() {
        this.testCalculateMaxAgeUsed(6);
    }

    @Test
    public void testCalculateMaximumAgeUsed9Month() {
        this.testCalculateMaxAgeUsed(9);
    }

    @Test
    public void testCalculateMaximumAgeUsed12Month() {
        this.testCalculateMaxAgeUsed(12);
    }

    @Test
    public void testCalculateMaximumAgeUsed24Month() {
        this.testCalculateMaxAgeUsed(24);
    }

    private void testCalculateMaxAgeUsed(int months) {
        cal.add(Calendar.MONTH, -months);
        calc = newProductStatsCalculator(cal.getTime(), list.returnCurrent(),
                list.returnRemoved());
        out.println("Maximum Age Used: " + months + " month = "
                + calc.maximumAgeUsed());
    }

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
