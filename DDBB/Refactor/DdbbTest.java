import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.*;

public class DdbbTest implements Runnable {

	public DdbbConfig cfg;
	public ArrayList<Hashtable<String,ArrayList<Object>>> generated_set = new ArrayList<Hashtable<String, ArrayList<Object>>>();
	public DdbbReport report = new DdbbReport();
	private String uId = (new SimpleDateFormat("dd-MM-yyyy_HHmmss").format(new Date())) + "_" +
			DdbbTool.generateRandomString(5, true);
	public Db db;
	private Thread t;
	private String thread_name;
	public String file_name;
	private long query_delay = 0;
	private long test_time_delay = 0;

	DdbbTest(String f) throws Exception {
		file_name = f;
		cfg = DdbbIO.in(file_name + ".json");
		System.out.println("aaa");
		thread_name = "Thread_" + cfg.settings.get("b_name").toString();
		System.out.println("bbb");

		if(cfg.settings.containsKey("query_delay")){
			query_delay = Long.valueOf(String.valueOf(cfg.settings.get("query_delay")));
		}

		if(cfg.settings.containsKey("test_time_delay")){
			test_time_delay = Long.valueOf(String.valueOf(cfg.settings.get("test_time_delay")));
		}

		File file = new File(file_name + "_CRTE_GNR8.txt");
		file.delete();
		file.createNewFile();
		file = new File(file_name + "_READ_GNR8.txt");
		file.delete();
		file.createNewFile();
		file = new File(file_name + "_STUP_GNR8.txt");
		file.delete();
		file.createNewFile();

	}

	public void start() {
		System.out.println("Starting " +  thread_name );
		if (t == null) {

			t = new Thread(this, thread_name);
			t.start();

		}
	}

	// Method to run the test
	public void run(){

		try {
			if (cfg.settings.containsKey("test_order_delay")) {
				System.out.println("Waiting for : " + String.valueOf(cfg.settings.get("test_order_delay")));
				String earlier_test = String.valueOf(cfg.settings.get("test_order_delay"));

				if (DdbbDriver.status.containsKey(earlier_test)) {

					while (!DdbbDriver.status.get(earlier_test)) {
						Thread.sleep(1000);
					}

				}

			}

			System.out.println("Waiting for : " + test_time_delay);
			Thread.sleep(test_time_delay);

		} catch(Exception e){
			System.out.println(e);
			Thread.currentThread().stop();
		}

		Long start_time = System.nanoTime();

		System.out.println("Starting Test " + file_name);

		db = DdbbTool.getInterface(cfg);

		db.connectDb();
		db.createTable();
		db.table();

		// Carries out the creation, reading, updating or deleletion of records

		try {

			if(file_name.equals("test_config1")){

				this.warmup();
				DdbbDriver.warmup_finished.set(true);

			}

			while(!DdbbDriver.warmup_finished.get()){
				Thread.sleep(1);
			}

			if(!cfg.setup.isEmpty()){
				setup();
			}
			if(!cfg.create.isEmpty()){
				create();
			}
			if(!cfg.read.isEmpty()){
				read();
			}
			if(!cfg.update.isEmpty()){
				update();
			}
			if(!cfg.delete.isEmpty()){
				delete();
			}
			db.disconnectDb();

			DdbbIO.out(file_name + "_RESULT.json", report);

		} catch(Exception e){
			System.out.println(e);
		}

		DdbbDriver.status.replace(file_name, true);

		System.out.println("Ending Test : " + file_name);

	}

	private void setup(){

		System.out.println("Set-up finished");

		/*
		if(this.cfg.settings.containsKey("save_generated") && (boolean) this.cfg.settings.get("save_generated")){
			DdbbIO.out_generated(file_name + "_STUP_GNR8.txt", generated_set);
		}
		*/
	}

	private void warmup(){

		System.out.println("Starting DDBB warm-up, please be patient.");

		Hashtable<String,ArrayList<Object>> plain_object;

		DdbbConfig cfgw = new DdbbConfig(cfg);
		cfgw.create = new DdbbProperty();
		cfgw.read = new DdbbProperty();
		cfgw.update = new DdbbProperty();
		cfgw.delete = new DdbbProperty();
		cfgw.settings.replace("table", "testWarmup");
		Db dbw = DdbbTool.getInterface(cfgw);

		dbw.connectDb();
		dbw.createTable();
		dbw.table();

		for(int i = 0; i < 11000; i++){
			plain_object = new Hashtable<>();
			int plain_int = DdbbTool.generateRandomInteger(999, true);
			ArrayList<Object> plain_array = new ArrayList<>();
			plain_array.add(plain_int);
			plain_object.put("a", plain_array);
			dbw.create(plain_object);
			dbw.read(plain_object);
			dbw.delete("a", String.valueOf(plain_int));
		}

		dbw.disconnectDb();

		System.out.println("Warm-up finished");

		System.out.println("Sleeping for 10 seconds as part of cool-down");
		try {
			Thread.sleep(10000);
		} catch(Exception e){
			System.out.println(e);
		}
		System.out.println("Cool-down finished.");
	}

	private void generate(DdbbProperty property, Integer amount) throws Exception {

		Hashtable<String, Object> meta = property.meta;
		Hashtable<String, ArrayList<Object>> data = property.data;
		Integer counter = 0;
		generated_set = new ArrayList<>();

		// 0 - From filefilename
		// 1 - To file
		// 2 - To RAM
		// 3 - On the go

		while(counter < amount){

			Integer field_amount = data.get("name").size();
			Integer field_counter = 0;
			Hashtable<String,ArrayList<Object>> generated = new Hashtable<>();
			while(field_counter < field_amount){

				String field_name = (String) data.get("name").get(field_counter);
				Integer length = 		((Number) data.get("length").get(field_counter)).intValue();
				boolean length_up_to = 	(boolean) data.get("length_up_to").get(field_counter);
				boolean in_order = 		(boolean) data.get("in_order").get(field_counter);
				boolean unique = 		(boolean) data.get("unique").get(field_counter);

				// for loop so that the record for the field is checked and if not found, created in generated hs
				for(int i = 0; i < 2; i++){
					// if record for field is found in generated hs-
					if (generated.containsKey(field_name)) {
						if((!cfg.create.data.containsKey("kv") || cfg.create.data.get("kv").get(cfg.create.data.get("name").indexOf(field_name)).equals("VALUE"))){
							// if the length of record to be generated is up to length specified, generate random length
							if(!length_up_to){

								length = (new Random()).nextInt(length);

							}

							// create "length" amount of values for a field so that it follows the configuration
							for(int j = 0; j < length; j++){
								// if the field is not supposed to be in order or unique, simply generate the val
								if(!in_order && !unique){
									generated.get(field_name).add(generate_value(data, field_counter));
								} else if(in_order){ // if the field is supposed to be in order (and by definition unique)
									generated.get(field_name).add(generate_value(data, field_counter));
								} else if(unique){ // if the field is supposed to be unique
									boolean unique_val = false;
									while(!unique_val){ // loops until generated value is unique
										// generates random val
										Object gen_val = generate_value(data, field_counter);
										// checks whether generated value was unique
										if(DdbbTool.getKey(generated, gen_val) == null){

											unique_val = true;

										}

										generated.get(field_name).add(gen_val);

									}

								}
							}
						} else {
						}

					} else {
						// if entry for field doesn't exist in generated, create it
						generated.put(field_name, new ArrayList<>());
					}
				}

				field_counter++;
			}

			generated_set.add(generated);

			counter++;

		}

	}

	public Object generate_value(Hashtable<String, ArrayList<Object>> data, int field_counter){

		Integer size = 			((Number) data.get("size").get(field_counter)).intValue();
		boolean size_up_to = 	(boolean) data.get("size_up_to").get(field_counter);
        boolean fixed_field = 	(boolean) data.get("fixed_fields").get(field_counter);
        String fixed_value = 	String.valueOf(data.get("fixed_values").get(field_counter));
		String value = 			String.valueOf(data.get("val").get(field_counter));

		Object generated_value = new Object();

		if(fixed_field){
			return fixed_value;
		}

		// checks what type the current field is supposed to be
		switch ((String) data.get("val").get(field_counter)) {
			case "INT" : generated_value = DdbbTool.generateRandomInteger(size, size_up_to);
				break;
			case "STRING" : generated_value = DdbbTool.generateRandomString(size, size_up_to);
				break;
			case "DOUBLE" : generated_value = DdbbTool.generateRandomDouble((double) size, size_up_to);
				break;
			case "FLOAT" : generated_value = DdbbTool.generateRandomFloat((float) size, size_up_to);
				break;
			case "BOOLEAN" : generated_value = DdbbTool.generateRandomBoolean();
				break;
			case "DATETIME" : generated_value = DdbbTool.generateTimeDate(fixed_value);
		}

		return generated_value;
	}

	// Creates the records in the database
	private void create() throws Exception {
		Integer record_number = 0;
		List<Long> results;
		while(record_number < (int) cfg.create.meta.get("amount")){

			int record_step = (((int) cfg.create.meta.get("step_generate") < ((int) cfg.create.meta.get("amount") - record_number)) ? (int) cfg.create.meta.get("step_generate") : ((int) cfg.create.meta.get("amount") - record_number));

			if(!this.cfg.create.meta.containsKey("load_file")){
				generate(cfg.create, record_step);

				if(this.cfg.settings.containsKey("save_generated") && (boolean) this.cfg.settings.get("save_generated")){
					DdbbIO.out_generated(file_name + "_CRTE_GNR8.txt", generated_set);
				}

			} else {

				generated_set = DdbbIO.in_generated((String) this.cfg.create.meta.get("load_file"), record_number, record_step);

				if(this.cfg.settings.containsKey("save_generated") && (boolean) this.cfg.settings.get("save_generated")){
					DdbbIO.out_generated(file_name + "_CRTE_GNR8.txt", generated_set);
				}

			}

			results = new ArrayList<>();

			for(int i = 0; i < record_step; i++){
				results.add(db.create(generated_set.get(i)));
				Thread.sleep(query_delay);
				record_number++;

			}

			long total_result = 0;

			for(int a = 0; a < results.size(); a++){
				total_result = total_result + results.get(a);
			}

			report.save("create", "step_average", Long.toString(total_result / results.size()));

		}

		System.out.println("Create test finished");

	}

	// Reads the records in the database
	private void read() throws Exception {
		Integer record_number = 0;
		List<Long> results;

		while(record_number < (int) cfg.read.meta.get("amount")){

			Thread.sleep(query_delay);

			//Integer record_step = (((int) cfg.read.meta.get("step_generate") > ((int) cfg.read.meta.get("amount") - record_number)) ? (int) cfg.read.meta.get("step_generate") : ((int) cfg.read.meta.get("amount") - record_number));
			Integer record_step = (int) cfg.read.meta.get("step_generate");

			if(!this.cfg.read.meta.containsKey("load_file")){

				generate(cfg.read, record_step);

			} else {

				generated_set = DdbbIO.in_generated((String) this.cfg.read.meta.get("load_file"), record_number, record_step);

			}

			ArrayList<Hashtable<String,ArrayList<Object>>> new_generated_set = new ArrayList<>();
			for(Hashtable<String,ArrayList<Object>> record : generated_set) {

				new_generated_set.add((Hashtable<String,ArrayList<Object>>) record.clone());
			}

			if(this.cfg.read.data.containsKey("fields")){

				for(int record_index = 0; record_index < generated_set.size(); record_index++){
					for(String old_field : generated_set.get(record_index).keySet()) {
						if (!(cfg.read.data.get("fields").contains(old_field))) {
							new_generated_set.get(record_index).remove(old_field);
						}
					}
				}

			}

			generated_set = new_generated_set;

			if(this.cfg.settings.containsKey("save_generated") && (boolean) this.cfg.settings.get("save_generated")){
				DdbbIO.out_generated(file_name + "_READ_GNR8.txt", generated_set);
			}

			results = new ArrayList<>();

			for(int i = 0; i < record_step; i++){
				results.add(db.read(generated_set.get(i)));
				record_number++;

			}

			long total_result = 0;

			for(int a = 0; a < results.size(); a++){
				total_result = total_result + results.get(a);
			}

			report.save("read", "step_average", Long.toString(total_result / results.size()));
		}

		System.out.println("Read test finished");
	}

	// Updates the records in the database
	private void update() throws Exception {
		Integer record_number = 0;
		while(record_number < (int) cfg.update.meta.get("amount")){

			Thread.sleep(query_delay);

			Integer record_step = (((int) cfg.update.meta.get("step_generate") > ((int) cfg.update.meta.get("amount") - record_number)) ? (int) cfg.update.meta.get("step_generate") : ((int) cfg.update.meta.get("amount") - record_number));
			generate(cfg.update, record_step);
			for(int i = 0; i < record_step; i++){
				//db.update(generated_set.get(i));
				record_number++;
			}

		}

		System.out.println("Update finished");

	}

	// Deletes the records in the database
	private void delete() throws Exception {
		Integer record_number = 0;

		while(record_number < (int) cfg.delete.meta.get("amount")){

			Thread.sleep(query_delay);

			//Integer record_step = (((int) cfg.delete.meta.get("step_generate") > ((int) cfg.delete.meta.get("amount") - record_number)) ? (int) cfg.delete.meta.get("step_generate") : ((int) cfg.delete.meta.get("amount") - record_number));
			Integer record_step = (int) cfg.delete.meta.get("step_generate");

			List<Long> results = new ArrayList<>();

			for(int i = 0; i < record_step; i++){
				Integer random_field_index = (new Random()).nextInt(cfg.delete.data.get("fields").size() - 1);
				String random_field = cfg.delete.data.get("fields").get(random_field_index).toString();
				Integer random_record_index = (new Random()).nextInt((int) cfg.setup.meta.get("amount") + (int) cfg.create.meta.get("amount"));

				ArrayList<String> list = new ArrayList<>();
				try (BufferedReader br = new BufferedReader(new FileReader("del.dat"))) {
					for (int j = 0; j < random_record_index - 1; j++) {
						br.readLine();
					}
					list = new ArrayList<>(Arrays.asList(br.readLine().split(",")));

				} catch(Exception e){
					System.out.println(e);
				}

				results.add(db.delete(random_field, list.get(random_field_index)));
				record_number++;

			}

			long total_result = 0;
			for(int a = 0; a < results.size(); a++){
				total_result = total_result + results.get(a);
			}

			report.save("delete", "step_average", Long.toString(total_result / results.size()));

		}

		System.out.println("Delete test finished");

	}

}