import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

public class DdbbIO {

	public static DdbbConfig in(String filename) throws Exception {

		DdbbConfig cfg = new DdbbConfig();

		JsonReader reader = new JsonReader(new FileReader(filename));
		reader.beginObject();

		while(reader.hasNext()) {

			String name = reader.nextName();

			switch (name) {
				case "settings":

					reader.beginObject();

					while (reader.hasNext()) {
						name = reader.nextName();
						if (reader.peek() == JsonToken.STRING) {
							String val = reader.nextString();
							cfg.settings.put(name, val);
						} else if (reader.peek() == JsonToken.NUMBER) {
							cfg.settings.put(name, reader.nextInt());
						} else if (reader.peek() == JsonToken.BOOLEAN) {
							cfg.settings.put(name, reader.nextBoolean());
						} else {
							reader.skipValue();
							System.out.println("SKIPPED META OF INVALID TYPE!");
						}
					}


					reader.endObject();

					break;
				case "setup":

					reader.beginObject();

					while (reader.hasNext()) {

						name = reader.nextName();

						if (name.equals("meta")) {

							reader.beginObject();

							while (reader.hasNext()) {
								name = reader.nextName();
								if (reader.peek() == JsonToken.STRING) {
									cfg.setup.meta.put(name, reader.nextString());
								} else if (reader.peek() == JsonToken.NUMBER) {
									cfg.setup.meta.put(name, reader.nextInt());
								} else if (reader.peek() == JsonToken.BOOLEAN) {
									cfg.setup.meta.put(name, reader.nextBoolean());
								} else {
									reader.skipValue();
									System.out.println("SKIPPED META OF INVALID TYPE!");
								}
							}

							reader.endObject();

						} else if (name.equals("data")) {

							reader.beginObject();

							while (reader.hasNext()) {
								name = reader.nextName();
								ArrayList<Object> list = new ArrayList<>();
								reader.beginArray();
								while (reader.hasNext()) {
									if (reader.peek() == JsonToken.STRING) {
										list.add(reader.nextString());
									} else if (reader.peek() == JsonToken.NUMBER) {
										list.add(reader.nextDouble());
									} else if (reader.peek() == JsonToken.BOOLEAN) {
										list.add(reader.nextBoolean());
									} else {
										reader.skipValue();
										System.out.println("SKIPPED VALUE OF INVALID TYPE!");
									}
								}
								reader.endArray();
								cfg.setup.data.put(name, list);
							}

							reader.endObject();
						}
					}

					reader.endObject();

					break;
				case "create":

					reader.beginObject();
					while (reader.hasNext()) {

						name = reader.nextName();

						if (name.equals("meta")) {

							reader.beginObject();

							while (reader.hasNext()) {
								name = reader.nextName();
								if (reader.peek() == JsonToken.STRING) {
									cfg.create.meta.put(name, reader.nextString());
								} else if (reader.peek() == JsonToken.NUMBER) {
									cfg.create.meta.put(name, reader.nextInt());
								} else if (reader.peek() == JsonToken.BOOLEAN) {
									cfg.create.meta.put(name, reader.nextBoolean());
								} else {
									reader.skipValue();
									System.out.println("SKIPPED META OF INVALID TYPE!");
								}
							}

							reader.endObject();

						} else if (name.equals("data")) {

							reader.beginObject();

							while (reader.hasNext()) {
								name = reader.nextName();
								ArrayList<Object> list = new ArrayList<>();
								reader.beginArray();
								while (reader.hasNext()) {
									if (reader.peek() == JsonToken.STRING) {
										list.add(reader.nextString());
									} else if (reader.peek() == JsonToken.NUMBER) {
										list.add(reader.nextDouble());
									} else if (reader.peek() == JsonToken.BOOLEAN) {
										list.add(reader.nextBoolean());
									} else {
										reader.skipValue();
										System.out.println("SKIPPED VALUE OF INVALID TYPE!");
									}
								}
								reader.endArray();
								cfg.create.data.put(name, list);
							}

							reader.endObject();
						}
					}

					reader.endObject();

					break;
				case "read":

					reader.beginObject();
					while (reader.hasNext()) {

						name = reader.nextName();

						if (name.equals("meta")) {

							reader.beginObject();

							while (reader.hasNext()) {
								name = reader.nextName();
								if (reader.peek() == JsonToken.STRING) {
									cfg.read.meta.put(name, reader.nextString());
								} else if (reader.peek() == JsonToken.NUMBER) {
									cfg.read.meta.put(name, reader.nextInt());
								} else if (reader.peek() == JsonToken.BOOLEAN) {
									cfg.read.meta.put(name, reader.nextBoolean());
								} else {
									reader.skipValue();
									System.out.println("SKIPPED META OF INVALID TYPE!");
								}
							}

							reader.endObject();

						} else if (name.equals("data")) {

							reader.beginObject();

							while (reader.hasNext()) {
								name = reader.nextName();
								ArrayList<Object> list = new ArrayList<>();
								reader.beginArray();
								while (reader.hasNext()) {
									if (reader.peek() == JsonToken.STRING) {
										list.add(reader.nextString());
									} else if (reader.peek() == JsonToken.NUMBER) {
										list.add(reader.nextDouble());
									} else if (reader.peek() == JsonToken.BOOLEAN) {
										list.add(reader.nextBoolean());
									} else {
										reader.skipValue();
										System.out.println("SKIPPED VALUE OF INVALID TYPE!");
									}
								}
								reader.endArray();
								cfg.read.data.put(name, list);
							}

							reader.endObject();
						}
					}

					reader.endObject();

					break;
				case "update":

					reader.beginObject();
					while (reader.hasNext()) {

						name = reader.nextName();

						if (name.equals("meta")) {

							reader.beginObject();

							while (reader.hasNext()) {
								name = reader.nextName();
								if (reader.peek() == JsonToken.STRING) {
									cfg.update.meta.put(name, reader.nextString());
								} else if (reader.peek() == JsonToken.NUMBER) {
									cfg.update.meta.put(name, reader.nextInt());
								} else if (reader.peek() == JsonToken.BOOLEAN) {
									cfg.update.meta.put(name, reader.nextBoolean());
								} else {
									reader.skipValue();
									System.out.println("SKIPPED META OF INVALID TYPE!");
								}
							}

							reader.endObject();

						} else if (name.equals("data")) {

							reader.beginObject();

							while (reader.hasNext()) {
								name = reader.nextName();
								ArrayList<Object> list = new ArrayList<>();
								reader.beginArray();
								while (reader.hasNext()) {
									if (reader.peek() == JsonToken.STRING) {
										list.add(reader.nextString());
									} else if (reader.peek() == JsonToken.NUMBER) {
										list.add(reader.nextDouble());
									} else if (reader.peek() == JsonToken.BOOLEAN) {
										list.add(reader.nextBoolean());
									} else {
										reader.skipValue();
										System.out.println("SKIPPED VALUE OF INVALID TYPE!");
									}
								}
								reader.endArray();
								cfg.update.data.put(name, list);
							}

							reader.endObject();
						}
					}

					reader.endObject();

					break;
				case "delete":

					reader.beginObject();
					while (reader.hasNext()) {

						name = reader.nextName();

						if (name.equals("meta")) {

							reader.beginObject();

							while (reader.hasNext()) {
								name = reader.nextName();
								if (reader.peek() == JsonToken.STRING) {
									cfg.delete.meta.put(name, reader.nextString());
								} else if (reader.peek() == JsonToken.NUMBER) {
									cfg.delete.meta.put(name, reader.nextInt());
								} else if (reader.peek() == JsonToken.BOOLEAN) {
									cfg.delete.meta.put(name, reader.nextBoolean());
								} else {
									reader.skipValue();
									System.out.println("SKIPPED META OF INVALID TYPE!");
								}
							}

							reader.endObject();

						} else if (name.equals("data")) {

							reader.beginObject();

							while (reader.hasNext()) {
								name = reader.nextName();
								ArrayList<Object> list = new ArrayList<>();
								reader.beginArray();
								while (reader.hasNext()) {
									if (reader.peek() == JsonToken.STRING) {
										list.add(reader.nextString());
									} else if (reader.peek() == JsonToken.NUMBER) {
										list.add(reader.nextDouble());
									} else if (reader.peek() == JsonToken.BOOLEAN) {
										list.add(reader.nextBoolean());
									} else {
										reader.skipValue();
										System.out.println("SKIPPED VALUE OF INVALID TYPE!");
									}
								}
								reader.endArray();
								cfg.delete.data.put(name, list);
							}

							reader.endObject();
						}
					}

					reader.endObject();

					break;
				default:
					reader.skipValue(); //avoid some unhandle events

					System.out.println("SKIPPED DATA WITH INVALID TAG!");
					break;
			}
		}

		reader.endObject();
		reader.close();


		return cfg;

	}
	/*
	public static void out(String filename, DdbbReport report) throws Exception {

		Writer writer = new FileWriter(filename);
		Gson g = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(DdbbReport.class, new DdbbReportAdapter()).create();

		g.toJson(report, writer);

		writer.close();

	} */

	public static void out(String filename, DdbbReport report) throws Exception {
		BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
		for(String op : report.report.keySet()){
		    writer.write(op + " = [");
            for(int i = 0; i < report.report.get(op).size() - 1; i++){
                writer.write("(" + report.report.get(op).get(i)[0] + "," + report.report.get(op).get(i)[1] + ") , ");
            }
            writer.write("(" + report.report.get(op).get(report.report.get(op).size() - 1)[0] + "," + report.report.get(op).get(report.report.get(op).size() - 1)[1] + ")]");
            writer.newLine();
		}

		writer.close();
	}

	public static void outCfg(String filename, DdbbConfig cfg) throws Exception {

		Writer writer = new FileWriter(filename);
		Gson g = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(DdbbConfig.class, new DdbbConfigAdapter()).create();

		g.toJson(cfg, writer);

		writer.close();

	}

	public static void out_generated(String filename, ArrayList<Hashtable<String, ArrayList<Object>>> set) throws Exception {
		File file = new File(filename);

		BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
		for(Hashtable<String, ArrayList<Object>> record : set){
			for(String key : record.keySet()){
				writer.write(key + "," + record.get(key));
				writer.newLine();
			}

			writer.write("-----");
			writer.newLine();

		}

		writer.close();

	}

	public static ArrayList<Hashtable<String, ArrayList<Object>>> in_generated(String filename, int record_number, int record_step) throws Exception {
		ArrayList<Hashtable<String, ArrayList<Object>>> input_array = new ArrayList<>();
		Hashtable<String, ArrayList<Object>> input_record;
		boolean initial = true;

		File file = new File(filename);

		BufferedReader reader = new BufferedReader(new FileReader(file));

		String input = "";

		for(int i = 0; i < record_number; i++){
			while(input != null && !input.equals("-----")) {

				input = reader.readLine();

			}

			input = reader.readLine();

		}

		while(input != null && input_array.size() < record_step){

			input_record = new Hashtable<>();
			if(initial && record_number == 0){
				input = reader.readLine();
				initial = false;
			}

			while(!input.equals("-----")){
				ArrayList<Object> v = new ArrayList<>(Arrays.asList(input.split(",", 2)[1].substring(1, input.split(",", 2)[1].length() - 1).split(", ")));
				input_record.put(input.split(",", 2)[0], v);
				input = reader.readLine();

			}

			input_array.add(input_record);
			input = reader.readLine();
		}

		reader.close();

		return input_array;

	}

	public static void writeObjectValue(JsonWriter writer, Object value){
		try {
			writer.value((int) value);
		} catch(ClassCastException a){
			try {
				writer.value((double) value);
			} catch(ClassCastException b){
				try {
					writer.value((boolean) value);
				} catch(ClassCastException c){
					try {
						writer.value((String) value);
					} catch(ClassCastException d){

					} catch(Exception e){
						System.out.println(e);
					}
				} catch(Exception f){
					System.out.println(f);
				}
			} catch(Exception g){
				System.out.println(g);
			}
		} catch(Exception h){
			System.out.println(h);
		}
	}

}