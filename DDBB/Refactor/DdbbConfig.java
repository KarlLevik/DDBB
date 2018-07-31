import java.util.*;

public class DdbbConfig {

    public Hashtable<String,Object> settings;
    public DdbbProperty generate;
    public DdbbProperty create;
    public DdbbProperty read;
    public DdbbProperty update;
    public DdbbProperty delete;

    DdbbConfig(){
        this.settings = new Hashtable<String,Object>();
        this.generate = new DdbbProperty();
        this.create = new DdbbProperty();
        this.read = new DdbbProperty();
        this.update = new DdbbProperty();
        this.delete = new DdbbProperty();
    }

}
