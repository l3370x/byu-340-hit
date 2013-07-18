package core.model;

import core.model.exception.HITException;

import java.io.Serializable;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * The {@code BarCode} class provides a unique identifier for products (assigned
 * by the manufacturer) and items (generated internally) in the system.
 * 
 * @author Andrew
 */
public class BarCode implements Serializable {
    private static final Random random = new Random(System.currentTimeMillis());

    /**
     * Generate a new {@code BarCode} for an {@link Item} in the system.
     * 
     * @pre none
     * 
     * @return a new bar code
     * 
     * @throws HITException if the bar code could not be generated for any 
     * reason
     */
    public static BarCode generateItemBarCode() {
        // bar code starts with "4" (reserved for local use)
        String prefix = "4";
        
        // the unique identifier must be 10 digits
        int val = random.nextInt(Integer.MAX_VALUE);                
        String codeValue = prefix + String.format("%010d", val);

        // compute the checksum digit
        int checkSum = computeCheckSum(codeValue);
        codeValue += checkSum;
        
        return new BarCode(codeValue);
    }
    
    /**
     * Create a new {@code BarCode} instance with the given code value.
     * 
     * @param value the bar code value
     * 
     * @return a new bar code
     * 
     * @throws HITException if the bar code could not be created for any reason
     */
    public static BarCode getBarCodeFor(String value) throws HITException {
        checkBarCodeValue(value);
        
        return new BarCode(value);
    }

    private static int computeCheckSum(String codeValue) throws NumberFormatException {
        int odds = 0;
        int evens = 0;
        
        for (int i = 0; i < codeValue.length(); i++) {
            if (i % 2 == 0) {
                evens += Integer.valueOf(codeValue.substring(i, i+1));
            } else {
                odds += Integer.valueOf(codeValue.substring(i, i + 1));
            }
        }
        
        int total = evens + (3*odds);
        int mod10 = total % 10;
        
        int checkSum;
        if (mod10 == 0) {
            checkSum = 0;
        } else {
            checkSum = 10 - mod10;
        }
        
        return checkSum;
    }
    
    private static final Pattern BAR_CODE_PATTERN = Pattern.compile("\\d{12}");
    
    private static void checkBarCodeValue(String value) throws HITException {
        /*
        // make sure the bar code is exactly 12 digits
        if (false == BAR_CODE_PATTERN.matcher(value).matches()) {
            throw new HITException(Severity.ERROR, 
                    "Invalid bar code value (" + value + ")");
        }
        
        // make sure the check digit is valid
        int checkSum = computeCheckSum(value.substring(0, value.length() - 1));
        String checkDigit = value.substring(value.length() - 1);
        if (false == String.valueOf(checkSum).equals(checkDigit)) {
            throw new HITException(Severity.ERROR, "Bar code check sum error");
        }
        */
    }
    
    private final String value;
    
    /**
     * Create a new bar code with the given value.
     * 
     * @pre value != null
     * 
     * @post BarCode.value == value
     * 
     * @param value the value of the bar code
     * 
     * @throws HITException if the bar code could not be created for any reason.
     */
    private BarCode(String value) {
        this.value = value;
    }

    /**
     * Get the string value of the bar code.
     * 
     * @pre 
     * 
     * @post return != null
     * 
     * @return the bar code value
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Print this bar code to the given stream.
     * 
     * @pre null != stream
     * 
     * @post this bar code has been written to the given stream
     * 
     * @param stream the stream to which to write this bar code
     * 
     * @throws HITException if this bar code could not be written to the given
     * stream for any reason.
     */
    public void print(OutputStream stream) throws HITException {
        try {
            stream.write(this.value.getBytes());
            stream.flush();
        } catch (IOException ex) {
            throw new HITException(ex);
        }
    }
    
    /*
     * generated by NetBeans
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.value);
        return hash;
    }

    /*
     * generated by NetBeans
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BarCode other = (BarCode) obj;
        if (!Objects.equals(this.value, other.value)) {
            return false;
        }
        return true;
    }
    
}
