package gui.reports;

import core.model.exception.ExceptionHandler;

import java.awt.*;
import java.io.*;

/**
 * The {@code AbstractReportRenderer} implements functionality common to all report renderer
 * implementations.
 */
abstract class AbstractReportRenderer implements ReportRenderer {
    private static final String BASE_FILE_NAME = System.getProperty("user.dir");
    private File outFile;

    protected AbstractReportRenderer() {
    }

    /**
     * Open an {@link OutputStream} to which the report document will be written.
     *
     * @param filename the base name (without extension) of the file to which the report document
     *                 will be written
     * @return the output stream to which the report document will be written
     *
     * @throws IOException if the output stream could not be opened for any reason
     */
    protected OutputStream openOutputStream(String filename) throws IOException {
        String path = BASE_FILE_NAME.replace("build/", "");

        this.outFile = new File(path + "/reports/" + filename + "." + this.getFileExtension());
        this.outFile.getParentFile().mkdirs();

        if (!this.outFile.exists()) {
            this.outFile.createNewFile();
        }

        return new FileOutputStream(this.outFile, false);
    }

    public void displayReport() {
        try {
            Desktop.getDesktop().open(this.outFile);
        } catch (IOException e) {
            ExceptionHandler.TO_USER.reportException(e, "Unable To Display Report");
            ExceptionHandler.TO_LOG.reportException(e, "Unable To Display Report");
        }
    }

    /**
     * Get the file extension used for this renderer.
     *
     * @return the file extension used by this renderer
     */
    protected abstract String getFileExtension();

}
