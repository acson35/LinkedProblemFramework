package main.util.model.instance.FLPInstance;

import java.util.List;

public class FLPInstance {

	private List<Facility> facilities;
	private List<Customer> customers;

	public FLPInstance(List<Facility> facilities, List<Customer> customers) {
	  this.facilities = facilities;
	  this.customers = customers;
	}
	
	public List<Facility> getFacilities() {
	   return this.facilities;
	}

	public List<Customer> getCustomers() {
	   return this.customers;
	}

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

	public double getFixedCost(int facility) {
	  return getFacility(facility).getFixedCost().getCost();
	}
	
}
