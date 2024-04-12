package main.util.model.instance.SFLPInstance;

import main.util.model.instance.FLPInstance.Cost;

public class EnergyConsumption {

	Gas gas;
	Electricity elec;
	Cost energycon;
	
	public EnergyConsumption(Gas gas, Electricity elec) {
		this.gas = gas;
		this.elec = elec;
	}
	
	public EnergyConsumption(Cost energycon) {
		this.energycon = energycon;
	}
	
	public Gas getGasConsumption() {
		return this.gas;
	}
	
	public void setGasConsumption(Gas gas) {
		this.gas = gas;
	}
	
	
	public Electricity getElectricityConsumption() {
		return this.elec;
	}
	
	public void setElectricityConsumption(Electricity elec) {
		this.elec = elec;
	}
	
	public Cost getEnergyCost() {
		return this.energycon;
	}
	
	public void setEnergyCost(Gas gas, Electricity elec) {
		double cost = gas.getGasCost().getCost() + elec.getElectricityCost().getCost();
		this.energycon = new Cost(cost);
	}
	
	public void setEnergyCost(Cost energycon) {
		this.energycon = energycon;
	}
	
	
}
