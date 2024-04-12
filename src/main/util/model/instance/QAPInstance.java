package main.util.model.instance;

import java.util.Arrays;

public class QAPInstance {

	private String name;
	private int pSize;
	private int[][] flow;
	private int[][] distance;
	private String logicalName;

	public QAPInstance(String filePath, int pSize, int[][] flow, int[][] distance) {
		this.name = filePath + " c" + pSize;
		this.logicalName = " c" + pSize;
		this.pSize = pSize;
		this.flow = flow;
		this.distance = distance;
	}

	public String getName() {
		return name;
	}

	public String getLogicalName() {
		return logicalName;
	}

	public int getProblemSize() {
		return pSize;
	}

	
	public int[][] getFlows() {
		return flow;
	}

	public int[][] getDistances() {
		return distance;
	}

	@Override
	public String toString() {
		return "Instance [name=" + name + "\nproblemSize=" + pSize + "\nflow="
				+ Arrays.deepToString(flow) + "\ndistance=" + Arrays.deepToString(distance) + "]";
	}
	
}
