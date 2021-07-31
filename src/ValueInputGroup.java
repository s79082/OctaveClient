import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ValueInputGroup{

    JTextField valueField;
    JButton incr, decr;
    JLabel label;

    public ValueInputGroup(String name) {
        valueField = new JTextField();
        label = new JLabel(name);
        incr = new JButton("+");
        incr.addActionListener(new TextFieldValueChanger(valueField, TextFieldValueChanger.ChangeAction.INCREMENT));
        decr = new JButton("-");
        decr.addActionListener(new TextFieldValueChanger(valueField, TextFieldValueChanger.ChangeAction.DECREMENT));

        
    }

    public List<Component> getComponents()
    {
        ArrayList<Component> ret = new ArrayList<>();
        ret.add(label);
        ret.add(incr);
        ret.add(decr);
        ret.add(valueField);
        return ret;
    }
    
    
}
