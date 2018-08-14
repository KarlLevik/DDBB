import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection; 
import com.mongodb.client.model.*; 
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;

import org.bson.Document;  

public class MongoInterface implements Db {
	
	public DdbbConfig cfg;
	public MongoClient client;
	public MongoCredential credential;
	public MongoDatabase db;
	public MongoCollection<Document> collection;

	MongoInterface(DdbbConfig cfg){
		this.cfg = cfg;
	}

	public void connectDb(){

		// Creating a Mongo client 
		client = new MongoClient((String) this.cfg.settings.get("ip"), (int) this.cfg.settings.get("port"));

		// Creating Credentials 
		credential = MongoCredential.createCredential((String) this.cfg.settings.get("user"), (String) this.cfg.settings.get("db_name"), ((String) this.cfg.settings.get("pwd")).toCharArray());
		System.out.println("Connected to the database successfully");
		
		// Accessing the database 
		db = client.getDatabase((String) this.cfg.settings.get("db_name"));
		System.out.println("Credentials ::"+ credential);

	}

	public void disconnectDb(){
		client.close();
	}

	public void table(){
		
		// Retrieving a collection
		collection = db.getCollection((String) this.cfg.settings.get("table"));

	}

	public void createTable(){

	}

	public long create(Hashtable<String,ArrayList<Object>> in){
		Long time_before;
		Long time_after;

		Document document = new Document();

		for(String key : in.keySet()){
			document.append(key, in.get(key));
		}

		time_before = System.nanoTime();
		collection.insertOne(document);
		time_after = System.nanoTime();

		return time_after - time_before;

	}

	public long read(Hashtable<String,ArrayList<Object>> in, String field){
		Long time_before;
		Long time_after;

		Integer amount = in.keySet().size();
		String rand_key = (String) in.keySet().toArray()[(new Random()).nextInt(amount)];

		time_before = System.nanoTime();
		//collection.find(Filters.eq(rand_key, in.get(rand_key)));
		time_after = System.nanoTime();

		return time_after - time_before;

	}

	public long update(Hashtable<String,ArrayList<Object>> in, Hashtable<String,ArrayList<Object>> up) {
		Long time_before;
		Long time_after;

		//collection.updateOne(Filters.eq(in_key.toString(), in_value.toString()),
		//	Updates.set(new_key.toString(), new_value.toString()));

		return Long.parseLong("0");
	
	}

	public long delete(String key, String value){
		Long time_before;
		Long time_after;

		time_before = System.nanoTime();
		collection.deleteOne(Filters.eq(key, value));
		time_after = System.nanoTime();

		return time_after - time_before;

	}

}