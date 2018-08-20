import java.util.Hashtable;
import java.util.Random;

public class DdbbTool {

	// Method to randomly generate a random string
	public static String generateRandomString(Integer length, Boolean use_plain){

		Random r = new Random(System.nanoTime());
		String rng_val = "";
		// Allows you to change which symbols are in the alphabet for the random string generation
		String alphabet = use_plain ? "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ" :
			"1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
			+ "`,./;#[]-=¬!£$%^&*()<>?:@~{}_+";

		while(rng_val.length() != length){

			rng_val = rng_val + alphabet.charAt(r.nextInt(alphabet.length()));

		}

		return rng_val;
	
	}

	// Method to randomly generate a random string
	public static boolean generateRandomBoolean(){

		Random r = new Random(System.nanoTime());
		Boolean rng_val;


		rng_val = r.nextBoolean();

		return rng_val;

	}

	// Method to randomly generate a random string
	public static Integer generateRandomInteger(Integer size, boolean size_up_to){

		Random r = new Random(System.nanoTime());
		Integer rng_val;

		Integer roof = size;
		Integer floor = size_up_to ? 10 ^ (size.toString().length()-1) : 0;

		rng_val = floor + r.nextInt(roof - floor);

		return rng_val;

	}

	// Method to randomly generate a random string
	public static Double generateRandomDouble(Double size, Boolean size_up_to){

		Random r = new Random(System.nanoTime());
		Double rng_val;

		Double roof = size;
		Integer floor = size_up_to ? 10 ^ (size.toString().length()-1) : 0;

		rng_val = floor + r.nextDouble() * roof - floor;

		return rng_val;

	}

	// Returns the runtime, often of a test, a benchmark or a session
	public static long runtime(Long start_time){

		return (System.nanoTime() - start_time);
	
	}

	// Returns the key of an object inside a hashtable
	// presence of object inside hashtable should be checked before calling getKey
	public static Object getKey(Hashtable hs, Object v){

		Object result = new Object();

		if(hs.contains(v)){
			for(Object key : hs.keySet()){
				if(hs.get(key) == v){
					result = key;
				}
			}

		} else {
			result = null;
		}

		return result;

	}

	// Returns the key of an object inside a hashtable
	// presence of object inside hashtable should be checked before calling getKey
	public static int getIndex(Hashtable hs, String k){

		int index = 0;

		int i = 0;
		if(hs.containsKey(k)){
			for(Object key : hs.keySet()){
				System.out.println("key = " + key + ", k = " + k + ", i = " + i);
				if(key == k){
					index = i;
					System.out.println("key " + key + " = string " + k + " at index " + i);
				}
				i++;
			}

		} else {
			index = -1;
		}
		return index;

	}

	public static Db getInterface(DdbbConfig cfg){
		switch ((String) cfg.settings.get("db_type")) {
			case "MongoDB": return new MongoInterface(cfg);
			case "Cassandra": return new CassandraInterface(cfg);
			case "MariaDB": return new MariaInterface(cfg);
			case "Redis": return new RedisInterface(cfg);
            case "Elasticsearch": return new ElasticInterface(cfg);
			default: return null;
		}
	}

}