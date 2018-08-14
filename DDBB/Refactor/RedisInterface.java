import java.util.*;
import redis.clients.jedis.Jedis;

public class RedisInterface implements Db {

    public Jedis jedis;
    public DdbbConfig cfg;
    public String list = "";

    RedisInterface(DdbbConfig cfg){
        this.cfg = cfg;
    }

    public void connectDb() {
        jedis = new Jedis( (this.cfg.settings.get("ip")).toString(), (int) this.cfg.settings.get("port"));
    }

    public void disconnectDb() {

    }

    public void table(){
        list = (String) cfg.settings.get("table") + ".";
    }

    public void createTable(){

    }

    public long create(Hashtable<String,ArrayList<Object>> in){
        long time_before = 0;
        long time_after = 0;
        long time = 0;

        Integer amount = in.keySet().size();
        String rand_key = (in.keySet().toArray()[(new Random()).nextInt(amount)]).toString();

        Map<String, String> hash_single = new HashMap<>();
        for(String k : in.keySet()){
            if((in.get(k)).size() == 1){
                hash_single.put(k, (in.get(k).get(0)).toString());
            }
        }

        Map<String, ArrayList<String>> hash_multi = new HashMap<>();
        for(String k : in.keySet()){
            if((in.get(k)).size() > 1){

                ArrayList<String> k_list = new ArrayList<>();

                if(hash_multi.containsKey(k)){
                    k_list = hash_multi.get(k);
                }

                for(int i = 0; i < in.get(k).size(); i++) {
                    k_list.add(((in.get(k)).get(i)).toString());
                }
                hash_multi.put(k, k_list);
            }
        }

        if(!list.equals("null") && !list.equals("null.") && !list.equals(".") && list != null) {
            time_before = System.nanoTime();
            jedis.hmset(list, hash_single);
            time_after = System.nanoTime();
            time = time + (time_after - time_before);
        }

        if(hash_multi.size() > 0){
            for(String k : hash_multi.keySet()) {
                for(String v : hash_multi.get(k)){
                    time_before = System.nanoTime();
                    jedis.sadd(list + k, v);
                    time_after = System.nanoTime();
                    time = time + (time_after - time_before);
                }

            }
        }

        return time;
    }

    public long read(Hashtable<String,ArrayList<Object>> in){
        return Long.parseLong("0");
    }

    public long update(Hashtable<String,ArrayList<Object>> in, Hashtable<String,ArrayList<Object>> up){
        return Long.parseLong("0");
    }

    public long delete(String key, String value){
        return Long.parseLong("0");
    }

    public static void main(String[] args){
        System.out.println("test");
    }

}