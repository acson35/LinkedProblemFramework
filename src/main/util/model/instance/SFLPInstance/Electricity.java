package main.util.model.instance.SFLPInstance;

import main.util.model.instance.FLPInstance.Cost;

public class Electricity {

	Cost elecCost;
	double unitRate=0.0;
	double numOfhr=0.0;
	
	public Electricity(Cost elecCost) {
		this.elecCost = elecCost;
	}
	
	public Electricity(double unitRate, double numOfhr) {
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
	
	public Cost getElectricityCost() {
		if(unitRate==0.0 || numOfhr==0.0) {
			return this.elecCost;
		}else {
			return new Cost(getUnitRate()*getNumOfHr());
		}	
	}
	
	public void setElectricityCost(Cost elecCost) {
		this.elecCost = elecCost;
	}
}
