package gui.reports;

import common.util.DateUtils;
import core.model.exception.ExceptionHandler;
import gui.common.FileFormat;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: kmcqueen
 * Date: 7/29/13
 * Time: 10:28 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractReport implements Report {
    private static final String BASE_FILE_NAME = System.getProperty("user.dir");

    private static void displayDocument(File file) {
        try {
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            ExceptionHandler.TO_USER.reportException(e,
                    "Can't open the file");
            ExceptionHandler.TO_LOG.reportException(e,
                    "Can't open the file");
        }
    }

    private static File initOutputFile(FileFormat type) {
        File outFile = null;
        try {
            Date date = DateUtils.removeTimeFromDate(DateUtils.currentDate());
            String dateString = DateUtils.formatDate(date);
            dateString = dateString.replace("/", "_");

            String fileName = BASE_FILE_NAME.replace("build/", "");
            outFile = new File(fileName + "reports/expiredItemsReport/",
                    dateString + "_" + "ExpiryReport." + type.toString().toLowerCase());
            outFile.getParentFile().mkdirs();

            if (!outFile.exists()) {
                outFile.createNewFile();
            }

        } catch (IOException e) {
            ExceptionHandler.TO_USER.reportException(e,
                    "Can't create file");
            ExceptionHandler.TO_LOG.reportException(e,
                    "Can't create output file");
        }

        return outFile;
    }
}
