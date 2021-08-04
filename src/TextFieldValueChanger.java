import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.Function;

import javax.swing.JTextField;

public class TextFieldValueChanger implements ActionListener {

    private JTextField textField;
    private ChangeOperation operation;
    List<Action> actions;

    enum ChangeOperation {
        INCREMENT, DECREMENT
    }

    public TextFieldValueChanger(JTextField f, ChangeOperation a, List<Action> as) {
        this.textField = f;
        this.operation = a;
        this.actions = new LinkedList<>();
        this.actions.add(this::changeTextFieldValue);
        this.actions.addAll(as);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        actions.forEach((Action a) -> {
            a.exec();
        });
        
    }

    private void changeTextFieldValue()
    {
        String value_text = textField.getText();
        if (value_text.isBlank())
            return;

        // exponential notation
        if (value_text.contains("e")) {
            String[] tmp = value_text.split("e");
            int val = Integer.valueOf(tmp[0]);

            switch (operation) {
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

    public void addAction(Action a)
    {
        this.actions.add(a);
    }

}
