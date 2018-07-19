import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection; 

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;

import java.io.*;
import java.util.*;

import org.bson.Document;  

public class MongoDriver {

	public static Integer test_number = 0;
	public static Integer record_number = 0;
	public static Random r = new Random();
	public static String alphabet = "01234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ`,./;'#[]-=¬!£$%^&*()<>?:@~{}_+";

	public static String cfg_db_type = "";
	public static String cfg_test_name = "";
	// public static String cfg_memory_limit = "";
	public static String cfg_db_name = "";
	public static String cfg_server_ip = "";
	public static Integer cfg_server_port = 27017;
	public static String cfg_user = "";
	public static String cfg_pwd = "";

	public static List<String> cfg_collection = new ArrayList<String>();
	public static List<String> cfg_record_size = new ArrayList<String>();
	public static List<String> cfg_record_amount = new ArrayList<String>();
	public static List<String> cfg_write_random = new ArrayList<String>();
	public static List<String> cfg_rand_reads = new ArrayList<String>();
	public static List<String> cfg_rand_dels = new ArrayList<String>();
	public static List<String> cfg_threads = new ArrayList<String>();
	public static List<String> cfg_mrt = new ArrayList<String>();
	public static List<String> cfg_repeats = new ArrayList<String>();

	public static void loadConfig(){
		try {
			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader("config.txt");

			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			bufferedReader.readLine();
			cfg_db_type = bufferedReader.readLine();
			bufferedReader.readLine();
			cfg_test_name = bufferedReader.readLine();
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

		// Creating a Mongo client 
		MongoClient mongo = new MongoClient( cfg_server_ip , cfg_server_port );
			
		// Creating Credentials 
		MongoCredential credential;
		credential = MongoCredential.createCredential(cfg_user, cfg_db_name,
			cfg_pwd.toCharArray());
		System.out.println("Connected to the database successfully");
		
		// Accessing the database 
		MongoDatabase database = mongo.getDatabase(cfg_db_name);
		System.out.println("Credentials ::"+ credential);

		while(test_number != cfg_test_name.length()){

			// Retrieving a collection
			MongoCollection<Document> collection = database.getCollection(cfg_collection.get(test_number)); 
			System.out.println("Collection " + cfg_collection.get(test_number) + " selected successfully");

			record_number = 0;
			while(record_number != Integer.parseInt(cfg_record_amount.get(test_number))){
				String rng_val = "";
				while(rng_val.length() != Integer.parseInt(cfg_record_size.get(test_number))){
					rng_val = rng_val + alphabet.charAt(r.nextInt(alphabet.length()));
				}

				Document document = new Document("1", rng_val);
				collection.insertOne(document); 

				record_number++;
				System.out.println("Added " + record_number + " records.");
			}
			
			System.out.println("Document inserted successfully");  

			test_number++;
		}



	}

}