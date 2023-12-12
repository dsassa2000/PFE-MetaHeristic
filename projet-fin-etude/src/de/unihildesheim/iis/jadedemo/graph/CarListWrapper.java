package de.unihildesheim.iis.jadedemo.graph;

import java.io.Serializable;
import java.util.List;

public class CarListWrapper implements Serializable{
	
	private List<Car> carsList;

    public CarListWrapper(List<Car> carsList) {
        this.carsList = carsList;
    }

    public List<Car> getCarsList() {
        return carsList;
    }

}
