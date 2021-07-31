import javax.swing.*;
import java.awt.Graphics;
import java.util.*;

public class GraphFrame extends JFrame {

     //List<String> values;

     DrawPane graph;

     public GraphFrame() {
          super("My Frame");
          // You can set the content pane of the frame to your custom class.
          graph = new DrawPane();
          setContentPane(graph);
          setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          setSize(400, 400);
          setVisible(true);
     }

     public void draw(List<String> vs) {
          //DrawPane d = ((DrawPane)getContentPane());
          //d.values = values;
          System.out.println(vs.size());
          this.graph.setValues(vs);
          this.graph.repaint();
          System.out.println(vs.size());
          //getContentPane().repaint();
     }

     public void drawValues(List<String> values)
     {

     }

     // Create a component that you can actually draw on.
     class DrawPane extends JPanel {
          List<String> values;

          public void setValues(List<String> v)
          {
               
               this.values = v;
               System.out.println("nvals " +this.values.size());
          }

          @Override
          public void paint(Graphics g) {
               super.paint(g);
               if(this.values == null || this.values.size() == 0)
                    return;
               System.out.println("values "+ this.values.size());
               int height = 200;
               int x = 0;
               Iterator<String> iter = values.iterator();
               for (int idx = 0; idx < values.size(); idx+=2){
                    String v = values.get(idx);
               //for (String v : this.values) {
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
