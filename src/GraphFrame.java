import javax.swing.*;
import java.awt.Graphics;
import java.util.*;

public class GraphFrame extends JFrame {

     List<String> values;

     public GraphFrame() {
          super("My Frame");

          values = new ArrayList<>();
          // You can set the content pane of the frame to your custom class.
          setContentPane(new DrawPane());
          setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          setSize(400, 400);
          setVisible(true);
     }

     public void draw() {
          DrawPane d = ((DrawPane)getContentPane());
          d.values = values;
          d.repaint();
          //getContentPane().repaint();
     }

     // Create a component that you can actually draw on.
     class DrawPane extends JPanel {
          List<String> values = new ArrayList<>();
          @Override
          public void paint(Graphics g) {
               super.paint(g);
               System.out.println(values.size());
               int height = 200;
               int x = 0;
               Iterator<String> iter = values.iterator();
               for (String v : this.values) {
               //     while(iter.hasNext()){
               //          String v = iter.next();
               //System.out.println("value" + v);
                    try {
                         double value = Double.parseDouble(v);

                         // eg 0.1 => 10
                         value *= 100;
                         //System.out.print((int) value);
                         g.drawRect(x, (int) (200.0 - value), 1, 1);
                         x += 1;
                    } catch (NumberFormatException e) {

                    }
               }

               //System.out.println(x);
          }
     }

}
