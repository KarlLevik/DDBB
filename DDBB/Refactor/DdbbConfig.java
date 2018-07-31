public class DdbbConfig {

    public DdbbProperty settings;
    public DdbbProperty generate;
    public DdbbProperty create;
    public DdbbProperty read;
    public DdbbProperty update;
    public DdbbProperty delete;

    DdbbConfig(){
        this.settings = new DdbbProperty();
        this.generate = new DdbbProperty();
        this.create = new DdbbProperty();
        this.read = new DdbbProperty();
        this.update = new DdbbProperty();
        this.delete = new DdbbProperty();
    }

}
