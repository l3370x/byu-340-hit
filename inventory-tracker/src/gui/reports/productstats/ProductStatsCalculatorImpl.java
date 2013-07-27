package gui.reports.productstats;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import common.util.DateUtils;
import core.model.Item;
import core.model.Product;

public class ProductStatsCalculatorImpl implements ProductStatsCalculator {

	Date startDate = new Date();
	int numberOfDays = calculateDayDifference(startDate,
			DateUtils.currentDate());
	Product product;
	Iterable<Item> currentItems;
	Iterable<Item> removedItems;

	public ProductStatsCalculatorImpl() {
	}

	public void setValues(Date startDate, Iterable<Item> currentItems,
			Iterable<Item> removedItems, Product product) {
		this.product = product;
		this.startDate = startDate;
		this.currentItems = currentItems;
		this.removedItems = removedItems;
		numberOfDays = calculateDayDifference(startDate,
				DateUtils.currentDate());
	}

	@Override
	public int calculateCurrentSupply() {
		int currentSupply = 0;
		Iterator<Item> iter = currentItems.iterator();
		while (iter.hasNext()) {
			iter.next();
			currentSupply++;
		}
		return currentSupply;
	}

	@Override
	public double calculateAverageSupply() {
		double averageSupply = 0;
		int netDaysItemsStored = 0;
		Item item;
		Iterator<Item> iterCur = currentItems.iterator();
		Iterator<Item> iterRem = removedItems.iterator();
		while (iterCur.hasNext()) {
			item = iterCur.next();
			netDaysItemsStored += calculateDayDifference(
					getLatestDate(item.getEntryDate(), startDate),
					DateUtils.currentDate());
		}
		while (iterRem.hasNext()) {
			item = iterRem.next();
			if (!item.getExitDate().before(startDate)) {
				netDaysItemsStored += calculateDayDifference(
						getLatestDate(item.getEntryDate(), startDate),
						item.getExitDate());
			}
		}
		averageSupply = netDaysItemsStored / numberOfDays;
		return averageSupply;
	}

	@Override
	public int calculateMinimumSupply() {
		int minimumSupply = (int) calculateAverageSupply() + 1;
		Calendar cal = Calendar.getInstance();
		cal.setTime(getLatestDate(startDate, product.getCreationDate()));
		while (!cal.getTime().after(DateUtils.currentDate())) {
			int itemsStoredinDate = calculateItemsStoredinDate(cal);
			if (itemsStoredinDate < minimumSupply) {
				minimumSupply = itemsStoredinDate;
			}
			cal.add(Calendar.DATE, 1);
		}
		return minimumSupply;
	}

	private int calculateItemsStoredinDate(Calendar cal) {
		Item item;
		Iterator<Item> iterCur = currentItems.iterator();
		Iterator<Item> iterRem = removedItems.iterator();
		int itemsStoredinDate = 0;
		// iterate through current items
		while (iterCur.hasNext()) {
			item = iterCur.next();
			if (item.getEntryDate().before(cal.getTime())) {
				itemsStoredinDate++;
			}
		}
		while (iterRem.hasNext()) {
			item = iterRem.next();
			if (!(item.getEntryDate().after(cal.getTime()) || item
					.getExitDate().before(cal.getTime()))) {
				itemsStoredinDate++;
			}
		}
		return itemsStoredinDate;
	}

	@Override
	public int calculateMaximumSupply() {
		int maximumSupply = (int) calculateAverageSupply();
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		while (!cal.getTime().after(DateUtils.currentDate())) {
			int itemsStoredinDate = calculateItemsStoredinDate(cal);
			if (itemsStoredinDate > maximumSupply) {
				maximumSupply = itemsStoredinDate;
			}
			cal.add(Calendar.DATE, 1);
		}
		return maximumSupply;
	}

	@Override
	public int calculateItemsUsed() {
		int itemsUsed = 0;
		Iterator<Item> iterRem = removedItems.iterator();
		while (iterRem.hasNext()) {
			Item item = iterRem.next();
			if (item.getExitDate().after(startDate)) {
				itemsUsed++;
			}
		}
		return itemsUsed;
	}

	@Override
	public int calculateItemsAdded() {
		int itemsAdded = 0;
		Iterator<Item> iterCur = currentItems.iterator();
		Iterator<Item> iterRem = removedItems.iterator();
		Item item;
		while (iterCur.hasNext()) {
			item = iterCur.next();
			if (item.getEntryDate().after(startDate)) {
				itemsAdded++;
			}
		}
		while (iterRem.hasNext()) {
			item = iterRem.next();
			if (item.getEntryDate().after(startDate)) {
				itemsAdded++;
			}
		}
		return itemsAdded;
	}

	@Override
	public double calculateAverageAgeUsed() {
		double averageAgeUsed = 0;
		int netAgeUsed = 0;
		int totalUsedItems = 0;
		Item item;
		Iterator<Item> iter = removedItems.iterator();
		while (iter.hasNext()) {
			item = iter.next();
			netAgeUsed += calculateDayDifference(item.getEntryDate(),
					item.getExitDate());
			totalUsedItems++;
		}
		if(totalUsedItems == 0)
			return 0;
		averageAgeUsed = (double) (netAgeUsed / totalUsedItems);
		return averageAgeUsed;
	}

	@Override
	public int calculateMaximumAgeUsed() {
		int maximumAgeUsed = 0;
		Item item;
		Iterator<Item> iter = removedItems.iterator();
		while (iter.hasNext()) {
			item = iter.next();
			int age = calculateDayDifference(item.getEntryDate(),
					item.getExitDate());
			if (age > maximumAgeUsed) {
				maximumAgeUsed = age;
			}
		}
		return maximumAgeUsed;
	}

	@Override
	public double calculateAverageAgedCurrent() {
		double averageAgeCurrent = 0;
		int netAgeCurrent = 0;
		int totalCurrentItems = 0;
		Item item;
		Iterator<Item> iter = currentItems.iterator();
		while (iter.hasNext()) {
			item = iter.next();
			netAgeCurrent += calculateDayDifference(item.getEntryDate(),
					DateUtils.currentDate());
			totalCurrentItems++;
		}
		averageAgeCurrent = (double) (netAgeCurrent / totalCurrentItems);
		return averageAgeCurrent;
	}

	@Override
	public int calculateMaximumAgeCurrent() {
		int maximumAgeCurrent = 0;
		Item item;
		Iterator<Item> iter = currentItems.iterator();
		while (iter.hasNext()) {
			item = iter.next();
			int age = calculateDayDifference(item.getEntryDate(),
					DateUtils.currentDate());
			if (age > maximumAgeCurrent) {
				maximumAgeCurrent = age;
			}
		}
		return maximumAgeCurrent;
	}

	public int calculateDayDifference(Date begin, Date end) {
		return (int) ((end.getTime() - begin.getTime()) / (1000 * 60 * 60 * 24))
				+ 1;
	}

	public Date getEarliestDate(Date date1, Date date2) {
		if (date1.before(date2)) {
			return date1;
		}
		return date2;
	}

	public Date getLatestDate(Date date1, Date date2) {
		if (date1.after(date2)) {
			return date1;
		}
		return date2;
	}
}
