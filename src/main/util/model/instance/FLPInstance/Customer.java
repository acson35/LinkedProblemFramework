package main.util.model.instance.FLPInstance;

import main.util.model.instance.location;

public class Customer {

	private double demand;
	private double dVariance;
	private location loc;
	private String id;

	public Customer(double demand, String id) {
	  this.demand = demand;
	  this.id = id;
	}
	
	public Customer(location loc, double demand, double dVariance, String id) {
		this.loc = loc;  
		this.demand = demand;
		this.dVariance = dVariance;
		this.id = id;
	}
	
	public Customer(location loc, double demand, String id) {
		this.loc = loc;  
		this.demand = demand;
		this.id = id;
	}

	public double getDemand() {
	  return this.demand;
	}
	
	public double getDemandVariance() {
		return this.dVariance;
	}
	
	public location getLocation() {
		return this.loc;
	}

	@Override
	public boolean equals(Object o) {
	  if(o instanceof Customer) {
	    return id.equals(((Customer) o).id);
	  }
	  return false;
	}

	@Override
	public String toString() {
	  return id;
	}
}
