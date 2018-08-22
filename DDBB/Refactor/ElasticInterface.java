
import java.util.ArrayList;
import java.util.Hashtable;

public class ElasticInterface implements Db {

    public DdbbConfig cfg;

    ElasticInterface(DdbbConfig cfg){
        this.cfg = cfg;
    }

    public void connectDb(){

    }

    public void disconnectDb(){

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

        long time_before = 0;

        return time_before;

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