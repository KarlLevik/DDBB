import java.util.Hashtable;

interface DB {

	void connectDb(Hashtable<String,Object> options);

	void disconnectDB(Object db);

	void table(Object pr, Object table);

	void create_table(Object options);

	void create(Object in);

	Object read(Object in);

	void update(Object in, Object new_in);

	void delete(Object in);
	
}