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

	public static void out(String filename, Hashtable<String,String> settings) throws Exception {
		
		Writer writer = new FileWriter(filename);
		Gson g = new GsonBuilder().create();
		
		g.toJson(settings, writer);
		
		writer.close();

	}

}