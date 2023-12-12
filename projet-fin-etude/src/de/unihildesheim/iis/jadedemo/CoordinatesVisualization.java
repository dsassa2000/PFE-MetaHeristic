package de.unihildesheim.iis.jadedemo;

import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JFrame;

public class CoordinatesVisualization extends JFrame  {
	private static ArrayList<Point.Double> parseCoordinates(String coordinates) {
		ArrayList<Point.Double> points = new ArrayList<>();
	    if (!coordinates.isEmpty()) {
	        String[] pairs = coordinates.split("\\|");
	        for (String pair : pairs) {
	            if (!pair.trim().isEmpty()) {
	                String[] values = pair.trim().split(",");
	                if (values.length == 2 && !values[0].isEmpty() && !values[1].isEmpty()) {
	                    try {
	                        double latitude = Double.parseDouble(values[0].trim());
	                        double longitude = Double.parseDouble(values[1].trim());
	                        points.add(new Point.Double(latitude, longitude));
	                    } catch (NumberFormatException e) {
	                        // Handle parsing exceptions, if needed
	                        e.printStackTrace(); // Or log the exception
	                    }
	                }
	            }
	        }
	    }
	    return points;
    }

    public CoordinatesVisualization(String tour) {
        super("Coordinates Visualization");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        // Coordinates data
       // String coordinatesData = "|34.034653, -5.0161924|35.178585, -5.2791204| ... ";

        // Parse coordinates
        ArrayList<Point.Double> points = parseCoordinates(tour);

        // Create and add map panel
        MapPanel mapPanel = new MapPanel(points);
        add(mapPanel);

        setVisible(true);
    }
    
}
