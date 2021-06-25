import javax.swing.*;
import java.awt.Graphics;
import java.util.*;

public class GraphFrame extends JFrame {

     List<String> values;

     public GraphFrame() {
          super("My Frame");

          // You can set the content pane of the frame to your custom class.
          setContentPane(new DrawPane());
          setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          setSize(400, 400);
          setVisible(true);
     }

     public void draw() {
          getContentPane().repaint();
     }

     // Create a component that you can actually draw on.
     class DrawPane extends JPanel {
          public void paintComponent(Graphics g) {
               int height = 200;
               int x = 0;
               for (String v : values) {
                    try {
                         double value = Double.parseDouble(v);

                         // eg 0.1 => 10
                         value *= 100;
                         System.out.print((int) value);
                         g.drawLine(x, height, x, (int) (200.0 - value));
                         x += 1;
                    } catch (NumberFormatException e) {

                    }
               }

               System.out.println(x);
          }
     }

}
