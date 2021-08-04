import javax.swing.*;
import java.awt.Graphics;
import java.util.*;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.Font;
import java.awt.Point;

public class GraphFrame extends JFrame {

     // List<String> values;

     DrawPane graph;

     private static final int BORDER_GAP = 30;
     private static final int GRAPH_POINT_WIDTH = 12;
     private static final int Y_HATCH_CNT = 10;
     private static final Stroke STROKE = new BasicStroke(2f);

     public GraphFrame() {
          super("My Frame");
          // You can set the content pane of the frame to your custom class.
          graph = new DrawPane();
          setContentPane(graph);
          setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          setSize(600, 600);
          setVisible(true);
     }

     public void draw(List<String> vs) {
          // DrawPane d = ((DrawPane)getContentPane());
          // d.values = values;
          System.out.println(vs.size());
          if (vs.size() != 0)
               this.graph.setValues(vs);
          this.graph.repaint();
          System.out.println(vs.size());
          // getContentPane().repaint();
     }

     public void drawValues(List<String> values) {

     }

     // Create a component that you can actually draw on.
     class DrawPane extends JPanel {
          List<String> values;

          public void setValues(List<String> v) {

               this.values = v;
               // System.out.println("nvals " + this.values.size());
          }

          @Override
          public void paint(Graphics g) {
               super.paint(g);
               Graphics2D g2 = (Graphics2D) g;
               if (this.values == null || this.values.size() == 0)
                    return;

               drawAxis(g2);

               // convert to double
               List<Double> double_list = new ArrayList<>();
               for (String s : this.values) {
                    try {
                         double_list.add(Double.parseDouble(s));
                    } catch (NumberFormatException e) {
                         continue;
                    }
               }

               Double max = Collections.max(double_list);

               // calc drawing points
               List<Point> points = new ArrayList<>();
               for (int i = 0; i < double_list.size(); i++) {

                    int w = getWidth() - 2 * BORDER_GAP;
                    double tmp = (double) ((double) w / (double) double_list.size());
                    int x = (int) (i * tmp) + BORDER_GAP;
                    int h = getHeight() - 2 * BORDER_GAP;
                    int y = (int) (h - (h / max) * double_list.get(i)) + BORDER_GAP;
                    points.add(new Point(x, y));
               }

               g2.setStroke(STROKE);
               g2.setColor(Color.BLUE);
               g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
               for (int i = 0; i < points.size() - 1; i++) {
                    int x1 = points.get(i).x;
                    int y1 = points.get(i).y;
                    int x2 = points.get(i + 1).x;
                    int y2 = points.get(i + 1).y;

                    g2.drawLine(x1, y1, x2, y2);

               }

          }

          public void drawAxis(Graphics2D g) {

               g.drawLine(BORDER_GAP, getHeight() - BORDER_GAP, BORDER_GAP, BORDER_GAP);
               for (int i = 0; i < Y_HATCH_CNT; i++) {
                    int x0 = BORDER_GAP;
                    int x1 = GRAPH_POINT_WIDTH + BORDER_GAP;
                    int y0 = getHeight() - (((i + 1) * (getHeight() - BORDER_GAP * 2)) / Y_HATCH_CNT + BORDER_GAP);
                    int y1 = y0;
                    g.drawLine(x0, y0, x1, y1);
               }
               g.drawLine(BORDER_GAP, getHeight() - BORDER_GAP, getWidth() - BORDER_GAP, getHeight() - BORDER_GAP);

               // draw y axis name
               Font orig = g.getFont();
               Font font = new Font(null, Font.PLAIN, 10);
               AffineTransform affineTransform = new AffineTransform();
               affineTransform.rotate(Math.toRadians(90), 0, 0);
               Font rotatedFont = font.deriveFont(affineTransform);
               g.setFont(rotatedFont);
               g.drawString("Absorbtionsgrad",5, getHeight() / 2);
               g.setFont(orig);
               
               for (int i = 0; i < 7; i += 1) {
                    int x0 = (i + 1) * (getWidth() - BORDER_GAP * 2) / (7) + BORDER_GAP;
                    int x1 = x0;
                    int y0 = getHeight() - BORDER_GAP;
                    int y1 = y0 - GRAPH_POINT_WIDTH;
                    g.drawLine(x0, y0, x1, y1);
                    g.drawString(String.valueOf((i + 1) * 1000), x0, y0);
               }
               g.drawString("Frequenz", getHeight() + BORDER_GAP, getWidth() / 2);
          }
     }

}
