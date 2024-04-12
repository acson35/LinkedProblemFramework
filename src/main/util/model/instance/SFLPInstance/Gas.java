package main.util.model.instance.SFLPInstance;

import main.util.model.instance.FLPInstance.Cost;

public class Gas {
	
	Cost gasCost;
	double unitRate=0.0;
	double numOfhr=0.0;
	
	public Gas(Cost gasCost) {
		this.gasCost = gasCost;
	}
	
	public Gas(double unitRate, double numOfhr) {
		this.unitRate = unitRate;
		this.numOfhr = numOfhr;
	}
	
	public double getUnitRate() {
		return this.unitRate;
	}
	
	public void setUnitRate(double unitRate) {
		this.unitRate = unitRate;
	}
	
	public double getNumOfHr() {
		return this.numOfhr;
	}
	
	public void setNumOfHr(double numOfhr) {
		this.numOfhr = numOfhr;
	}
	
	public Cost getGasCost() {
		if(unitRate==0.0 || numOfhr==0.0) {
			return this.gasCost;
		}else {
			return new Cost(getUnitRate()*getNumOfHr());
		}	
	}
	
	public void setGasCost(Cost gasCost) {
		this.gasCost = gasCost;
	}
}
