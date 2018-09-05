
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

    public Long[] create(Hashtable<String,ArrayList<Object>> in){

        return new Long[]{ new Long(0), new Long(0) };

    }

    public Long[] read(Hashtable<String,ArrayList<Object>> in){

        return new Long[]{ new Long(0), new Long(0) };

    }

    public Long[] update(Hashtable<String,ArrayList<Object>> in, Hashtable<String,ArrayList<Object>> up){

        return new Long[]{ new Long(0), new Long(0) };

    }

    public Long[] delete(String key, String value){

        return new Long[]{ new Long(0), new Long(0) };

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