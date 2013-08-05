package gui.product;

import core.model.exception.ExceptionHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DefaultHandler2;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.MessageFormat;

/**
 * The {@code UPCDatabaseDotOrg} class is a {@link ProductDetector} implementation that issues a
 * request to www.upcdatabase.org to find the product details for a particular barcode.
 *
 * @author Eric Hullinger
 */
public class SearchUPCDatabase extends DefaultHandler2 implements ProductDetector {
    private static final String URL_FORMAT = "http://www.searchupc.com/handlers/upcsearch.ashx?" +
    										 "request_type=1&access_token={0}&upc={1}";
    
    private static final String SEARCH_UPC_KEY = "6FB556B6-0D02-4661-AE16-BADCA230E1CB";

    private String barcode;
    private String description = NOT_FOUND;
    private String csvSplitBy = ",";

    @Override
    public ProductDescriptor getProductDescription(String barcode) {
        InputStream inputStream = null;

        try {
            // create the URL
            URL url = new URL(MessageFormat.format(URL_FORMAT, SEARCH_UPC_KEY, barcode));

            // open a connection to the URL
            URLConnection urlConn = url.openConnection();
            InputStreamReader inStream = new InputStreamReader(urlConn.getInputStream());
            BufferedReader buff= new BufferedReader(inStream);
            String titles = buff.readLine();
            String line = buff.readLine();
            String[] words = line.split(csvSplitBy);
    
            this.barcode = barcode;
            this.description = words[0].substring(1);
        
        } catch (Exception e) {
            ExceptionHandler.TO_LOG.reportException(e,
                    "Unable to get product description from www.searchupc.com");
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    ExceptionHandler.TO_LOG.reportException(e,
                            "Error closing input stream to www.searchupc.com");
                }
            }
        }

        return new ProductDescriptor(this.barcode, this.description);
    }

   
}
