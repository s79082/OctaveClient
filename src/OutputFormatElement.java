import java.util.ArrayList;
import java.util.List;

public class OutputFormatElement {

    String ident;
    private boolean reading = false;
    List<String> raw_values;

    public void read(String line)
    {

        if (line.contains(ident))
        {
            raw_values = new ArrayList<>();
            reading = true;
        }

        if (!reading)
            return;

        
    }
    
}
