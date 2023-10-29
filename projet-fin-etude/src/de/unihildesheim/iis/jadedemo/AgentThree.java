package de.unihildesheim.iis.jadedemo;

import java.io.IOException;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import java.io.Serializable;

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

      @Override
      public void action() {

        // Receive the incoming message
        ACLMessage aclMsg = receive();

        // Interpret the message
        if (aclMsg != null) {
		try {
              /*Tour curentSolution = (Tour) aclMsg.getContentObject();
              System.out.println("doha : " + curentSolution);
              long start = System.currentTimeMillis();
              Metaheuristic.metaHeuristic(curentSolution);*/
              
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          // TODO Aufgabe 1
        }
        block(); // Stop the behaviour until next message is received
      }
    };
    addBehaviour(loop);
  }
}
