import java.util.*;
import java.io.*;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime; 
import java.text.SimpleDateFormat;   

public class DdbbDriver {

	public static void main(String args[]) {
		Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
		Thread[] threadArray = threadSet.toArray(new Thread[threadSet.size()]);
		System.out.println("Threads BEFORE TESTS = " + threadSet.size() + " = " + threadArray);
		int i = 0;
		for(Thread t : threadArray){
			System.out.println("t" + i + " = " + threadArray[i].getName());
			i++;
		}
		
		// Unique ID generated for each session of DdbbDriver
		String uId = (new SimpleDateFormat("dd-MM-yyyy_HHmmss").format(new Date())) + "_" + 
			DdbbTool.generateRandomString(5, true);

		Hashtable<String, DdbbReport> test_reports = new Hashtable<String, DdbbReport>();
		
		try {
			System.out.println("5");
			// Creates a new benchmark (each DdbbBenchmark consistents of DdbbTests)
			DdbbBenchmark bench = new DdbbBenchmark();
			bench.run();
			threadSet = Thread.getAllStackTraces().keySet();
			threadArray = threadSet.toArray(new Thread[threadSet.size()]);
			System.out.println("Threads AFTER TESTS = " + threadSet.size() + " = " + threadArray);
			i = 0;
			for(Thread t : threadArray){
				System.out.println("t" + i + " = " + threadArray[i].getName());
				i++;
			}
			System.out.println("6");
			//Create output file
			// Puts the cfg into the output file
			DdbbIO.out("REPORT_" + uId, bench);
			System.out.println("7");

		} catch(Exception e) {

			System.out.println(e);
		
		}

	}

}