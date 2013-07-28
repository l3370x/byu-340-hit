package gui.reports;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import core.model.exception.ExceptionHandler;

public class ReportController implements IReport{

	File file;
	FileOutputStream output;
	String filename;
	String title;

	private static final String BASE_FILE_NAME = System.getProperty("user.dir");

	public ReportController(String filename, String title) {
		this.filename = filename + getFileExtention();
		this.title = title;
		openFile();
	}

	/**
	 * open file on system
	 */
	private void openFile() {
		File outFile = null;
		try {

			String fileName = BASE_FILE_NAME.replace("build/", "");

			outFile = new File(fileName + "/reports/" + filename);
			outFile.getParentFile().mkdirs();

			if (!outFile.exists()) {
				outFile.createNewFile();
			}
			this.file = outFile;
			output = new FileOutputStream(this.file, false);

		} catch (IOException e) {
			ExceptionHandler.TO_USER.reportException(e,
					"Can't create report file");
			ExceptionHandler.TO_LOG.reportException(e,
					"Can't create report file");
		}

	}

	public File getFile() {
		return this.file;
	}

	/**
	 * Bring the report to the user's screen
	 * 
	 * @param file
	 */
	public void displayReport() {
		try {
			Desktop.getDesktop().open(file);
		} catch (IOException e) {
			ExceptionHandler.TO_USER.reportException(e, "Can't print report");
			ExceptionHandler.TO_LOG.reportException(e, "Can't print report");
		}
	}

	/**
	 * Write file to disk
	 */
	private void writeFile() {
		// I guess this isn't neccessary.
	}

	/**
	 * Clean up any file stuff
	 */
	private void closeFile() {
		// also possibly not neccessary.
	}
	
	
	@Override
	public void appendTable(List<ArrayList<String>> data){
		
	}
	@Override
	public void appendText(String s){
		
	}
	@Override
	public void finalize() {
		finalizeReport();
	}
	@Override
	public void initialize(){
		
	}

	/**
	 * write to and close open file then display the file
	 */
	protected void finalizeReport() {
		writeFile();
		closeFile();
		displayReport();
	}

	public String getFileExtention() {
		return "";
	}

}
