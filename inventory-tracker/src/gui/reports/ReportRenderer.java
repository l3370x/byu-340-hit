package gui.reports;

import gui.common.FileFormat;

import java.io.IOException;

/**
 * The {@code ReportRenderer} interface defines the contract for an object that can be used to
 * render a report to a particular file format.  A report renderer instance is acquired using the
 * included {@link ReportRenderer.Factory#getRendererFor(gui.common.FileFormat)} method.
 */
public interface ReportRenderer {
    /**
     * Initialize the report document with the given title text.  This method should be called 
     * before adding any content to the report document.
     *
     * @param filename the base filename (without extension) to which the report document should be
     *                 written
     * @param title the title of the report
     *
     * @throws IOException
     */
    void beginDocument(String filename, String title) throws IOException;

    /**
     * Finalize the document, or make it ready for publishing.
     *
     * @throws IOException
     */
    void endDocument() throws IOException;

    /**
     * Add the given text as a heading to the report document.
     * 
     * @param headingText the heading text
     *
     * @throws IOException
     */
    void addHeading(String headingText) throws IOException;

    /**
     * Add the given text (as-is) to the report document
     *
     * @param text the text to be added
     *
     * @throws IOException
     */
    void addText(String text) throws IOException;

    /**
     * Begin a table using each of the passed Strings as a header for a column.
     *
     * @param headers the column string(s) for the header row
     *
     * @throws IOException
     */
    void beginTable(String ... headers) throws IOException;

    /**
     * Add a row to the current table with the given values (one value per column).
     *
     * @param values the values to be used for the cells in the row (one per column)
     *
     * @throws IOException
     */
    void addTableRow(String ... values) throws IOException;

    /**
     * End the table rendering.
     *
     * @throws IOException
     */
    void endTable() throws IOException;

    /**
     * Display the rendered report to the user.
     */
    void displayReport();

    /**
     * The {@code ReportRenderer.Factory} class is used to get an instance of a report renderer
     * based on a given file format.
     */
    public static class Factory {
        /**
         * Get a {@link ReportRenderer} instance for the given file format.
         *
         * @param format the file format for which to get a report renderer
         *
         * @return a report renderer instance that handles the given file format
         */
        public static ReportRenderer getRendererFor(FileFormat format) {
            switch (format) {
                case HTML:
                    return new HTMLReportRenderer();

                case PDF:
                    return new PDFReportRenderer();
            }

            return null;
        }
    }
}
