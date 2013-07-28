package gui.reports.expired;


import gui.common.*;
import gui.item.ItemData;
import gui.reports.productstats.ProductStatsCalculator;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import common.util.DateUtils;
import core.model.Item;
import core.model.ItemCollection;
import core.model.Product;
import core.model.exception.ExceptionHandler;
import core.model.exception.HITException;
import static core.model.InventoryManager.Factory.getInventoryManager;

/**
 * Controller class for the expired items report view.
 */
public class ExpiredReportController extends Controller implements
										IExpiredReportController {

	private List<Item> itemsExpired = new ArrayList<Item>();
	private static final String BASE_FILE_NAME = System.getProperty("user.dir");

	/**
	 * Constructor.
	 * 
	 * @param view Reference to the expired items report view
	 */
	public ExpiredReportController(IView view) {
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
	protected IExpiredReportView getView() {
		return (IExpiredReportView)super.getView();
	}

	/**
	 * Sets the enable/disable state of all components in the controller's view.
	 * A component should be enabled only if the user is currently
	 * allowed to interact with that component.
	 * 
	 * {@pre None}
	 * 
	 * {@post The enable/disable state of all components in the controller's view
	 * have been set appropriately.}
	 */
	@Override
	protected void enableComponents() {
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
	}

	//
	// IExpiredReportController overrides
	//

	/**
	 * This method is called when any of the fields in the
	 * expired items report view is changed by the user.
	 */
	@Override
	public void valuesChanged() {
	}
	
	/**
	 * This method is called when the user clicks the "OK"
	 * button in the expired items report view.
	 */
	
	private void getExpiredItems() 
	{
		Iterator<Item> allItems = getInventoryManager().getItems().iterator();
	 	  while(allItems.hasNext()) {
	 		  Item itemN = allItems.next();
 	         if ((itemN.getExpirationDate().before(new Date())) || (itemN.getExpirationDate().equals(new Date())))
 	        		 
 	         {
					this.itemsExpired.add(itemN);
			
 	         }
	 	  }
	}
	
	@Override
	public void display() {
	
		getExpiredItems();
		
		File outFile = initOutputFile(this.getView().getFormat());
		
		if (this.getView().getFormat()==FileFormat.PDF)
		{
		try {
			
			Document document = new Document(PageSize.LETTER_LANDSCAPE);
			PdfWriter pdfWriter = PdfWriter.getInstance(document,
					new FileOutputStream(outFile, true));
			document.open();
			PdfPTable table = new PdfPTable(6);
			formatDocument(document, pdfWriter.getDirectContent(), null , table);
			for (Item i : itemsExpired) {
				formatDocument(document, pdfWriter.getDirectContent(), i, 
						table);
			}
			document.add(table);
			document.close();
			displayDocument(outFile);
		} catch (FileNotFoundException | DocumentException e) {
			ExceptionHandler.TO_USER.reportException(e,
					"Can't print Expired Items Report");
			ExceptionHandler.TO_LOG.reportException(e,
					"Can't print Expired Items Report");
		}
		}
		else
		{
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(outFile));
				  bw.write("<html><head><title>Expired Items</title></head><body><h1>Expired Items</h1><br><table border=\"1\"><tr>"
				  		+ "<th>Description</th><th>Storage Unit</th><th>Product Group</th>"
				  		+ "<th>Entry Date</th><th>Expire Date</th><th>Item Barcode</th></tr>");
				
					for (Item expiredItem : itemsExpired) {
							ItemData item = new ItemData(expiredItem);
						
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
							
						 bw.write("<tr><td>" + expiredItem.getProduct().getDescription() + "</td><td>" + item.getStorageUnit() +
								 "</td><td>" + pGroup + "</td><td>" + outputFormatter.format(date) + 
								 "</td><td>" + outputFormatter.format(date1) +  "</td><td>" + item.getBarcode() + "</td></td></tr>");
						 
					
						 
					}
					 bw.write("</table></body></html>");
					 bw.close();
					 Desktop.getDesktop().browse(outFile.toURI());
				
			} catch (IOException e) {
				ExceptionHandler.TO_USER.reportException(e,
						"Can't print Expired Items Report");
				ExceptionHandler.TO_LOG.reportException(e,
						"Can't print Expired Items Report");
			}
		}
	}
	private static void displayDocument(File file) {
		try {
			Desktop.getDesktop().open(file);
		} catch (IOException e) {
			ExceptionHandler.TO_USER.reportException(e,
					"Can't open the file");
			ExceptionHandler.TO_LOG.reportException(e,
					"Can't open the file");
		}
	}
	
	private static File initOutputFile(FileFormat type) {
		File outFile = null;
		try {
			Date date = DateUtils.removeTimeFromDate(DateUtils.currentDate());
			String dateString = DateUtils.formatDate(date);
			dateString = dateString.replace("/", "_");

			String fileName = BASE_FILE_NAME.replace("build/", "");
			outFile = new File(fileName + "reports/expiredItemsReport/",
					dateString + "_" + "ExpiryReport." + type.toString().toLowerCase());
			outFile.getParentFile().mkdirs();

			if (!outFile.exists()) {
				outFile.createNewFile();
			}

		} catch (IOException e) {
			ExceptionHandler.TO_USER.reportException(e,
					"Can't create file");
			ExceptionHandler.TO_LOG.reportException(e,
					"Can't create output file");
		}

		return outFile;
	}
	
	private static void formatDocument(Document document,
			PdfContentByte contentByte,Item i, PdfPTable table) {
		if (i == null) {
			for (int j = 0; j < 6; j++) {
				table.addCell(REPORT_HEADINGS.get("h" + String.valueOf(j + 1)));
			}
		} else {
			ItemData item = new ItemData(i);
			for (int j = 1; j <= 6; j++) {
				switch (j) {
				case 1:
					table.addCell(i.getProduct().getDescription());
					break;
				case 2:
					table.addCell(item.getStorageUnit());
					break;
				case 3:
					table.addCell(item.getProductGroup());
					break;
				case 4:
					//Date Conversion
					Date date = item.getEntryDate();
					DateFormat outputFormatter = new SimpleDateFormat("MM/dd/yyyy");
					table.addCell(outputFormatter.format(date));
					break;
				case 5:
					Date date1 = item.getExpirationDate();
					DateFormat outputFormatter1 = new SimpleDateFormat("MM/dd/yyyy");
					table.addCell(outputFormatter1.format(date1));
			
					break;
				case 6:
					table.addCell(item.getBarcode());
					break;
				}
			}
		}
	}

	private static final Map<String, String> REPORT_HEADINGS = new HashMap<>();
	static {
		REPORT_HEADINGS.put("h1", "Description");
		REPORT_HEADINGS.put("h2", "Storage Unit");
		REPORT_HEADINGS.put("h3", "Product Group");
		REPORT_HEADINGS.put("h4", "Entry Date");
		REPORT_HEADINGS.put("h5", "Expire Date");
		REPORT_HEADINGS.put("h6", "Item Barcode");
	}

}

