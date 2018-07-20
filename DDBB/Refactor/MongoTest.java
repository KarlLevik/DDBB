import java.util.*;
import java.io.*;

public class MongoTest {

	public Integer cfg_test_amount = 0;
	public Hashtable<String, String> cfg = new Hashtable<String, String>();

	MongoTest(String filename) throws Exception {
		BufferedReader cfg_reader = new BufferedReader(new FileReader(filename + ".txt"));

		String key = cfg_reader.readLine();
		String value = cfg_reader.readLine();

		while(key != null && value != null){
			cfg.put(key, value);
			key = cfg_reader.readLine();
			value = cfg_reader.readLine();
		}

	}

}