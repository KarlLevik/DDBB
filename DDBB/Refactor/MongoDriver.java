import java.util.*;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime; 
import java.text.SimpleDateFormat;   

public class MongoDriver {

	// Unique ID generated for each session of MongoDriver
	public static String uId = (new SimpleDateFormat("dd-MM-yyyy_HHmmss").format(new Date())) + "_" + 
		DDBBTool.generateRandomString(5, true);

	public static void main(String args[]) {
		
		try {

			// Creates a new benchmark (each MongoBenchmark consistents of MongoTests)
			MongoBenchmark bench = new MongoBenchmark();
			// Gets the set of test names
			Set<String> tests = bench.tests.keySet();

			// Executes each test in order
			for(String test : tests){
				(bench.tests.get(test)).run(uId);
			
			}

		} catch(Exception e) {

			System.out.println(e);
		
		}

	}

}