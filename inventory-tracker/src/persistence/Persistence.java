/**
 * 
 */
package persistence;

import java.util.Observer;

import core.model.exception.HITException;

/**
 * @author Aaron
 *
 */
public interface Persistence extends Observer{
	void save() throws HITException;
	void load() throws HITException;
	void update() throws HITException;
	
	// add other methods here then implement them in
	// SerializationPersistence and SqlitePersistence
	
}
