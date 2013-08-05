package persistence;

import static core.model.InventoryManager.Factory.getInventoryManager;
import static core.model.exception.HITException.Severity.WARNING;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Observable;

import core.model.exception.HITException;

/**
 * The {@code SerializationPersistence} will be used to handle loading and
 * saving the data that the program will be using.
 * 
 * @author aaron
 */
public class SerializationPersistence implements Persistence {
	
	private SerializationPersistence() {
		
	}

	/**
	 * Save the data that the program uses. If there is no data to save, do
	 * nothing. This should be called when the program quits to save the data
	 * for the next time the program is opened.
	 * 
	 * @throws HITException
	 *             if saving the data failed for some reason.
	 * @post the data has been saved in a format and location that can be easily
	 *       read in by the load method at a later time.
	 */
	@Override
	public void save() throws HITException {
		try {
			FileOutputStream fileOut = new FileOutputStream("save.data");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(getInventoryManager());

			out.close();
			fileOut.close();
		} catch (Exception e) {
			throw new HITException(WARNING, e.getMessage());
		}

	}

	/**
	 * Load the data that the program will use. If there is no data that was
	 * read in, do nothing. This should only be called at the start of the
	 * program to load in anything that was previously saved.
	 * 
	 * @throws HITException
	 *             if there was an error in reading the data.
	 * @post the data has been read and has populated the necessary objects the
	 *       program will use.
	 */
	@Override
	public void load() throws HITException {
		try {
			FileInputStream fileIn = new FileInputStream("save.data");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			in.readObject();
			in.close();
			fileIn.close();
		} catch (EOFException eof) {
		} catch (Exception e) {
			if (e instanceof FileNotFoundException) {
				System.out.println("No previous save data found.");
			} else {
				if (e.getClass() == InvalidClassException.class) {
					System.out
							.println("Couldn't load old save file due to version mismatch.  Starting over.");
					return;
				}
				throw new HITException(WARNING, e.getMessage());
			}
		}
	}


	/**
	 * The {@code PersistenceManager.Factory} class is used to get the singleton
	 * instance of the {@link SerializationPersistence}.
	 * 
	 * @invariant ?
	 */
	public static class Factory {
		private static final SerializationPersistence INSTANCE = new SerializationPersistence();

		/**
		 * Get the {@link SerializationPersistence} instance.
		 * 
		 * @return the persistence manager
		 * @pre
		 * @post return != null
		 */
		public static SerializationPersistence getPersistenceManager() {
			return INSTANCE;
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
}
