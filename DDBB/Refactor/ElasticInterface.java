import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.*;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;

import java.util.ArrayList;
import java.util.Hashtable;

public class ElasticInterface implements Db {

    public DdbbConfig cfg;
    RestHighLevelClient client;

    ElasticInterface(DdbbConfig cfg){
        this.cfg = cfg;
    }

    public void connectDb(){
        client = new RestHighLevelClient(
            RestClient.builder(
                new HttpHost("localhost", 9200, "http"),
                new HttpHost("localhost", 9201, "http")));
    }

    public void disconnectDb(){
        try {
            client.close();
        } catch(Exception e){
            System.out.println(e);
        }
    }

    public void table(){

    }

    public void createTable(){

    }

    public long create(Hashtable<String,ArrayList<Object>> in){


        Long time_before = System.nanoTime();

        Long time_after = System.nanoTime();

        return time_after - time_before;

    }

    public long read(Hashtable<String,ArrayList<Object>> in){

        GetRequest getRequest = new GetRequest(String.valueOf(cfg.settings.get("db_name")).toLowerCase());
        getRequest.type(String.valueOf(cfg.settings.get("table")));
        getRequest.id("qcbKVmUBVzQgNUtIj2OP");

        String[] includes = new String[]{"a", "sdfsdf"};
        FetchSourceContext fetchSourceContext = new FetchSourceContext(true, includes, new String[]{});
        getRequest.fetchSourceContext(fetchSourceContext);

        Long time_before = System.nanoTime();
        try {
            GetResponse getResponse = client.get(getRequest);
            System.out.println("response = " + getResponse.getSourceAsString());
        } catch(Exception e){
            System.out.println(e);
        }
        Long time_after = System.nanoTime();
        return time_after - time_before;

    }

    public long update(Hashtable<String,ArrayList<Object>> in, Hashtable<String,ArrayList<Object>> up){ return Long.parseLong("0"); }

    public long delete(String key, String value){


        Long time_before = System.nanoTime();

        Long time_after = System.nanoTime();

        return time_after - time_before;

    }

    public static void main(String[] args){
        System.out.println("test");

        try {
            ElasticInterface ei = new ElasticInterface(DdbbIO.in("test_config1.json"));
            Hashtable<String,ArrayList<Object>> in = new Hashtable<>();
            ArrayList<Object> a = new ArrayList<>();
            a.add("sdfsdf");
            in.put("a", a);
            ei.connectDb();

            System.out.println("time = " + ei.read(in));
        } catch(Exception e){
            System.out.println(e);
        }

    }

}