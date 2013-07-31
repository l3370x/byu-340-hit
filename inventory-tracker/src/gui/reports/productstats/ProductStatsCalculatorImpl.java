package gui.reports.productstats;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import static common.util.DateUtils.*;
import core.model.Item;
import core.model.Product;

public class ProductStatsCalculatorImpl implements ProductStatsCalculator {

	Date startDate;
	Date endDate;
	int numberOfDays;
	Product product;
	Iterable<Item> currentItems;
	Iterable<Item> removedItems;

	public void setValues(Date startDate, Iterable<Item> currentItems,
			Iterable<Item> removedItems, Product product) {
		this.product = product;
<<<<<<< HEAD
		this.startDate = getLatestDate(DateUtils.removeTimeFromDate(startDate),
				DateUtils.removeTimeFromDate(product.getCreationDate()));
		this.endDate = DateUtils.removeTimeFromDate(new Date());
=======
		this.startDate = removeTimeFromDate(startDate);
		this.endDate = removeTimeFromDate(new Date());
>>>>>>> 364eb0e33e8eb9b765444fa4f20cbc45e9260c0c
		this.currentItems = currentItems;
		this.removedItems = removedItems;
		numberOfDays = calculateDayDifference(this.startDate, endDate);
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
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		int netDaysItemsStored = 0;
		while (!cal.getTime().after(endDate)) {
			netDaysItemsStored += calculateItemsStoredinDate(cal);
			cal.add(Calendar.DAY_OF_YEAR, 1);
		}
		if (numberOfDays == 0)
			return netDaysItemsStored;
		averageSupply = (double) netDaysItemsStored / (double) numberOfDays;
		averageSupply = roundOnePlace(averageSupply);
		return averageSupply;
	}

	private double roundOnePlace(double in) {
		return (double) Math.round(in * 1000) / 1000;
	}

	private int calculateItemsStoredinDate(Calendar cal) {
		Item item;
		Iterator<Item> iterCur = currentItems.iterator();
		Iterator<Item> iterRem = removedItems.iterator();
		int itemsStoredinDate = 0;
		// iterate through items
		while (iterCur.hasNext()) {
			item = iterCur.next();
			if (!item.getEntryDate().after(cal.getTime())) {
				itemsStoredinDate++;
			}
		}
		while (iterRem.hasNext()) {
			item = iterRem.next();
			if (!item.getEntryDate().after(cal.getTime())
					&& item.getExitDate().after(cal.getTime())
					&& !item.getExitDate().equals(cal.getTime())) {
				itemsStoredinDate++;
			}
		}
		return itemsStoredinDate;
	}

	@Override
	public int calculateMinimumSupply() {
		int minimumSupply = calculateCurrentSupply();
		if (minimumSupply == 0)
			return 0;
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		while (!cal.getTime().after(endDate) || cal.getTime().equals(endDate)) {
			int itemsStoredinDate = calculateItemsStoredinDate(cal);
			if (itemsStoredinDate < minimumSupply) {
				minimumSupply = itemsStoredinDate;
			}
			cal.add(Calendar.DAY_OF_YEAR, 1);
		}
		return minimumSupply;
	}

	@Override
	public int calculateMaximumSupply() {
		int maximumSupply = calculateCurrentSupply();
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		while (!cal.getTime().after(endDate) || cal.getTime().equals(endDate)) {
			int itemsStoredinDate = calculateItemsStoredinDate(cal);
			if (itemsStoredinDate > maximumSupply) {
				maximumSupply = itemsStoredinDate;
			}
			cal.add(Calendar.DAY_OF_YEAR, 1);
		}
		return maximumSupply;
	}

	@Override
	public int calculateItemsUsed() {
		int itemsUsed = 0;
		Iterator<Item> iterRem = removedItems.iterator();
		while (iterRem.hasNext()) {
			Item item = iterRem.next();
			if (item.getExitDate().after(startDate)
					|| item.getExitDate().equals(startDate)) {
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
			if (item.getEntryDate().after(startDate)
					|| item.getEntryDate().equals(startDate)) {
				itemsAdded++;
			}
		}
		while (iterRem.hasNext()) {
			item = iterRem.next();
			if (item.getEntryDate().after(startDate)
					|| item.getEntryDate().equals(startDate)) {
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
<<<<<<< HEAD
			netAgeUsed += calculateDayDifference(
					DateUtils.removeTimeFromDate(item.getEntryDate()),
					DateUtils.removeTimeFromDate(item.getExitDate()));
=======
			netAgeUsed += calculateDayDifference(removeTimeFromDate(item.getEntryDate()),
					removeTimeFromDate(item.getExitDate()));
>>>>>>> 364eb0e33e8eb9b765444fa4f20cbc45e9260c0c
			totalUsedItems++;
		}
		if (totalUsedItems == 0)
			return 0;
		averageAgeUsed = ((double) netAgeUsed / (double) totalUsedItems);
		return averageAgeUsed;
	}

	@Override
	public int calculateMaximumAgeUsed() {
		int maximumAgeUsed = 0;
		Item item;
		Iterator<Item> iter = removedItems.iterator();
		while (iter.hasNext()) {
			item = iter.next();
<<<<<<< HEAD
			int age = calculateDayDifference(
					DateUtils.removeTimeFromDate(item.getEntryDate()),
					DateUtils.removeTimeFromDate(item.getExitDate()));
=======
			int age = calculateDayDifference(removeTimeFromDate(item.getEntryDate()),
					removeTimeFromDate(item.getExitDate()));
>>>>>>> 364eb0e33e8eb9b765444fa4f20cbc45e9260c0c
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
<<<<<<< HEAD
			netAgeCurrent += calculateDayDifference(
					DateUtils.removeTimeFromDate(item.getEntryDate()),
					DateUtils.removeTimeFromDate(endDate));
=======
			netAgeCurrent += calculateDayDifference(removeTimeFromDate(item.getEntryDate()),
					removeTimeFromDate(endDate));
>>>>>>> 364eb0e33e8eb9b765444fa4f20cbc45e9260c0c
			totalCurrentItems++;
		}
		averageAgeCurrent = ((double) netAgeCurrent / (double) totalCurrentItems);
		return averageAgeCurrent;
	}

	@Override
	public int calculateMaximumAgeCurrent() {
		int maximumAgeCurrent = 0;
		Item item;
		Iterator<Item> iter = currentItems.iterator();
		while (iter.hasNext()) {
			item = iter.next();
<<<<<<< HEAD
			int age = calculateDayDifference(
					DateUtils.removeTimeFromDate(item.getEntryDate()),
					DateUtils.removeTimeFromDate(new Date()));
=======
			int age = calculateDayDifference(removeTimeFromDate(item.getEntryDate()),
					removeTimeFromDate(new Date()));
>>>>>>> 364eb0e33e8eb9b765444fa4f20cbc45e9260c0c
			if (age > maximumAgeCurrent) {
				maximumAgeCurrent = age;
			}
		}
		return maximumAgeCurrent;
	}

	public int calculateDayDifference(Date begin, Date end) {
		if (begin.equals(end) || begin.after(end)) {
			return 0;
		}
		int dayDifference = 0;
		Calendar cal = Calendar.getInstance();
		cal.setTime(begin);
		while (!cal.getTime().equals(end) || cal.getTime().after(end)) {
			dayDifference++;
			cal.add(Calendar.DAY_OF_YEAR, 1);
		}
		return dayDifference;
	}

	public Date getEarliestDate(Date date1, Date date2) {
		return (date1.before(date2) ? date1 : date2);
	}

	public Date getLatestDate(Date date1, Date date2) {
		return (date1.after(date2) ? date1 : date2);
	}
}