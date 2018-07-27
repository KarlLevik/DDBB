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
		/*

		DdbbReport report = new DdbbReport();
		
		GsonBuilder gb = new GsonBuilder();
		gb.registerTypeAdapter(DdbbReport.class, new DdbbReportAdapter());
		gb.setPrettyPrinting();
		Gson g = gb.create();
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		
		report = g.fromJson(reader, DdbbReport.class);
		
		reader.close();
		return report;

		*/
		/*
		Hashtable<String,String> settings = new Hashtable<String,String>();
		
		Gson g = new Gson();
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		
		settings = g.fromJson(reader, Hashtable.class);
		
		reader.close();
		
		return settings;
		*/

	}

	public static void out(String filename, DdbbBenchmark bench) throws Exception {
		
		System.out.println("8");
		Writer writer = new FileWriter(filename);
		Gson g = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(DdbbReport.class, new DdbbReportAdapter()).create();

		System.out.println("9");
		/*
		System.out.println("cfg = " + bench.cfg);
		System.out.println("tests = " + bench.tests);
		System.out.println("test1 cfg = " + bench.tests.get("test1").cfg);
		System.out.println("test1 gen = " + bench.tests.get("test1").generated);
		System.out.println("test1 uId = " + bench.tests.get("test1").uId);
		System.out.println("test1 r.r = " + bench.tests.get("test1").report.report);
		System.out.println("test_reports" + bench.test_reports);
		System.out.println("test_reports1 = " + bench.test_reports.get(1).report);
		*/
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