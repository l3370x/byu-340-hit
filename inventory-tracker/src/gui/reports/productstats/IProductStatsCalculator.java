package gui.reports.productstats;

import core.model.Product;

public interface IProductStatsCalculator {
	
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
	
}
