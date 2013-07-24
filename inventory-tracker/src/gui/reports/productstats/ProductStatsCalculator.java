package gui.reports.productstats;

import java.util.Date;

import core.model.Item;

public interface ProductStatsCalculator {

	void setValues(Date startDate, Iterable<Item> currentItems,
			Iterable<Item> removedItems);

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
		private static final ProductStatsCalculator INSTANCE = new ProductStatsCalculatorImpl();

		public ProductStatsCalculator getInstance() {
			return INSTANCE;
		}
	}

}
