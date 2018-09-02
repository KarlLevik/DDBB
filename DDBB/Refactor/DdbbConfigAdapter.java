import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class DdbbConfigAdapter extends TypeAdapter<DdbbConfig> {

    @Override
    public DdbbConfig read(JsonReader reader) throws IOException {
        DdbbConfig cfg = new DdbbConfig();
        return cfg;
    }

    @Override
    public void write(JsonWriter writer, DdbbConfig cfg) throws IOException {
        writer.beginObject();

        writer.name("settings");
        writer.beginObject();
        for(String setting : cfg.settings.keySet()){
            writer.name(setting);
            DdbbIO.writeObjectValue(writer, cfg.settings.get(setting));
        }
        writer.endObject();

        writer.name("setup");
        writer.beginObject();
        writer.name("meta");
        writer.beginObject();
        for(String setting : cfg.setup.meta.keySet()){
            writer.name(setting);
            DdbbIO.writeObjectValue(writer, cfg.setup.meta.get(setting));
        }
        writer.endObject();

        writer.name("data");
        writer.beginObject();
        for(String setting : cfg.setup.data.keySet()){
            writer.name(setting);
            writer.beginArray();
            for(int setting_index = 0; setting_index < cfg.setup.data.get(setting).size(); setting_index++){
                DdbbIO.writeObjectValue(writer, cfg.setup.data.get(setting).get(setting_index));
            }
            writer.endArray();

        }
        writer.endObject();

        writer.endObject();



        writer.name("create");
        writer.beginObject();
        writer.name("meta");
        writer.beginObject();
        for(String setting : cfg.create.meta.keySet()){
            writer.name(setting);
            DdbbIO.writeObjectValue(writer, cfg.create.meta.get(setting));
        }
        writer.endObject();

        writer.name("data");
        writer.beginObject();
        for(String setting : cfg.create.data.keySet()){
            writer.name(setting);
            writer.beginArray();
            for(int setting_index = 0; setting_index < cfg.create.data.get(setting).size(); setting_index++){
                DdbbIO.writeObjectValue(writer, cfg.create.data.get(setting).get(setting_index));
            }
            writer.endArray();

        }
        writer.endObject();

        writer.endObject();



        writer.name("read");
        writer.beginObject();
        writer.name("meta");
        writer.beginObject();
        for(String setting : cfg.read.meta.keySet()){
            writer.name(setting);
            DdbbIO.writeObjectValue(writer, cfg.create.meta.get(setting));
        }
        writer.endObject();

        writer.name("data");
        writer.beginObject();
        for(String setting : cfg.read.data.keySet()){
            writer.name(setting);
            writer.beginArray();
            for(int setting_index = 0; setting_index < cfg.read.data.get(setting).size(); setting_index++){
                DdbbIO.writeObjectValue(writer, cfg.read.data.get(setting).get(setting_index));
            }
            writer.endArray();

        }
        writer.endObject();

        writer.endObject();



        writer.name("update");
        writer.beginObject();
        writer.name("meta");
        writer.beginObject();
        for(String setting : cfg.update.meta.keySet()){
            writer.name(setting);
            DdbbIO.writeObjectValue(writer, cfg.update.meta.get(setting));
        }
        writer.endObject();

        writer.name("data");
        writer.beginObject();
        for(String setting : cfg.update.data.keySet()){
            writer.name(setting);
            writer.beginArray();
            for(int setting_index = 0; setting_index < cfg.update.data.get(setting).size(); setting_index++){
                DdbbIO.writeObjectValue(writer, cfg.update.data.get(setting).get(setting_index));
            }
            writer.endArray();

        }
        writer.endObject();

        writer.endObject();



        writer.name("delete");
        writer.beginObject();
        writer.name("meta");
        writer.beginObject();
        for(String setting : cfg.delete.meta.keySet()){
            writer.name(setting);
            DdbbIO.writeObjectValue(writer, cfg.delete.meta.get(setting));
        }
        writer.endObject();

        writer.endObject();

        writer.name("data");
        writer.beginObject();
        for(String setting : cfg.delete.data.keySet()){
            writer.name(setting);
            writer.beginArray();
            for(int setting_index = 0; setting_index < cfg.delete.data.get(setting).size(); setting_index++){
                DdbbIO.writeObjectValue(writer, cfg.delete.data.get(setting).get(setting_index));
            }
            writer.endArray();

        }
        writer.endObject();

        writer.endObject();
    }

}