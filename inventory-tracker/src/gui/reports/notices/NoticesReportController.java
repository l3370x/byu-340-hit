package gui.reports.notices;

import java.util.ArrayList;
import java.util.List;

import core.model.exception.ExceptionHandler;
import core.model.exception.HITException;
import core.model.exception.HITException.Severity;
import gui.common.*;
import gui.reports.HTMLNoticesReport;
import gui.reports.HTMLProductStatisticsReport;
import gui.reports.PDFNoticesReport;
import gui.reports.PDFProductStatisticsReport;
import gui.reports.ReportController;

/**
 * Controller class for the notices report view.
 */
public class NoticesReportController extends Controller implements
		INoticesReportController {

	/**
	 * Constructor.
	 * 
	 * @param view Reference to the notices report view
	 */	
	public NoticesReportController(IView view) {
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
	protected INoticesReportView getView() {
		return (INoticesReportView)super.getView();
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
	 * notices report view is changed by the user.
	 */
	@Override
	public void valuesChanged() {
		
		List<ArrayList<String>> data = createData();

		ReportController report = null;

		FileFormat f = this.getView().getFormat();
		String title = String.format("Notices Report");
		String name = "NoticesReport";

		if (f.equals(FileFormat.PDF))
			report = new PDFNoticesReport(name, title);
		else if (f.equals(FileFormat.HTML))
			report = new HTMLNoticesReport(name, title);
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
	
	private List<ArrayList<String>> createData() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * This method is called when the user clicks the "OK"
	 * button in the notices report view.
	 */
	@Override
	public void display() {
	}
	
	private boolean isValidInput() {
		if(this.getView().getFormat()==null)
			return false;
		return true;
	}

}

