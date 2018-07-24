import java.io.*;
import java.util.*;

public class MongoReport {

	// Variable holding the report of a test
	// (operation, [type, [results]])
	public Hashtable<String, Hashtable<String, ArrayList<String>>> report = 
		new Hashtable<String, Hashtable<String, ArrayList<String>>>();

	MongoReport(){ }

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

}