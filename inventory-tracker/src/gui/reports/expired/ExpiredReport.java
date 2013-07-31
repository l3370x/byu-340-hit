package gui.reports.expired;


import core.model.InventoryManager;
import core.model.Item;
import core.model.ProductContainer;
import core.model.exception.ExceptionHandler;
import gui.item.ItemData;
import gui.reports.AbstractReport;
import gui.reports.ReportRenderer;

import java.io.IOException;
import java.util.*;

public class ExpiredReport extends AbstractReport {
    private Map<ProductContainer, List<Item>> expiredItemsByContainer = new LinkedHashMap<>();

    @Override
    public void render(ReportRenderer renderer) {
        try {
            renderer.beginDocument("Expired Items",
                    "Expired Items (as of " + renderer.formatDateAndTime(new Date()) + ")");
            renderer.beginTable(
                    "Description",
                    "Storage Unit",
                    "Product Group",
                    "Entry Date",
                    "Expire Date",
                    "Item Barcode");

            Comparator<Item> itemComparator = new Comparator<Item>() {
                @Override
                public int compare(Item item1, Item item2) {
                    String desc1 = item1.getProduct().getDescription();
                    String desc2 = item2.getProduct().getDescription();

                    int comparison = desc1.compareTo(desc2);
                    if (comparison != 0) {
                        return comparison;
                    }

                    return item1.getEntryDate().compareTo(item2.getEntryDate());
                }
            };

            for (List<Item> items : this.expiredItemsByContainer.values()) {
                Collections.sort(items, itemComparator);

                for (Item i : items) {

                    ItemData item = new ItemData(i);

                    Date entryDate = item.getEntryDate();
                    Date expirationDate = item.getExpirationDate();
                    String pGroup = item.getProductGroup();

                    renderer.addTableRow(
                            i.getProduct().getDescription(),
                            item.getStorageUnit(),
                            pGroup != null ? pGroup : "",
                            renderer.formatDate(entryDate),
                            renderer.formatDate(expirationDate),
                            item.getBarcode());
                }

            }

            renderer.endTable();
            renderer.endDocument();
        } catch (IOException e) {
            ExceptionHandler.TO_USER.reportException(e, "Unable To Prepare Expired Items Report");
            ExceptionHandler.TO_LOG.reportException(e, "Unable To Prepare Expired Items Report");
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Boolean operate(ProductContainer container) {
        // skip the InventoryManager to avoid duplicate entries
        if (container instanceof InventoryManager) {
            return true;
        }

        List<Item> expiredItems = new ArrayList<>();

        // get the current date/time
        Date now = new Date();

        // for each item in the container
        for (Item item : (Iterable<Item>) container.getItems()) {
            // get the item's expiration date
            Date expirationDate = item.getExpirationDate();

            // if the item doesn't have an expiration date, then skip it
            if (null == expirationDate) {
                continue;
            }

            // if the expiration date is before now, then it has expired
            if (expirationDate.before(now) || expirationDate.equals(now)) {
                expiredItems.add(item);
            }
        }

        if (false == expiredItems.isEmpty()) {
            this.expiredItemsByContainer.put(container, expiredItems);
        }

        return true;
    }


}
