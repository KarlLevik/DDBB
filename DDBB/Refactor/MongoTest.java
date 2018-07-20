import java.util.*;
import java.io.*;

public class MongoTest {

	public Hashtable<String, String> cfg = new Hashtable<String, String>();
	public List<String> generated = new ArrayList<String>();
	public MongoReport report = new MongoReport();

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

	public MongoReport run(String uId){
		Long start_time = System.currentTimeMillis();

		// generate(uId);

		create();
		read();
		update();
		delete();

		return report;
	}

	private void generate(String uId){
		Long start_time = System.currentTimeMillis();
		Integer record_number = 0;

		if(cfg.containsKey("create_in_file") && cfg.get("create_in_file") == "1"){

			/*
			new File("RCRD_" + uId + ".txt").createNewFile();
			FileChannel.open(Paths.get("RCRD_" + uId + ".txt)", StandardOpenOption.WRITE).truncate(0).close();
			BufferedWriter writer = new BufferedWriter(new FileWriter(records_name + ".txt", true));

			record_number = 0;
			while(record_number != cfg_test_amount){
				writer.write(generateRandomString(Integer.parseInt((cfg_record_size).get(test_number)), false));
				writer.newLine();
				record_number++;
			}

			writer.close();
			*/

		} else {

			while(record_number != Integer.parseInt(cfg.get("create_record_amount"))){

				generated.add(DDBBTool.generateRandomString(Integer.parseInt(cfg.get("create_record_size")), false));
				record_number++;

			}

		}

		System.out.println("Generated " + record_number + " records.");

	}

	private void create(){
		Long start_time = System.currentTimeMillis();
		Integer record_number = 0;

		while(record_number != Integer.parseInt(cfg.get("create_record_amount"))){

		}

	}

	private void read(){
		Long start_time = System.currentTimeMillis();

	}

	private void update(){
		Long start_time = System.currentTimeMillis();

	}

	private void delete(){
		Long start_time = System.currentTimeMillis();

	}

}