package gui.reports;

import java.util.ArrayList;
import java.util.List;

public interface IReport {

	void initialize();
	
	void appendTable(List<ArrayList<String>> data);

	void appendText(String s);

	void finalize();

}
