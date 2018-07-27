import java.util.*;
import java.io.*;

public class DdbbBenchmark {

	public Hashtable<String, String> cfg = new Hashtable<String, String>();
	public Hashtable<String, DdbbTest> tests = new Hashtable<String, DdbbTest>();
	public List<DdbbReport> test_reports = new ArrayList<DdbbReport>();
	//public List<DdbbTest> tests = ArrayList<DdbbTest>();

	DdbbBenchmark() throws Exception {
		System.out.println("0");
		cfg = DdbbIO.in("config.json");
		System.out.println("1");
		Boolean testsRemain = true;
		System.out.println("2");
		Integer testNumber = 0;
		System.out.println("3");
		
		while(testsRemain){
			System.out.println("4");

			if(cfg.containsKey("test" + testNumber)){
			
				tests.put("test" + testNumber, new DdbbTest (cfg.get("test" + testNumber)));
				testNumber++;
			
			} else {
			
				testsRemain = false;
			
			}
		}
		
	}

	// Method that executes all of the tests in a benchmark
	public void run(){

		for(String test : this.tests.keySet()) {

			test_reports.add((this.tests.get(test)).run(cfg.get("db_type")));

		}

	}

}