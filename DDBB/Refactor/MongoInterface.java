import java.util.Hashtable;

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
		client = new MongoClient((String) this.cfg.settings.get("ip"), (int) (this.cfg.settings.get("port")));

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
		collection = db.getCollection((String) this.cfg.settings.get("collection"));

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