import java.io.IOException;
import com.google.gson.*;
import com.google.gson.stream.*;

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