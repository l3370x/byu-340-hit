package gui.reports.productstats;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import common.util.DateUtils;

import core.model.InventoryManager;
import core.model.Item;
import core.model.Product;
import gui.reports.productstats.ProductStatsCalculator;
import core.model.exception.ExceptionHandler;
import core.model.exception.HITException;
import core.model.exception.HITException.Severity;

import gui.common.*;

/**
 * Controller class for the product statistics report view.
 */
public class ProductStatsReportController extends Controller implements
		IProductStatsReportController {

	private static final String BASE_FILE_NAME = System.getProperty("user.dir");

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
	 * Sets the enable/disable state of all components in the controller's view.
	 * A component should be enabled only if the user is currently allowed to
	 * interact with that component.
	 * 
	 * {@pre None}
	 * 
	 * {@post The enable/disable state of all components in the controller's
	 * view have been set appropriately.}
	 */
	@Override
	protected void enableComponents() {
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
	@Override
	public void display() {
		if (!isValidInput(this.getView().getMonths())) {
			ExceptionHandler.TO_USER.reportException(new HITException(
					Severity.INFO, "Couldn't make report from given input."),
					"Try different input values.");
			return;
		}

		int nMonths = Integer.parseInt(this.getView().getMonths());

		Date startingDate = new Date();
		GregorianCalendar goBack = new GregorianCalendar();
		goBack.setTime(startingDate);
		goBack.add(Calendar.MONTH, -1 * nMonths);
		startingDate = goBack.getTime();

		ProductStatsCalculator calc = ProductStatsCalculator.Factory
				.getInstance();

		InventoryManager inventory = InventoryManager.Factory
				.getInventoryManager();
		Iterable<Product> allProducts = inventory.getProducts();

		try {
			File outFile = initOutputFile();
			Document document = new Document(PageSize.LETTER_LANDSCAPE);
			PdfWriter pdfWriter = PdfWriter.getInstance(document,
					new FileOutputStream(outFile, true));
			document.open();
			PdfPTable table = new PdfPTable(10);
			formatDocument(document, pdfWriter.getDirectContent(), null, calc,
					table);
			for (Product p : allProducts) {
				calc.setValues(startingDate, inventory.getItems(p),
						inventory.getRemovedItems(p), p);
				formatDocument(document, pdfWriter.getDirectContent(), p, calc,
						table);
			}
			document.add(table);
			document.close();
			displayDocument(outFile);
		} catch (FileNotFoundException | DocumentException e) {
			ExceptionHandler.TO_USER.reportException(e,
					"Can't print bar code labels");
			ExceptionHandler.TO_LOG.reportException(e,
					"Can't print bar code labels");
		}

		for (Product p : allProducts) {
			System.out.println(p.toString());
		}

	}

	private static void displayDocument(File file) {
		try {
			Desktop.getDesktop().open(file);
		} catch (IOException e) {
			ExceptionHandler.TO_USER.reportException(e,
					"Can't print bar code labels");
			ExceptionHandler.TO_LOG.reportException(e,
					"Can't print bar code labels");
		}
	}

	private static File initOutputFile() {
		File outFile = null;
		try {
			Date date = DateUtils.removeTimeFromDate(DateUtils.currentDate());
			String dateString = DateUtils.formatDate(date);
			dateString = dateString.replace("/", "_");

			String fileName = BASE_FILE_NAME.replace("build/", "");
			outFile = new File(fileName + "reports/productStatisticsReport/",
					dateString + "_" + ".pdf");
			outFile.getParentFile().mkdirs();

			if (!outFile.exists()) {
				outFile.createNewFile();
			}

		} catch (IOException e) {
			ExceptionHandler.TO_USER.reportException(e,
					"Can't print bar code labels");
			ExceptionHandler.TO_LOG.reportException(e,
					"Can't print bar code labels");
		}

		return outFile;
	}

	private static void formatDocument(Document document,
			PdfContentByte contentByte, Product p, ProductStatsCalculator calc,
			PdfPTable table) {
		if (p == null) {
			for (int i = 0; i < 10; i++) {
				table.addCell(REPORT_HEADINGS.get("h" + String.valueOf(i + 1)));
			}
		} else {
			for (int i = 1; i <= 10; i++) {
				switch (i) {
				case 1:
					table.addCell(p.getDescription());
					break;
				case 2:
					table.addCell(p.getBarCode().getValue());
					break;
				case 3:
					table.addCell(p.getSize().toString());
					break;
				case 4:
					table.addCell(String.valueOf(p.get3MonthSupplyQuota()));
					break;
				case 5:
					table.addCell(String.format("%d / %f",
							calc.calculateCurrentSupply(),
							calc.calculateAverageSupply()));
					break;
				case 6:
					table.addCell(String.format("%d days / %d days",
							calc.calculateMinimumSupply(),
							calc.calculateMaximumSupply()));
					break;
				case 7:
					table.addCell(String.format("%d days / %d days",
							calc.calculateItemsUsed(),
							calc.calculateItemsAdded()));
					break;
				case 8:
					table.addCell(String.format("%d months",
							p.getShelfLifeInMonths()));
					break;
				case 9:
					table.addCell(String.format("%f days / %d days",
							calc.calculateAverageAgeUsed(),
							calc.calculateMaximumAgeUsed()));
					break;
				case 10:
					table.addCell(String.format("%f days / %d days",
							calc.calculateAverageAgedCurrent(),
							calc.calculateMaximumAgeCurrent()));
					break;
				}
			}
		}
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
		REPORT_HEADINGS.put("h3", "Size");
		REPORT_HEADINGS.put("h4", "3-Month Supply");
		REPORT_HEADINGS.put("h5", "Supply: Cur/Avg");
		REPORT_HEADINGS.put("h6", "Supply: Min/Max");
		REPORT_HEADINGS.put("h7", "Supply: Used/Added");
		REPORT_HEADINGS.put("h8", "Shelf Life");
		REPORT_HEADINGS.put("h9", "Used Age: Avg/Max");
		REPORT_HEADINGS.put("h10", "Cur Age: Avg/Max");
	}

}
