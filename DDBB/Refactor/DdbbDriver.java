import java.io.*;

public class DdbbDriver {

	public static void main(String args[]) {

		try {

			for(int i = 0; i<100; i++){
				String test_name = "test_config" + i;
				if((new File(test_name + ".json")).exists()){
					DdbbTest test = new DdbbTest(test_name + ".json");
					if(test.validate()){

						DdbbIO.out(test_name + "_RESULT.json", test.run());

						System.out.println("Finished test number " + i + " with the name \"" + test.cfg.settings.get("b_name") + "\".");

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