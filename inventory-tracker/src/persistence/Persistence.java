/**
 * 
 */
package persistence;

import java.util.Date;
import java.util.Observer;

import core.model.exception.HITException;

/**
 * @author Aaron
 *
 */
public interface Persistence extends Observer{
	void save() throws HITException;
	void load() throws HITException;
	void setLastReportRun(Date lastReportRun) throws HITException;
	Date getLastReportRun() throws HITException;
	
	
}
