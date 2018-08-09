import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;

public class DdbbTest implements Runnable {

	public DdbbConfig cfg;
	public ArrayList<Hashtable<String,ArrayList<Object>>> generated_set = new ArrayList<>();
	public DdbbReport report = new DdbbReport();
	private String uId = (new SimpleDateFormat("dd-MM-yyyy_HHmmss").format(new Date())) + "_" +
			DdbbTool.generateRandomString(5, true);
	public Db db;
	private Thread t;
	private String thread_name;
	private String file_name;

	DdbbTest(String f) throws Exception {
		file_name = f;
		cfg = DdbbIO.in(file_name + ".json");
		thread_name = "Thread_" + cfg.settings.get("b_name").toString();

	}

	public void start() {
		System.out.println("Starting " +  thread_name );
		if (t == null) {
			t = new Thread (this, thread_name);
			t.start();
		}
	}

	// Method to run the test
	public void run(){
		Long start_time = System.nanoTime();

		switch ((String) cfg.settings.get("db_type")) {
			case "MongoDB": db = new MongoInterface(cfg);
				break;
			//case "Elasticsearch": db = new ElasticInterface(cfg);
			////	break;
			//case "Cassandra": db = new CassandraInterface(cfg);
			////	break;
			case "Redis": db = new RedisInterface(cfg);
				break;
		}

		db.connectDb();
		db.createTable();
		db.table();

		// Carries out the creation, reading, updating or deleletion of records
		try {

			warmup();
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
	}

	public boolean validate(){
		return true;
	}private void setup(){
		System.out.println("Set-up finished");
	}

	private void warmup(){
		Hashtable<String,ArrayList<Object>> plain_object;

		for(int i = 0; i < 11000; i++){
			plain_object = new Hashtable<>();
			String plain_string = DdbbTool.generateRandomString(2, false);
			ArrayList<Object> plain_array = new ArrayList<>();
			plain_array.add(plain_string);
			plain_object.put(String.valueOf(i), plain_array);
			db.create(plain_object);
			db.delete(String.valueOf(i), plain_string);
		}
		System.out.println("Warm-up finished");

		System.out.println("Sleeping for 9 seconds as part of cool-down");
		try {
			Thread.sleep(9000);
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

		// 0 - From file
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
		Object value = new Object();

		// checks what type the current field is supposed to be
		switch ((String) data.get("val").get(field_counter)) {
			case "INT" : value = DdbbTool.generateRandomInteger(size, size_up_to);
				break;
			case "STRING" : value = DdbbTool.generateRandomString(size, size_up_to);
				break;
			case "DOUBLE" : value = DdbbTool.generateRandomDouble((double) size, size_up_to);
				break;
			case "BOOLEAN" : value = DdbbTool.generateRandomBoolean();
				break;
		}

		return value;
	}

	// Creates the records in the database
	private void create() throws Exception {
		Integer record_number = 0;
		List<Long> results;
		while(record_number < (int) cfg.create.meta.get("amount")){
			Integer record_step = (((int) cfg.create.meta.get("step_generate") < ((int) cfg.create.meta.get("amount") - record_number)) ? (int) cfg.create.meta.get("step_generate") : ((int) cfg.create.meta.get("amount") - record_number));
			generate(cfg.create, record_step);
			results = new ArrayList<>();

			for(int i = 0; i < record_step; i++){
				results.add(db.create(generated_set.get(i)));
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
		while(record_number != cfg.read.meta.get("amount")){
			//Integer record_step = (((int) cfg.read.meta.get("step_generate") > ((int) cfg.read.meta.get("amount") - record_number)) ? (int) cfg.read.meta.get("step_generate") : ((int) cfg.read.meta.get("amount") - record_number));
			Integer record_step = (int) cfg.read.meta.get("amount");
			generate(cfg.read, record_step);
			String field = "";
			for(int c = 0; c < cfg.read.data.get("in_order").size();c++){
				if((boolean) cfg.read.data.get("in_order").get(c)){
					field = (String) cfg.read.data.get("name").get(c);
				}
			}
			for(int i = 0; i < record_step; i++){
				db.read(generated_set.get(i), field);
				record_number++;
			}

		}

		System.out.println("Read test finished");
	}

	// Updates the records in the database
	private void update() throws Exception {
		Integer record_number = 0;
		while(record_number != cfg.update.meta.get("amount")){
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