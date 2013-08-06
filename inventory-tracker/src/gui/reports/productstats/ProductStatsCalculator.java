package gui.reports.productstats;

import core.model.Item;

import java.util.Date;

public interface ProductStatsCalculator {

    int currentSupply();

    double averageSupply();

    int minimumSupply();

    int maximumSupply();

    int itemsUsed();

    int itemsAdded();

    double averageAgeUsed();

    int maximumAgeUsed();

    double averageAgedCurrent();

    int maximumAgeCurrent();

    public static class Factory {
        public static ProductStatsCalculator newProductStatsCalculator(
                Date startDate, Iterable<Item> currentItems, Iterable<Item> removedItems) {
            return new ProductStatsCalculatorImpl(startDate, currentItems, removedItems);
        }
    }

}
