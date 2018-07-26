import java.util.Hashtable;

interface Db {

	void connectDb();

	void disconnectDb();

	void table();

	void createTable();

	void create(Hashtable<Object,Object> in);

	void read(String key, String val);

	void update(Object in_key, Object in_value, Object new_key, Object new_value);

	void delete(Object key, Object value);
	
}