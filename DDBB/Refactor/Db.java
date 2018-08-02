import java.util.Hashtable;
import java.util.ArrayList;

interface Db {

	void connectDb();

	void disconnectDb();

	void table();

	void createTable();

	void create(Hashtable<String,ArrayList<Object>> in);

	void read(Hashtable<String,ArrayList<Object>> in);

	void update(Hashtable<String,ArrayList<Object>> in, Hashtable<String,ArrayList<Object>> up);

	void delete(Hashtable<String,ArrayList<Object>> in);
	
}