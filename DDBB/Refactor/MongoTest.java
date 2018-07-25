import java.util.*;
import java.io.*;
import java.nio.*;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime; 
import java.text.SimpleDateFormat; 

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection; 
import com.mongodb.client.model.*; 
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;

import org.bson.Document;  

public class MongoTest {

	public Hashtable<String, String> cfg = new Hashtable<String, String>();
	public List<String> generated = new ArrayList<String>();
	public MongoReport report = new MongoReport();
	public String uId = (new SimpleDateFormat("dd-MM-yyyy_HHmmss").format(new Date())) + "_" + 
			DDBBTool.generateRandomString(5, true);

	MongoTest(String filename) throws Exception {
		// Opens th cfg file for the test
		BufferedReader cfg_reader = new BufferedReader(new FileReader(filename + ".txt"));

		// Reads a key and value from the cfg
		String key = cfg_reader.readLine();
		String value = cfg_reader.readLine();

		// Loads all of the configuration into the cfg for the test
		while(key != null && value != null){
			cfg.put(key, value);
			key = cfg_reader.readLine();
			value = cfg_reader.readLine();
		}

	}

	// Method to run the test
	public MongoReport run(){
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

		// Carries out the creation, reading, updating or deleletion of records
		try {
			generate();
			create(collection);
			read(collection);
			update(collection);
			delete(collection);
		} catch(Exception e){
			System.out.println(e);
		}

		return report;
	}

	private void generate() throws Exception {
		Long start_time = System.currentTimeMillis();
		Integer record_number = 0;

		// Generates records and stores them in a file or in RAM
		if(cfg.containsKey("generate_in_file") && (cfg.get("generate_in_file").equals("true") || 
			cfg.get("generate_in_file").equals("True") || cfg.get("generate_in_file").equals("1") || 
			cfg.get("generate_in_file").equals("yes") || cfg.get("generate_in_file").equals("Yes"))){

			BufferedWriter writer = new BufferedWriter(new FileWriter("RCRD_" + this.uId + ".txt", true));

			record_number = 0;
			while(record_number != Integer.parseInt(cfg.get("create_record_amount"))){
				writer.write(DDBBTool.generateRandomString(Integer.parseInt(cfg.get("create_record_size")), false));
				writer.newLine();
				record_number++;
			}

			writer.close();

			this.report.save("generate", "total_time", String.valueOf(DDBBTool.runtime(start_time)));
			this.report.save("generate", "record_amount", String.valueOf(record_number));

		} 

	}

	// Creates the records in the database
	private void create(MongoCollection<Document> collection) throws Exception {
		Long start_time = System.currentTimeMillis();
		Integer record_number = 0;
		List<Long> single_time_taken = new ArrayList<Long>();
		Long before = System.currentTimeMillis();

		// If records are not to be generated in a file, do:
		if(cfg.containsKey("generate_in_file") == false || cfg.get("generate_in_file").equals("false") || 
			cfg.get("generate_in_file").equals("False") || cfg.get("generate_in_file").equals("0") || 
			cfg.get("generate_in_file").equals("no") || cfg.get("generate_in_file").equals("No")){

			while(record_number != Integer.parseInt(cfg.get("create_record_amount"))){
				
				before = System.currentTimeMillis();
				
				// Creates randomly generated documents
				Document document = new Document("", 
				DDBBTool.generateRandomString(Integer.parseInt(cfg.get("create_record_size")), false));
				collection.insertOne(document);

				if(this.cfg.containsKey("single_time_taken") && this.cfg.get("single_time_taken").equals("1")) {

					single_time_taken.add(DDBBTool.runtime(before));

				}

				record_number++;

			}

		} else { // OTherwise, if they were generated into a file, do:

			try {
				BufferedReader rng_reader = new BufferedReader(new FileReader("RCRD_" + this.uId + ".txt"));

				while(record_number != Integer.parseInt(cfg.get("create_record_amount"))){

					before = System.currentTimeMillis();
					// Use randomly generated string file to create records
					Document document = new Document("", rng_reader.readLine());
					collection.insertOne(document);

					if(this.cfg.containsKey("single_time_taken") && this.cfg.get("single_time_taken").equals("1")) {
						single_time_taken.add(DDBBTool.runtime(before));
					}
					record_number++;
				}

				rng_reader.close();
			} catch(Exception e){
				System.out.println(e);
			}

		}

		System.out.println("Generated " + record_number + " records.");
		// Saves the result for the test
		this.report.save("create", "total_time", String.valueOf(DDBBTool.runtime(start_time)));
		this.report.save("create", "total_amount", String.valueOf(record_number));
		if(this.cfg.containsKey("single_time_taken") && this.cfg.get("single_time_taken").equals("1")) {
			this.report.save("create", "single_time_taken", single_time_taken.toString());
		}

	}

	// Creates the records in the database used for RUD tests
	private void create_in_order(MongoCollection<Document> collection, String op) throws Exception {
		Long start_time = System.currentTimeMillis();
		Integer record_number = 0;

		while(record_number != Integer.parseInt(cfg.get(op + "_total_record_amount"))){
			
			// Creates randomly generated documents
			Document document = new Document("",record_number.toString())
				.append("v", DDBBTool.generateRandomString(Integer.parseInt(cfg.get(op + "_total_record_size")), false));
			collection.insertOne(document);

			record_number++;

		}

		// Saves the result for the test
		this.report.save(op, "create_total_time", String.valueOf(DDBBTool.runtime(start_time)));

	}

	// Reads the records in the database
	private void read(MongoCollection<Document> collection) throws Exception {
		Long start_time = System.currentTimeMillis();
		Integer read_number = 0;

		create_in_order(collection, "read");

		while(read_number != Integer.parseInt(cfg.get("read_total_amount"))){
			collection.find(Filters.eq("", Double.toString(Math.floor(Math.random() * (Integer.parseInt(cfg.get("read_total_record_amount")) - 1)))));
			read_number++;
		}

		// Saves the result for the test
		this.report.save("read", "read_total_time", String.valueOf(DDBBTool.runtime(start_time)));

	}

	// Updates the records in the database
	private void update(MongoCollection<Document> collection) throws Exception {
		Long start_time = System.currentTimeMillis();
		Integer update_number = 0;

		create_in_order(collection, "update");

		while(update_number != Integer.parseInt(cfg.get("update_total_amount"))){
			collection.updateOne(Filters.eq("", Double.toString(Math.floor(Math.random() * (Integer.parseInt(cfg.get("update_total_record_amount")) - 1)))), 
				Updates.set("v", DDBBTool.generateRandomString(Integer.parseInt(cfg.get("update_new_record_size")), false)));
			update_number++;
		}

		// Saves the result for the test
		this.report.save("update", "update_total_time", String.valueOf(DDBBTool.runtime(start_time)));

	}

	// Deletes the records in the database
	private void delete(MongoCollection<Document> collection) throws Exception {
		Long start_time = System.currentTimeMillis();
		Integer delete_number = 0;

		create_in_order(collection, "delete");

		while(delete_number != Integer.parseInt(cfg.get("delete_total_amount"))){
			collection.deleteOne(Filters.eq("", Double.toString(Math.floor(Math.random() * (Integer.parseInt(cfg.get("delete_total_record_amount")) - 1)))));
			delete_number++;
		}

		// Saves the result for the test
		this.report.save("delete", "delete_total_time", String.valueOf(DDBBTool.runtime(start_time)));

	}

}