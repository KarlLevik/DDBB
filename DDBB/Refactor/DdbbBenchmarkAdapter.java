import java.io.IOException;
import com.google.gson.*;
import com.google.gson.stream.*;
import java.util.*;

public class DdbbBenchmarkAdapter extends TypeAdapter<DdbbBenchmark> {

    @Override
    public DdbbBenchmark read(JsonReader reader) throws IOException {

        DdbbBenchmark bench = new DdbbBenchmark();
        return bench;

    }

    @Override
    public void write(JsonWriter writer, DdbbBenchmark bench) throws IOException {
        writer.beginObject();

        writer.name("Configuration");
        writer.beginObject();
        for(String cfg_s : bench.cfg.keySet()){
            writer.name(cfg_s);
            writer.value(bench.cfg.get(cfg_s));
        }
        writer.endObject();

        writer.name("Tests");
        writer.beginObject();
        for(String test_s : bench.tests.keySet()) {
            writer.name(test_s);
            writer.beginObject();
            writer.name("Test Configuration");
            writer.beginObject();
            for(String test_cfg_s : bench.tests.get(test_s).cfg.keySet()){
                writer.name(test_s);
                writer.value(bench.tests.get(test_s).cfg.get(test_cfg_s));
            }
            writer.endObject();
            writer.name("Test Report");
            writer.beginObject();

            for(String op : bench.tests.get(test_s).report.report.keySet()){
                writer.name(op);
                writer.beginObject();
                for(String type : bench.tests.get(test_s).report.report.get(op).keySet()){
                    writer.name(type);

                    writer.beginArray();
                    for(String res : (bench.tests.get(test_s).report.report.get(op).get(type))){

                        writer.value(res);

                    }
                    /*
                    for(String res : (bench.tests.get(test_s).report.report.get(op).get(type))){
                        Pattern pattern = Pattern.compile("[.*]",Pattern.CASE_INSENSITIVE);
                        Matcher m = pattern.matcher(res);
                        if(m.matches()){
                            res = res.trim();

                        } else {
                            writer.value(res);
                        }

                    }
                    */
                    writer.endArray();

                }
                writer.endObject();
            }
            writer.endObject();

            writer.endObject();
        }
        writer.endObject();
        writer.endObject();
    }

}