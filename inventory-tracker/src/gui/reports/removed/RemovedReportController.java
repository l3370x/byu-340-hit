package gui.reports.removed;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import common.util.DateUtils;
import core.model.Item;
import core.model.Product;
import core.model.exception.ExceptionHandler;
import gui.common.*;
import gui.item.ItemData;
import gui.reports.AbstractReportController;
import gui.reports.Report;

import static core.model.InventoryManager.Factory.getInventoryManager;


/**
 * Controller class for the removed items report view.
 */
public class RemovedReportController extends AbstractReportController implements
		IRemovedReportController {
	
	private static Map<Product, int[]> itemsRemoved = new HashMap<Product, int[]>();
	private static final String BASE_FILE_NAME = System.getProperty("user.dir");

	/**
	 * Constructor.
	 * 
	 * @param view Reference to the removed items report view
	 */
	public RemovedReportController(IView view) {
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
	protected IRemovedReportView getView() {
		return (IRemovedReportView) super.getView();
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
		
		IRemovedReportView view = this.getView();
		
		if (getInventoryManager().getLastReportRun() == null)
		{
			view.enableSinceLast(false);
			view.setSinceDate(true);
		}
		else
		{
			view.setSinceLast(true);
			view.setSinceLastValue(getInventoryManager().getLastReportRun());
			view.enableSinceLast(true);
		}
	}

	/**
	 * This method is called when the user clicks the "OK"
	 * button in the removed items report view.
	 */
	@Override
	public void display() {
		
		itemsRemoved = new HashMap<Product, int[]>();
		
		Date date;
		
		if (this.getView().getSinceLast())
		{
			date = getInventoryManager().getLastReportRun();
			
		}
		else
		{
			date = this.getView().getSinceDateValue();
		}
		getInventoryManager().setLastReportRun(new Date());
		
		Iterator <Item> allRemovedItems = getInventoryManager().getRemovedItems().iterator();
		
		
		
		while (allRemovedItems.hasNext())
		{
			Item item = allRemovedItems.next();
			if (item.getExitDate().after(date) || 
					item.getExitDate().equals(date))
			{
				
				if (itemsRemoved.containsKey(item.getProduct()))
				{
					int x[]=itemsRemoved.get(item.getProduct());
					x[0]=x[0]+1;
					
					itemsRemoved.put(item.getProduct(), x);
				}
				else
				{
					int x[]= new int[1];
					x[0]=1;
					itemsRemoved.put(item.getProduct(), x);
				}
			}
		}
		
		
	File outFile = initOutputFile(this.getView().getFormat());
		
		if (this.getView().getFormat()==FileFormat.PDF)
		{
		try {
			
			Document document = new Document(PageSize.LETTER_LANDSCAPE);
			PdfWriter pdfWriter = PdfWriter.getInstance(document,
					new FileOutputStream(outFile, true));
			document.open();
			PdfPTable table = new PdfPTable(5);
			formatDocument(document, pdfWriter.getDirectContent(), null , table);
			for (Product p : itemsRemoved.keySet()) {
				formatDocument(document, pdfWriter.getDirectContent(), p, 
						table);
			}
			document.add(table);
			document.close();
			displayDocument(outFile);
		} catch (FileNotFoundException | DocumentException e) {
			ExceptionHandler.TO_USER.reportException(e,
					"Can't print Removed Items Report");
			ExceptionHandler.TO_LOG.reportException(e,
					"Can't print Removed Items Report");
		}
		}
		else
		{
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(outFile));
				  bw.write("<html><head><title>Removed Items</title></head><body><h1>Removed Items</h1><br><table border=\"1\"><tr>"
				  		+ "<th>Description</th><th>Size</th><th>Product Barcode</th>"
				  		+ "<th>Removed</th><th>Current Supply</th></tr>");
				
					for (Product p : itemsRemoved.keySet()) {
							
						 bw.write("<tr><td>" + p.getDescription() + "</td><td>" + p.getSize().toString() +
								 "</td><td>" + p.getBarCode().toString() + "</td><td>" + Integer.toString(itemsRemoved.get(p)[0]) + 
								 "</td><td>" + Integer.toString(getInventoryManager().getItemCount(p)) +  "</td></tr>");
						 
					}
					 bw.write("</table></body></html>");
					 bw.close();
					 Desktop.getDesktop().browse(outFile.toURI());
				
			} catch (IOException e) {
				ExceptionHandler.TO_USER.reportException(e,
						"Can't print Removed Items Report");
				ExceptionHandler.TO_LOG.reportException(e,
						"Can't print Removed Items Report");
			}
		}
	}

    @Override
    protected FileFormat getReportFormat() {
        return this.getView().getFormat();
    }

    @Override
    protected Report getReport() {
        return null;
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
			outFile = new File(fileName + "reports/removedItemsReport/",
					dateString + "_" + "RemovedReport." + type.toString().toLowerCase());
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
			PdfContentByte contentByte,Product p, PdfPTable table) {
		if (p == null) {
			for (int j = 0; j < 5; j++) {
				table.addCell(REPORT_HEADINGS.get("h" + String.valueOf(j + 1)));
			}
		} else {
			for (int j = 1; j <= 5; j++) {
				switch (j) {
				case 1:
					table.addCell( p.getDescription());
					break;
				case 2:
					table.addCell( p.getSize().toString());
					break;
				case 3:
					table.addCell(p.getBarCode().toString());
					break;
				case 4:
					table.addCell(Integer.toString(itemsRemoved.get(p)[0]));
					break;
				case 5:
					table.addCell(Integer.toString(getInventoryManager().getItemCount(p)));
					break;
				}
			}
		}
	}

	private static final Map<String, String> REPORT_HEADINGS = new HashMap<>();
	static {
		REPORT_HEADINGS.put("h1", "Description");
		REPORT_HEADINGS.put("h2", "Size");
		REPORT_HEADINGS.put("h3", "Product Barcode");
		REPORT_HEADINGS.put("h4", "Removed");
		REPORT_HEADINGS.put("h5", "Current Supply");
		
	}

}

