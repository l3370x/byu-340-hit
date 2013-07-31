package gui.reports.notices;

import gui.common.UnitsConverter;
import gui.reports.AbstractReport;
import gui.reports.ReportRenderer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import core.model.Category;
import core.model.Item;
import core.model.Product;
import core.model.ProductContainer;
import core.model.Quantity.Units;
import core.model.exception.HITException;

public class NoticesReport extends AbstractReport {

    private class ProductItemWrapper {
        private Map<Product, List<Item>> itemsByProduct = 
                new LinkedHashMap<Product, List<Item>>();

        public ProductItemWrapper() {

        }

        public void addProduct(Item i) {
            if (!itemsByProduct.containsKey(i.getProduct())) {
                List<Item> items = new ArrayList<Item>();
                items.add(i);
                itemsByProduct.put(i.getProduct(), items);
            } else {
                itemsByProduct.get(i.getProduct()).add(i);
            }

        }

        public boolean isEmpty() {
            return itemsByProduct.isEmpty();
        }

    }

    private Map<Category, ProductItemWrapper> notices = 
            new LinkedHashMap<Category, ProductItemWrapper>();

    @Override
    public void render(ReportRenderer renderer) {
        try {
            renderer.beginDocument("NoticesReport", "Notices");

            if (notices.isEmpty()) {
                renderer.addText("There are no notices at this time.");
            } else {
                renderer.addHeading("3-Month Supply Notices");
                for (Category c : notices.keySet()) {
                    renderer.addText(String
                            .format("Product group %s::%s has a 3-month supply "
                                    + "of (%s) that is inconsistent with the "
                                    + "following products:",
                                    c.getContainer().getName(), c.getName(), c
                                            .get3MonthSupplyQuantity()
                                            .toString()));
                    for (List<Item> problemItems : notices.get(c).itemsByProduct
                            .values()) {
                        for (Item i : problemItems) {
                            renderer.addText(String.format("- %s::%s (%s)", i
                                    .getContainer().getName(), i.getProduct()
                                    .getDescription(), i.getProduct().getSize()
                                    .toString()));
                        }
                    }
                }
            }
            renderer.endDocument();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Boolean operate(ProductContainer container) {
        if ((container instanceof Category)) {
            Units goodUnit = ((Category) container).get3MonthSupplyQuantity()
                    .getUnits();
            if (goodUnit.equals(Units.COUNT)) {
                return true;
            }
            ProductItemWrapper toAdd = new ProductItemWrapper();
            for (Item i : ((Category) container).getItems()) {
                try {
                    if (!UnitsConverter.unitsToUnitType(
                            i.getProduct().getSize().getUnits()).equals(
                            goodUnit)) {
                        toAdd.addProduct(i);
                    }
                } catch (HITException e) {
                    e.printStackTrace();
                }
            }
            if (!toAdd.isEmpty())
                this.notices.put((Category) container, toAdd);

        }
        return true;
    }

}
