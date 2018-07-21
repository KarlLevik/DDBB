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

	public static String uId = (new SimpleDateFormat("dd-MM-yyyy_HHmmss").format(new Date())) + "_" + 
		DDBBTool.generateRandomString(5, true);

	public static void main(String args[]) {
		System.out.println("1");
		try {
			System.out.println("2");
			MongoBenchmark bench = new MongoBenchmark();
			System.out.println("3");
			Set<String> tests = bench.tests.keySet();
			System.out.println(tests);
			for(String test : tests){
				System.out.println("5");
				(bench.tests.get(test)).run(uId);
			}

		} catch(Exception e) {
			System.out.println("6");
			System.out.println(e);
		}

	}

}