package de.unihildesheim.iis.jadedemo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.JPanel;

public class MapPanel extends JPanel{
	private ArrayList<Point.Double> points;

    public MapPanel(ArrayList<Point.Double> points) {
        this.points = points;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        // Set rendering hints for smoother lines
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the points on the panel
        g2d.setColor(Color.RED);
        for (Point.Double point : points) {
            int x = (int) ((point.y + 180) * (getWidth() / 360.0));
            int y = (int) ((-point.x + 90) * (getHeight() / 180.0));
            g2d.fillOval(x, y, 5, 5);
        }
     // Draw the lines connecting the points
        g2d.setColor(Color.BLUE);
        for (int i = 0; i < points.size() - 1; i++) {
            Point.Double point1 = points.get(i);
            Point.Double point2 = points.get(i + 1);
            
            int x1 = (int) ((point1.y + 180) * (getWidth() / 360.0));
            int y1 = (int) ((-point1.x + 90) * (getHeight() / 180.0));
            int x2 = (int) ((point2.y + 180) * (getWidth() / 360.0));
            int y2 = (int) ((-point2.x + 90) * (getHeight() / 180.0));

            g2d.drawLine(x1, y1, x2, y2);
        }
    }

}
