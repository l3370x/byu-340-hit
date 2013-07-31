package gui.reports.removed;

import core.model.Product;
import gui.common.FileFormat;
import gui.common.IView;
import gui.reports.AbstractReportController;
import gui.reports.ExpiredReport;
import gui.reports.RemovedReport;
import gui.reports.Report;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static core.model.InventoryManager.Factory.getInventoryManager;


/**
 * Controller class for the removed items report view.
 */
public class RemovedReportController extends AbstractReportController implements
		IRemovedReportController {
	
	private static Map<Product, int[]> itemsRemoved = new HashMap<Product, int[]>();
	//private static final String BASE_FILE_NAME = System.getProperty("user.dir");

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
    protected FileFormat getReportFormat() {
        return this.getView().getFormat();
    }

    @Override
    protected Report getReport() {
    	
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
		
		RemovedReport report = new RemovedReport(date);
		return report;	
    	       
    }

}

