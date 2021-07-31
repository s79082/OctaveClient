import java.util.ArrayList;
import java.util.List;

public class OutputFormatElement {

    String ident;
    boolean reading = false;
    List<String> raw_values;

    public OutputFormatElement(String i)
    {
        this.ident = i;
        this.raw_values = new ArrayList<>();
    }

    public void read(String line)
    {
        

        
    }
    
}
