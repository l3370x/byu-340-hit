package gui.reports.productstats;

import java.util.Date;

import core.model.Item;
import core.model.Product;

public interface ProductStatsCalculator {

	void setValues(Date startDate, Iterable<Item> currentItems,
			Iterable<Item> removedItems, Product product);

	int calculateCurrentSupply();

	double calculateAverageSupply();

	int calculateMinimumSupply();

	int calculateMaximumSupply();

	int calculateItemsUsed();

	int calculateItemsAdded();

	double calculateAverageAgeUsed();

	int calculateMaximumAgeUsed();

	double calculateAverageAgedCurrent();

	int calculateMaximumAgeCurrent();

	public static class Factory {
		public static ProductStatsCalculator newProductStatsCalculator() {
			return new ProductStatsCalculatorImpl();
		}
	}

}
