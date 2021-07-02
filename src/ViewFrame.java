import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.event.*;
import java.io.*;
import java.awt.GridLayout;

public class ViewFrame extends JFrame {

    JButton submitButton;
    JTextField tf_d2, tf_d1, tf_rpore, tf_phi;

    BufferedWriter writer;
    CLPI clpi;

    public ViewFrame() {
        submitButton = new JButton("calc");
        tf_d2 = new JTextField();
        tf_d1 = new JTextField();
        tf_rpore = new JTextField();
        tf_phi = new JTextField();
        GridLayout layout = new GridLayout(0, 2);
        setLayout(layout);
        add(submitButton);
        add(tf_d2);
        add(tf_d1);
        add(tf_rpore);
        add(tf_phi);
        setVisible(true);
        setSize(1000, 620);
        setResizable(true);
        setLocation(50, 50);

        submitButton.addActionListener((ActionEvent e) -> {

            
            try {

                writer.write("[a1,a2]=calc(15e-3, 15e-3, 0.75e-3, 0.1)"+ System.lineSeparator());
                writer.flush();
                // input the file content to the StringBuffer "input"
                /*
                BufferedReader file = new BufferedReader(new FileReader(clpi.script_path));
                StringBuffer inputBuffer = new StringBuffer();
                String line;
                int line_nr = 0;
                clpi.read = false;

                while ((line = file.readLine()) != null) {
                    line_nr++;
                    if (line_nr == 9)
                        line = "d_2 = " + tf_d2.getText();

                    if (line_nr == 10)
                        line = "d_1 = " + tf_d1.getText();
                    if (line_nr == 11)
                        line = "R_pore = " + tf_rpore.getText();
                    if (line_nr == 12)
                        line = "phi = " + tf_phi.getText();
                    inputBuffer.append(line);
                    writer.write(line.replace(";", "") + System.lineSeparator());
                    inputBuffer.append('\n');
                    // writer.write(line);
                    //System.out.println(line);
                }
                writer.flush();
                file.close();

                String inputStr = inputBuffer.toString();
                FileOutputStream fileOut = new FileOutputStream(clpi.script_path);
                fileOut.write(inputStr.getBytes());
                fileOut.close();

                */

            } catch (Exception ex) {
                System.out.println("Problem reading file.");
            }
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent arg0) {

            }
        });

    }

}
