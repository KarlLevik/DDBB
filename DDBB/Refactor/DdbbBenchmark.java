import java.util.*;
import java.io.*;

public class DdbbBenchmark {

	public Hashtable<String, String> cfg = new Hashtable<String, String>();
	public Hashtable<String, DdbbTest> tests = new Hashtable<String, DdbbTest>();
	public List<DdbbReport> test_reports = new ArrayList<DdbbReport>();

	DdbbBenchmark() {
		System.out.println("0");
		try {
			cfg = DdbbIO.in("config.json");
		} catch(Exception e){
			System.out.println(e);
		}
		System.out.println("1");
		Boolean testsRemain = true;
		System.out.println("2");
		Integer testNumber = 0;
		System.out.println("3");

		// Add each test and it's name into "tests"
		while(testsRemain){
			System.out.println("4");

			if(cfg.containsKey("test" + testNumber)){

				try {
					tests.put("test" + testNumber, new DdbbTest (cfg.get("test" + testNumber)));
				} catch(Exception e){
					System.out.println(e);
				}
				testNumber++;

			} else {

				testsRemain = false;

			}
		}

	}

	// Method that executes all of the tests in a benchmark
	public void run(){

		// Add each test to "test_reports"
		for(String test : this.tests.keySet()) {

			test_reports.add((this.tests.get(test)).run(cfg.get("db_type")));

		}

	}

}