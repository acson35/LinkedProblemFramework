package main.util.model.instance;

import java.util.Arrays;

public class JAPInstance {
	private String name;
	private int agentsNum;
	private int jobsNum;
	private int[][] costs;
	private int[][] resoucesNeeded;
	private int[] agentsCapacity;
	private String logicalName;

	public JAPInstance(String filePath, int agentsNum, int jobsNum, int[][] costs, int[][] resourcesNeeded,
			int[] agentsCapacity) {
		this.name = filePath + " c" + agentsNum + "" + jobsNum;
		this.logicalName = " c" + agentsNum + "" + jobsNum;
		this.agentsNum = agentsNum;
		this.jobsNum = jobsNum;
		this.costs = costs;
		this.resoucesNeeded = resourcesNeeded;
		this.agentsCapacity = agentsCapacity;
	}

	public String getName() {
		return name;
	}

	public String getLogicalName() {
		return logicalName;
	}

	public int getAgentsNum() {
		return agentsNum;
	}

	public int getJobsNum() {
		return jobsNum;
	}

	public int[][] getCosts() {
		return costs;
	}

	public int[][] getResoucesNeeded() {
		return resoucesNeeded;
	}

	public int[] getAgentsCapacity() {
		return agentsCapacity;
	}

	@Override
	public String toString() {
		return "Instance [name=" + name + "\nagentsNum=" + agentsNum + "\njobsNum=" + jobsNum + "\ncosts="
				+ Arrays.deepToString(costs) + "\nresoucesNeeded=" + Arrays.deepToString(resoucesNeeded)
				+ "\nagentsCapacity=" + Arrays.toString(agentsCapacity) + "]";
	}
}
