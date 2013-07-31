package gui.reports.supply;

import static core.model.InventoryManager.Factory.getInventoryManager;

import java.io.IOException;
import java.util.HashMap;
import gui.reports.AbstractReport;
import gui.reports.ReportRenderer;
import core.model.Product;
import core.model.ProductContainer;

public class SupplyReport extends AbstractReport {
	private String title;
	private int Months;
	HashMap<String, Product> products = new HashMap<>();
	HashMap<String, ProductContainer> productGroups = new HashMap<>();
	
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
		renderer.endDocument();
	} catch (IOException e) {
		e.printStackTrace();
	}
		
	}

	@Override
	public Boolean operate(ProductContainer obj) {
		    
		Iterable<Product> containerProducts = obj.getProducts();
		for(Product p : containerProducts) {
			int count = (getInventoryManager()).getItemCount(p);
		    if((p).get3MonthSupplyQuota() > 0 &&
	           (p).get3MonthSupplyQuota() > count){
		    	products.put(p.getDescription(), p);
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
