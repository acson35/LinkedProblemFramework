package main.util.model.instance.IWSPInstance;

import main.util.model.instance.FLPInstance.Cost;
import main.util.model.instance.FLPInstance.Customer;

public class Inventory {
	
	private Cost hCost;
	private Cost oCost;
	private int oLeadTime;
	private double zScore;
	
	
	public Inventory(Cost hCost, Cost oCost, int oLeadTime, double zScore) {
		
		this.hCost = hCost;
		this.oCost = oCost;
		this.oLeadTime = oLeadTime;
		this.zScore = zScore;
	}
	
	public Cost getHoldingCost() {
	    return hCost;
	}
	
	public Cost getOrderingCost() {
	    return oCost;
	}
	
	public int getLeadTime() {
	    return oLeadTime;
	}
	
	public double getZScore() {
	    return zScore;
	}
	
	
}
