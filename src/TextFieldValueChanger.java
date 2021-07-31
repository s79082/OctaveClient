import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Function;

import javax.swing.JTextField;

public class TextFieldValueChanger implements ActionListener {

    
    private JTextField textField;
    private ChangeAction action;

    enum ChangeAction 
    {
        INCREMENT,
        DECREMENT
    }

    public TextFieldValueChanger(JTextField f, ChangeAction a) {
        this.textField = f;
        this.action = a;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String value_text = textField.getText();

            if (value_text.contains("e"))
            {
                String[] tmp = value_text.split("e");
                int val = Integer.valueOf(tmp[0]);

                switch (action) {
                    case INCREMENT:
                        val++;
                        break;
                
                    case DECREMENT:
                        val--;
                        break;
                }
                textField.setText(String.valueOf(val) + "e" + tmp[1]);
                return;
            }

            try {
                double value = Double.parseDouble(textField.getText());
                value += 0.001;
                textField.setText(String.valueOf(value));


            } catch (NumberFormatException ex) {
                return;
            }

        
    }
    
}
