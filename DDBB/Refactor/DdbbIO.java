import com.google.gson.*;
import com.google.gson.stream.*;
import java.util.*;

import java.io.*;

public class DdbbIO {

	public static Hashtable<String,String> in2(String filename) throws Exception {

		Hashtable<String,String> settings = new Hashtable<String,String>();

		Gson g = new Gson();
		BufferedReader reader = new BufferedReader(new FileReader(filename));

		settings = g.fromJson(reader, Hashtable.class);

		reader.close();

		return settings;

	}

	public static DdbbConfig in(String filename) throws Exception {

		DdbbConfig cfg = new DdbbConfig();

		JsonReader reader = new JsonReader(new FileReader(filename));
		reader.beginObject();

		while(reader.hasNext()) {

			String name = reader.nextName();

			if (name.equals("settings")) {

				reader.beginObject();

				while (reader.hasNext()) {
					name = reader.nextName();
					if(reader.peek() == JsonToken.STRING){
						String val = reader.nextString();
						cfg.settings.put(name, val);
					} else if(reader.peek() == JsonToken.NUMBER){
						cfg.settings.put(name, reader.nextDouble());
					} else if(reader.peek() == JsonToken.BOOLEAN){
						cfg.settings.put(name, reader.nextBoolean());
					} else {
						reader.skipValue();
						System.out.println("SKIPPED META OF INVALID TYPE!");
					}
				}


				reader.endObject();

			} else if (name.equals("generate")) {

				reader.beginObject();

				while(reader.hasNext()){

					name = reader.nextName();

					if (name.equals("meta")) {

						reader.beginObject();

						while (reader.hasNext()) {
							name = reader.nextName();
							if(reader.peek() == JsonToken.STRING){
								cfg.generate.meta.put(name, reader.nextString());
							} else if(reader.peek() == JsonToken.NUMBER){
								cfg.generate.meta.put(name, reader.nextDouble());
							} else if(reader.peek() == JsonToken.BOOLEAN){
								cfg.generate.meta.put(name, reader.nextBoolean());
							} else {
								reader.skipValue();
								System.out.println("SKIPPED META OF INVALID TYPE!");
							}
						}

						reader.endObject();

					} else if(name.equals("data")) {

						reader.beginObject();

						while (reader.hasNext()) {
							name = reader.nextName();
							ArrayList<Object> list = new ArrayList<Object>();
							reader.beginArray();
							while(reader.hasNext()){
								if(reader.peek() == JsonToken.STRING){
									list.add(reader.nextString());
								} else if(reader.peek() == JsonToken.NUMBER){
									list.add(reader.nextDouble());
								} else if(reader.peek() == JsonToken.BOOLEAN){
									list.add(reader.nextBoolean());
								} else {
									reader.skipValue();
									System.out.println("SKIPPED VALUE OF INVALID TYPE!");
								}
							}
							reader.endArray();
							cfg.generate.data.put(name, list);
						}

						reader.endObject();
					}
				}

				reader.endObject();

			} else if (name.equals("create")) {

				reader.beginObject();
				while(reader.hasNext()) {

					name = reader.nextName();

					if (name.equals("meta")) {

						reader.beginObject();

						while (reader.hasNext()) {
							name = reader.nextName();
							if(reader.peek() == JsonToken.STRING){
								cfg.create.meta.put(name, reader.nextString());
							} else if(reader.peek() == JsonToken.NUMBER){
								cfg.create.meta.put(name, reader.nextDouble());
							} else if(reader.peek() == JsonToken.BOOLEAN){
								cfg.create.meta.put(name, reader.nextBoolean());
							} else {
								reader.skipValue();
								System.out.println("SKIPPED META OF INVALID TYPE!");
							}
						}

						reader.endObject();

					} else if(name.equals("data")) {

						reader.beginObject();

						while (reader.hasNext()) {
							name = reader.nextName();
							ArrayList<Object> list = new ArrayList<Object>();
							reader.beginArray();
							while(reader.hasNext()){
								if(reader.peek() == JsonToken.STRING){
									list.add(reader.nextString());
								} else if(reader.peek() == JsonToken.NUMBER){
									list.add(reader.nextDouble());
								} else if(reader.peek() == JsonToken.BOOLEAN){
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

			} else if (name.equals("read")) {

				reader.beginObject();
				while(reader.hasNext()) {

					name = reader.nextName();

					if (name.equals("meta")) {

						reader.beginObject();

						while (reader.hasNext()) {
							name = reader.nextName();
							if (reader.peek() == JsonToken.STRING) {
								cfg.read.meta.put(name, reader.nextString());
							} else if (reader.peek() == JsonToken.NUMBER) {
								cfg.read.meta.put(name, reader.nextDouble());
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
							ArrayList<Object> list = new ArrayList<Object>();
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

			} else if (name.equals("update")) {

				reader.beginObject();
				while(reader.hasNext()) {

					name = reader.nextName();

					if (name.equals("meta")) {

						reader.beginObject();

						while (reader.hasNext()) {
							name = reader.nextName();
							if (reader.peek() == JsonToken.STRING) {
								cfg.update.meta.put(name, reader.nextString());
							} else if (reader.peek() == JsonToken.NUMBER) {
								cfg.update.meta.put(name, reader.nextDouble());
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
							ArrayList<Object> list = new ArrayList<Object>();
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

			} else if (name.equals("delete")) {

				reader.beginObject();
				while(reader.hasNext()) {

					name = reader.nextName();

					if (name.equals("meta")) {

						reader.beginObject();

						while (reader.hasNext()) {
							name = reader.nextName();
							if (reader.peek() == JsonToken.STRING) {
								cfg.delete.meta.put(name, reader.nextString());
							} else if (reader.peek() == JsonToken.NUMBER) {
								cfg.delete.meta.put(name, reader.nextDouble());
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
							ArrayList<Object> list = new ArrayList<Object>();
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

			} else {
				reader.skipValue(); //avoid some unhandle events
				System.out.println("SKIPPED DATA WITH INVALID TAG!");
			}
		}

		reader.endObject();
		reader.close();


		return cfg;

	}

	public static void out(String filename, DdbbReport report) throws Exception {

		System.out.println("8");
		Writer writer = new FileWriter(filename);
		Gson g = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(DdbbReport.class, new DdbbReportAdapter()).create();

		g.toJson(report, writer);

		writer.close();

	}

	public static void main(String[] args) {
		try {
            /*DdbbProperty test = in("/home/skace/IdeaProjects/DDBB/src/test_config3.json");
            System.out.println("test = " + test);
            Hashtable<String,ArrayList<String>> test2 = in2("/home/skace/IdeaProjects/DDBB/src/test_config3.json");
            System.out.println("test2 = " + test2);
            //System.out.println("testB = " + test2.get("generate").get("meta").get("save"));
            */
			DdbbConfig test = in("/home/skace/IdeaProjects/DDBB/src/test_config3.json");
			System.out.println("test3 = " + test);

		} catch(Exception e){
			System.out.println(e);
		}
	}

}