import java.util.*;
import java.io.*;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime; 
import java.text.SimpleDateFormat;   

public class DdbbDriver {

	public static void main(String args[]) {
		
		// Unique ID generated for each session of DdbbDriver
		String uId = (new SimpleDateFormat("dd-MM-yyyy_HHmmss").format(new Date())) + "_" + 
			DdbbTool.generateRandomString(5, true);

		Hashtable<String, DdbbReport> test_reports = new Hashtable<String, DdbbReport>();
		
		try {
			// Creates a new benchmark (each DdbbBenchmark consistents of DdbbTests)
			DdbbBenchmark bench = new DdbbBenchmark();
			bench.run();

			//Create output file
			// Puts the cfg into the output file
			DdbbIO.out("REPORT_" + uId, bench);

		} catch(Exception e) {

			System.out.println(e);
		
		}

	}

}