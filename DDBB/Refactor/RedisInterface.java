import java.util.Hashtable;
import java.util.ArrayList;
//import redis.clients.jedis.Jedis;

public class RedisInterface implements Db {

    //public Jedis jedis;
    //public Hashtable<String,String> cfg = new Hashtable<String,String>();

    //RedisInterface(Hashtable<String,String> cfg){
        //this.cfg = cfg;
    //}

    public void connectDb(){

        //jedis = new Jedis(this.cfg.get("ip"),this.cfg.get("port"));
    }

    public void disconnectDb(){

    }

    public void table(){

    }

    public void createTable(){

    }

    public long create(Hashtable<String,ArrayList<Object>> in){
        return Long.parseLong("0");
    }

    public long read(Hashtable<String,ArrayList<Object>> in, String field){ return Long.parseLong("0"); }

    public long update(Hashtable<String,ArrayList<Object>> in, Hashtable<String,ArrayList<Object>> up){ return Long.parseLong("0"); }

    public long delete(Hashtable<String,ArrayList<Object>> in, String field){ return Long.parseLong("0"); }

    public static void main(String[] args){
        System.out.println("test");
    }

}