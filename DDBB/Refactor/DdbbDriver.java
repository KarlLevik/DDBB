import java.io.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class DdbbDriver {

	public static volatile AtomicBoolean warmup_started = new AtomicBoolean(false);
	public static volatile AtomicBoolean warmup_finished = new AtomicBoolean(false);

	public static void main(String args[]) {

		try {

			for(int i = 1; i<10; i++){

				String test_name = "test_config" + i;
				if((new File(test_name + ".json")).exists()){
					DdbbTest test = new DdbbTest(test_name);
					if(test.validate()){

						test.start();

						//System.out.println("Finished test number " + i + " with the name \"" + test.cfg.settings.get("b_name") + "\".");

					}

				}

			}

		} catch(NullPointerException e) {

			System.out.println("FINISH");

		} catch(Exception e) {

			System.out.println(e);

		}

	}

}