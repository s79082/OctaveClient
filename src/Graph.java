import java.util.List;
import java.awt.Graphics2D;
import java.awt.Color;
import java.util.ArrayList;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.BasicStroke;

public class Graph {

    List<Double> values;
    Color color;
    public static final Stroke STROKE = new BasicStroke(2f);
    public static final int BORDER_GAP = 30;

    public Graph(List<Double> vals, Color c) {
        values = vals;
        color = c;
    }

    public void draw(Graphics2D g2, int width, int height, Double max) {

        // calc drawing points
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < values.size(); i++) {

            int w = width - 2 * BORDER_GAP;
            double tmp = (double) ((double) w / (double) values.size());
            int x = (int) (i * tmp) + BORDER_GAP;
            int h = height - 2 * BORDER_GAP;
            int y = (int) (h - (h / max) * values.get(i)) + BORDER_GAP;
            points.add(new Point(x, y));
        }

        g2.setStroke(STROKE);
        g2.setColor(color);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (int i = 0; i < points.size() - 1; i++) {
            int x1 = points.get(i).x;
            int y1 = points.get(i).y;
            int x2 = points.get(i + 1).x;
            int y2 = points.get(i + 1).y;

            g2.drawLine(x1, y1, x2, y2);

        }
    }
}
