import java.io.IOException;
import com.google.gson.*;
import com.google.gson.stream.*;

public class DdbbReportAdapter extends TypeAdapter<DdbbReport> {

	@Override
	public DdbbReport read(JsonReader reader) throws IOException {
		DdbbReport report = new DdbbReport();
		return report;
	}

	@Override
	public void write(JsonWriter writer, DdbbReport report) throws IOException {
		writer.beginObject();
		for(String op : report.report.keySet()){
			writer.name(op);
			writer.beginObject();
			for(String type : report.report.get(op).keySet()){
				writer.name(type);
				writer.value("[" + String.join(",",(report.report.get(op).get(type))) + "]");
			}
			writer.endObject();
		}
		writer.endObject();
	}

}