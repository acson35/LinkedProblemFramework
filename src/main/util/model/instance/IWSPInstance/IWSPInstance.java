package main.util.model.instance.IWSPInstance;

import java.util.List;

import main.util.distance.DistImplementation.EuclideanDistanceBetweenTwoLocations;
import main.util.distance.DistImplementation.EuclideanDistanceBetweenTwoLocations.WeightType;
import main.util.model.instance.FLPInstance.Customer;
import main.util.model.instance.FLPInstance.Facility;

public class IWSPInstance {

	private List<Facility> facilities;
	private List<Customer> customers;
	private List<Vehicle> vehicles;
	private EuclideanDistanceBetweenTwoLocations euc = 
			new EuclideanDistanceBetweenTwoLocations(WeightType.CEIL_2D);
	
	public IWSPInstance(List<Facility> facilities, List<Customer> customers, List<Vehicle> vehicles) {
		this.facilities = facilities;
		this.customers = customers;
		this.vehicles = vehicles;
	}
	
	
	public List<Facility> getFacilities() {
		return this.facilities;
	}

	public List<Customer> getCustomers() {
		return this.customers;
	}
	
	public List<Vehicle> getVehicles() {
		return this.vehicles;
	}

	public int getnFacilities() {
		return this.facilities.size();
	}

	public int getnCustomers() {
	   return this.customers.size();
	}
	
	public int getnVehicles() {
		return this.vehicles.size();
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
		
	public Vehicle getVehicle(int vehicle) {
		  if(vehicle < 0 || vehicle >= vehicles.size()) {
		    throw new RuntimeException("Cannot find vehicle, invalid vehicle number: " + vehicle);
		  }
		  return vehicles.get(vehicle);
	}
	
	
	public double getDemandcost(int facility, int client) {
	  Facility f = getFacility(facility);
	  Customer c = getCustomer(client);
	  return f.getDemandCost(c).getCost();
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
		
	//public double getVehicleCost(int vehicle) {
	//  return getVehicle(vehicle).getFixedCost().getCost();
	//}
	
}
