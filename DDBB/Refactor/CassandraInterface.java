import com.datastax.driver.core.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

public class CassandraInterface implements Db {

    public DdbbConfig cfg;
    public Cluster cluster;
    public Session session;
    public Cluster.Builder b;

    CassandraInterface(DdbbConfig cfg){
        this.cfg = cfg;
    }

    public void connectDb(){

        b = Cluster.builder().addContactPoint((String) cfg.settings.get("ip"));
        if (cfg.settings.containsKey("port")) {
            b.withPort((int) cfg.settings.get("port"));
        }
        cluster = b.build();

        session = cluster.connect((String) cfg.settings.get("db_name"));

    }

    public void disconnectDb(){

    }

    public void table(){

    }

    public void createTable(){

    }

    public long create(Hashtable<String,ArrayList<Object>> in){

        StringBuilder sb = new StringBuilder("INSERT INTO " + cfg.settings.get("table"));

        sb.append("(");

        int i = 0;
        for(String key : in.keySet()){

            if(in.get(key).size() > 0) {
                sb.append(key);

                String last_key = "";
                Iterator<String> it = in.keySet().iterator();
                while(it.hasNext()){
                    last_key = it.next();
                }
                if(i != in.keySet().size() - 1 || (i == in.keySet().size() - 2 && in.get(last_key).size() > 0)){
                    sb.append(", ");
                }

            }

            i++;

        }

        sb.append(") ");
        sb.append("VALUES (");

        i = 0;
        for(String k : in.keySet()){

            if(in.get(k).size() > 1){
                sb.append("[");
                for(int v = 0; v < in.get(k).size(); v++){

                    if(cfg.create.data.get("type").get(i) == "STRING"){
                        sb.append("'");
                    }
                    sb.append(String.valueOf(in.get(v)));
                    if(cfg.create.data.get("type").get(i) == "STRING"){
                        sb.append("'");
                    }

                    if(i != in.keySet().size() - 1){
                        sb.append(", ");
                    }

                }
                sb.append("]");
            }

            i++;

        }

        String query = sb.toString();
        System.out.println("query = " + query);
        Long time_before = System.nanoTime();
        session.execute(query);
        Long time_after = System.nanoTime();

        return time_after - time_before;

    }

    public long read(Hashtable<String,ArrayList<Object>> in){

        session.execute("select * from myKeyspace.myTable where id = 1").one();

        return Long.parseLong("0");

    }

    public long update(Hashtable<String,ArrayList<Object>> in, Hashtable<String,ArrayList<Object>> up){ return Long.parseLong("0"); }

    public long delete(String key, String value){
        return Long.parseLong("0");
    }

    public static void main(String[] args){
        System.out.println("test");
    }

}