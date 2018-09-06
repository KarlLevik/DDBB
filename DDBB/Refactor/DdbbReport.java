import java.util.ArrayList;
import java.util.Hashtable;

public class DdbbReport {

	// Variable holding the report of a test
	// (operation, [type, [results]])
	public Hashtable<String, ArrayList<long[]>> report = new Hashtable<>();

	DdbbReport(){ }

	// Saves results in the report
	public void save(String op, long[] result){

		if(report.containsKey(op)) {

			report.get(op).add(result);

		} else {

			report.put(op, new ArrayList<>());
			report.get(op).add(result);

		}

	}

	// Saves results in the report
	public void saveList(String op, ArrayList<long[]> result){

		if(report.containsKey(op)) {

			for(long[] res : result){
				report.get(op).add(res);
			}

		} else {

			report.put(op, new ArrayList<>());
			saveList(op, result);

		}

	}

}