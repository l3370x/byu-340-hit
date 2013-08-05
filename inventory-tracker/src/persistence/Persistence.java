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
	
	
}
