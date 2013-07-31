package gui.reports.supply;

import static core.model.InventoryManager.Factory.getInventoryManager;

import java.io.IOException;
import java.util.HashMap;

import gui.common.UnitsConverter;
import gui.common.UnitsConverter.UnitType;
import gui.reports.AbstractReport;
import gui.reports.ReportRenderer;
import core.model.*;
import core.model.Quantity.Units;
import core.model.exception.HITException;


public class SupplyReport extends AbstractReport {
	private String title;
	private int Months;
	private int number = 0;
	HashMap<String, Product> products = new HashMap<>();
	HashMap<Integer, Category> productGroups = new HashMap<>();
	HashMap<String, Float> productGroupsAmount = new HashMap<>();
	
	@Override
	public void render(ReportRenderer renderer) {
	try {
		renderer.beginDocument("MonthSupply", title);
		
		renderer.addHeading("Products");
		renderer.beginTable("Description", "Barcode", 
	      String.valueOf(Months) + "-Month Supply", "Current Supply");
		
		  for (Product p : products.values()) {
			  String description = p.getDescription();
			  String barcode = p.getBarCode().getValue();
			  String monthSupply = String.valueOf((int)(p.get3MonthSupplyQuota()
						           * (double)Months/3.0)) + " Count";
			  String currentSupply = String.valueOf(getInventoryManager()
						             .getItemCount(p)) + " Count";			
			  renderer.addTableRow(description, barcode, monthSupply, currentSupply);
		 }
		renderer.endTable();
		
		renderer.addHeading("Product Groups");
		renderer.beginTable("Product Group", "Storage Unit", 
		  String.valueOf(Months) + "-Month Supply", "Current Supply");
		for (Category c : productGroups.values()) {
			String name = c.getName();
			String storageUnit = c.getStorageUnit().getName();
			
			String monthSupply = String.valueOf((float)
					(c.get3MonthSupplyQuantity().getValue() * (double)Months/3.0)) + " " 
					+ c.get3MonthSupplyQuantity().getUnits().getLabel();
			if(c.get3MonthSupplyQuantity().getUnits() == Units.COUNT){
				int i = (int) Math.round((c.get3MonthSupplyQuantity().getValue() * (double)Months/3.0));
				monthSupply = String.valueOf(i) + " " 
						+ c.get3MonthSupplyQuantity().getUnits().getLabel();
			}
			
			String currentSupply = String.valueOf(productGroupsAmount.get(c.getName())) 
								   + " " + c.get3MonthSupplyQuantity().getUnits().getLabel();
			if(c.get3MonthSupplyQuantity().getUnits() == Units.COUNT){
				int i = Math.round(productGroupsAmount.get(c.getName()));
				currentSupply = String.valueOf(i) 
						   + " " + c.get3MonthSupplyQuantity().getUnits().getLabel();
			}
			renderer.addTableRow(name, storageUnit, monthSupply, currentSupply);
		 }
		renderer.endTable();
		
		renderer.endDocument();
	} catch (IOException e) {
		e.printStackTrace();
	}
		
	}

	@Override
	public Boolean operate(ProductContainer obj) {
		float categoryCount = 0;
		Iterable<Product> containerProducts = obj.getProducts();
		for(Product p : containerProducts) {
			int count = (getInventoryManager()).getItemCount(p);
		    if((p).get3MonthSupplyQuota() > 0 &&
	           (p).get3MonthSupplyQuota() > count){
		    	products.put(p.getDescription(), p);
	        	}      
		    }
		if(true == obj instanceof Category) {
			Category c = (Category)obj;
			Iterable<Item> containerItems = c.getItems();
			Quantity q = c.get3MonthSupplyQuantity();
			try {
				UnitType t = UnitsConverter.unitsToUnitType(q.getUnits());		
				for(Item i : containerItems) {
					Units u = i.getProduct().getSize().getUnits();
					if(t == (UnitsConverter.unitsToUnitType(u))) {
						float constant = 1;
						if(u != q.getUnits())
						{
							constant = (float)UnitsConverter.unitsToConstant(u, q.getUnits());
						}
						categoryCount += (constant * i.getProduct().getSize().getValue());
					}	
				}
			} catch (HITException e) {
				e.printStackTrace();
			}
		    if(c.get3MonthSupplyQuantity().getValue() > 0 &&
		      (c.get3MonthSupplyQuantity().getValue() * (double)Months/3.0) > categoryCount){
		    	productGroups.put(number, c);
		    	productGroupsAmount.put(c.getName(), categoryCount);
		    	number++;
	        	}      
		}
		return true;
	}



	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setMonths(int months) {
		Months = months;
	}
	
}
