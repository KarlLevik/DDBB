import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import java.io.*;

public class MongoDriver {

	public static String cfg_db_type = "";
	public static String cfg_test_name = "";
	public static String cfg_threads = "";
	// public static String cfg_memory_limit = "";
	public static String cfg_db_name = "";
	public static String cfg_server_ip = "";
	public static String cfg_server_port = "";
	public static String cfg_user = "";
	public static String cfg_pwd = "";

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
			bufferedReader.readLine();
			cfg_threads = bufferedReader.readLine();
			// bufferedReader.readLine();
			// cfg_memory_limit = 0;
			bufferedReader.readLine();
			cfg_db_name = bufferedReader.readLine();
			bufferedReader.readLine();
			cfg_server_ip = bufferedReader.readLine();
			bufferedReader.readLine();
			cfg_server_port = bufferedReader.readLine();
			bufferedReader.readLine();
			cfg_user = bufferedReader.readLine();
			bufferedReader.readLine();
			cfg_pwd = bufferedReader.readLine();

			/*
			System.out.println(cfg_db_type);
			System.out.println(cfg_test_name);
			System.out.println(cfg_threads);
			//System.out.println(cfg_memory_limit);
			System.out.println(cfg_db_name);
			System.out.println(cfg_server_ip);
			System.out.println(cfg_server_port);
			System.out.println(cfg_user);
			System.out.println(cfg_pwd);
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
		MongoClient mongo = new MongoClient( "localhost" , 27017 );
			
		// Creating Credentials 
		MongoCredential credential;
		credential = MongoCredential.createCredential("sampleUser", "myDb",
			"password".toCharArray());
		System.out.println("Connected to the database successfully");
		
		// Accessing the database 
		MongoDatabase database = mongo.getDatabase("myDb");
		System.out.println("Credentials ::"+ credential);

	}

}