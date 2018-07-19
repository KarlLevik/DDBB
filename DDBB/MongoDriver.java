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

	public static String report_name = "";
	public static Integer test_number = 0;
	public static Integer record_number = 0;
	public static Random r = new Random();
	public static String alphabet = "01234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ`,./;'#[]-=¬!£$%^&*()<>?:@~{}_+";
	public static String plain_alphabet = "01234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

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

	public static void generateReport(){
		try {
			String rng_val = "";
				for(int x = 0; x < 5; x++){
					rng_val = rng_val + plain_alphabet.charAt(r.nextInt(plain_alphabet.length()));
				}
				System.out.println("DDBB_" + cfg_db_type + "_" + cfg_b_name + "_" + (new SimpleDateFormat("dd-MM-yyyy_HHmmss").format(new Date())) + "_" + rng_val + ".txt");
				(new File("DDBB_" + cfg_db_type + "_" + cfg_b_name + "_" + (new SimpleDateFormat("dd-MM-yyyy_HHmmss").format(new Date())) + "_" + rng_val + ".txt")).createNewFile();
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}

	public static void generateRecords() throws Exception{
		new File("generateRecords.txt").createNewFile();
		FileChannel.open(Paths.get("generateRecords.txt"), StandardOpenOption.WRITE).truncate(0).close();
		BufferedWriter writer = new BufferedWriter(new FileWriter("generateRecords.txt", true));

		record_number = 0;
		while(record_number != Integer.parseInt(cfg_record_amount.get(test_number))){
			String rng_val = "";
			while(rng_val.length() != Integer.parseInt(cfg_record_size.get(test_number))){
				rng_val = rng_val + alphabet.charAt(r.nextInt(alphabet.length()));
			}
			writer.write(rng_val);
			writer.newLine();
			record_number++;
		}

		writer.close();
		System.out.println("Generated " + record_number + " records.");
	}

	public static void createRecords(MongoCollection<Document> collection) throws Exception{
		// FileReader reads text files in the default encoding.
		FileReader fileReader = new FileReader("generateRecords.txt");

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
			System.out.println(cfg_test_name);
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
		catch(FileNotFoundException ex) {
			System.out.println(
				"Unable to open file 'config.txt'");                
		}
        catch(IOException ex) {
            System.out.println(
                "Error reading file 'config.txt'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        }
	}

	public static void main( String args[] ) {
		
		loadConfig();
		generateReport();

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

				createRecords(collection);
			}
			catch(Exception e) {
				System.out.println(e);
			}

			test_number++;
		}



	}

}