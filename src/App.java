import java.io.*;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.GridLayout;

public class App extends JFrame {
    JButton ok;
    JTextField tf;
    String probendicke, last;

    static Process process;

    public static void main(String[] args) throws Exception {

        GridLayout layout = new GridLayout(0, 2);
        App frame = new App();
        frame.last = "55e-3";
        frame.setLayout(layout);
        frame.ok = new JButton("ok");
        frame.tf = new JTextField();
        frame.ok.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                frame.probendicke = frame.tf.getText();
                replaceSelected("d_2 = "+frame.last+"; % [m] Probendicke", "d_2 = "+frame.probendicke+"; % [m] Probendicke");
                frame.last = frame.probendicke;

                try{
                    Thread.sleep(1000);
                    loadOctave();
                }catch(Exception exception)
                {
                    exception.printStackTrace();
                }
            }
        });
        frame.ok.setLocation(50, 50);
        frame.tf.setLocation(100,100);
        frame.add(frame.ok);
        frame.add(frame.tf);
        frame.setTitle("Word Cloud");
        frame.setSize(1000, 620);
        frame.setResizable(true);
        frame.setLocation(50, 50);
        frame.setVisible(true);
    }

    public static void loadOctave() throws Exception
    {
        if(process != null)
            process.destroy();
        process = Runtime.getRuntime()
        //.exec("octave --persist /home/moritz/Downloads/stinson.m");
        .exec("octave");
        //System.out.println(process.getInputStream().read());

        //Thread.sleep(10000);

    }

    public static void replaceSelected(String original, String replace) {
        try {
            // input the file content to the StringBuffer "input"
            BufferedReader file = new BufferedReader(new FileReader("/home/moritz/Downloads/stinson.m"));
            StringBuffer inputBuffer = new StringBuffer();
            String line;
            int line_nr = 0;
    
            while ((line = file.readLine()) != null) {
                line_nr++;
                inputBuffer.append(line);
                inputBuffer.append('\n');
            }
            file.close();
            String inputStr = inputBuffer.toString();
    
            //System.out.println(inputStr); // display the original file for debugging
    
            // logic to replace lines in the string (could use regex here to be generic)
            inputStr = inputStr.replace(original, replace); 
           
    
            // display the new file for debugging
            //System.out.println("----------------------------------\n" + inputStr);
    
            // write the new string with the replaced line OVER the same file
            FileOutputStream fileOut = new FileOutputStream("/home/moritz/Downloads/stinson.m");
            fileOut.write(inputStr.getBytes());
            fileOut.close();
    
        } catch (Exception e) {
            System.out.println("Problem reading file.");
        }
    }
}
