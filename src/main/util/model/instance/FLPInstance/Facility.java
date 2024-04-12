package main.util.model.instance.FLPInstance;

import java.util.HashMap;
import java.util.Map;

import main.util.model.instance.location;
import main.util.model.instance.IWSPInstance.Inventory;
import main.util.model.instance.IWSPInstance.Vehicle;
import main.util.model.instance.SFLPInstance.EnergyConsumption;

public class Facility {
	
	
	  private double capacity;
	  private Cost fixedCost;
	  private Map<Customer, Cost> demandcost;
	  private Map<Vehicle, Cost> vehiclecost;
	  private Map<Customer, Cost> transport_emission;
	  private EnergyConsumption energy;
	  private location loc;
	  private Inventory inv;
	  private String id;

	  public Facility(location loc, Cost fixedCost, double capacity,  Map<Customer, Cost> demandcost, Map<Vehicle, Cost> vehiclecost, Inventory inv, String id) {
		  
	    if (capacity < 0) {
	      throw new RuntimeException("Capacity must be larger or equal to zero instead of " + capacity);
	    }
	    this.capacity = capacity;
	    if (!fixedCost.isGreaterOrEqualZero()) {
	      throw new RuntimeException(
	          "Cost for setting up the facility must be larger than zero instead of: " + fixedCost);
	    }
	    this.fixedCost = fixedCost;
	    this.demandcost = new HashMap<>(demandcost); 
	    this.vehiclecost = new HashMap<>(vehiclecost);
	    this.loc = loc;
	    this.inv = inv;
	    this.id = id;
	  }
	  
	  public Facility(double capacity,  Cost fixedCost, Map<Customer, Cost> demandcost,  String id) {
		  if (capacity < 0) {
		      throw new RuntimeException("Capacity must be larger or equal to zero instead of " + capacity);
		    }
		    this.capacity = capacity;
		    if (!fixedCost.isGreaterOrEqualZero()) {
		      throw new RuntimeException(
		          "Cost for setting up the facility must be larger than zero instead of: " + fixedCost);
		    }
		    this.fixedCost = fixedCost;
		    this.demandcost = new HashMap<>(demandcost); 
		    this.id = id;
	  }
	  
	  
	  public Facility(location loc, Cost fixedCost, double capacity,  Map<Customer, Cost> demandcost, Map<Customer, Cost> transport_emission, EnergyConsumption energy, String id) {
		  
		    if (capacity < 0) {
		      throw new RuntimeException("Capacity must be larger or equal to zero instead of " + capacity);
		    }
		    this.capacity = capacity;
		    if (!fixedCost.isGreaterOrEqualZero()) {
		      throw new RuntimeException(
		          "Cost for setting up the facility must be larger than zero instead of: " + fixedCost);
		    }
		    this.fixedCost = fixedCost;
		    this.demandcost = new HashMap<>(demandcost); 
		    this.transport_emission = new HashMap<>(transport_emission);
		    this.energy = energy;
		    this.loc = loc;
		    this.id = id;
		  }
	  
	  public String getId() {
		  return this.id;
	  }

	  public double getCapacity() {
	    return capacity;
	  }

	  public Cost getFixedCost() {
	    return fixedCost;
	  }
	  
	  public location getLocation() {
		 return loc;
	  }
	  
	  public Inventory getInventory() {
		return inv;
	  }

	  public Cost getDemandCost(Customer customer) {
	    return demandcost.get(customer);
	  }
	  
	  public void setDemandCost(Map<Customer, Cost> demandcost) {
		  this.demandcost = demandcost;
	  }
	  
	  public Map<Customer, Cost> getCustomersDemandCost(){
		  return demandcost;
	  }
	  
	  	  
	  public Cost getVehicleCost(Vehicle vehicle) {
		  return vehiclecost.get(vehicle);
	  }
	  
		  
	  public void setVehicleCost(Map<Vehicle, Cost> vehicleCost) {
		  this.vehiclecost = vehicleCost;
	  }
		  
	  public Map<Vehicle, Cost> getVehiclesCost(){
		  return vehiclecost;
	  }
	  
	  public Map<Customer, Cost> getTransportEmission(){
		  return transport_emission;
	  }
	  
	  public Cost getTransportEmissionCost(Customer customer){
		  return transport_emission.get(customer);
	  }
	  
	  public void setTransportEmissionCost(Map<Customer, Cost> transportCost) {
		  this.transport_emission = transportCost;
	  }
	  
	  public EnergyConsumption getEnergyConsumption() {
		  return this.energy;
	  }
	  
	  public void setEnergyConsumption(EnergyConsumption energy) {
		  this.energy = energy;
	  }

	  @Override
	  public boolean equals(Object o) {
	    if (o instanceof Facility) {
	      return id.equals(((Facility) o).id);
	    }
	    return false;
	  }

	  @Override
	  public String toString() {
	    return id;
	  }
}
