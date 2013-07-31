package gui.reports.expired;


import gui.common.FileFormat;
import gui.common.IView;
import gui.reports.AbstractReportController;
import gui.reports.Report;

/**
 * Controller class for the expired items report view.
 */
public class ExpiredReportController extends AbstractReportController implements
										IExpiredReportController {


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

  

    @Override
    protected FileFormat getReportFormat() {
        return this.getView().getFormat();
    }

    @Override
    protected Report getReport() {
    	ExpiredReport report = new ExpiredReport();
    	return report;	
    }

  

}

