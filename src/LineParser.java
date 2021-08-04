import java.util.ArrayList;
import java.util.List;

// A line parser represents a list of values that can be parsed from multiple lines
public class LineParser {
    String id;
    List<String> values;
    boolean done = false;
    public LineParser(String id) {
        this.id = id;
        this.values = new ArrayList<>();
    }

    public void parseLine(String line)
    {
        if (line.contains(id))
            return;
        if (line.contains("1241"))
            done = true;

        if (line.isBlank() || line.contains("Column"))
            return;
        this.values.addAll(extractNumbers(line));
    }

    public static List<String> extractNumbers(String s) {
        // the numbers of the line
        List<String> ret = new ArrayList<>();
        // current char
        char c;
        boolean new_number = true;
        String number = "";
        for (int i = 0; i < s.length(); i++) {
            c = s.charAt(i);
            // the regex '.' matches all but newline
            if (c == ' ' || !String.valueOf(c).matches(".")) {
                if (!number.isBlank()) {
                    ret.add(number);
                    number = "";
                }
                continue;

            } else
                number += c;

        }

        return ret;
    }

    public List<String> getValues()
    {
        return this.values;
    }
    
    
}
