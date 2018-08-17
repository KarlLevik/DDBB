import java.util.Hashtable;

public class DdbbConfig {

    public Hashtable<String,Object> settings;
    public DdbbProperty setup;
    public DdbbProperty create;
    public DdbbProperty read;
    public DdbbProperty update;
    public DdbbProperty delete;

    DdbbConfig(){
        this.settings = new Hashtable<String,Object>();
        this.setup = new DdbbProperty();
        this.create = new DdbbProperty();
        this.read = new DdbbProperty();
        this.update = new DdbbProperty();
        this.delete = new DdbbProperty();
    }

    DdbbConfig(DdbbConfig cfg){

        this.settings = new Hashtable<String,Object>();
        this.setup = new DdbbProperty();
        this.create = new DdbbProperty();
        this.read = new DdbbProperty();
        this.update = new DdbbProperty();
        this.delete = new DdbbProperty();

        for(String k : cfg.settings.keySet()){
            this.settings.put(k, cfg.settings.get(k));
        }

        for(String setup_m_k : cfg.setup.meta.keySet()){
            this.setup.meta.put(setup_m_k, cfg.setup.meta.get(setup_m_k));
        }

        for(String setup_d_k : cfg.setup.data.keySet()){
            this.setup.data.put(setup_d_k, cfg.setup.data.get(setup_d_k));
        }

        for(String create_m_k : cfg.create.meta.keySet()){
            this.create.meta.put(create_m_k, cfg.create.meta.get(create_m_k));
        }

        for(String create_d_k : cfg.create.data.keySet()){
            this.create.data.put(create_d_k, cfg.create.data.get(create_d_k));
        }

        for(String read_m_k : cfg.read.meta.keySet()){
            this.read.meta.put(read_m_k, cfg.read.meta.get(read_m_k));
        }

        for(String read_d_k : cfg.read.data.keySet()){
            this.read.data.put(read_d_k, cfg.read.data.get(read_d_k));
        }

        for(String update_m_k : cfg.update.meta.keySet()){
            this.update.meta.put(update_m_k, cfg.update.meta.get(update_m_k));
        }

        for(String update_d_k : cfg.update.data.keySet()){
            this.update.data.put(update_d_k, cfg.update.data.get(update_d_k));
        }

        for(String delete_m_k : cfg.delete.meta.keySet()){
            this.delete.meta.put(delete_m_k, cfg.delete.meta.get(delete_m_k));
        }

        for(String delete_d_k : cfg.delete.data.keySet()){
            this.delete.data.put(delete_d_k, cfg.delete.data.get(delete_d_k));
        }

    }

}
