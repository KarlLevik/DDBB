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

    public void create(Hashtable<String,ArrayList<Object>> in){
        System.out.println("RedisInterface");
    }

    public void read(Hashtable<String,ArrayList<Object>> in, String field){
        System.out.println("RedisInterface");
    }

    public void update(Hashtable<String,ArrayList<Object>> in, Hashtable<String,ArrayList<Object>> up){
        System.out.println("RedisInterface");
    }

    public void delete(Hashtable<String,ArrayList<Object>> in, String field){
        System.out.println("RedisInterface");
    }

    public static void main(String[] args){
        System.out.println("test");
    }

}