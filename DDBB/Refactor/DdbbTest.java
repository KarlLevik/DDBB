import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;

public class DdbbTest {

	public DdbbConfig cfg;
	public ArrayList<Hashtable<String,ArrayList<Object>>> generated_set = new ArrayList<>();
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

	private void generate(DdbbProperty property, Integer amount) throws Exception {

		Hashtable<String, Object> meta = property.meta;
		Hashtable<String, ArrayList<Object>> data = property.data;
		Integer counter = 0;
		ArrayList<Hashtable<String,ArrayList<Object>>> generated_set = new ArrayList<>();

		// 0 - From file
		// 1 - To file
		// 2 - To RAM
		// 3 - On the go

		while(counter < amount){

			Integer field_amount = data.get("name").size();
			Integer field_counter = 0;
			while(field_counter < field_amount){
				Hashtable<String,ArrayList<Object>> generated = new Hashtable<>();

				String field_name = (String) data.get("name").get(field_counter);
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

				generated_set.add(generated);
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
		Integer record_number = 0;
		while(record_number != cfg.create.meta.get("amount")){
			Integer record_step = (((int) cfg.create.meta.get("step_generate") > ((int) cfg.create.meta.get("amount") - record_number)) ? (int) cfg.create.meta.get("step_generate") : ((int) cfg.create.meta.get("amount") - record_number));
			generate(cfg.create, record_step);
			for(int i = 0; i < record_step; i++){
				db.create(generated_set.get(i));
				record_number++;
			}

		}

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
	}

	// Deletes the records in the database
	private void delete() throws Exception {
		Integer record_number = 0;
		while(record_number != cfg.delete.meta.get("amount")){
			//Integer record_step = (((int) cfg.delete.meta.get("step_generate") > ((int) cfg.delete.meta.get("amount") - record_number)) ? (int) cfg.delete.meta.get("step_generate") : ((int) cfg.delete.meta.get("amount") - record_number));
			Integer record_step = (int) cfg.delete.meta.get("amount");
			generate(cfg.delete, record_step);
			String field = "";
			for(int c = 0; c < cfg.delete.data.get("in_order").size();c++){
				if((boolean) cfg.delete.data.get("in_order").get(c)){
					field = (String) cfg.delete.data.get("name").get(c);
				}
			}
			for(int i = 0; i < record_step; i++){
				db.read(generated_set.get(i), field);
				record_number++;
			}
		}
	}

}