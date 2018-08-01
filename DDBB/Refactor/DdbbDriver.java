import java.io.*;

public class DdbbDriver {

	public static void main(String args[]) {

		try {

			for(int i = 0; i<100; i++){
				String test_name = "test_config" + i + ".json";
				if((new File(test_name)).exists()){
					DdbbTest test = new DdbbTest(test_name);
					if(test.validate()){

						DdbbIO.out(test_name, (new DdbbTest(test_name)).run());

					}

				}

			}

		} catch(Exception e) {

			System.out.println(e);

		}

	}

}