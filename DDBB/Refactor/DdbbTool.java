import java.util.Random;
import java.util.Hashtable;

public class DdbbTool {

	// Method to randomly generate a random string
	public static String generateRandomString(Integer length, Boolean use_plain){		

		Random r = new Random();
		String rng_val = "";
		// Allows you to change which symbols are in the alphabet for the random string generation
		String alphabet = use_plain ? "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ" : 
			"1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
			+ "`,./;'#[]-=¬!£$%^&*()<>?:@~{}_+";

		while(rng_val.length() != length){

			rng_val = rng_val + alphabet.charAt(r.nextInt(alphabet.length()));

		}

		return rng_val;
	
	}

	// Returns the runtime, often of a test, a benchmark or a session
	public static long runtime(Long start_time){

		return (System.currentTimeMillis() - start_time);
	
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

		}

		return result;

	}

}