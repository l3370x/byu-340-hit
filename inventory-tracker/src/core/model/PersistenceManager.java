/**
 * 
 */
package core.model;

import core.model.exception.HITException;
import core.model.StorageUnitManager;
import core.model.ProductManager;
import core.model.ItemManager;


/**
 * The {@code PersistenceManager} will be used to handle loading and saving
 * the data that the program will be using.
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
		
	}
	
	/**
	 * Update the saved data to reflect any changes.
	 * 
	 * @throws HITException if there was an error performing the update.
	 */
	public void update() throws HITException {
		
	}
	
}
