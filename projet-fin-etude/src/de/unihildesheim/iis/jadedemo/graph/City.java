package de.unihildesheim.iis.jadedemo.graph;

public class City {
	String name;
	float x;
    float y;
    
    // Constructs a randomly placed city
    public City(){
        this.x = (float)(Math.random()*200);
        this.y = (float)(Math.random()*200);
    }
    
    // Constructs a city at chosen x, y location
    public City(float x, float y){
        this.x = x;
        this.y = y;
    }
    public String getName() {
    	return this.name;
    }
    // Gets city's x coordinate
    public float getX(){
        return this.x;
    }
    
    // Gets city's y coordinate
    public float getY(){
        return this.y;
    }
    
    // Gets the distance to given city
    public double distanceTo(City city){
    	float xDistance = Math.abs(getX() - city.getX());
    	float yDistance = Math.abs(getY() - city.getY());
        double distance = Math.sqrt( (xDistance*xDistance) + (yDistance*yDistance) );
        
        return distance;
    }
    
    @Override
    public String toString(){
        return getX()+", "+getY();
    }
}
