/**
 * 
 */
package gui.reports.productstats;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import core.model.InventoryManager;
import core.model.Product;
import core.model.ProductContainer;
import gui.reports.AbstractReport;
import gui.reports.ReportRenderer;
import gui.reports.ReportRenderer.ReportOrientation;
import core.model.Item;
import core.model.exception.ExceptionHandler;

/**
 * @author aaron
 * 
 */
public class ProductStatisticsReport extends AbstractReport {

    
    private Comparator<Product> productComparator = new Comparator<Product>() {
        @Override
        public int compare(Product p1, Product p2) {
            String desc1 = p1.getDescription();
            String desc2 = p2.getDescription();

            int comparison = desc1.compareTo(desc2);
            if (comparison != 0) {
                return comparison;
            }

            return p1.getBarCode().getValue().compareTo(p2.getBarCode().getValue());
        }
    };
    
    private Map<Product, List<Item>> itemsByProduct = new TreeMap<Product,List<Item>>(productComparator);
    private int nMonths = 3;
    private Date startingDate = new Date();

    public ProductStatisticsReport(int months) {
        this.nMonths = months;

        Date startingDate = new Date();
        GregorianCalendar goBack = new GregorianCalendar();
        goBack.setTime(startingDate);
        goBack.add(Calendar.MONTH, -1 * Integer.valueOf(this.nMonths));
        startingDate = goBack.getTime();
        this.startingDate = startingDate;
    }

    @Override
    public void render(ReportRenderer renderer) {

        try {
            String title = String.format("Product Report (%d Months)", nMonths);
            String name = "ProductReport";

            renderer.beginDocument(name, title, ReportOrientation.LANDSCAPE);
            renderer.beginTable(
                    "Description",
                    "Barcode",
                    "Size",
                    "3-Month Supply",
                    "Supply: Cur/Avg",
                    "Supply: Min/Max",
                    "Supply: Used/Added",
                    "Shelf Life",
                    "Used Age: Avg/Max",
                    "Cur Age: Avg/Max");
            ProductStatsCalculator calc = ProductStatsCalculator.Factory
                    .newProductStatsCalculator();
            InventoryManager inventory = InventoryManager.Factory
                    .getInventoryManager();
            for (List<Item> items : this.itemsByProduct.values()) {
                Item i = items.get(0);
                Product p = i.getProduct();
                String threeMonth = p.get3MonthSupplyQuota() == 0 ? "" : String
                        .valueOf(p.get3MonthSupplyQuota());
                String shelfLife = p.getShelfLifeInMonths() == 0 ? "" : String
                        .format("%d months", p.getShelfLifeInMonths());
                calc.setValues(startingDate, items,
                        inventory.getRemovedItems(p), p);
                renderer.addTableRow(
                        p.getDescription(),
                        p.getBarCode().getValue(),
                        p.getSize().toString(),
                        threeMonth,
                        String.format("%d / %s", calc.calculateCurrentSupply(),
                                String.valueOf(calc.calculateAverageSupply())
                                        .replaceAll("\\.?0*$", "")),
                        String.format("%d / %d", calc.calculateMinimumSupply(),
                                calc.calculateMaximumSupply()),
                        String.format("%d / %d", calc.calculateItemsUsed(),
                        calc.calculateItemsAdded()),
                        shelfLife,
                        String.format("%s days / %d days",
                        String.valueOf(calc.calculateAverageAgeUsed())
                                .replaceAll("\\.?0*$", ""), calc
                                .calculateMaximumAgeUsed()),
                        String.format("%s days / %d days",
                        String.valueOf(calc.calculateAverageAgedCurrent())
                                .replaceAll("\\.?0*$", ""), calc
                                .calculateMaximumAgeCurrent()));
            }
            renderer.endTable();

            renderer.endDocument();

        } catch (IOException e) {
            ExceptionHandler.TO_USER.reportException(e,
                    "Couldn't make product statistics report");
            ExceptionHandler.TO_LOG.reportException(e,
                    "Couldn't make product statistics report");
        }
    }

    @Override
    public Boolean operate(ProductContainer container) {

        if (container instanceof InventoryManager) {
            return true;
        }

        // for each item in the container
        for (Item item : (Iterable<Item>) container.getItems()) {
            Product p = item.getProduct();
            if (!itemsByProduct.containsKey(p)) {
                List<Item> itemsForProduct = new ArrayList<Item>();
                itemsForProduct.add(item);
                itemsByProduct.put(p, itemsForProduct);
            } else {
                itemsByProduct.get(p).add(item);
            }
        }

        return true;
    }

}
