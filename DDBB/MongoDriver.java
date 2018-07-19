import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection; 

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;

import java.io.*;
import java.util.*;
import java.nio.channels.*;
import java.nio.file.*;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime; 
import java.text.SimpleDateFormat;   

import org.bson.Document;  

public class MongoDriver {

	public static long start_time = System.currentTimeMillis();
	public static String time_date = (new SimpleDateFormat("dd-MM-yyyy_HHmmss").format(new Date()));

	public static String uId = (new SimpleDateFormat("dd-MM-yyyy_HHmmss").format(new Date())) + "_" + generateRandomString(5, true);
	public static String report_name = "";
	public static String error_name = "EROR_" + uId;
	public static String records_name = "RCRD_" + uId;
	public static Integer test_number = 0;
	public static Integer record_number = 0;

	public static String cfg_db_type = "";
	public static String cfg_b_name = "";
	// public static String cfg_memory_limit = "";
	public static String cfg_db_name = "";
	public static String cfg_server_ip = "";
	public static Integer cfg_server_port = 27017;
	public static String cfg_user = "";
	public static String cfg_pwd = "";

	public static Integer cfg_test_amount = 0;
	public static List<String> cfg_collection = new ArrayList<String>();
	public static List<String> cfg_record_size = new ArrayList<String>();
	public static List<String> cfg_record_amount = new ArrayList<String>();
	public static List<String> cfg_write_random = new ArrayList<String>();
	public static List<String> cfg_rand_reads = new ArrayList<String>();
	public static List<String> cfg_rand_dels = new ArrayList<String>();
	public static List<String> cfg_threads = new ArrayList<String>();
	public static List<String> cfg_mrt = new ArrayList<String>();
	public static List<String> cfg_repeats = new ArrayList<String>();

	public static long runtime(){
		return (System.currentTimeMillis() - start_time);
	}

	public static void reportGenerate(){
		try {
			String short_b_name = cfg_b_name;
			short_b_name = short_b_name.replaceAll("[-!£$%^&*()=_+{}'#@~,./<>? |]","");
			if(cfg_db_name.length() > 16){
				short_b_name = short_b_name.substring(0,5);
			}
			report_name = "DDBB_" + cfg_db_type + "_" + short_b_name + "_" + uId;
			(new File(report_name + ".txt")).createNewFile();
		}
		catch(Exception e) {
			reportError("Can't create report file", e.toString());
		}
	}

	public static void reportConfig(){
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(report_name + ".txt", true));
			writer.write("=========================");
			writer.newLine();
			writer.write("=========================");
			writer.newLine();
			writer.write("report_name: " + report_name);
			writer.newLine();
			writer.write("=========================");
			writer.newLine();
			writer.write("DDBB CONFIGURATION");
			writer.newLine();
			writer.write("cfg_db_type: " + cfg_db_type);
			writer.newLine();
			writer.write("cfg_b_name: " + cfg_b_name);
			writer.newLine();
			writer.write("cfg_db_name: " + cfg_db_name);
			writer.newLine();
			writer.write("cfg_server_ip: " + cfg_server_ip);
			writer.newLine();
			writer.write("cfg_server_port: " + cfg_server_port);
			writer.newLine();
			writer.write("cfg_user: " + cfg_user);
			writer.newLine();
			//writer.write("cfg_pwd");
			//writer.newLine();
			writer.write("=========================");
			writer.newLine();
			Integer r_test_number = 0;
			while(r_test_number != cfg_test_amount){
				writer.write("TEST " + r_test_number + " CONFIGURATION");
				writer.newLine();
				writer.write("cfg_collection: " + cfg_collection.get(r_test_number));
				writer.newLine();
				writer.write("cfg_record_size: " + cfg_record_size.get(r_test_number));
				writer.newLine();
				writer.write("cfg_record_amount: " + cfg_record_amount.get(r_test_number));
				writer.newLine();
				writer.write("cfg_write_random: " + cfg_write_random.get(r_test_number));
				writer.newLine();
				writer.write("cfg_rand_reads: " + cfg_rand_reads.get(r_test_number));
				writer.newLine();
				writer.write("cfg_rand_dels: " + cfg_rand_dels.get(r_test_number));
				writer.newLine();
				writer.write("cfg_threads: " + cfg_threads.get(r_test_number));
				writer.newLine();
				writer.write("cfg_mrt: " + cfg_mrt.get(r_test_number));
				writer.newLine();
				writer.write("cfg_repeats: " + cfg_repeats.get(r_test_number));
				writer.newLine();
				writer.write("=========================");
				writer.newLine();
				r_test_number++;
			}
			
			writer.close();
		}
		catch(Exception e) {
			reportError("Can't write configuration to report file", e.toString());
		}
	}

	public static void reportError(String message, String error){
		try {
			(new File(error_name + ".txt")).createNewFile();
			BufferedWriter writer = new BufferedWriter(new FileWriter(error_name + ".txt", true));
			writer.write("RUNTIME: " + runtime());
			writer.newLine();
			writer.write(message);
			writer.newLine();
			writer.write(error);
			writer.newLine();
			writer.write("-------------------------");
			writer.newLine();
			writer.close();
		}
		catch(Exception e){
			System.out.println("-------------------------");
			System.out.println("Can't print errors to report file");
			System.out.println(e);
		}
	}

	public static void reportMerge(){
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(report_name + ".txt", true));
			BufferedReader error_reader = new BufferedReader(new FileReader(error_name + ".txt"));

			writer.write("-------------------------");
			writer.newLine();
			writer.write("-------------------------");
			writer.newLine();
			writer.write("ERRORS");
			writer.newLine();
			writer.write("-------------------------");
			writer.newLine();

			String current_line = error_reader.readLine();
			while(current_line != null){
				writer.write(current_line);
				writer.newLine();
				current_line = error_reader.readLine();
			}
			writer.close();
			error_reader.close();
		}
		catch(Exception e){
			System.out.println("-------------------------");
			System.out.println("Can't merge temp files to report file");
			System.out.println(e);
		}		
	}

	public static String generateRandomString(Integer length, Boolean use_plain){		
		Random r = new Random();
		String rng_val = "";
		String alphabet = use_plain ? "01234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ" : "01234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ`,./;'#[]-=¬!£$%^&*()<>?:@~{}_+";
		while(rng_val.length() != length){
			rng_val = rng_val + alphabet.charAt(r.nextInt(alphabet.length()));
		}
		return rng_val;
	}

	public static void generateRecords() throws Exception{
		new File(records_name + ".txt").createNewFile();
		FileChannel.open(Paths.get(records_name + ".txt"), StandardOpenOption.WRITE).truncate(0).close();
		BufferedWriter writer = new BufferedWriter(new FileWriter(records_name + ".txt", true));

		record_number = 0;
		while(record_number != Integer.parseInt(cfg_record_amount.get(test_number))){
			writer.write(generateRandomString(Integer.parseInt(cfg_record_size.get(test_number)), false));
			writer.newLine();
			record_number++;
		}

		writer.close();
		System.out.println("Generated " + record_number + " records.");
	}

	public static void createRecords(MongoCollection<Document> collection) throws Exception{
		// FileReader reads text files in the default encoding.
		FileReader fileReader = new FileReader(records_name + ".txt");

		// Always wrap FileReader in BufferedReader.
		BufferedReader bufferedReader = new BufferedReader(fileReader);

		record_number = 0;
		while(record_number != Integer.parseInt(cfg_record_amount.get(test_number))){
			String line = bufferedReader.readLine();
			Document document = new Document("1", line);
			collection.insertOne(document); 
			record_number++;
		}

		bufferedReader.close();
		System.out.println("Created " + record_number + " records."); 
	}

	public static void loadConfig(){
		try {
			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader("config.txt");

			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			bufferedReader.readLine();
			cfg_db_type = bufferedReader.readLine();
			bufferedReader.readLine();
			cfg_b_name = bufferedReader.readLine();
			// bufferedReader.readLine();
			// cfg_memory_limit = 0;
			bufferedReader.readLine();
			cfg_db_name = bufferedReader.readLine();
			bufferedReader.readLine();
			cfg_server_ip = bufferedReader.readLine();
			bufferedReader.readLine();
			cfg_server_port = Integer.parseInt(bufferedReader.readLine());
			bufferedReader.readLine();
			cfg_user = bufferedReader.readLine();
			bufferedReader.readLine();
			cfg_pwd = bufferedReader.readLine();
			while(bufferedReader.readLine() != null){
				bufferedReader.readLine();
				cfg_collection.add(bufferedReader.readLine());
				bufferedReader.readLine();
				cfg_record_size.add(bufferedReader.readLine());
				bufferedReader.readLine();
				cfg_record_amount.add(bufferedReader.readLine());
				bufferedReader.readLine();
				cfg_write_random.add(bufferedReader.readLine());
				bufferedReader.readLine();
				cfg_rand_reads.add(bufferedReader.readLine());
				bufferedReader.readLine();
				cfg_rand_dels.add(bufferedReader.readLine());
				bufferedReader.readLine();
				cfg_threads.add(bufferedReader.readLine());
				bufferedReader.readLine();
				cfg_mrt.add(bufferedReader.readLine());
				bufferedReader.readLine();
				cfg_repeats.add(bufferedReader.readLine());
				cfg_test_amount++;
			}

			/*
			System.out.println(cfg_db_type);
			System.out.println(cfg_b_name);
			System.out.println(cfg_threads);
			//System.out.println(cfg_memory_limit);
			System.out.println(cfg_db_name);
			System.out.println(cfg_server_ip);
			System.out.println(Integer.toString(cfg_server_port));
			System.out.println(cfg_user);
			System.out.println(cfg_pwd);

			System.out.println(cfg_collection);
			System.out.println(cfg_record_size);
			System.out.println(cfg_record_amount);
			System.out.println(cfg_write_random);
			System.out.println(cfg_rand_reads);
			System.out.println(cfg_rand_dels);
			System.out.println(cfg_threads);
			System.out.println(cfg_mrt);
			System.out.println(cfg_repeats);
			*/

			// Always close files.
			bufferedReader.close();
		}
		catch(FileNotFoundException e) {
			reportError("Unable to open file 'config.txt'", e.toString());
		}
        catch(IOException e) {
			reportError("Error reading file 'config.txt'", e.toString());
        }
	}

	public static void main( String args[] ) {
		loadConfig();
		reportGenerate();
		reportConfig();

		// Creating a Mongo client 
		MongoClient mongo = new MongoClient( cfg_server_ip , cfg_server_port );
			
		// Creating Credentials 
		MongoCredential credential;
		credential = MongoCredential.createCredential(cfg_user, cfg_db_name, cfg_pwd.toCharArray());
		System.out.println("Connected to the database successfully");
		
		// Accessing the database 
		MongoDatabase database = mongo.getDatabase(cfg_db_name);
		System.out.println("Credentials ::"+ credential);

		while(test_number < cfg_test_amount){

			// Retrieving a collection
			MongoCollection<Document> collection = database.getCollection(cfg_collection.get(test_number)); 
			System.out.println("Collection " + cfg_collection.get(test_number) + " selected successfully");

			try {
				generateRecords();
			}
			catch(Exception e) {
				reportError("Can't generate records", e.toString());
			}

			try {
				createRecords(collection);
			}
			catch(Exception e) {
				reportError("Can't create records", e.toString());
			}

			test_number++;
		}


		reportMerge();
	}

}