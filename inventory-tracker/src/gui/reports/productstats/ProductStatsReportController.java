package gui.reports.productstats;

import gui.common.FileFormat;
import gui.common.IView;
import gui.reports.*;

/**
 * Controller class for the product statistics report view.
 */
public class ProductStatsReportController extends AbstractReportController implements
		IProductStatsReportController {

	ProductStatsCalculator calc = ProductStatsCalculator.Factory.newProductStatsCalculator();

	/**
	 * Constructor.
	 * 
	 * @param view
	 *            Reference to the item statistics report view
	 */
	public ProductStatsReportController(IView view) {
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
	protected IProductStatsReportView getView() {
		return (IProductStatsReportView) super.getView();
	}

	/**
	 * Loads data into the controller's view.
	 * 
	 * {@pre None}
	 * 
	 * {@post The controller has loaded data into its view}
	 */
	@Override
	protected void loadValues() {
		this.getView().setFormat(FileFormat.PDF);
		this.getView().setMonths("3");
	}

	//
	// IProductStatsReportController overrides
	//

	/**
	 * This method is called when any of the fields in the product statistics
	 * report view is changed by the user.
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
		data.addAll(createData());

		ReportController report = null;

		FileFormat f = this.getView().getFormat();
		String title = String.format("Product Report (%d Months)", nMonths);
		String name = "ProductReport";

		if (f.equals(FileFormat.PDF))
			report = new PDFProductStatisticsReport(name, title);
		else if (f.equals(FileFormat.HTML))
			report = new HTMLProductStatisticsReport(name, title);
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

    /*
    private List<ArrayList<String>> createData() {
		List<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
		Date startingDate = new Date();
		Calendar goBack = Calendar.newProductStatsCalculator();
		goBack.setTime(startingDate);
		goBack.add(Calendar.MONTH,
				-1 * Integer.valueOf(this.getView().getMonths()));
		startingDate = goBack.getTime();

		InventoryManager inventory = InventoryManager.Factory
				.getInventoryManager();
		Iterable<Product> allProducts = inventory.getProducts();
		for (Product p : allProducts) {
			ArrayList<String> toAppend = new ArrayList<String>();
			calc.setValues(startingDate, inventory.getItems(p),
					inventory.getRemovedItems(p), p);
			for (int i = 1; i <= 10; i++) {
				switch (i) {
				case 1:
					toAppend.add(p.getDescription());
					break;
				case 2:
					toAppend.add(p.getBarCode().getValue());
					break;
				case 3:
					toAppend.add(p.getSize().toString());
					break;
				case 4:
					toAppend.add(String.valueOf(p.get3MonthSupplyQuota()));
					break;
				case 5:
					toAppend.add(String.format("%d / %s", calc
							.calculateCurrentSupply(),
							String.valueOf(calc.calculateAverageSupply())
									.replaceAll("\\.?0*$", "")));
					break;
				case 6:
					toAppend.add(String.format("%d / %d",
							calc.calculateMinimumSupply(),
							calc.calculateMaximumSupply()));
					break;
				case 7:
					toAppend.add(String.format("%d / %d",
							calc.calculateItemsUsed(),
							calc.calculateItemsAdded()));
					break;
				case 8:
					toAppend.add(String.format("%d months",
							p.getShelfLifeInMonths()));
					break;
				case 9:
					toAppend.add(String.format("%s days / %d days", String
							.valueOf(calc.calculateAverageAgeUsed())
							.replaceAll("\\.?0*$", ""), calc
							.calculateMaximumAgeUsed()));
					break;
				case 10:
					toAppend.add(String.format("%s days / %d days", String
							.valueOf(calc.calculateAverageAgedCurrent())
							.replaceAll("\\.?0*$", ""), calc
							.calculateMaximumAgeCurrent()));
					break;
				}
			}
			data.add(toAppend);
		}
		return data;
	}
	*/

    /*
	public List<ArrayList<String>> createHeader() {
		List<ArrayList<String>> rows = new ArrayList<ArrayList<String>>();
		ArrayList<String> table = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			table.add(REPORT_HEADINGS.get("h" + String.valueOf(i + 1)));
		}
		rows.add(table);
		return rows;
	}
	*/

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

    /*
	private static final Map<String, String> REPORT_HEADINGS = new HashMap<>();
	static {
		REPORT_HEADINGS.put("h1", "Description");
		REPORT_HEADINGS.put("h2", "Barcode");
		REPORT_HEADINGS.put("h3", "Size");
		REPORT_HEADINGS.put("h4", "3-Month Supply");
		REPORT_HEADINGS.put("h5", "Supply: Cur/Avg");
		REPORT_HEADINGS.put("h6", "Supply: Min/Max");
		REPORT_HEADINGS.put("h7", "Supply: Used/Added");
		REPORT_HEADINGS.put("h8", "Shelf Life");
		REPORT_HEADINGS.put("h9", "Used Age: Avg/Max");
		REPORT_HEADINGS.put("h10", "Cur Age: Avg/Max");
	}
	*/

}
