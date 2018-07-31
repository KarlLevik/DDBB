import com.google.gson.*;
import java.util.*;
import java.io.*;

public class DdbbIO {

	public static Hashtable<String,String> in(String filename) throws Exception {
		
		Hashtable<String,String> settings = new Hashtable<String,String>();
		
		Gson g = new Gson();
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		
		settings = g.fromJson(reader, Hashtable.class);
		
		reader.close();
		
		return settings;

	}

	public static void out(String filename, DdbbBenchmark bench) throws Exception {
		
		System.out.println("8");
		Writer writer = new FileWriter(filename);
		Gson g = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(DdbbBenchmark.class, new DdbbBenchmarkAdapter()).create();

		System.out.println("9");
		g.toJson(bench, writer);
		System.out.println("10");
		
		writer.close();

	}
	
	public static void main(String[] args) {
		try {
			Hashtable<String, Hashtable<String, ArrayList<String>>> testa = 
				new Hashtable<String, Hashtable<String, ArrayList<String>>>();
			Hashtable<String, ArrayList<String>> testb = new Hashtable<String, ArrayList<String>>();
			ArrayList<String> arra = new ArrayList<String>();
			Hashtable<String, String> cfg = new Hashtable<String, String>();
			Hashtable<String, DdbbTest> tests = new Hashtable<String, DdbbTest>();
			List<DdbbReport> test_reports = new ArrayList<DdbbReport>();
			arra.add("10");
			arra.add("20");
			testb.put("type", arra);
			testa.put("op", testb);

			DdbbReport rep = new DdbbReport();
			DdbbBenchmark ben = new DdbbBenchmark();
			rep.report = testa;
			test_reports.add(rep);
			cfg.put("22", "33443");
			cfg.put("aa22", "3aaa3443");
			DdbbTest t = new DdbbTest("test_config1.json");
			tests.put("test1", t);
			ben.test_reports = test_reports;
			ben.cfg = cfg;
			ben.tests = tests;

			String filename = "test_config2.json";
			System.out.println("IN = " + in(filename));
			out("test_output.json", ben);
			System.out.println("OUT");
		} catch(Exception e){
			System.out.println(e);
		}
	}

}