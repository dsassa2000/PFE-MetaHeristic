package de.unihildesheim.iis.jadedemo;

import javax.swing.SwingUtilities;

import de.unihildesheim.iis.jadedemo.graph.City;
import de.unihildesheim.iis.jadedemo.graph.MetaheuristicResponse;

public class Metaheuristic {

	// Calculate the acceptance probability
    public static double acceptanceProbability(int energy, int newEnergy, double temperature) {
        // If the new solution is better, accept it
        if (newEnergy < energy) {
            return 1.0;
        }
        // If the new solution is worse, calculate an acceptance probability
        return Math.exp((energy - newEnergy) / temperature);
    }
    public static MetaheuristicResponse metaHeuristic(Tour tour) {
    	  // Set initial temp
        double temp = 10000;

        // Cooling rate
        double coolingRate = 0.003;
    	// Loop until system has cooled
    	Tour best = new Tour(tour.getTour());
        while (temp > 1) {
            // Create new neighbour tour
            Tour newSolution = new Tour(tour.getTour());

            // Get a random positions in the tour
            int tourPos1 = (int) (newSolution.tourSize() * Math.random());
            int tourPos2 = (int) (newSolution.tourSize() * Math.random());

            // Get the cities at selected positions in the tour
            City citySwap1 = newSolution.getCity(tourPos1);
            City citySwap2 = newSolution.getCity(tourPos2);

            // Swap them
            newSolution.setCity(tourPos2, citySwap1);
            newSolution.setCity(tourPos1, citySwap2);
            
            // Get energy of solutions
            int currentEnergy = tour.getDistance();
            int neighbourEnergy = newSolution.getDistance();

            // Decide if we should accept the neighbour
            if (acceptanceProbability(currentEnergy, neighbourEnergy, temp) > Math.random()) {
            	tour = new Tour(newSolution.getTour());
            }

            // Keep track of the best solution found
            if (tour.getDistance() < best.getDistance()) {
                best = new Tour(tour.getTour());
            }
            
            // Cool system
            temp *= 1-coolingRate;
        }

        System.out.println("Final solution distance: " + best.getDistance());
        System.out.println("Tour: " + best.toString());
        System.out.println("Tour lenght : "+ best.tourSize());
        final String coordinates = best.toString();
        SwingUtilities.invokeLater(() -> new CoordinatesVisualization(coordinates));
        return new MetaheuristicResponse(best.tourSize(),best.getDistance());
    }
}

