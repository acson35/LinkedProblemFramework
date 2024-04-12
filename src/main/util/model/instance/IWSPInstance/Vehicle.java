package main.util.model.instance.IWSPInstance;

import java.util.HashMap;
import java.util.Map;

import main.util.model.instance.FLPInstance.Cost;
import main.util.model.instance.FLPInstance.Customer;

public class Vehicle {

	
	private Cost fCost;
	private Cost vCost;
	private Map<Customer, Cost> deliverycost;
	private double capacity;
	private String id;
	
	public Vehicle(Cost fixedCost, Cost variableCost, Map<Customer, Cost> deliverycost, double capacity, String id) {
		
		
		if (!fixedCost.isGreaterOrEqualZero()) {
		      throw new RuntimeException(
		          "Cost for setting up the facility must be larger than zero instead of: " + fixedCost);
		}
		
		this.fCost = fixedCost;
		
		if (!variableCost.isGreaterOrEqualZero()) {
		      throw new RuntimeException(
		          "Cost for setting up the facility must be larger than zero instead of: " + variableCost);
		}
		this.vCost = variableCost;
		
		if (capacity < 0) {
		      throw new RuntimeException("Capacity must be larger or equal to zero instead of " + capacity);
		}
		this.capacity = capacity;
		    
		this.deliverycost = new HashMap<>(deliverycost); 
		this.id = id;
		
	}
	
	public Cost getFixedCost() {
	    return fCost;
	}
	
	public Cost getVariableCost() {
	    return vCost;
	}
	
	public Cost getDeliveryCost(Customer customer) {
	    return deliverycost.get(customer);
	}
	  
	public void setCustomersDeliveryCost(Map<Customer, Cost> cdeliverycost) {
		this.deliverycost = cdeliverycost;
	}
	  
	public Map<Customer, Cost> getCustomersDeliveryCost(){
	  return deliverycost;
	}
	
	public double getCapacity() {
	    return capacity;
	}
	
	
	public String getVehicleId() {
	    return id;
	}
	
	@Override
	public boolean equals(Object o) {
	  if (o instanceof Vehicle) {
	    return id.equals(((Vehicle) o).id);
	  }
	  return false;
	}

	@Override
	public String toString() {
	  return id;
	}	
}
