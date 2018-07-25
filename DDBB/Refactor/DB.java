import java.util.Hashtable;

interface DB {

	void connectDb();

	void disconnectDB();

	void table();

	void create_table();

	void create(Hashtable<Object,Object> in);

	void read(String key, String val);

	void update(Object in_key, Object in_value, Object new_key, Object new_value);

	void delete(Object key, Object value);
	
}