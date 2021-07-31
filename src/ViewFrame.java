import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;

import java.awt.Component;
import java.awt.event.*;
import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ViewFrame extends JFrame {

    List<ValueInputGroup> valueInputGroups;

    JButton submitButton;
    JTextField tf_d2, tf_d1, tf_rpore, tf_phi;

    JButton incr, decr;

    JSlider sl_d2;
    BufferedWriter writer;
    CLPI clpi;
    long last = 0, timeSincelastchanged = 0;
    boolean dothedraw;

    public ViewFrame() {
        submitButton = new JButton("calc");
        tf_d2 = new JTextField(10);
        tf_d1 = new JTextField(10);
        tf_rpore = new JTextField(10);
        tf_phi = new JTextField(10);

        incr = new JButton();
        incr.setText("+");

        //incr.addActionListener(new TextFieldValueChanger(tf_d2));
        decr = new JButton();
        //ButtonGroup group = new ButtonGroup();
        // group.add(incr);
        // decr.add(decr);

        sl_d2 = new JSlider();
        // FlowLayout layout = new FlowLayout();
        // BoxLayout layout = new BoxLayout();
        GridLayout layout = new GridLayout(0, 4);
        setLayout(layout);
        // setLayout(null);
        submitButton.setLocation(50, 50);
        ValueInputGroup d2 = new ValueInputGroup("d2");
        ValueInputGroup d1 = new ValueInputGroup("d1");
        ValueInputGroup rpore = new ValueInputGroup("rpore");
        ValueInputGroup phi = new ValueInputGroup("phi");

        this.valueInputGroups = new LinkedList<>();
        valueInputGroups.add(d2);
        valueInputGroups.add(d1);
        valueInputGroups.add(rpore);
        valueInputGroups.add(phi);

        for (ValueInputGroup g: this.valueInputGroups)

            for (Component c : g.getComponents()) 
                add(c);
            
            
        //add(tf_d2);
        //add(incr);
        // add(sl_d2);
        //add(tf_d1);
        //add(tf_rpore);
        //add(tf_phi);
        add(submitButton);
        pack();
        setVisible(true);
        setSize(1000, 620);
        setResizable(true);
        setLocation(50, 50);
        dothedraw = false;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                dothedraw = !dothedraw;

            }
        }, 500);

        last = System.nanoTime();
        sl_d2.addChangeListener((ChangeEvent e) -> {
            if (!dothedraw)
                return;
            long current = System.nanoTime();
            long timediff = current - last;
            System.out.println(timediff);
            last = current;
            timeSincelastchanged += timediff;
            if (timeSincelastchanged < 50000000) {
                System.out.println("jumpin");
                return;
            }

            String cmd = "[a1,a2]=calc(" + String.valueOf(sl_d2.getValue()) + "e-3" + ", " + tf_d1.getText() + ", "
                    + tf_rpore.getText() + ", " + tf_phi.getText() + ")";

            try {
                writer.write(cmd + System.lineSeparator());
                writer.flush();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        });

        // sl_d2.addMouseListener(l);

        submitButton.addActionListener((ActionEvent e) -> {

            this.sendCommand();
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent arg0) {
                // clpi.saveStartValues(new String[]{tf_d2.getText(), tf_d1.getText(),
                // tf_rpore.getText(), tf_phi.getText()});
            }
        });

    }

    public void sendCommand()
    {
        try {

            String cmd = "[a1,a2]=calc(" + tf_d2.getText() + ", " + tf_d1.getText() + ", " + tf_rpore.getText()
                    + ", " + tf_phi.getText() + ")";
            writer.write(cmd + System.lineSeparator());
            writer.flush();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

}
