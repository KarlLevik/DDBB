import java.util.Hashtable;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection; 
import com.mongodb.client.model.*; 
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;

import org.bson.Document;  

public class Test {
	public static void main(String[] args){

		MongoClient client;
		MongoCredential credential;
		MongoDatabase db;
		MongoCollection<Document> collection;

		char[] pwd = {'p','a','s','s','w','o','r','d'};
		client = new MongoClient("localhost", 27017);
		credential = MongoCredential.createCredential("user", "myDbTest3", pwd);
		db = client.getDatabase("myDbTest3");
		collection = db.getCollection("testCol5"); 
		client.close();

	}
}