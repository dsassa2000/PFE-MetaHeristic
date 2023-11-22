package de.unihildesheim.iis.jadedemo;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import java.io.BufferedReader;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import de.unihildesheim.iis.jadedemo.graph.City;
import de.unihildesheim.iis.jadedemo.graph.MetaheuristicResponse;
import de.unihildesheim.iis.jadedemo.graph.TourManager;

import java.lang.reflect.Type;

/**
 * @author Viktor Eisenstadt
 */
public class AgentTwo extends Agent {
  private static final long serialVersionUID = 1L;
  BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
  protected void setup() {

    // Define the behaviour
    CyclicBehaviour loop = new CyclicBehaviour(this) {
      private static final long serialVersionUID = 1L;

      @Override
      public void action() {
        // Receive the incoming message
        ACLMessage aclMsg = receive();
        // Interpret the message
        if (aclMsg != null) {
		  Gson gson = new Gson();
		  // Use Gson to parse the JSON input
          Type locationListType = new TypeToken<List<City>>(){}.getType();
          List<City> dataCities = gson.fromJson(aclMsg.getContent(), locationListType);
		  System.out.print("dataCities : " +dataCities);
		  for (City city : dataCities) {
		    System.out.println("Name: " + city.getName());
		    System.out.println("Latitude: " + city.getX());
		    System.out.println("Longitude: " + city.getY());
		    City cityTour = new City(city.getX(), city.getY());
            TourManager.addCity(cityTour);
		  }
	      // Initialize intial solution
          Tour currentSolution = new Tour();
          currentSolution.generateIndividual();
          System.out.println("Initial Tour : "+currentSolution);
          long start = System.currentTimeMillis();
          MetaheuristicResponse reponse =  Metaheuristic.metaHeuristic(currentSolution);
          long end = System.currentTimeMillis();
          System.out.println("date execution : "+ (end - start) +"ms");
          ACLMessage newMsg = new ACLMessage(ACLMessage.REQUEST);
          newMsg.addReceiver(new AID("AgentThree", AID.ISLOCALNAME));
          try {
			newMsg.setContentObject(reponse.getFinalDistance());
			send(newMsg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        }
        block(); // Stop the behaviour until next message is received
      }
    };
    addBehaviour(loop);
    
 // Start a server to listen for Python messages
    startServer();
  }
  private void startServer() {
      new Thread(() -> {
          try {
              ServerSocket serverSocket = new ServerSocket(9876);
              System.out.println("Java Server listening on port 9876");

              while (true) {
                  Socket socket = serverSocket.accept();
                  Scanner scanner = new Scanner(socket.getInputStream());

                  while (scanner.hasNextLine()) {
                      String message = scanner.nextLine();
                      ACLMessage aclMessage = new ACLMessage(ACLMessage.INFORM);
                      aclMessage.setContent(message);
                      aclMessage.addReceiver(getAID());
                      send(aclMessage);
                  }

                  scanner.close();
                  socket.close();
              }
          } catch (IOException e) {
              e.printStackTrace();
          }
      }).start();
  }
}
