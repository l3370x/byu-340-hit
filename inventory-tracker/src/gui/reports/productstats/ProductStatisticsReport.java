/**
 *
 */
package gui.reports.productstats;

import core.model.InventoryManager;
import core.model.Item;
import core.model.Product;
import core.model.ProductContainer;
import core.model.exception.ExceptionHandler;
import gui.reports.AbstractReport;
import gui.reports.ReportRenderer;
import gui.reports.ReportRenderer.ReportOrientation;

import java.io.IOException;
import java.util.*;

import static common.util.DateUtils.max;
import static core.model.InventoryManager.Factory.getInventoryManager;
import static gui.reports.productstats.ProductStatsCalculator.Factory.newProductStatsCalculator;

/**
 * @author aaron
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

    private Map<Product, List<Item>> itemsByProduct = new TreeMap<>(productComparator);
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

            for (List<Item> items : this.itemsByProduct.values()) {
                Item i = items.get(0);
                Product p = i.getProduct();

                ProductStatsCalculator calc = newProductStatsCalculator(
                        max(startingDate, p.getCreationDate()),
                        items,
                        getInventoryManager().getRemovedItems(p));

                renderer.addTableRow(
                        p.getDescription(),
                        p.getBarCode().getValue(),
                        p.getSize().toString(),
                        get3MonthSupplyString(p),
                        getCurrentSlashAverageSupplyString(calc),
                        getMinMaxSupplyString(calc),
                        getItemsUsedSlashAddedString(calc),
                        getShelfLifeString(p),
                        getAvgSlashMaxAgeUsedString(calc),
                        getAvgSlashMaxAgeCurrentString(calc));
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

    private static String getAvgSlashMaxAgeCurrentString(ProductStatsCalculator calc) {
        return String.format("%s days / %d days",
                String.valueOf(calc.averageAgedCurrent())
                        .replaceAll("\\.?0*$", ""), calc
                .maximumAgeCurrent());
    }

    private static String getAvgSlashMaxAgeUsedString(ProductStatsCalculator calc) {
        return String.format("%s days / %d days",
                String.valueOf(calc.averageAgeUsed())
                        .replaceAll("\\.?0*$", ""), calc
                .maximumAgeUsed());
    }

    private static String getItemsUsedSlashAddedString(ProductStatsCalculator calc) {
        return String.format("%d / %d", calc.itemsUsed(),
                calc.itemsAdded());
    }

    private static String getMinMaxSupplyString(ProductStatsCalculator calc) {
        return String.format("%d / %d", calc.minimumSupply(),
                calc.maximumSupply());
    }

    private static String getCurrentSlashAverageSupplyString(ProductStatsCalculator calc) {
        return String.format("%d / %s", calc.currentSupply(),
                String.valueOf(calc.averageSupply())
                        .replaceAll("\\.?0*$", ""));
    }

    private static String getShelfLifeString(Product p) {
        return p.getShelfLifeInMonths() == 0 ? "" : String
                .format("%d months", p.getShelfLifeInMonths());
    }

    private static String get3MonthSupplyString(Product p) {
        return p.get3MonthSupplyQuota() == 0 ? "" : String
                .valueOf(p.get3MonthSupplyQuota());
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
