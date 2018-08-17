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

        System.out.println("session = " + cfg.settings.get("db_name"));
        session = cluster.connect((String) cfg.settings.get("db_name"));
        System.out.println("here");

    }

    public void disconnectDb(){

        session.close();
        cluster.close();

    }

    public void table(){

    }

    public void createTable(){

    }

    public long create(Hashtable<String,ArrayList<Object>> in){

        StringBuilder sb = new StringBuilder("INSERT INTO " + cfg.settings.get("table"));

        sb.append(" (");

        Iterator<String> it = in.keySet().iterator();
        String last_key = "";
        while(it.hasNext()){
            last_key = it.next();
        }

        int i = 0;
        for(String key : in.keySet()){

            if(in.get(key).size() > 0) {
                sb.append(key);

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

            int cfg_field_list_index = cfg.create.data.get("name").indexOf(k);

            System.out.println("1");
            System.out.println("size = " + in.get(k).size());
            if(in.get(k).size() == 1 && (boolean) cfg.create.data.get("length_up_to").get(cfg_field_list_index)){

                System.out.println("2");

                if(cfg.create.data.get("val").get(cfg_field_list_index).equals("STRING")){
                    sb.append("'");

                    System.out.println("3");
                }
                System.out.println("out = " + in.get(k));
                sb.append(String.valueOf(in.get(k).get(0)));
                if(cfg.create.data.get("val").get(cfg_field_list_index).equals("STRING")){
                    sb.append("'");
                }

                if(i != in.keySet().size() - 1){
                    sb.append(", ");
                }
            } else if(in.get(k).size() > 0){
                sb.append("[");
                for(int v = 0; v < in.get(k).size(); v++){

                    if(in.get(k).get(v) != null && !in.get(k).get(v).equals("null") && v != in.get(k).size() - 1){
                        if(cfg.create.data.get("val").get(cfg_field_list_index).equals("STRING")){

                            sb.append("'");
                            sb.append(String.valueOf(in.get(k).get(v)));
                            sb.append("', ");

                        } else {
                            sb.append(String.valueOf(in.get(k).get(v)));
                            sb.append(", ");
                        }

                    } else if(in.get(k).get(v) != null && !in.get(k).get(v).equals("null")){
                        if(cfg.create.data.get("val").get(cfg_field_list_index).equals("STRING")){

                            sb.append("'");
                            sb.append(String.valueOf(in.get(k).get(v)));
                            sb.append("'");

                        } else {
                            sb.append(String.valueOf(in.get(k).get(v)));
                        }
                    } else {
                        if(cfg.create.data.get("val").get(cfg_field_list_index).equals("STRING")){

                            sb.append("''");

                        }
                    }

                }

                sb.append("]");

                if(i != in.keySet().size() - 1){
                    sb.append(", ");
                }

            }

            i++;

        }

        sb.append(");");

        String query = sb.toString();
        System.out.println("query = " + query);
        Long time_before = System.nanoTime();
        session.execute(query);
        Long time_after = System.nanoTime();

        return time_after - time_before;

    }

    public long read(Hashtable<String,ArrayList<Object>> in){

        StringBuilder sb = new StringBuilder("SELECT * from " + cfg.settings.get("db_name") + "." + cfg.settings.get("table") + " where ");

        Iterator<String> it = in.keySet().iterator();
        String last_key = "";
        while(it.hasNext()){
            last_key = it.next();
        }

        int i = 0;
        for(String key : in.keySet()){

            sb.append(key + " = ");











            int cfg_field_list_index = cfg.read.data.get("name").indexOf(key);

            if(in.get(key).size() == 1 && (boolean) cfg.read.data.get("length_up_to").get(cfg_field_list_index)){

                System.out.println("2");

                if(cfg.read.data.get("val").get(cfg_field_list_index).equals("STRING")){
                    sb.append("'");

                    System.out.println("3");
                }
                System.out.println("out = " + in.get(key));
                sb.append(String.valueOf(in.get(key).get(0)));
                if(cfg.read.data.get("val").get(cfg_field_list_index).equals("STRING")){
                    sb.append("'");
                }

                if(i != in.keySet().size() - 1){
                    sb.append(", ");
                }
            } else if(in.get(key).size() > 0){
                sb.append("[");
                for(int v = 0; v < in.get(key).size(); v++){

                    if(in.get(key).get(v) != null && !in.get(key).get(v).equals("null") && v != in.get(key).size() - 1){
                        if(cfg.read.data.get("val").get(cfg_field_list_index).equals("STRING")){

                            sb.append("'");
                            sb.append(String.valueOf(in.get(key).get(v)));
                            sb.append("', ");

                        } else {
                            sb.append(String.valueOf(in.get(key).get(v)));
                            sb.append(", ");
                        }

                    } else if(in.get(key).get(v) != null && !in.get(key).get(v).equals("null")){
                        if(cfg.read.data.get("val").get(cfg_field_list_index).equals("STRING")){

                            sb.append("'");
                            sb.append(String.valueOf(in.get(key).get(v)));
                            sb.append("'");

                        } else {
                            sb.append(String.valueOf(in.get(key).get(v)));
                        }
                    } else {
                        if(cfg.read.data.get("val").get(cfg_field_list_index).equals("STRING")){

                            sb.append("''");

                        }
                    }

                }

                sb.append("]");

                if(i != in.keySet().size() - 1){
                    sb.append(", ");
                }

            }











            if(in.keySet().size() > 1 && i < in.keySet().size() - 1){
                sb.append(", ");
            }

        }

        if(i == in.keySet().size() - 1 && cfg.read.meta.containsKey("allow_filtering")){
            if((boolean) cfg.read.meta.get("allow_filtering")) {
                sb.append(" ALLOW FILTERING");
            }
        }

        sb.append(";");

        String query = sb.toString();
        System.out.println("query = " + query);
        Long time_before = System.nanoTime();
        session.execute(query);
        Long time_after = System.nanoTime();

        return time_after - time_before;

    }

    public long update(Hashtable<String,ArrayList<Object>> in, Hashtable<String,ArrayList<Object>> up){ return Long.parseLong("0"); }

    public long delete(String key, String value){
        return Long.parseLong("0");
    }

    public static void main(String[] args){
        System.out.println("test");
    }

}