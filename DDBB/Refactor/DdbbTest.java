import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;

public class DdbbTest {

	public DdbbConfig cfg;
	public Hashtable<String,ArrayList<Object>> generated = new Hashtable<>();
	public DdbbReport report = new DdbbReport();
	private String uId = (new SimpleDateFormat("dd-MM-yyyy_HHmmss").format(new Date())) + "_" +
			DdbbTool.generateRandomString(5, true);
	public Db db;

	DdbbTest(String filename) throws Exception {

		cfg = DdbbIO.in(filename);

	}

	// Method to run the test
	public DdbbReport run(){
		Long start_time = System.currentTimeMillis();

		switch ((String) cfg.settings.get("db_type")) {
			case "MongoDB": db = new MongoInterface(cfg);
				break;
			//case "Elasticsearch": db = new ElasticInterface(cfg);
			////	break;
			//case "Cassandra": db = new CassandraInterface(cfg);
			////	break;
			//case "Redis": db = new RedisInterface(cfg);
			////	break;
		}

		db.connectDb();
		db.createTable();
		db.table();

		// Carries out the creation, reading, updating or deleletion of records
		try {

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

		} catch(Exception e){
			System.out.println(e);
		}

		return report;
	}

	public boolean validate(){
		return true;
	}

	private void setup(){	}

	private void generate(DdbbProperty property) throws Exception {

		Hashtable<String, Object> meta = property.meta;
		Hashtable<String, ArrayList<Object>> data = property.data;
		Integer counter = 0;
		generated = new Hashtable<>();
		Integer amount = (int) meta.get("step_generate");

		// 0 - From file
		// 1 - To file
		// 2 - To RAM
		// 3 - On the go

		while(counter < amount){

			Integer field_amount = data.get("name").size();
			Integer field_counter = 0;
			while(field_counter < field_amount){

				String field_name = (String) data.get("name").get(field_counter);Hashtable<String,ArrayList<Object>>
				Integer length = 		(int) data.get("length").get(field_counter);
				boolean length_up_to = 	(boolean) data.get("length_up_to").get(field_counter);
				boolean in_order = 		(boolean) data.get("in_order").get(field_counter);
				boolean unique = 		(boolean) data.get("unique").get(field_counter);

				// for loop so that the record for the field is checked and if not found, created in generated hs
				for(int i = 0; i < 2; i++){
					// if record for field is found in generated hs-
					if (generated.containsKey(field_name)) {
						// if the length of record to be generated is up to length specified, generate random length
						if(length_up_to){

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
									Integer gen_val = (int) generate_value(data, field_counter);
									// checks whether generated value was unique
									if(DdbbTool.getKey(generated, gen_val) != null){

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
			counter++;

		}

		if((int) meta.get("generate_method") == 0){

		} else if((int) meta.get("generate_method") == 1){

		} else {

		}

	}

	public Object generate_value(Hashtable<String, ArrayList<Object>> data, int field_counter){

		Integer size = 			(int) data.get("size").get(field_counter);
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
		Long start_time = System.currentTimeMillis();
		Integer record_number = 0;
		List<String> single_time_taken = new ArrayList<String>();
		Long before;

		// If records are not to be generated in a file, do:
		if(cfg.generate.meta.containsKey("generate_in_file") && (boolean) cfg.generate.meta.get("generate_in_file")){

			while(record_number != cfg.create.meta.get("create_record_amount")){

				before = System.currentTimeMillis();
				
				// Creates randomly generated documents
				//Document document = new Document("", 
				//DdbbTool.generateRandomString(Integer.parseInt(cfg.get("create_record_size")), false));
				//collection.insertOne(document);
				Hashtable<String, String> doc = new Hashtable<>();
				doc.put("k", DdbbTool.generateRandomString((int) cfg.create.meta.get("create_record_size"), false));

				if(this.cfg.create.meta.containsKey("single_time_taken") && (boolean) this.cfg.create.meta.get("single_time_taken")) {

					single_time_taken.add(String.valueOf(DdbbTool.runtime(before)));

				}
				record_number++;

			}

		} else { // Otherwise, if they were generated into a file, do:

			try {
				BufferedReader rng_reader = new BufferedReader(new FileReader("RCRD_" + this.uId + ".txt"));

				while(record_number != cfg.create.meta.get("create_record_amount")){

					before = System.currentTimeMillis();
					// Use randomly generated string file to create records
					//Document document = new Document("", rng_reader.readLine());
					//collection.insertOne(document);
					Hashtable<String, String> doc = new Hashtable<String, String>();
					doc.put("k", rng_reader.readLine());


					if(this.cfg.create.meta.containsKey("single_time_taken") && (boolean) this.cfg.create.meta.get("single_time_taken")) {
						single_time_taken.add(String.valueOf(DdbbTool.runtime(before)));
					}
					record_number++;
				}

				rng_reader.close();
			} catch(Exception e){
				System.out.println(e);
			}

		}

		System.out.println("Generated " + record_number + " records.");

		// Saves the result for the test
		this.report.save("create", "total_time", String.valueOf(DdbbTool.runtime(start_time)));
		this.report.save("create", "total_amount", String.valueOf(record_number));

		if(this.cfg.create.meta.containsKey("single_time_taken") && (boolean) this.cfg.create.meta.get("single_time_taken")) {
			this.report.saveList("create", "single_time_taken", single_time_taken);
		}

	}

	// Creates the records in the database used for RUD tests
	private void create_in_order(String op) throws Exception {
		Long start_time = System.currentTimeMillis();
		Integer record_number = 0;

		while(record_number != cfg.generate.meta.get(op + "_total_record_amount")){
			
			// Creates randomly generated documents
			Hashtable<String, String> doc = new Hashtable<String, String>();
			doc.put("k", record_number.toString());
			doc.put("v", DdbbTool.generateRandomString((int) cfg.generate.meta.get("create_record_size"), false));

			record_number++;

		}

		// Saves performance statistics
		this.report.save(op, "create_total_time", String.valueOf(DdbbTool.runtime(start_time)));

	}

	// Reads the records in the database
	private void read() throws Exception {

		Integer read_number = 0;
		create_in_order("read");

		Long start_time = System.currentTimeMillis();
		while(read_number != cfg.read.meta.get("read_total_amount")){
			this.db.read("k", Integer.toString((int) Math.floor(Math.random() * ((int) cfg.read.meta.get("read_total_record_amount") - 1))));
			read_number++;
		}

		// Saves the result for the test
		this.report.save("read", "read_total_time", String.valueOf(DdbbTool.runtime(start_time)));
	}

	// Updates the records in the database
	private void update() throws Exception {

		Integer update_number = 0;
		create_in_order("update");

		Long start_time = System.currentTimeMillis();
		while(update_number != cfg.update.meta.get("update_total_amount")){
			this.db.update("k", Integer.toString((int) Math.floor(Math.random() * ((int) cfg.update.meta.get("update_total_record_amount") - 1))), "v", DdbbTool.generateRandomString((int) cfg.update.meta.get("update_new_record_size"), false));
			update_number++;
		}

		// Saves the result for the test
		this.report.save("update", "update_total_time", String.valueOf(DdbbTool.runtime(start_time)));

	}

	// Deletes the records in the database
	private void delete() throws Exception {

		Integer delete_number = 0;
		create_in_order("delete");

		Long start_time = System.currentTimeMillis();
		while(delete_number != cfg.delete.meta.get("delete_total_amount")){
			this.db.delete("k", Integer.toString((int) Math.floor(Math.random() * ((int) cfg.delete.meta.get("delete_total_record_amount") - 1))));
			delete_number++;
		}

		// Saves the result for the test
		this.report.save("delete", "delete_total_time", String.valueOf(DdbbTool.runtime(start_time)));
	}

}