import java.io.File;
import java.util.Hashtable;
import java.util.concurrent.atomic.AtomicBoolean;

public class DdbbDriver {

	public static volatile AtomicBoolean warmup_finished = new AtomicBoolean(false);
	public static Hashtable<String, Boolean> status = new Hashtable<>();

	public static String validate(DdbbTest test){
		return "";
	}

	public static void test_split_start(DdbbTest test){

		status.put(test.file_name, false);

		if(test.cfg.settings.containsKey("test_repeat_type") && test.cfg.settings.get("test_repeat_type").equals("async")){
			for(int i = 0; i < (int) test.cfg.settings.get("test_repeat_amount"); i++){
				try {

					File file = new File(test.file_name + "_" + i + ".json");
					file.createNewFile();
					DdbbTool.copyFile(test.file_name + ".json", test.file_name + "_" + i + ".json");
					DdbbTest repeat_test = new DdbbTest(test.file_name + "_" + i);
					repeat_test.start();

				} catch(Exception e){
					System.out.println(e);
				}
			}
		} else if (test.cfg.settings.containsKey("test_repeat_type") && test.cfg.settings.get("test_repeat_type").equals("sync")){

			try {

				DdbbConfig cfg = DdbbIO.in(test.file_name + ".json");

				for(int i = 0; i < (int) test.cfg.settings.get("test_repeat_amount"); i++){

					if(i > 0 && cfg.settings.containsKey("test_order_delay")){
						cfg.settings.replace("test_order_delay", test.file_name + "_" + (i-1));
					} else if(i > 0){
						cfg.settings.put("test_order_delay", test.file_name + "_" + (i-1));
					}

					if(cfg.settings.containsKey("test_repeat_separate_files") && (boolean) cfg.settings.get("test_repeat_separate_files")){
						if(cfg.create.meta.containsKey("load_file")){
							String[] dot_split = String.valueOf(cfg.create.meta.get("load_file")).split(".");
							String separate_load_file_name = "";
							for(int z = 0; z < dot_split.length; z++){
								if(z == dot_split.length - 1){
									separate_load_file_name = separate_load_file_name + "_" + i;
								}

								separate_load_file_name = separate_load_file_name + dot_split[z];

							}

							cfg.create.meta.replace("load_file", separate_load_file_name);
						}
						if(cfg.read.meta.containsKey("load_file")){
							String[] dot_split = String.valueOf(cfg.read.meta.get("load_file")).split(".");
							String separate_load_file_name = "";
							for(int z = 0; z < dot_split.length; z++){
								if(z == dot_split.length - 1){
									separate_load_file_name = separate_load_file_name + "_" + i;
								}

								separate_load_file_name = separate_load_file_name + dot_split[z];

							}

							cfg.read.meta.replace("load_file", separate_load_file_name);
						}
						if(cfg.update.meta.containsKey("load_file")){
							String[] dot_split = String.valueOf(cfg.update.meta.get("load_file")).split(".");
							String separate_load_file_name = "";
							for(int z = 0; z < dot_split.length; z++){
								if(z == dot_split.length - 1){
									separate_load_file_name = separate_load_file_name + "_" + i;
								}

								separate_load_file_name = separate_load_file_name + dot_split[z];

							}

							cfg.update.meta.replace("load_file", separate_load_file_name);
						}
						if(cfg.delete.meta.containsKey("load_file")){
							String[] dot_split = String.valueOf(cfg.delete.meta.get("load_file")).split(".");
							String separate_load_file_name = "";
							for(int z = 0; z < dot_split.length; z++){
								if(z == dot_split.length - 1){
									separate_load_file_name = separate_load_file_name + "_" + i;
								}

								separate_load_file_name = separate_load_file_name + dot_split[z];

							}

							cfg.delete.meta.replace("load_file", separate_load_file_name);
						}
					}

					File file = new File(test.file_name + "_" + i + ".json");
					file.createNewFile();
					DdbbIO.outCfg(test.file_name + "_" + i + ".json", cfg);
					DdbbTest repeat_test = new DdbbTest(test.file_name + "_" + i);

					repeat_test.start();
				}

			} catch(Exception e){
				System.out.println(e);
			}

		}
	}

	public static void main(String args[]) {

		try {

			for(int i = 1; i<2; i++){

				String test_name = "test_config" + i;
				System.out.println((new File(test_name + ".json")).exists());
				if((new File(test_name + ".json")).exists()){
					DdbbTest test = new DdbbTest(test_name);
					if(validate(test).equals("")){

						test_split_start(test);

						test.start();

						//System.out.println("Finished test number " + i + " with the name \"" + test.cfg.settings.get("b_name") + "\".");

					}

				}

			}

		} catch(NullPointerException e) {

			System.out.println("FINISH");

		} catch(Exception e) {

			System.out.println(e);
			System.out.println("ERROR");

		}

	}

}