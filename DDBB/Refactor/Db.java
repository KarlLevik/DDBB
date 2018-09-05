import java.util.ArrayList;
import java.util.Hashtable;

interface Db {

	void connectDb();

	void disconnectDb();

	void table();

	void createTable();

	Long[] create(Hashtable<String, ArrayList<Object>> in);

	Long[] read(Hashtable<String, ArrayList<Object>> in);

	Long[] update(Hashtable<String, ArrayList<Object>> in, Hashtable<String, ArrayList<Object>> up);

	Long[] delete(String key, String value);
	
}