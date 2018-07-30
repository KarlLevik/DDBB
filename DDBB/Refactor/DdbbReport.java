import java.io.*;
import java.util.*;

public class DdbbReport {

	// Variable holding the report of a test
	// (operation, [type, [results]])
	public Hashtable<String, Hashtable<String, ArrayList<String>>> report = 
		new Hashtable<String, Hashtable<String, ArrayList<String>>>();

	DdbbReport(){ }

	// Saves results in the report
	public void save(String op, String type, String result){

		if(report.containsKey(op)) {
			
			if((report.get(op)).containsKey(type)) {
				
				((report.get(op)).get(type)).add(result);

			} else {

				(report.get(op)).put(type, (new ArrayList<String>()));
				save(op, type, result);
			
			}

		} else {

			report.put(op, new Hashtable<String, ArrayList<String>>());
			save(op, type, result);

		}
	
	}

	// Saves results in the report
	public void saveList(String op, String type, List<String> results){

		if(report.containsKey(op)) {

			if((report.get(op)).containsKey(type)) {

				for(Object result : results) {
					((report.get(op)).get(type)).add(result.toString());
				}

			} else {

				(report.get(op)).put(type, (new ArrayList<String>()));
				saveList(op, type, results);

			}

		} else {

			report.put(op, new Hashtable<String, ArrayList<String>>());
			saveList(op, type, results);

		}

	}

}