package gui.reports;

import static core.model.InventoryManager.Factory.getInventoryManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import gui.common.FileFormat;
import gui.item.ItemData;
import core.model.InventoryManager;
import core.model.Item;
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
	
	
	/* private List<ArrayList<String>> createProductData() {
			List<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
		
			InventoryManager inventory = InventoryManager.Factory
					.getInventoryManager();
			Iterable<Product> allProducts = inventory.getProducts();
		
			for (Product p : allProducts) {
				p.
			      int count = 0;
			      Iterator<Item> allItems = getInventoryManager().getItems().iterator();
				  while(allItems.hasNext()) {
			 		 Item itemN = allItems.next();
		 	         if (itemN.getProduct().getBarCode().getValue().equals(p.getBarCode().getValue()))		 
		 	         {
		 	        	 ++count;
		 	         }
			 	  }
				
				if(p.get3MonthSupplyQuota() > 0 && p.get3MonthSupplyQuota() > count){
					ArrayList<String> toAppend = new ArrayList<String>();
					
					for (int i = 1; i <= 4; i++) {
						switch (i) {
						case 1:
							toAppend.add(p.getDescription());
							break;
						case 2:
							toAppend.add(p.getBarCode().getValue());
							break;
						case 3:
							toAppend.add(String.valueOf((int)(p.get3MonthSupplyQuota()
							* (Integer.parseInt(this.getView().getMonths())/ 3.0))) + " Count");
							break;
						case 4:
							toAppend.add(String.valueOf(count) + " Count");
							break;
					}
				}
				data.add(toAppend);
				}
			}
			return data;
		}
		*/
}
