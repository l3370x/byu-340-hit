package gui.reports.supply;

import core.model.InventoryManager;
import core.model.Item;
import core.model.Product;
import core.model.exception.*;
import core.model.exception.HITException.Severity;
import gui.common.FileFormat;
import gui.common.IView;
import gui.reports.AbstractReportController;
import gui.reports.HTMLReport;
import gui.reports.PDFReport;
import gui.reports.Report;
import gui.reports.SupplyReport;

import java.util.*;

import static core.model.InventoryManager.Factory.getInventoryManager;

/**
 * Controller class for the N-month supply report view.
 */
	public class SupplyReportController extends AbstractReportController implements
		ISupplyReportController {
		
		private String name = "MonthSupply";

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
    
    @Override
    protected FileFormat getReportFormat() {
        return this.getView().getFormat();
    }

    @Override
    protected Report getReport() { 
    	SupplyReport report = new SupplyReport();
    	int nMonths = Integer.parseInt(this.getView().getMonths());
    	String title = String.format("%d Month Supply Report",
				nMonths);
    	report.setTitle(title);
    	report.setMonths(nMonths);
    	return report;	
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
	
}
