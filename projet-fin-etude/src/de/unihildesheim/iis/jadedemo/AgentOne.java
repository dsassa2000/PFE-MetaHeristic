package de.unihildesheim.iis.jadedemo;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.wrapper.StaleProxyException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;

import de.unihildesheim.iis.jadedemo.graph.City;
import de.unihildesheim.iis.jadedemo.graph.TourManager;

/**
 * Jade Agent template
 *
 * @author Viktor Eisenstadt
 */
public class AgentOne extends Agent {
  private static final long serialVersionUID = 1L;

  protected void setup() {

    // Define the behaviour
    CyclicBehaviour loop = new CyclicBehaviour(this) {
      private static final long serialVersionUID = 1L;
      @Override
      public void action() {
        // Receive the incoming message
        ACLMessage aclMsg = receive();
        // Interpret the message
        	ACLMessage newMsg = new ACLMessage(ACLMessage.INFORM);
            newMsg.addReceiver(new AID("AgentThree", AID.ISLOCALNAME));
            try {
            	 // Create and add our cities
                City city = new City(60, 200);
                TourManager.addCity(city);
                City city2 = new City(180, 200);
                TourManager.addCity(city2);
                City city3 = new City(80, 180);
                TourManager.addCity(city3);
                City city4 = new City(140, 180);
                TourManager.addCity(city4);
                City city5 = new City(20, 160);
                TourManager.addCity(city5);
                City city6 = new City(100, 160);
                TourManager.addCity(city6);
                City city7 = new City(200, 160);
                TourManager.addCity(city7);
                City city8 = new City(140, 140);
                TourManager.addCity(city8);
                City city9 = new City(40, 120);
                TourManager.addCity(city9);
                City city10 = new City(100, 120);
                TourManager.addCity(city10);
                City city11 = new City(180, 100);
                TourManager.addCity(city11);
                City city12 = new City(60, 80);
                TourManager.addCity(city12);
                City city13 = new City(120, 80);
                TourManager.addCity(city13);
                City city14 = new City(180, 60);
                TourManager.addCity(city14);
                City city15 = new City(20, 40);
                TourManager.addCity(city15);
                City city16 = new City(100, 40);
                TourManager.addCity(city16);
                City city17 = new City(200, 40);
                TourManager.addCity(city17);
                City city18 = new City(20, 20);
                TourManager.addCity(city18);
                City city19 = new City(60, 20);
                TourManager.addCity(city19);
                City city20 = new City(160, 20);
                TourManager.addCity(city20);
             // Initialize intial solution
                Tour currentSolution = new Tour();
                currentSolution.generateIndividual();
                System.out.println("DOHA : "+currentSolution);
				///newMsg.setContentObject(currentSolution);
                long start = System.currentTimeMillis();
                Metaheuristic.metaHeuristic(currentSolution);
                long end = System.currentTimeMillis();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            send(newMsg);
       // }       
        block(); // Stop the behaviour until next message is received
      }
    };
    addBehaviour(loop);
  }
}
