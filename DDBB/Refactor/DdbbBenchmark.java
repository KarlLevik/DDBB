import java.util.*;
import java.io.*;

public class DdbbBenchmark {

	public Hashtable<String, String> cfg = new Hashtable<String, String>();
	public Hashtable<String, DdbbTest> tests = new Hashtable<String, DdbbTest>();
	//public List<DdbbTest> tests = ArrayList<DdbbTest>();

	DdbbBenchmark() throws Exception {
		BufferedReader cfg_reader = new BufferedReader(new FileReader("config.txt"));

		String key = cfg_reader.readLine();
		String value = cfg_reader.readLine();
		String test_file;
		String test_name;

		// Loads all of the settings into the benchmark's cfg
		while(key != null && value != null && key.equals("=====") == false){
			cfg.put(key, value);
			key = cfg_reader.readLine();
			value = cfg_reader.readLine();
		}

		// Loads all of the test_files into the tests hashtable
		test_name = value;
		test_file = cfg_reader.readLine();
		while(test_name != null && test_file != null && test_name.equals("-----") == false){
			cfg.put(test_name, test_file);
			tests.put(test_name, new DdbbTest(test_file));
			test_name = cfg_reader.readLine();
			test_file = cfg_reader.readLine();
		}
		
	}

	// Method that executes all of the tests in a benchmark
	public List<DdbbReport> run(){

		List<DdbbReport> test_reports = new ArrayList<DdbbReport>();

		for(String test : this.tests.keySet()) {

			test_reports.add((this.tests.get(test)).run(cfg.get("db_type")));

		}

		return test_reports;

	}

}