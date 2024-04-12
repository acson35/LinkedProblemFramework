package main.util.model.instance;

import java.util.Arrays;

public class KPInstance {
	private String name;
	private int itemsNum;
	private int knapsackWeight;
	private int[] itemsValue;
	private int[] itemsWeight;
	private String logicalName;

	public KPInstance(String filePath, int itemsNum, int knapsackWeight, int[] itemsValue,
			int[] itemsWeight) {
		this.name = filePath + " i" + itemsNum + "_" + knapsackWeight;
		this.logicalName = " i" + itemsNum + "_" + knapsackWeight;
		this.itemsNum = itemsNum;
		this.knapsackWeight = knapsackWeight;
		this.itemsValue = itemsValue;
		this.itemsWeight = itemsWeight;
	}

	public String getName() {
		return name;
	}

	public String getLogicalName() {
		return logicalName;
	}

	public int getItemsNum() {
		return itemsNum;
	}
	
	public int getKnapsackWeight() {
		return knapsackWeight;
	}


	public int[] getItemsValue() {
		return itemsValue;
	}

	public int[] getItemsWeight() {
		return itemsWeight;
	}

	

	@Override
	public String toString() {
		return "Instance [name=" + name + "\nitemsNum=" + itemsNum + "\nknapsackWeight=" + knapsackWeight + "\nitemsValue=" + Arrays.toString(itemsValue)
				+ "\nitemsWeight=" + Arrays.toString(itemsWeight) + "]";
	}

}
