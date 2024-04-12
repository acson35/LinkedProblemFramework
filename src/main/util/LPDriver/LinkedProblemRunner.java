package main.util.LPDriver;

import java.util.List;

import main.algorithm.Algorithm;
import main.algorithm.GenericAlgorithm;
import main.linkedproblem.LinkedProblem;
import main.solution.solution;

public class LinkedProblemRunner {

	private String linkedproblemName;
	private List<GenericAlgorithm> algorithmList;
	private LinkedProblem problemList;
	private String outputLinkedProblemFileName;
	private int independentRuns;
	

	/** Constructor */
	public LinkedProblemRunner(LinkedProblemRunnerBuilder builder) {
	   this.linkedproblemName = builder.getLinkedProblemName();
	   this.algorithmList = builder.getAlgorithmList();
	   this.problemList = builder.getProblemList();
	   this.outputLinkedProblemFileName = builder.getOutputLinkedProblemFileName();
	   this.independentRuns = builder.getIndependentRuns();
	}

	/* Getters */
	public String getLinkedproblemName() {
	  return linkedproblemName;
	}

	public List<GenericAlgorithm> getAlgorithmList() {
	  return algorithmList;
	}

	public LinkedProblem getProblemList() {
	  return problemList;
	}

	public String getOutputLinkedProblemFileName() {
	  return outputLinkedProblemFileName;
	}
	
	public int getIndependentRuns() {
		return independentRuns;
	}

	/* Setters */
	public void setAlgorithmList(List<GenericAlgorithm> algorithmList) {
	  this.algorithmList = algorithmList;
	}
}
