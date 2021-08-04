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
    List<Action> actions;

    public ValueInputGroup(String name, List<Action> as) {
        valueField = new JTextField();
        label = new JLabel(name);
        incr = new JButton("+");
        TextFieldValueChanger incrementer = new TextFieldValueChanger(valueField, TextFieldValueChanger.ChangeOperation.INCREMENT, as);
        incr.addActionListener(incrementer);
        decr = new JButton("-");
        TextFieldValueChanger decrementer = new TextFieldValueChanger(valueField, TextFieldValueChanger.ChangeOperation.DECREMENT, as);
        decr.addActionListener(decrementer);

    }

    public String getValue()
    {
        return this.valueField.getText();
    }

    public void setValue(String v)
    {
        this.valueField.setText(v);
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
