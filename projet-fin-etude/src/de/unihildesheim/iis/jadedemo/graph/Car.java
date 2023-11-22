package de.unihildesheim.iis.jadedemo.graph;

import java.io.Serializable;

public class Car implements Serializable {
	String name;
	float fuel_efficiency;
	float tank_capacity;
	
	public Car() {};
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getFuel_efficiency() {
		return fuel_efficiency;
	}

	public void setFuel_efficiency(float fuel_efficiency) {
		this.fuel_efficiency = fuel_efficiency;
	}

	public float getTank_capacity() {
		return tank_capacity;
	}

	public void setTank_capacity(float tank_capacity) {
		this.tank_capacity = tank_capacity;
	}

	public Car(String name,float fuel_efficiency,float tank_capacity) {
		this.name = name;
		this.fuel_efficiency = fuel_efficiency;
		this.tank_capacity = tank_capacity;
	}
    public float calculate_max_distance() {
        return this.fuel_efficiency * this.tank_capacity;
    }

    public float calculate_refueling_stops(float total_distance) {
        float max_distance = calculate_max_distance();
        return total_distance / max_distance;
    }

}
