package persistence;

import java.util.*;

/**
 * The {@code DataTransferObject} class is used to transfer data to and from the *DAO objects.  Data
 * values are stored as key-value pairs.
 *
 * @author Keith McQueen
 */
public class DataTransferObject {
    private Map<String, Object> valuesByKey = new HashMap<>();

    public DataTransferObject() {

    }

    public Object getValue(String key) {
        return this.valuesByKey.get(key);
    }

    public void setValue(String key, Object value) {
        this.valuesByKey.put(key, value);
    }

    public Iterable<String> getKeys() {
        return Collections.unmodifiableSet(new HashSet<>(this.valuesByKey.keySet()));
    }

    public Iterable<Object> getValues() {
        return Collections.unmodifiableCollection(new ArrayList<>(this.valuesByKey.values()));
    }
}
