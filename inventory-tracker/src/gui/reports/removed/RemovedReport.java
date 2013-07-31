package gui.reports.removed;

import core.model.InventoryManager;
import core.model.Item;
import core.model.Product;
import core.model.ProductContainer;
import gui.reports.AbstractReport;
import gui.reports.ReportRenderer;

import java.io.IOException;
import java.util.*;

import static core.model.InventoryManager.Factory.getInventoryManager;

public class RemovedReport extends AbstractReport {

    Date date;

    private Map<Product, int[]> itemsRemoved = new HashMap<Product, int[]>();

    public RemovedReport(Date date) {
        this.date = date;
    }

    @Override
    public void render(ReportRenderer renderer) {
        try {

            renderer.beginDocument("Removed Items", "Removed Items");
            renderer.beginTable("Description", "Size",
                    "Product Barcode", "Removed", "Current Supply");

            for (Product p : itemsRemoved.keySet()) {
                renderer.addTableRow(p.getDescription(), p.getSize().toString(), p.getBarCode().toString(), Integer.toString(itemsRemoved.get(p)[0]),
                        Integer.toString(getInventoryManager().getItemCount(p)));
            }
            renderer.endTable();
            renderer.endDocument();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Boolean operate(ProductContainer obj) {

        if (false == obj instanceof InventoryManager) {
            return true;
        }

        InventoryManager mgr = (InventoryManager) obj;


        Iterator<Item> removedList = mgr.getRemovedItems().iterator();

        List<Item> allItemsList = new ArrayList<Item>();
        while (removedList.hasNext())
            allItemsList.add(removedList.next());

        ListIterator<Item> allItems = allItemsList.listIterator(allItemsList.size());

        while (allItems.hasPrevious()) {
            Item item = allItems.previous();

            if (item.getExitDate().after(date) ||
                    item.getExitDate().equals(date)) {

                if (itemsRemoved.containsKey(item.getProduct())) {
                    int x[] = itemsRemoved.get(item.getProduct());
                    x[0] = x[0] + 1;

                    itemsRemoved.put(item.getProduct(), x);
                } else {
                    int x[] = new int[1];
                    x[0] = 1;
                    itemsRemoved.put(item.getProduct(), x);
                }
            }
        }
        return false;
    }

    public void setDate(Date newdate) {
        this.date = newdate;
    }


}
