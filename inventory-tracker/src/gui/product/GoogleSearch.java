/**
 * 
 */
package gui.product;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import core.model.exception.ExceptionHandler;

public class GoogleSearch implements ProductDetector {
	// https://www.googleapis.com/shopping/search/v1/public/products?
	// country=US&q=3000000060353&key=AIzaSyA3HGkxPt8OOHig-8LlSGCXsStC2H-HEC8
	// &maxResults=1&fields=totalItems,items

	private String baseURL = "https://www.googleapis.com/shopping/"
			+ "search/v1/public/products?country=US&q=";
	private String endURL = "&key=AIzaSyA3HGkxPt8OOHig-8LlSGCXsStC2H-HEC8"
			+ "&maxResults=1&fields=totalItems,items";

	@Override
	public ProductDescriptor getProductDescription(String barcode) {
		String description = "";
		if (barcode.matches("[0-9]+") && barcode.length() > 6) {

			try {
				URL url = new URL(baseURL + URLEncoder.encode(barcode, "UTF-8") + endURL);
				BufferedReader rdr = new BufferedReader(new InputStreamReader(url.openStream()));
				String line;

				while ((line = rdr.readLine()) != null) {
					// yes, this might be the lazy way
					if (line.contains("\"description\": ")) {
						description = line.substring(20, line.length() - 3);
					}
				}
				rdr.close();

			} catch (MalformedURLException e) {
				ExceptionHandler.TO_LOG.reportException(e, "Error passing url to www.google.com");
			} catch (IOException e) {
				ExceptionHandler.TO_LOG.reportException(e,
						"Error reading input stream from www.google.com");
			}
		}
		return new ProductDescriptor(barcode, description);
	}
}
