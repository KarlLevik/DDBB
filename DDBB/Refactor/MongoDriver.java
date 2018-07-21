import java.util.*;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime; 
import java.text.SimpleDateFormat;   

public class MongoDriver {

	public static String uId = (new SimpleDateFormat("dd-MM-yyyy_HHmmss").format(new Date())) + "_" + 
		DDBBTool.generateRandomString(5, true);

	public static void main(String args[]) {
		
		try {

			MongoBenchmark bench = new MongoBenchmark();
			Set<String> tests = bench.tests.keySet();
			System.out.println(tests);
			for(String test : tests){

				(bench.tests.get(test)).run(uId);
			
			}

		} catch(Exception e) {

			System.out.println(e);
		
		}

	}

}