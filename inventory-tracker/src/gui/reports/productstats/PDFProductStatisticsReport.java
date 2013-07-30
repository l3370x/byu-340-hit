/**
 * 
 */
package gui.reports.productstats;

import gui.reports.PDFReport;

/**
 * @author aaron
 * 
 */
public class PDFProductStatisticsReport extends PDFReport {

	public PDFProductStatisticsReport(String filename, String title) {
		super(filename, title);
		super.setLandscape(true);
	}
}
