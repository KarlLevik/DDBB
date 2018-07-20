import java.util.Random;

public class DDBBTool {

	public static String generateRandomString(Integer length, Boolean use_plain){		

		Random r = new Random();
		String rng_val = "";
		String alphabet = use_plain ? "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ" : 
			"1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ`,./;'#[]-=¬!£$%^&*()<>?:@~{}_+";

		while(rng_val.length() != length){

			rng_val = rng_val + alphabet.charAt(r.nextInt(alphabet.length()));

		}

		return rng_val;
	
	}

	public static long runtime(Long start_time){

		return (System.currentTimeMillis() - start_time);
	
	}

}