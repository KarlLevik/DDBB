import java.util.Hashtable;
import java.util.ArrayList;

interface Db {

	void connectDb();

	void disconnectDb();

	void table();

	void createTable();

	long create(Hashtable<String,ArrayList<Object>> in);

	long read(Hashtable<String,ArrayList<Object>> in);

	long update(Hashtable<String,ArrayList<Object>> in, Hashtable<String,ArrayList<Object>> up);

	long delete(String key, String value);
	
}