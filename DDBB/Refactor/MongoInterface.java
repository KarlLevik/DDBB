import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Hashtable;

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

	public Long[] create(Hashtable<String,ArrayList<Object>> in){
        Long time_before;
        Long time_after;

		Document document = new Document();

		for(String key : in.keySet()){
			document.append(key, in.get(key));
		}

		time_before = System.nanoTime();
		collection.insertOne(document);
		time_after = System.nanoTime();

        return new Long[]{ time_before, time_after };

	}

	public Long[] read(Hashtable<String,ArrayList<Object>> in){
        Long time_before;
        Long time_after;

		Document document = new Document();

		for(String key : in.keySet()){
			document.append(key, in.get(key));
		}
		//System.out.println("dc = " + document);
		time_before = System.nanoTime();
		Document it = (collection.find(document).batchSize(1).iterator().next());
		time_after = System.nanoTime();
		//Document it1 = collection.find(document).first();
		//System.out.println("it = " + it1);

        return new Long[]{ time_before, time_after };

	}

	public Long[] update(Hashtable<String,ArrayList<Object>> in, Hashtable<String,ArrayList<Object>> up) {
		Long time_before = new Long(0);
		Long time_after = new Long(0);

		//collection.updateOne(Filters.eq(in_key.toString(), in_value.toString()),
		//	Updates.set(new_key.toString(), new_value.toString()));

        return new Long[]{ time_before, time_after };
	
	}

	public Long[] delete(String key, String value){
        Long time_before;
        Long time_after;

		time_before = System.nanoTime();
		collection.deleteOne(Filters.eq(key, value));
		time_after = System.nanoTime();

        return new Long[]{ time_before, time_after };

	}

}