package de.unihildesheim.iis.jadedemo;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.wrapper.StaleProxyException;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import de.unihildesheim.iis.jadedemo.graph.Car;
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
            try {
            	if(aclMsg != null) {
            		Gson gson = new Gson();
                	Type carsListType = new TypeToken<List<Car>>(){}.getType();
                    List<Car> dataCars = gson.fromJson(aclMsg.getContent(), carsListType);
                    System.out.print("dataCities : " +dataCars);
                    List<Car> carList = new ArrayList<>();
          		  for (Car car : dataCars) {
          		    Car carObject = new Car(car.getName(), car.getFuel_efficiency(),car.getTank_capacity());
                    carList.add(carObject);
          		  }
          		  System.out.println("listcar: " + carList);
                  ACLMessage newMsg = new ACLMessage(ACLMessage.REQUEST);
                  newMsg.addReceiver(new AID("AgentThree", AID.ISLOCALNAME));
                  byte[] serializedList = serializeObject(carList);
                  newMsg.setContentObject(serializedList);
            	}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
              ServerSocket serverSocket = new ServerSocket(9877);
              System.out.println("Java Server listening on port 9877");

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
//Method to serialize an object into a byte array
  private byte[] serializeObject(Object obj) {
      try {
          ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
          ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteOutputStream);
          objectOutputStream.writeObject(obj);
          objectOutputStream.flush();
          return byteOutputStream.toByteArray();
      } catch (IOException e) {
          e.printStackTrace();
          return null;
      }
  }
}
