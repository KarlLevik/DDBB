import java.io.*;

public class MongoReport {

	public static void test(String mes){
		System.out.println(mes);
	}

	public static void generate(String b_name, String db_type){
		try {
			String short_b_name = b_name;
			short_b_name = short_b_name.replaceAll("[-!£$%^&*()=_+{}'#@~,./<>? |]","");
			if(short_b_name.length() > 5){
				short_b_name = short_b_name.substring(0,5);
			}
			report_name = "DDBB_" + db_type + "_" + short_b_name + "_" + uId;
			(new File(report_name + ".txt")).createNewFile();
		}
		catch(Exception e) {
			error("Can't create report file", e.toString());
		}
	}

	public static void config(String report_name, Hash cfg){
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(report_name + ".txt", true));
			writer.write("=========================");
			writer.newLine();
			writer.write("=========================");
			writer.newLine();
			writer.write("report_name: " + report_name);
			writer.newLine();
			writer.write("=========================");
			writer.newLine();
			writer.write("DDBB CONFIGURATION");
			writer.newLine();
			writer.write("cfg_db_type: " + cfg_db_type);
			writer.newLine();
			writer.write("cfg_b_name: " + cfg_b_name);
			writer.newLine();
			writer.write("cfg_db_name: " + cfg_db_name);
			writer.newLine();
			writer.write("cfg_server_ip: " + cfg_server_ip);
			writer.newLine();
			writer.write("cfg_server_port: " + cfg_server_port);
			writer.newLine();
			writer.write("cfg_user: " + cfg_user);
			writer.newLine();
			//writer.write("cfg_pwd");
			//writer.newLine();
			writer.write("=========================");
			writer.newLine();
			Integer r_test_number = 0;
			while(r_test_number != cfg_test_amount){
				writer.write("TEST " + r_test_number + " CONFIGURATION");
				writer.newLine();
				writer.write("cfg_collection: " + cfg_collection.get(r_test_number));
				writer.newLine();
				writer.write("cfg_record_size: " + cfg_record_size.get(r_test_number));
				writer.newLine();
				writer.write("cfg_record_amount: " + cfg_record_amount.get(r_test_number));
				writer.newLine();
				writer.write("cfg_write_random: " + cfg_write_random.get(r_test_number));
				writer.newLine();
				writer.write("cfg_rand_reads: " + cfg_rand_reads.get(r_test_number));
				writer.newLine();
				writer.write("cfg_rand_dels: " + cfg_rand_dels.get(r_test_number));
				writer.newLine();
				writer.write("cfg_threads: " + cfg_threads.get(r_test_number));
				writer.newLine();
				writer.write("cfg_mrt: " + cfg_mrt.get(r_test_number));
				writer.newLine();
				writer.write("cfg_repeats: " + cfg_repeats.get(r_test_number));
				writer.newLine();
				writer.write("=========================");
				writer.newLine();
				r_test_number++;
			}
			
			writer.close();
		}
		catch(Exception e) {
			error("Can't write configuration to report file", e.toString());
		}
	}

	public static void error(String message, String error){
		try {
			error_amount++;
			(new File(error_name + ".txt")).createNewFile();
			BufferedWriter writer = new BufferedWriter(new FileWriter(error_name + ".txt", true));
			writer.write("RUNTIME: " + runtime());
			writer.newLine();
			writer.write(message);
			writer.newLine();
			writer.write(error);
			writer.newLine();
			writer.write("-------------------------");
			writer.newLine();
			writer.close();
		}
		catch(Exception e){
			System.out.println("-------------------------");
			System.out.println("Can't print errors to report file");
			System.out.println(e);
		}
	}

	public static void merge(String report_name, String error_name, Integer error_amount, String records_name, String records_amount, String results_name, String results_amount){
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(report_name + ".txt", true));
			
			File error_file = new File(error_name + ".txt");
			File records_file = new File(records_name + ".txt");
			File results_file = new File(results_name + ".txt");

			if(error_file.exists()){
				BufferedReader error_reader = new BufferedReader(new FileReader(error_name + ".txt"));

				writer.write("-------------------------");
				writer.newLine();
				writer.write("-------------------------");
				writer.newLine();
				writer.write("ERRORS");
				writer.newLine();
				writer.write("-------------------------");
				writer.newLine();
				writer.write("AMOUNT: " + error_amount);
				writer.newLine();
				writer.write("-------------------------");
				writer.newLine();

				String current_line = error_reader.readLine();
				while(current_line != null){
					writer.write(current_line);
					writer.newLine();
					current_line = error_reader.readLine();
				}
				
				if(error_file.delete()){
					System.out.println("Error file deleted successfully");
				} else {
					System.out.println("Failed to delete the error file");
				}

				error_reader.close();
			} else {
				writer.write("-------------------------");
				writer.newLine();
				writer.write("-------------------------");
				writer.newLine();
				writer.write("ERRORS");
				writer.newLine();
				writer.write("-------------------------");
				writer.newLine();
				writer.write("None!");
				writer.newLine();
				writer.write("-------------------------");
				writer.newLine();
			}

			if(results_file.exists()){
				BufferedReader results_reader = new BufferedReader(new FileReader(error_name + ".txt"));

				writer.write("+++++++++++++++++++++++++");
				writer.newLine();
				writer.write("+++++++++++++++++++++++++");
				writer.newLine();
				writer.write("RESULTS");
				writer.newLine();
				writer.write("+++++++++++++++++++++++++");
				writer.newLine();
				writer.write("AMOUNT: " + results_amount);
				writer.newLine();
				writer.write("+++++++++++++++++++++++++");
				writer.newLine();

				String current_line = results_reader.readLine();
				while(current_line != null){
					writer.write(current_line);
					writer.newLine();
					current_line = results_reader.readLine();
				}
				
				if(results_file.delete()){
					System.out.println("Results file deleted successfully");
				} else {
					System.out.println("Failed to delete the results file");
				}

				results_reader.close();
			} else {
				writer.write("+++++++++++++++++++++++++");
				writer.newLine();
				writer.write("+++++++++++++++++++++++++");
				writer.newLine();
				writer.write("RESULTS");
				writer.newLine();
				writer.write("+++++++++++++++++++++++++");
				writer.newLine();
				writer.write("None!");
				writer.newLine();
				writer.write("+++++++++++++++++++++++++");
				writer.newLine();
			}

			if(records_file.exists()){
				if(records_file.delete()){
					System.out.println("Records file deleted successfully");
				} else {
					System.out.println("Failed to delete the records file");
				}
			} 

			writer.close();
		}
		catch(Exception e){
			System.out.println("-------------------------");
			System.out.println("Can't merge temp files to report file");
			System.out.println(e);
			System.out.println("Not deleting temporary files!");
		}		
	}

}