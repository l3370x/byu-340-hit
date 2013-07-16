package core.model;

import core.model.exception.HITException;
import core.model.exception.HITException.Severity;
import java.util.*;
import java.io.*;

/**
 * The {@code PersistenceManager} will be used to handle loading and saving
 * the data that the program will be using.
 * 
 * @author aaron
 *
 */
public enum PersistenceManager {
	INSTANCE;

	/**
	 * Save the data that the program uses.  If there is no data to save, do
	 * nothing.  This should be called when the program quits to save the data
	 * for the next time the program is opened.
	 * 
	 * @post the data has been saved in a format and location that can be easily
	 * read in by the load method at a later time.
	 * 
	 * @throws HITException if saving the data failed for some reason.
	 */
	public void save() throws HITException {
		Iterator<StorageUnit> storageUnits = InventoryManager.Factory.getInventoryManager().getContents().iterator();

		try {
			FileOutputStream fileOut = new FileOutputStream("save.data");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			while ( storageUnits.hasNext() ) {
				out.writeObject(storageUnits.next());
			}
			
			//TODO save removed items
			
			out.close();
			fileOut.close();
		} catch(Exception e) {
			throw new HITException(Severity.WARNING,e.getMessage());
		}


	}

	/**
	 * Load the data that the program will use.  If there is no data that was 
	 * read in, do nothing.  This should only be called at the start of the 
	 * program to load in anything that was previously saved.
	 * 
	 * @post the data has been read and has populated the necessary objects the
	 * program will use.
	 *  
	 * @throws HITException if there was an error in reading the data.
	 */
	public void load() throws HITException {
		InventoryManager i =  InventoryManager.Factory.getInventoryManager();
		ArrayList<StorageUnit> storageUnits = new ArrayList<StorageUnit>();
		try {
			FileInputStream fileIn = new FileInputStream("save.data");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			try {
				while(true) {
					storageUnits.add((StorageUnit) in.readObject());
				}
			} catch (OptionalDataException e) {
		        if (!e.eof) throw e;
		    } finally {
		    	// System.out.println(storageUnits.size());
		        in.close();
		    }
			// System.out.println(storageUnits.size());
			in.close();
			fileIn.close();
		} catch(Exception e) {
			if(!e.getClass().equals(EOFException.class)){
				throw new HITException(Severity.WARNING,e.getMessage());
			}
		}
		Iterator<StorageUnit> storageUnitIterator = storageUnits.iterator();
		while(storageUnitIterator.hasNext()){
			i.add(storageUnitIterator.next());
		}
	}

	/**
	 * Update the saved data to reflect any changes.
	 * 
	 * @throws HITException if there was an error performing the update.
	 */
	public void update() throws HITException {
		
	}

}
