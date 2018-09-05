import com.datastax.driver.core.*;
import com.datastax.driver.core.exceptions.InvalidQueryException;

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
            b.withPort(Integer.valueOf(String.valueOf(cfg.settings.get("port"))));
        }
        cluster = b.build();
        session = cluster.connect(String.valueOf(cfg.settings.get("db_name")));

    }

    public void disconnectDb(){

        session.close();
        cluster.close();

    }

    public void table(){

    }

    public void createTable(){

    }

    public Long[] create(Hashtable<String,ArrayList<Object>> in){

        StringBuilder sb = new StringBuilder("INSERT INTO " + cfg.settings.get("table"));

        sb.append(" (");

        Iterator<String> it = in.keySet().iterator();
        String last_key = "";
        while(it.hasNext()){
            last_key = it.next();
        }

        int i = 0;
        for(String key : in.keySet()){

            if(in.get(key).size() > 0 || (cfg.create.data.containsKey("kv") && cfg.create.data.get("kv").get(cfg.create.data.get("name").indexOf(key)).equals("KEY"))) {
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

            int cfg_field_list_index = -1;
            if(cfg.create.data.containsKey("name") && cfg.create.data.get("name").contains(k)){
                cfg_field_list_index = cfg.create.data.get("name").indexOf(k);
            }

            if(in.get(k).size() == 1 && (cfg_field_list_index == -1 || ((boolean) cfg.create.data.get("length_up_to").get(cfg_field_list_index)))){

                if(cfg_field_list_index != -1 && cfg.create.data.get("val").get(cfg_field_list_index).equals("STRING")){
                    sb.append("'");
                }
                sb.append(String.valueOf(in.get(k).get(0)));
                if(cfg_field_list_index != -1 && cfg.create.data.get("val").get(cfg_field_list_index).equals("STRING")){
                    sb.append("'");
                }

                if(i != in.keySet().size() - 1){
                    sb.append(", ");
                }
            } else if(in.get(k).size() > 0){
                sb.append("[");
                for(int v = 0; v < in.get(k).size(); v++){

                    if(in.get(k).get(v) != null && !in.get(k).get(v).equals("null") && v != in.get(k).size() - 1){
                        if(cfg_field_list_index != -1 && cfg.create.data.get("val").get(cfg_field_list_index).equals("STRING")){

                            sb.append("'");
                            sb.append(String.valueOf(in.get(k).get(v)));
                            sb.append("', ");

                        } else {
                            sb.append(String.valueOf(in.get(k).get(v)));
                            sb.append(", ");
                        }

                    } else if(in.get(k).get(v) != null && !in.get(k).get(v).equals("null")){
                        if(cfg_field_list_index != -1 && cfg.create.data.get("val").get(cfg_field_list_index).equals("STRING")){

                            sb.append("'");
                            sb.append(String.valueOf(in.get(k).get(v)));
                            sb.append("'");

                        } else {
                            sb.append(String.valueOf(in.get(k).get(v)));
                        }
                    } else {
                        if(cfg_field_list_index != -1 && cfg.create.data.get("val").get(cfg_field_list_index).equals("STRING")){

                            sb.append("''");

                        }
                    }

                }

                sb.append("]");

                if(i != in.keySet().size() - 1){
                    sb.append(", ");
                }

            } else if(cfg.create.data.containsKey("kv") && cfg.create.data.get("kv").get(cfg_field_list_index).equals("KEY")){

                if(cfg.create.data.containsKey("val") && cfg.create.data.get("val").get(cfg_field_list_index).equals("TIMEUUID")){

                    sb.append("now()");

                    if(i != in.keySet().size() - 1){
                        sb.append(", ");
                    }

                } else if(cfg.create.data.containsKey("val") && cfg.create.data.get("val").get(cfg_field_list_index).equals("UUID")){

                }
            }

            i++;

        }

        sb.append(");");

        String query = sb.toString();

        Long time_before = System.nanoTime();
        session.execute(query);
        Long time_after = System.nanoTime();

        return new Long[]{ time_before, time_after };

    }

    public Long[] read(Hashtable<String,ArrayList<Object>> in){

        StringBuilder sb = new StringBuilder("SELECT * from " + cfg.settings.get("db_name") + "." + cfg.settings.get("table") + " where ");

        Iterator<String> it = in.keySet().iterator();

        int i = 0;
        for(String key : in.keySet()){

            int cfg_field_list_index = -1;

            if(cfg.create.data.contains("name") && cfg.create.data.get("name").contains(key)){
                cfg_field_list_index = cfg.create.data.get("name").indexOf(key);
            }

            sb.append(key + " = ");

            if(in.get(key).size() == 1 && (cfg_field_list_index == -1 || (boolean) cfg.read.data.get("length_up_to").get(cfg_field_list_index))){

                if(cfg_field_list_index != -1 && cfg.read.data.get("val").get(cfg_field_list_index).equals("STRING")){
                    sb.append("'");

                }

                sb.append(String.valueOf(in.get(key).get(0)));

                if(cfg_field_list_index != -1 && cfg.read.data.get("val").get(cfg_field_list_index).equals("STRING")){
                    sb.append("'");
                }

            } else if(in.get(key).size() > 0){

                sb.append("[");

                for(int v = 0; v < in.get(key).size(); v++){

                    if(in.get(key).get(v) != null && !in.get(key).get(v).equals("null") && v != in.get(key).size() - 1){
                        if(cfg_field_list_index != -1 && cfg.read.data.get("val").get(cfg_field_list_index).equals("STRING")){

                            sb.append("'");
                            sb.append(String.valueOf(in.get(key).get(v)));
                            sb.append("'");

                            if(in.get(key).size() > 1 && v < in.get(key).size() - 1){
                                sb.append(", ");
                            }


                        } else {
                            sb.append(String.valueOf(in.get(key).get(v)));
                            sb.append(", ");

                            if(in.get(key).size() > 1 && v < in.get(key).size() - 1){
                                sb.append(", ");
                            }
                        }

                    } else if(in.get(key).get(v) != null && !in.get(key).get(v).equals("null")){
                        if(cfg_field_list_index != -1 && cfg.read.data.get("val").get(cfg_field_list_index).equals("STRING")){

                            sb.append("'");
                            sb.append(String.valueOf(in.get(key).get(v)));
                            sb.append("'");

                        } else {
                            sb.append(String.valueOf(in.get(key).get(v)));
                        }
                    } else {
                        if(cfg_field_list_index != -1 && cfg.read.data.get("val").get(cfg_field_list_index).equals("STRING")){

                            sb.append("''");

                        }
                    }

                }

                sb.append("]");

            }

            if(in.keySet().size() > 1 && i < in.keySet().size() - 1){
                sb.append(" AND ");
            }
            i++;

        }

        sb.append(";");

        String query = sb.toString();

        Long time_before = new Long(0);
        Long time_after = new Long(-1);

        try {

            time_before = System.nanoTime();
            session.execute(query);
            time_after = System.nanoTime();

        } catch(InvalidQueryException e){

            if(e.toString().equals("com.datastax.driver.core.exceptions.InvalidQueryException: Cannot execute this query as it might involve data filtering and thus may have unpredictable performance. If you want to execute this query despite the performance unpredictability, use ALLOW FILTERING")){

                sb.deleteCharAt(sb.toString().length() - 1);
                sb.append(" ALLOW FILTERING;");
                query = sb.toString();

                time_before = System.nanoTime();
                session.execute(query);
                time_after = System.nanoTime();

            }

        }

        return new Long[]{ time_before, time_after };

    }

    public Long[] update(Hashtable<String,ArrayList<Object>> in, Hashtable<String,ArrayList<Object>> up){


        return new Long[]{ new Long(0), new Long(0) };

    }

    public Long[] delete(String key, String value){

        int cfg_field_list_index = -1;

        if(cfg.create.data.contains("name") && cfg.create.data.get("name").contains(key)){
            cfg_field_list_index = cfg.create.data.get("name").indexOf(key);
        }

        StringBuilder sb;
        if(cfg_field_list_index != -1 && cfg.delete.data.get("val").get(cfg.delete.data.get("name").indexOf(key)).equals("STRING")) {
            sb = new StringBuilder(
                    "DELETE from " + cfg.settings.get("db_name") + "." + cfg.settings.get("table") +
                            " where " + key + " = '" + value + "';");
        } else {
            sb = new StringBuilder(
                    "DELETE from " + cfg.settings.get("db_name") + "." + cfg.settings.get("table") +
                            " where " + key + " = " + value + ";");
        }

        String query = sb.toString();
        Long time_before = System.nanoTime();
        session.execute(query);
        Long time_after = System.nanoTime();

        return new Long[]{ time_before, time_after };

    }

    public static void main(String[] args){
        System.out.println("test");
    }

}