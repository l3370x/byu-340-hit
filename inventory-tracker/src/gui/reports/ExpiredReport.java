package gui.reports;


import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import gui.item.ItemData;
import core.model.Item;
import core.model.ProductContainer;

public class ExpiredReport extends AbstractReport {

		private List<Item> itemsExpired = new ArrayList<Item>();
		
		@Override
		public void render(ReportRenderer renderer) {
		try {
			renderer.beginDocument("Expired Items","Expired Items");
			renderer.beginTable("Description", "Storage Unit", 
		      "Product Group", "Entry Date", "Expire Date", "Item Barcode");
			
			  for (Item i : itemsExpired) {
				  
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
			 
			Iterator<Item> allItems = obj.getItems().iterator();
		 	  while(allItems.hasNext()) {
		 		  Item itemN = allItems.next();
	 	         if ((itemN.getExpirationDate().before(new Date())) || (itemN.getExpirationDate().equals(new Date())))
	 	        		 
	 	         {
						this.itemsExpired.add(itemN);
				
	 	         }
		 	  }
		 		return true;
		}

	
}
