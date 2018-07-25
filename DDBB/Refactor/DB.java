import java.util.Hashtable;

interface DB {

	void connectDb();

	void disconnectDB();

	void table();

	void create_table();

	void create(Object in);

	void read(String key, String val);

	void update(Object in, Object new_in);

	void delete(Object in);
	
}