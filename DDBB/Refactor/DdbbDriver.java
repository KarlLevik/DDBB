import java.util.*;
import java.io.*;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime; 
import java.text.SimpleDateFormat;   

public class DdbbDriver {

	public static void main(String args[]) {
		
		// Unique ID generated for each session of DdbbDriver
		String uId = (new SimpleDateFormat("dd-MM-yyyy_HHmmss").format(new Date())) + "_" + 
			DdbbTool.generateRandomString(5, true);

		Hashtable<String, DdbbReport> test_reports = new Hashtable<String, DdbbReport>();
		
		try {
			// Creates a new benchmark (each DdbbBenchmark consistents of DdbbTests)
			DdbbBenchmark bench = new DdbbBenchmark();
			// Gets the set of test names
			Set<String> tests = bench.tests.keySet();

			// Executes each test in order
			for(String test : tests){

				test_reports.put(test, (bench.tests.get(test)).run(bench.cfg.get("db_type")));
			
			}

			//Create output file
			String report_name = "REPORT_" + uId;
			BufferedWriter writer = new BufferedWriter(new FileWriter(report_name + ".txt", true));
			
			// Puts the cfg into the output file
			List<String> reverse_cfg = new ArrayList<String>(bench.cfg.keySet());
			Collections.reverse(reverse_cfg);
			for(String setting : reverse_cfg) {

				writer.write(setting);
				writer.newLine();
				writer.write("    " + bench.cfg.get(setting));
				writer.newLine();

			}
			writer.write("=====");
			writer.newLine();

			// Puts the tests into the output file
			List<DdbbReport> reverse_test_r_vals = new ArrayList<DdbbReport>(test_reports.values());
			Collections.reverse(reverse_test_r_vals);
			for(DdbbReport test_report : reverse_test_r_vals) {
				
				writer.write("TEST : " + (DdbbTool.getKey(test_reports, test_report)).toString());
				writer.newLine();

				// Puts the operation results of a test into the output file
				List<String> reverse_test_report_ops = new ArrayList<String>(test_report.report.keySet());
				Collections.reverse(reverse_test_report_ops);
				for(String op : reverse_test_report_ops) {
					writer.write("    - " + op + " -");
					writer.newLine();

					// Puts each result for an operation of a test into the output file
					List<String> reverse_t_r_o_types = new ArrayList<String>(test_report.report.get(op).keySet());
					Collections.reverse(reverse_t_r_o_types);
					for(String type : reverse_t_r_o_types) {
					writer.write("        " + type + ": ");
					writer.newLine();
					writer.write("            " + (test_report.report.get(op)).get(type));
					writer.newLine();

					}

					writer.write("    ---");
					writer.newLine();

				}

				writer.write("++++");
				writer.newLine();

			}

			writer.close();


		} catch(Exception e) {

			System.out.println(e);
		
		}

	}

}