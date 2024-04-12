package main.util.model.instance;

import java.util.Arrays;
import java.util.List;

import main.util.model.instance.SFLPInstance.EnergyConsumption;

public class SPFSPInstance {
	private String name;
	private int nJobs;
	private int nMachines;
	private int nFacilities;
	private List<int[][]> processingTimes;
	private String logicalName;
	private EnergyConsumption[] energyPower;

	public SPFSPInstance(String filePath, int nJobs, int nMachines, int nFacilities, EnergyConsumption[] energyPower,  List<int[][]> processingTimes) {
		this.name = filePath + " i" + nJobs + "_" + nMachines+ "_" + nFacilities;
		this.logicalName = " i" + nJobs + "_" + nMachines+ "_" + nFacilities;
		this.nJobs = nJobs;
		this.nMachines = nMachines;
		this.nFacilities = nFacilities;
		this.processingTimes = processingTimes;
		this.energyPower = energyPower;
	}

	public String getName() {
		return name;
	}

	public String getLogicalName() {
		return logicalName;
	}

	public int getNJobs() {
		return nJobs;
	}
	
	public int getnMachine() {
		return nMachines;
	}


	public int getNFacilities() {
		return nFacilities;
	}
	
	public EnergyConsumption[] getEnergyPower() {
		return energyPower;
	}

	public List<int[][]> getProcessingTimes() {
		return processingTimes;
	}

	@Override
	public String toString() {
		
		 StringBuilder arrayAsString = new StringBuilder("[");
	        for (int i = 0; i < getProcessingTimes().size(); i++) {
	            arrayAsString.append(Arrays.toString(getProcessingTimes().get(i)));
	            if (i < getProcessingTimes().size() - 1) {
	                arrayAsString.append(", ");
	            }
	        }
	        arrayAsString.append("]");
		return "Instance [name=" + name + "\nJobs=" + nJobs + "\nMachines=" + nMachines + "\nFacilities=" + nFacilities+ "\nEnergyPower=" + Arrays.toString(energyPower)
				+ "\nprocessing times=" + arrayAsString + "]";
	}
}
