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

public class DdbbTest {

	public DdbbConfig cfg;
	public List<String> generated = new ArrayList<String>();
	public DdbbReport report = new DdbbReport();
	public String uId = (new SimpleDateFormat("dd-MM-yyyy_HHmmss").format(new Date())) + "_" + 
			DdbbTool.generateRandomString(5, true);
	public Db db;

	DdbbTest(String filename) throws Exception {

		cfg = DdbbIO.in(filename);

	}

	// Method to run the test
	public DdbbReport run(String db_type){
		Long start_time = System.currentTimeMillis();

		switch (db_type) {
			case "MongoDB": db = new MongoInterface(cfg);
				break;
			//case "Elasticsearch": db = new MongoInterface(cfg);
			////	break;
			//case "Cassandra": db = new ElasticInterface(cfg);
			////	break;
			//case "Redis": db = new CassandraInterface(cfg);
			////	break;
		}

		db.connectDb();
		db.createTable();
		db.table();

		// Carries out the creation, reading, updating or deleletion of records
		try {

			if(cfg.generate.meta.containsKey("generate_in_file") && cfg.generate.meta.get("generate_in_file").equals("1")){
				generate();
			}
			if(cfg.create.meta.containsKey("create_record_amount")){
				create();
			}
			if(cfg.read.meta.containsKey("read_total_amount")){
				read();
			}
			if(cfg.update.meta.containsKey("update_total_amount")){
				update();
			}
			if(cfg.delete.meta.containsKey("delete_total_amount")){
				delete();
			}
			db.disconnectDb();

		} catch(Exception e){
			System.out.println(e);
		}

		return report;
	}

	private void generate() throws Exception {
		Long start_time = System.currentTimeMillis();
		Integer record_number = 0;

		// Generates records and stores them in a file or in RAM
		if(cfg.generate.meta.containsKey("generate_in_file") && (cfg.generate.meta.get("generate_in_file").equals("true") ||
			cfg.generate.meta.get("generate_in_file").equals("True") || cfg.generate.meta.get("generate_in_file").equals("1") ||
			cfg.generate.meta.get("generate_in_file").equals("yes") || cfg.generate.meta.get("generate_in_file").equals("Yes"))){

			BufferedWriter writer = new BufferedWriter(new FileWriter("RCRD_" + this.uId + ".txt", true));

			// Generates random strings into the RCRD file
			record_number = 0;
			while(record_number != cfg.create.meta.get("create_record_amount")){
				writer.write(DdbbTool.generateRandomString((int) cfg.create.meta.get("create_record_size"), false));
				writer.newLine();
				record_number++;
			}

			writer.close();

			// Save performance statistics into the test report
			this.report.save("generate", "total_time", String.valueOf(DdbbTool.runtime(start_time)));
			this.report.save("generate", "record_amount", String.valueOf(record_number));

		} 

	}

	// Creates the records in the database
	private void create() throws Exception {
		Long start_time = System.currentTimeMillis();
		Integer record_number = 0;
		List<String> single_time_taken = new ArrayList<String>();
		Long before;

		// If records are not to be generated in a file, do:
		if(!cfg.generate.meta.containsKey("generate_in_file") || cfg.generate.meta.get("generate_in_file").equals("false") ||
			cfg.generate.meta.get("generate_in_file").equals("False") || cfg.generate.meta.get("generate_in_file").equals("0") ||
			cfg.generate.meta.get("generate_in_file").equals("no") || cfg.generate.meta.get("generate_in_file").equals("No")){

			while(record_number != cfg.create.meta.get("create_record_amount")){

				before = System.currentTimeMillis();
				
				// Creates randomly generated documents
				//Document document = new Document("", 
				//DdbbTool.generateRandomString(Integer.parseInt(cfg.get("create_record_size")), false));
				//collection.insertOne(document);
				Hashtable<String, String> doc = new Hashtable<String, String>();
				doc.put("k", DdbbTool.generateRandomString((int) cfg.create.meta.get("create_record_size"), false));

				if(this.cfg.create.meta.containsKey("single_time_taken") && (boolean) this.cfg.create.meta.get("single_time_taken")) {

					single_time_taken.add(String.valueOf(DdbbTool.runtime(before)));

				}
				record_number++;

			}

		} else { // Otherwise, if they were generated into a file, do:

			try {
				BufferedReader rng_reader = new BufferedReader(new FileReader("RCRD_" + this.uId + ".txt"));

				while(record_number != cfg.create.meta.get("create_record_amount")){

					before = System.currentTimeMillis();
					// Use randomly generated string file to create records
					//Document document = new Document("", rng_reader.readLine());
					//collection.insertOne(document);
					Hashtable<String, String> doc = new Hashtable<String, String>();
					doc.put("k", rng_reader.readLine());


					if(this.cfg.create.meta.containsKey("single_time_taken") && (boolean) this.cfg.create.meta.get("single_time_taken")) {
						single_time_taken.add(String.valueOf(DdbbTool.runtime(before)));
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
		this.report.save("create", "total_time", String.valueOf(DdbbTool.runtime(start_time)));
		this.report.save("create", "total_amount", String.valueOf(record_number));

		if(this.cfg.create.meta.containsKey("single_time_taken") && (boolean) this.cfg.create.meta.get("single_time_taken")) {
			this.report.saveList("create", "single_time_taken", single_time_taken);
		}

	}

	// Creates the records in the database used for RUD tests
	private void create_in_order(String op) throws Exception {
		Long start_time = System.currentTimeMillis();
		Integer record_number = 0;

		while(record_number != cfg.generate.meta.get(op + "_total_record_amount")){
			
			// Creates randomly generated documents
			Hashtable<String, String> doc = new Hashtable<String, String>();
			doc.put("k", record_number.toString());
			doc.put("v", DdbbTool.generateRandomString((int) cfg.generate.meta.get("create_record_size"), false));

			record_number++;

		}

		// Saves performance statistics
		this.report.save(op, "create_total_time", String.valueOf(DdbbTool.runtime(start_time)));

	}

	// Reads the records in the database
	private void read() throws Exception {

		Integer read_number = 0;
		create_in_order("read");

		Long start_time = System.currentTimeMillis();
		while(read_number != cfg.read.meta.get("read_total_amount")){
			this.db.read("k", Integer.toString((int) Math.floor(Math.random() * ((int) cfg.read.meta.get("read_total_record_amount") - 1))));
			read_number++;
		}

		// Saves the result for the test
		this.report.save("read", "read_total_time", String.valueOf(DdbbTool.runtime(start_time)));
	}

	// Updates the records in the database
	private void update() throws Exception {

		Integer update_number = 0;
		create_in_order("update");

		Long start_time = System.currentTimeMillis();
		while(update_number != cfg.update.meta.get("update_total_amount")){
			this.db.update("k", Integer.toString((int) Math.floor(Math.random() * ((int) cfg.update.meta.get("update_total_record_amount") - 1))), "v", DdbbTool.generateRandomString((int) cfg.update.meta.get("update_new_record_size"), false));
			update_number++;
		}

		// Saves the result for the test
		this.report.save("update", "update_total_time", String.valueOf(DdbbTool.runtime(start_time)));

	}

	// Deletes the records in the database
	private void delete() throws Exception {

		Integer delete_number = 0;
		create_in_order("delete");

		Long start_time = System.currentTimeMillis();
		while(delete_number != cfg.delete.meta.get("delete_total_amount")){
			this.db.delete("k", Integer.toString((int) Math.floor(Math.random() * ((int) cfg.delete.meta.get("delete_total_record_amount") - 1))));
			delete_number++;
		}

		// Saves the result for the test
		this.report.save("delete", "delete_total_time", String.valueOf(DdbbTool.runtime(start_time)));
	}

}