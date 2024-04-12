package main.util.model.instance.SFLPInstance;

import java.util.List;

import main.util.distance.DistImplementation.EuclideanDistanceBetweenTwoLocations;
import main.util.distance.DistImplementation.EuclideanDistanceBetweenTwoLocations.WeightType;
import main.util.model.instance.FLPInstance.Customer;
import main.util.model.instance.FLPInstance.Facility;
import main.util.model.instance.IWSPInstance.Vehicle;

public class SFLPInstance {

	
	private List<Facility> facilities;
	private List<Customer> customers;
	//private List<Customer> transport_emission;
	private EuclideanDistanceBetweenTwoLocations euc = 
			new EuclideanDistanceBetweenTwoLocations(WeightType.CEIL_2D);
	
	public SFLPInstance(List<Facility> facilities, List<Customer> customers) {
		this.facilities = facilities;
		this.customers = customers;
		//this.transport_emission = transport_emission;
	}
	
	
	public List<Facility> getFacilities() {
		return this.facilities;
	}

	public List<Customer> getCustomers() {
		return this.customers;
	}
	
	//public List<Customer> getTransportEmissions() {
		//return this.transport_emission;
	//}

	public int getnFacilities() {
		return this.facilities.size();
	}

	public int getnCustomers() {
	   return this.customers.size();
	}
		
	public Customer getCustomer(int customer) {
	  if(customer < 0 || customer >= customers.size()) {
	    throw new RuntimeException("Cannot find customer, invalid customer number: " + customer);
	  }
	    return customers.get(customer);
	}

	public Facility getFacility(int facility) {
	  if(facility < 0 || facility >= facilities.size()) {
	    throw new RuntimeException("Cannot find facility, invalid facility number: " + facility);
	  }
	  return facilities.get(facility);
	}
	
	public double getDemandcost(int facility, int client) {
	  Facility f = getFacility(facility);
	  Customer c = getCustomer(client);
	  return f.getDemandCost(c).getCost();
	}
	
	public double getTransportEmissionCost(int facility, int client) {
		  Facility f = getFacility(facility);
		  Customer c = getCustomer(client);
		  return f.getTransportEmissionCost(c).getCost();
	}
	
	public double getDistance(int facility, int client) {
		  Facility f = getFacility(facility);
		  Customer c = getCustomer(client);
		  return euc.distance(f.getLocation(), c.getLocation());
	}
	
	public double getCustDistance(int client1, int client2) {
		  Customer c1 = getCustomer(client1);
		  Customer c2 = getCustomer(client2);
		  return euc.distance(c1.getLocation(), c2.getLocation());
	}

	public double getFixedCost(int facility) {
	  return getFacility(facility).getFixedCost().getCost();
	}
	
	public double getCapacity(int facility) {
		  return getFacility(facility).getCapacity();
	}
	
	public EnergyConsumption getEnergyConsumption(int facility) {
		  return getFacility(facility).getEnergyConsumption();
	}
		
	//public double getVehicleCost(int vehicle) {
	//  return getVehicle(vehicle).getFixedCost().getCost();
	//}
	
}
