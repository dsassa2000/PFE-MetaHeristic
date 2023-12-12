package de.unihildesheim.iis.jadedemo;

import java.io.IOException;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.unihildesheim.iis.jadedemo.graph.Car;
import de.unihildesheim.iis.jadedemo.graph.CarListWrapper;
import de.unihildesheim.iis.jadedemo.graph.MetaheuristicResponse;

/**
 * @author Viktor Eisenstadt
 */
public class AgentThree extends Agent {
  private static final long serialVersionUID = 1L;
  int maxMoves = 3000;
  int maxTries = 20;
  int maxCol = 15;
  float temperature = 1f;
  float alpha = 0.5f;
  boolean verbose = false;

  protected void setup() {

    // Define the behaviour
    CyclicBehaviour loop = new CyclicBehaviour(this) {
      private static final long serialVersionUID = 1L;
      private ArrayList<ACLMessage> receivedMessages;
      @Override
      public void action() {
  	      
    	  receivedMessages = new ArrayList<>();

          // Receive messages and store them
          for (int i = 0; i < 2; i++) { // Receive 2 messages in this example
              ACLMessage receivedMsg = receive();
              System.out.println(receivedMsg);
              if (receivedMsg != null) {
                  receivedMessages.add(receivedMsg);
              }
          }
           
          // Process the stored messages
          //if(receivedMessages.size() == 2) {
        	  for (ACLMessage msg : receivedMessages) {
                  try {
                      Object contentObject = msg.getContentObject();
                      List<Car> carList = new ArrayList();
                      float finalDist = 0;
                      if (contentObject instanceof CarListWrapper) {
                    	  CarListWrapper car = (CarListWrapper) contentObject;
                          // Process Car object
                          System.out.println("Received Car: " + car.getCarsList());
                          carList = car.getCarsList();
                      } else if (contentObject instanceof MetaheuristicResponse) {
                          MetaheuristicResponse response = (MetaheuristicResponse) contentObject;
                          // Process MetaheuristicResponse object
                          System.out.println("Received MetaheuristicResponse: " + response.getFinalDistance());
                          finalDist = response.getFinalDistance();
                      }
                      Car carAvecPlusMinRef = null;
                      float refMin = Float.MAX_VALUE; 
                      if(!carList.isEmpty() || finalDist != 0) {
                          for(Car car : carList) {
                        	  float ref = car.calculate_refueling_stops(358);
                        	  System.out.println("distanceRef pour " + car.getName() + " : " + ref);

                        	    if (ref < refMin) {
                        	        refMin = ref;
                        	        carAvecPlusMinRef = car;
                        	    }
                          }
                          if (carAvecPlusMinRef != null) {
                        	    System.out.println("La voiture avec le moins d'arrêts de ravitaillement nécessaire est : " + carAvecPlusMinRef.getName());
                        	    System.out.println("Nombre d'arrêts : " + refMin);
                        	} else {
                        	    System.out.println("Aucune voiture trouvée dans la liste.");
                        	}
                      }
                  } catch (UnreadableException e) {
                      e.printStackTrace();
                  }
              //}
          }
        block(); // Stop the behaviour until next message is received
      }
    };
    addBehaviour(loop);
  }
}
