package core.model;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created with IntelliJ IDEA. User: kmcqueen Date: 8/1/13 Time: 9:12 PM To change this template use
 * File | Settings | File Templates.
 */
public class UPCDatabaseDotOrgTests {
    @Test
    public void testGetProductInfo() throws IOException {
        //URL url = new URL("http://api.upcdatabase.org/xml/07a1f2e4a6d47b72564476ebe54502e9/0111222333446");
        URL url = new URL("http://api.upcdatabase.org/json/07a1f2e4a6d47b72564476ebe54502e9/123456789123");
        Object obj = url.getContent();

        if (obj instanceof InputStream) {
            BufferedReader reader = new BufferedReader(new InputStreamReader((InputStream) obj));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}
