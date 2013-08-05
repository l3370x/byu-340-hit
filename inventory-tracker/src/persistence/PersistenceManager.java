/**
 * 
 */
package persistence;

/**
 * @author Aaron
 * 
 */
public class PersistenceManager {

	private static Persistence INSTANCE = null;

	private PersistenceManager() {

	}

	public static void setPersistence(PersistenceType type) {
		if (INSTANCE == null) {
			switch (type) {
			case serialization:
				INSTANCE = new SerializationPersistence();
				break;
			case sqlite:
				INSTANCE = new SqlitePersistence();
			}
		}
	}

	public static class Factory {
		public static Persistence getPersistenceManager() {
			return INSTANCE;
		}
	}

}
