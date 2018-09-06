
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

    public String create(Hashtable<String,ArrayList<Object>> in){

        return "";

    }

    public String read(Hashtable<String,ArrayList<Object>> in){

        return "";

    }

    public String update(Hashtable<String,ArrayList<Object>> in, Hashtable<String,ArrayList<Object>> up){

        return "";

    }

    public String delete(String key, String value){

        return "";

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