package de.unihildesheim.iis.jadedemo.graph;

import java.io.Serializable;

public class MetaheuristicResponse implements Serializable {
	
	private int tourSize;
	private int finalDistance;
	
	public int getTourSize() {
		return tourSize;
	}
	public MetaheuristicResponse() {}
	public MetaheuristicResponse(int tourSize,int finalDistance) {
		this.tourSize = tourSize;
		this.finalDistance = finalDistance;
	}
	
	public void setTourSize(int tourSize) {
		this.tourSize = tourSize;
	}
	public int getFinalDistance() {
		return finalDistance;
	}
	public void setFinalDistance(int finalDistance) {
		this.finalDistance = finalDistance;
	}
	

}
