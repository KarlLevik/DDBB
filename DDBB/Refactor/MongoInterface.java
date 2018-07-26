import java.util.Hashtable;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection; 
import com.mongodb.client.model.*; 
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;

import org.bson.Document;  

public class MongoInterface implements Db {
	
	public Hashtable<String,String> cfg = new Hashtable<String,String>();
	public MongoClient client;
	public MongoCredential credential;
	public MongoDatabase db;
	public MongoCollection<Document> collection;

	MongoInterface(Hashtable<String,String> cfg){
		this.cfg = cfg;
	}

	public void connectDb(){

		// Creating a Mongo client 
		client = new MongoClient(this.cfg.get("ip"), Integer.parseInt(this.cfg.get("port")));
			
		// Creating Credentials 
		credential = MongoCredential.createCredential(this.cfg.get("user"), this.cfg.get("db_name"), this.cfg.get("pwd").toCharArray());
		System.out.println("Connected to the database successfully");
		
		// Accessing the database 
		db = client.getDatabase(this.cfg.get("db_name"));
		System.out.println("Credentials ::"+ credential);

	}

	public void disconnectDb(){
		
	}

	public void table(){
		
		// Retrieving a collection
		collection = db.getCollection(this.cfg.get("collection")); 

	}

	public void createTable(){

	}

	public void create(Hashtable<Object,Object> in){
		
		Document document = new Document();

		for(Object key : in.keySet()){
			document.append(key.toString(), in.get(key));
		}

		collection.insertOne(document);

	}

	public void read(String key, String val){
		collection.find(Filters.eq(key, val));

	}

	public void update(Object in_key, Object in_value, Object new_key, Object new_value) {

		collection.updateOne(Filters.eq(in_key.toString(), in_value.toString()), 
			Updates.set(new_key.toString(), new_value.toString()));
	
	}

	public void delete(Object key, Object value){

		collection.deleteOne(Filters.eq(key.toString(), value.toString()));

	}

}