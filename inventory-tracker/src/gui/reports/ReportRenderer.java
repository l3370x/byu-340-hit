package gui.reports;

import gui.common.FileFormat;

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
     * @param title the title of the report
     */
    void beginDocument(String title);

    /**
     * Finalize the document, or make it ready for publishing.
     */
    void endDocument();

    /**
     * Add the given text as a heading to the report document.
     * 
     * @param headingText the heading text
     */
    void addHeading(String headingText);

    /**
     * Begin a table using each of the passed Strings as a header for a column.
     *
     * @param headers the column string(s) for the header row
     */
    void beginTable(String ... headers);

    /**
     * Add a row to the current table with the given values (one value per column).
     *
     * @param values the values to be used for the cells in the row (one per column)
     */
    void addTableRow(String ... values);

    /**
     * End the table rendering.
     */
    void endTable();

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
                    // return new HTMLReportRenderer;
                    return null;

                case PDF:
                    // return new PDFReportRenderer
                    return null;
            }

            return null;
        }
    }
}
