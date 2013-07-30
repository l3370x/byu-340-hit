package gui.reports.supply;

import core.model.InventoryManager;
import core.model.Item;
import core.model.Product;
import gui.common.FileFormat;
import gui.common.IView;
import gui.reports.AbstractReportController;
import gui.reports.Report;

import java.util.*;

import static core.model.InventoryManager.Factory.getInventoryManager;

/**
 * Controller class for the N-month supply report view.
 */
	public class SupplyReportController extends AbstractReportController implements
		ISupplyReportController {
		
	private static final String BASE_FILE_NAME = System.getProperty("user.dir");

	/**
	 * Constructor.
	 * 
	 * @param view Reference to the N-month supply report view
	 */	
	public SupplyReportController(IView view) {
		super(view);
		
		construct();
	}

	//
	// Controller overrides
	//
	
	/**
	 * Returns a reference to the view for this controller.
	 * 
	 * {@pre None}
	 * 
	 * {@post Returns a reference to the view for this controller.}
	 */
	@Override
	protected ISupplyReportView getView() {
		return (ISupplyReportView)super.getView();
	}

	/**
	 * Loads data into the controller's view.
	 * 
	 *  {@pre None}
	 *  
	 *  {@post The controller has loaded data into its view}
	 */
	@Override
	protected void loadValues() {
		this.getView().setFormat(FileFormat.PDF);
		this.getView().setMonths("3");
	}

	//
	// IExpiredReportController overrides
	//

	/**
	 * This method is called when any of the fields in the
	 * N-month supply report view is changed by the user.
	 */
	@Override
	public void valuesChanged() {
		if (!isValidInput(this.getView().getMonths())) {
			this.getView().enableOK(false);
			return;
		}
		this.getView().enableOK(true);
	}
	
	
	/**
	 * This method is called when the user clicks the "OK" button in the product
	 * statistics report view.
	 */
    /*
	@Override
	public void display() {
		if (!isValidInput(this.getView().getMonths())) {
			ExceptionHandler.TO_USER.reportException(new HITException(
					Severity.INFO, "Couldn't make report from given input."),
					"Try different input values.");
			return;
		}
		int nMonths = Integer.parseInt(this.getView().getMonths());
		List<ArrayList<String>> data = createHeader();
		data.addAll(createProductData());

		ReportController report = null;

		FileFormat f = this.getView().getFormat();
		String title = String.format("%d Month Supply Report",
				nMonths);
		String name = "MonthSupply";

		if (f.equals(FileFormat.PDF))
			report = new PDFSupplyReport(name, title);
		else if (f.equals(FileFormat.HTML))
			report = new HTMLSupplyReport(name, title);
		else {
			ExceptionHandler.TO_USER.reportException(new HITException(
					Severity.INFO, "Couldn't make report from given input."),
					String.format("Bad Format Entered - %s.", f.toString()));
			return;
		}

		report.initialize();
		report.appendTable(data);
		report.finalize();
		
	}
	*/

    @Override
    protected FileFormat getReportFormat() {
        return this.getView().getFormat();
    }

    @Override
    protected Report getReport() {
        return null;
    }

    private List<ArrayList<String>> createProductData() {
		List<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
	
		InventoryManager inventory = InventoryManager.Factory
				.getInventoryManager();
		Iterable<Product> allProducts = inventory.getProducts();
		
		for (Product p : allProducts) {
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
	
	private List<ArrayList<String>> createProductGroupData() {
		List<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
	
		InventoryManager inventory = InventoryManager.Factory
				.getInventoryManager();
		Iterable<Product> allProducts = inventory.getProducts();
		for (Product p : allProducts) {
			
			ArrayList<String> toAppend = new ArrayList<String>();
			//calc.setValues(startingDate, inventory.getItems(p),
			//		inventory.getRemovedItems(p), p);
			for (int i = 1; i <= 4; i++) {
				switch (i) {
				case 1:
					toAppend.add(p.getDescription());
					break;
				case 2:
					toAppend.add(p.getBarCode().getValue());
					break;
				/*case 3:
					toAppend.add(p.getSize().toString());
					break;
				case 4:
					toAppend.add(String.valueOf(p.get3MonthSupplyQuota()));
					break;*/
				}
			}
			data.add(toAppend);
			
		}
		return data;
	}

	public List<ArrayList<String>> createHeader() {
		List<ArrayList<String>> rows = new ArrayList<ArrayList<String>>();
		ArrayList<String> table = new ArrayList<String>();
		for (int i = 0; i < 4; i++) {
			if(i == 2){
				table.add(this.getView().getMonths() + "-Month Supply");
			}
			else
				table.add(REPORT_HEADINGS.get("h" + String.valueOf(i + 1)));
		}
		rows.add(table);
		return rows;
	}

	private boolean isValidInput(String months) {
		// Check to make sure the month value isn't blank
		if (null == months || months.isEmpty()) {
			return false;
		}

		// check to make sure the month value is an integer and in the
		// determined range.
		Float count = Float.parseFloat(this.getView().getMonths());
		if (count != Math.round(count) || (count > 100) || (count <= 0)) {
			return false;
		}
		return true;
	}
	
	private static final Map<String, String> REPORT_HEADINGS = new HashMap<>();
	static {
		REPORT_HEADINGS.put("h1", "Description");
		REPORT_HEADINGS.put("h2", "Barcode");
		REPORT_HEADINGS.put("h3", "Month Supply");
		REPORT_HEADINGS.put("h4", "Current Supply");
	}

}
