import java.util.ArrayList;
import java.util.Hashtable;

interface Db {

	void connectDb();

	void disconnectDb();

	void table();

	void createTable();

	String create(Hashtable<String, ArrayList<Object>> in);

	String read(Hashtable<String, ArrayList<Object>> in);

	String update(Hashtable<String, ArrayList<Object>> in, Hashtable<String, ArrayList<Object>> up);

	String delete(String key, String value);
	
}