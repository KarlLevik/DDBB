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
		
		Writer writer = new FileWriter(filename);
		Gson g = new GsonBuilder().setPrettyPrinting().create();
		
		g.toJson(bench, writer);
		
		writer.close();

	}
	/*
	public static void main(String[] args) {
		try {
			Hashtable<String, Hashtable<String, ArrayList<String>>> testa = 
				new Hashtable<String, Hashtable<String, ArrayList<String>>>();
			Hashtable<String, ArrayList<String>> testb = new Hashtable<String, ArrayList<String>>();
			ArrayList<String> arra = new ArrayList<String>();
			arra.add("10");
			arra.add("20");
			testb.put("type", arra);
			testa.put("op", testb);

			DdbbReport rep = new DdbbReport();
			rep.report = testa;

			//filename = "test20.json";
			//System.out.println("IN = " + in(filename));
			out("test20.json", rep);
			System.out.println("OUT = " + testa);
		} catch(Exception e){
			System.out.println(e);
		}
	}
	*/
}