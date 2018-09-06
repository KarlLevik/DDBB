import java.util.ArrayList;
import java.util.Hashtable;

public class DdbbReport {

	// Variable holding the report of a test
	// (operation, [type, [results]])
	public Hashtable<String, ArrayList<String>> report = new Hashtable<>();

	DdbbReport(){ }

	// Saves results in the report
	public void save(String op, String result){

		if(report.containsKey(op)) {

            report.get(op).add(result);

		} else {

			report.put(op, new ArrayList<>());
            report.get(op).add(result);

		}
	
	}

}