import java.util.*;
import java.io.*;

public class DdbbBenchmark {

	public Hashtable<String, String> cfg = new Hashtable<String, String>();
	public Hashtable<String, DdbbTest> tests = new Hashtable<String, DdbbTest>();
	public List<DdbbReport> test_reports = new ArrayList<DdbbReport>();
	//public List<DdbbTest> tests = ArrayList<DdbbTest>();

	DdbbBenchmark() throws Exception {

		cfg = DdbbIO.in("config.txt");
		Boolean testsRemain = true;
		Integer testNumber = 0;
		
		while(testsRemain){

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