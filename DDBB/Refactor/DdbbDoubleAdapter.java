import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class DdbbDoubleAdapter extends TypeAdapter<Double> {

	@Override
	public Double read(JsonReader reader) throws IOException {
		reader.nextNull();
		return null;
	}

	@Override
	public void write(JsonWriter writer, Double d) throws IOException {
		writer.nullValue();
	}

}