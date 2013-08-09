package gui.reports.productstats;

import core.model.Item;

import java.util.Calendar;
import java.util.Date;

import static common.util.DateUtils.removeTimeFromDate;

public class ProductStatsCalculatorImpl implements ProductStatsCalculator {

    private static final int MILLIS_PER_DAY = 1000 * 60 * 60 * 24;
    private Date startDate;
    private Date endDate;
    private int numberOfDays;
    private Iterable<Item> currentItems;
    private Iterable<Item> removedItems;

    public ProductStatsCalculatorImpl(
            Date startDate, Iterable<Item> currentItems, Iterable<Item> removedItems) {
        this.startDate = removeTimeFromDate(startDate);
        this.endDate = new Date();//removeTimeFromDate(new Date());
        this.currentItems = currentItems;
        this.removedItems = removedItems;
        this.numberOfDays = calculateDayDifference(this.startDate, this.endDate);
    }

    @Override
    public int currentSupply() {
        int itemCount = 0;
        for (Item ignored : this.currentItems) {
            itemCount++;
        }

        return itemCount;
    }

    @Override
    public double averageSupply() {

        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        int netDaysItemsStored = 0;
        while (!cal.getTime().after(endDate)) {
            netDaysItemsStored += calculateItemsStoredInDate(cal.getTime());
            cal.add(Calendar.DAY_OF_YEAR, 1);
        }
        if (numberOfDays == 0)
            return netDaysItemsStored;

        return roundOnePlace((double) netDaysItemsStored / (double) numberOfDays);
    }

    private static double roundOnePlace(double in) {
        return (double) Math.round(in * 1000) / 1000;
    }

    private int calculateItemsStoredInDate(Date date) {
        int itemsStoredInDate = 0;

        // iterate through items
        for (Item item : this.currentItems) {
            if (!item.getEntryDate().after(date)) {
                itemsStoredInDate++;
            }
        }

        for (Item item : this.removedItems) {
            if (item.getEntryDate().compareTo(date) <= 0 &&
                    (null != item.getExitDate() && item.getExitDate().compareTo(date) > 0)) {
                itemsStoredInDate++;
            }
            /*
            if (!item.getEntryDate().after(cal.getTime())
                    && item.getExitDate().after(cal.getTime())
                    && !item.getExitDate().equals(cal.getTime())) {
                itemsStoredInDate++;
            }
            */
        }

        return itemsStoredInDate;
    }

    @Override
    public int minimumSupply() {
        int minimumSupply = currentSupply();
        if (minimumSupply == 0) {
            return 0;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(this.startDate);
        //while (!cal.getTime().after(endDate) || cal.getTime().equals(endDate)) {
        while (cal.getTime().compareTo(this.endDate) <= 0) {
            minimumSupply = Math.min(minimumSupply, this.calculateItemsStoredInDate(cal.getTime()));
            cal.add(Calendar.DAY_OF_YEAR, 1);
        }

        return minimumSupply;
    }

    @Override
    public int maximumSupply() {
        int maximumSupply = currentSupply();
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        //while (!cal.getTime().after(endDate) || cal.getTime().equals(endDate)) {
        while (cal.getTime().compareTo(this.endDate) <= 0) {
            maximumSupply = Math.max(maximumSupply, this.calculateItemsStoredInDate(cal.getTime()));
            cal.add(Calendar.DAY_OF_YEAR, 1);
        }
        return maximumSupply;
    }

    @Override
    public int itemsUsed() {
        int itemsUsed = 0;
        for (Item item : this.removedItems) {
            if (null != item.getExitDate() && item.getExitDate().compareTo(this.startDate) >= 0) {
                itemsUsed++;
            }
            /*
            if (item.getExitDate().after(startDate)
                    || item.getExitDate().equals(startDate)) {
            }
            */
        }
        return itemsUsed;
    }

    @Override
    public int itemsAdded() {
        int itemsAdded = 0;

        for (Item item : this.currentItems) {
            if (item.getEntryDate().compareTo(this.startDate) >= 0) {
                itemsAdded++;
            }
            /*
            if (item.getEntryDate().after(startDate)
                    || item.getEntryDate().equals(startDate)) {
                itemsAdded++;
            }
            */
        }

        for (Item item : this.removedItems) {
            if (item.getEntryDate().compareTo(this.startDate) >= 0) {
                itemsAdded++;
            }
            /*
            if (item.getEntryDate().after(startDate)
                    || item.getEntryDate().equals(startDate)) {
                itemsAdded++;
            }
            */
        }

        return itemsAdded;
    }

    @Override
    public double averageAgeUsed() {
        int netAgeUsed = 0;
        int totalUsedItems = 0;
        for (Item item : this.removedItems) {
            netAgeUsed += calculateDayDifference(
                    removeTimeFromDate(item.getEntryDate()),
                    removeTimeFromDate(item.getExitDate()));
            totalUsedItems++;
        }

        if (totalUsedItems == 0) {
            return 0;
        }

        return ((double) netAgeUsed / (double) totalUsedItems);
    }

    @Override
    public int maximumAgeUsed() {
        int maximumAgeUsed = 0;
        for (Item item : this.removedItems) {
            int age = calculateDayDifference(
                    removeTimeFromDate(item.getEntryDate()),
                    removeTimeFromDate(item.getExitDate()));
            if (age > maximumAgeUsed) {
                maximumAgeUsed = age;
            }
        }
        return maximumAgeUsed;
    }

    @Override
    public double averageAgedCurrent() {
        int netAgeCurrent = 0;
        int totalCurrentItems = 0;
        for (Item item : this.currentItems) {
            netAgeCurrent += calculateDayDifference(
                    removeTimeFromDate(item.getEntryDate()),
                    removeTimeFromDate(endDate));
            totalCurrentItems++;
        }

        return ((double) netAgeCurrent / (double) totalCurrentItems);
    }

    @Override
    public int maximumAgeCurrent() {
        int maximumAgeCurrent = 0;
        for (Item item : this.currentItems) {
            int age = calculateDayDifference(
                    removeTimeFromDate(item.getEntryDate()),
                    removeTimeFromDate(new Date()));
            if (age > maximumAgeCurrent) {
                maximumAgeCurrent = age;
            }
        }

        return maximumAgeCurrent;
    }

    public int calculateDayDifference(Date begin, Date end) {
        long diff = end.getTime() - begin.getTime();

        return (int) (diff / MILLIS_PER_DAY);
    }
}