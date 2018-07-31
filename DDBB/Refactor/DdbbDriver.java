import java.util.*;
import java.io.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.text.SimpleDateFormat;   

public class DdbbDriver {

	public static void main(String args[]) {

		try {

			for(int i = 0; i<100; i++){
				String test_name = "test_config" + i + ".json";
				if((new File(test_name)).exists()){
					DdbbIO.out(test_name, (new DdbbTest(test_name)).run());
				}

			}

		} catch(Exception e) {

			System.out.println(e);

		}

	}

}