/**
 * 
 */
package gui.reports;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author aaron
 * 
 */
public class HTMLProductStatisticsReport extends ReportController implements
		IReport {

	PrintWriter writer;

	public HTMLProductStatisticsReport(String filename, String title) {
		super(filename, title);
		initializeFile();
	}

	private void initializeFile() {
		this.writer = new PrintWriter(output);
		addHTMLHeaders();
	}

	private void addHTMLHeaders() {
		writer.write("<html><head><title>");
		writer.write(title);
		writer.write("</title></head><body><h1>");
		writer.write(title);
		writer.write("</h1><br>");
	}

	private void addHTMLEnding() {
		writer.write("</body></html>");
	}

	private void appendRow(ArrayList<String> row) {
		writer.write("<tr>");
		for (String s : row) {
			writer.write("<td>");
			writer.write(s);
			writer.write("</td>");
		}
		writer.write("</tr>");
	}

	@Override
	public void appendTable(List<ArrayList<String>> data) {
		writer.write("<table border='1'>");
		for (ArrayList<String> row : data) {
			appendRow(row);
		}
		writer.write("</table>");
	}

	@Override
	public void appendText(String s) {
		writer.write(s);
	}

	@Override
	public void finalize() {
		addHTMLEnding();
		writer.close();
		finalizeReport();
	}

	@Override
	public String getFileExtention() {
		return ".html";
	}

}
