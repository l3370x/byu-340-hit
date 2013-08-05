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
				INSTANCE = SerializationPersistence.Factory.getPersistenceManager();
				break;
			case sqlite:
				INSTANCE = SqlitePersistence.Factory.getPersistenceManager();
			}
		}
	}

	public static class Factory {
		public static Persistence getPersistenceManager() {
			return INSTANCE;
		}
	}

}
