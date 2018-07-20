import java.util.*;
import java.io.*;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection; 
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;

import org.bson.Document;  

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

		// Creating a Mongo client 
		MongoClient mongo = new MongoClient(cfg.get("ip"), Integer.parseInt(cfg.get("port")));
			
		// Creating Credentials 
		MongoCredential credential;
		credential = MongoCredential.createCredential(cfg.get("user"), cfg.get("db_name"), cfg.get("pwd").toCharArray());
		System.out.println("Connected to the database successfully");
		
		// Accessing the database 
		MongoDatabase database = mongo.getDatabase(cfg.get("db_name"));
		System.out.println("Credentials ::"+ credential);

		// Retrieving a collection
		MongoCollection<Document> collection = database.getCollection(cfg.get("collection")); 
		System.out.println("Collection " + cfg.get("collection") + " selected successfully");

		// generate(collection, uId);
		try {
		create(collection);
		read(collection);
		update(collection);
		delete(collection);
		} catch(Exception e){
			System.out.println(e);
		}

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

	private void create(MongoCollection<Document> collection) throws Exception {
		Long start_time = System.currentTimeMillis();
		Integer record_number = 0;

		while(record_number != Integer.parseInt(cfg.get("create_record_amount"))){
			Document document = new Document("", 
			DDBBTool.generateRandomString(Integer.parseInt(cfg.get("create_record_size")), false));
			collection.insertOne(document);
			record_number++;

			/*
			if(record_number % cfg_rcrd_interval.get(test_number) == 0 || record_number == cfg_record_amount.get(test_number)){
				testRecord(runtime(), interval_results);
				interval_results = new ArrayList<Integer>();
			}
			*/
		}

		System.out.println("Generated " + record_number + " records.");

	}

	private void read(MongoCollection<Document> collection) throws Exception {
		Long start_time = System.currentTimeMillis();

	}

	private void update(MongoCollection<Document> collection) throws Exception {
		Long start_time = System.currentTimeMillis();

	}

	private void delete(MongoCollection<Document> collection) throws Exception {
		Long start_time = System.currentTimeMillis();

	}

}