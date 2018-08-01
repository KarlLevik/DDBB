import java.util.ArrayList;
import java.util.Hashtable;

public class DdbbProperty {
    public Hashtable<String,Object> meta;
    public Hashtable<String, ArrayList<Object>> data;

    DdbbProperty() {
        this.meta = new Hashtable<>();
        this.data = new Hashtable<>();
    }

    public boolean isEmpty(){
        return (this.meta.isEmpty() && this.data.isEmpty());
    }

}
