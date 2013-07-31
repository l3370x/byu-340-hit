package gui.reports;


import java.io.Console;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import gui.item.ItemData;
import core.model.InventoryManager;
import core.model.Item;
import core.model.Product;
import core.model.ProductContainer;

public class ExpiredReport extends AbstractReport {

		private Set<Item> itemsExpired = new HashSet<Item>();
		
		@Override
		public void render(ReportRenderer renderer) {
		try {
			renderer.beginDocument("Expired Items","Expired Items");
			renderer.beginTable("Description", "Storage Unit", 
		      "Product Group", "Entry Date", "Expire Date", "Item Barcode");
			
			
			//To separate and sort the List of items removed....Warning! Its not very efficient!
			
			List<Item> allItems = new ArrayList<Item>();
			
			  for (Item i : itemsExpired) {
				  allItems.add(i);
			  }
			  
			  
			  
			  Collections.sort(allItems, new Comparator<Item>() {
			        @Override
			        public int compare(final Item object1, final Item object2) {
			        	
			        	 int value1 = object1.getProduct().getDescription().compareTo(object1.getProduct().getDescription());
			             if (value1 == 0) {
			                 int value2 = object1.getEntryDate().compareTo(object2.getEntryDate());
			                 return value2;
			             }
			             return value1;
			         
			        }
			       } );
			  
			  
			
			  for (Item i : allItems) {
				  
				  ItemData item = new ItemData(i);
					
					Date date = item.getEntryDate();
					Date date1 = item.getExpirationDate();
					DateFormat outputFormatter = new SimpleDateFormat("MM/dd/yyyy");
					String pGroup;
					if (item.getProductGroup() == null)
					{
						pGroup = "";
					}
					else
					{
						pGroup = item.getProductGroup();
					}
				  
				  renderer.addTableRow(i.getProduct().getDescription(), item.getStorageUnit(), pGroup, outputFormatter.format(date),
						  outputFormatter.format(date1), item.getBarcode());
			 }
			renderer.endTable();
			renderer.endDocument();
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		}

		@Override
		public Boolean operate(ProductContainer obj) {
			
			  if (false == obj instanceof InventoryManager) {
		            return true;
		        }
			  InventoryManager mgr = (InventoryManager) obj; 
			 
			Iterator<Item> allItems1 = mgr.getRemovedItems().iterator();
			
		 	  while(allItems1.hasNext()) {
		 		  Item itemN = allItems1.next();
	 	         if ((itemN.getExpirationDate().before(new Date())) || (itemN.getExpirationDate().equals(new Date())))
	 	        		 
	 	         {
						this.itemsExpired.add(itemN);
				
	 	         }
		 	  }
		 		return false;
		}

	
}
