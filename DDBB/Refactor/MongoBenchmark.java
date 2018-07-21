import java.util.*;
import java.io.*;

public class MongoBenchmark {

	public Hashtable<String, String> cfg = new Hashtable<String, String>();
	public Hashtable<String, MongoTest> tests = new Hashtable<String, MongoTest>();
	//public List<MongoTest> tests = ArrayList<MongoTest>();

	MongoBenchmark() throws Exception {
		BufferedReader cfg_reader = new BufferedReader(new FileReader("config.txt"));

		String key = cfg_reader.readLine();
		String value = cfg_reader.readLine();
		String test_file;
		String test_name;

		while(key != null && value != null && key.equals("=====") == false){
			cfg.put(key, value);
			System.out.println("key : " + key);
			System.out.println("value : " + value);
			key = cfg_reader.readLine();
			value = cfg_reader.readLine();
		}

		test_name = value;
		test_file = cfg_reader.readLine();
		while(test_name != null && test_file != null && test_name.equals("-----") == false){
			System.out.println("test_name : " + test_name);
			System.out.println("test_file : " + test_file);
			cfg.put(test_name, cfg_reader.readLine());
			tests.put(test_name, new MongoTest(test_file));
			test_name = cfg_reader.readLine();
			test_file = cfg_reader.readLine();
			System.out.println("test_name1 : " + test_name);
			System.out.println("test_file1 : " + test_file);
		}

		System.out.println("a)");
		
	}
}