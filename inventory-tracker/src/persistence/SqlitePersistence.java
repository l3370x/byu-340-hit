/**
 * 
 */
package persistence;

import java.util.Observable;

import core.model.exception.HITException;

/**
 * @author Aaron
 *
 */
public class SqlitePersistence implements Persistence {

	/* (non-Javadoc)
	 * @see persistence.PersistenceManager#save()
	 */
	@Override
	public void save() throws HITException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see persistence.PersistenceManager#load()
	 */
	@Override
	public void load() throws HITException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see persistence.PersistenceManager#update()
	 */
	@Override
	public void update() throws HITException {
		// TODO Auto-generated method stub

	}
	
	
	public static class Factory {
        private static final SqlitePersistence INSTANCE =
                new SqlitePersistence();

        /**
         * Get the {@link SqlitePersistence} instance.
         *
         * @return the persistence manager
         * @pre
         * @post return != null
         */
        public static SqlitePersistence getPersistenceManager() {
            return INSTANCE;
        }
    }


	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

}
