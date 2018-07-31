import java.util.Hashtable;
import redis.clients.jedis.Jedis;

public class RedisInterface implements Db {

    public Jedis jedis;
    public Hashtable<String,String> cfg = new Hashtable<String,String>();

    RedisInterface(Hashtable<String,String> cfg){
        this.cfg = cfg;
    }

    public void connectDb(){
        jedis = new Jedis(this.cfg.get("ip"),this.cfg.get("port"));
    }

    public void disconnectDb(){

    }

    public void table(){

    }

    public void createTable(){

    }

    public void create(Hashtable<Object,Object> in){

    }

    public void read(String key, String val){

    }

    public void update(Object in_key, Object in_value, Object new_key, Object new_value) {

    }

    public void delete(Object key, Object value) {

    }

    public static void main(String[] args){
        System.out.println("test");
    }

}