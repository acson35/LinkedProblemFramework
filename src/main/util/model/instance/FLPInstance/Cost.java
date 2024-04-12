package main.util.model.instance.FLPInstance;

public class Cost {
	
	private double cost;

	public Cost(double cost) {
	  this.cost = cost;
	}

	public double getCost() {
	  return this.cost;
	}

	public boolean isGreaterOrEqualZero() {
	  return this.cost >= 0;
	}
}
