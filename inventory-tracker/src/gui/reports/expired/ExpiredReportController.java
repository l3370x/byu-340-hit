package gui.reports.expired;


import core.model.Item;
import gui.common.FileFormat;
import gui.common.IView;
import gui.reports.AbstractReportController;
import gui.reports.Report;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static core.model.InventoryManager.Factory.getInventoryManager;

/**
 * Controller class for the expired items report view.
 */
public class ExpiredReportController extends AbstractReportController implements
										IExpiredReportController {

	private List<Item> itemsExpired = new ArrayList<Item>();
	//private static final String BASE_FILE_NAME = System.getProperty("user.dir");

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

	//
	// IExpiredReportController overrides
	//

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

    /*
	@Override
	public void display() {
	
		getExpiredItems();
		
		File outFile = initOutputFile(this.getView().getFormat());
		
		if (this.getView().getFormat()==FileFormat.PDF)
		{
		try {
			
			Document document = new Document(PageSize.LETTER_LANDSCAPE);
			PdfWriter pdfWriter = PdfWriter.newProductStatsCalculator(document,
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
	*/

    /*
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
	*/

    /*
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
	*/

    /*
	private static final Map<String, String> REPORT_HEADINGS = new HashMap<>();
	static {
		REPORT_HEADINGS.put("h1", "Description");
		REPORT_HEADINGS.put("h2", "Storage Unit");
		REPORT_HEADINGS.put("h3", "Product Group");
		REPORT_HEADINGS.put("h4", "Entry Date");
		REPORT_HEADINGS.put("h5", "Expire Date");
		REPORT_HEADINGS.put("h6", "Item Barcode");
	}
	*/

}

