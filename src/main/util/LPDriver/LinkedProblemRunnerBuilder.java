package main.util.LPDriver;

import java.util.ArrayList;
import java.util.List;

import main.algorithm.Algorithm;
import main.algorithm.GenericAlgorithm;
import main.linkedproblem.LinkedProblem;
import main.solution.solution;

public class LinkedProblemRunnerBuilder<S extends solution<?>> {

	
	private List<GenericAlgorithm> algorithmList;
	private final String linkedproblemName;
	private LinkedProblem problemList;
	private String outputLinkedProblemFileName;
	private int independentRuns;
	
	public LinkedProblemRunnerBuilder(String linkedproblemName) {
	    this.linkedproblemName = linkedproblemName ;
	    this.independentRuns = 1 ;
	 
	}
	
	@SuppressWarnings("rawtypes")
	public LinkedProblemRunnerBuilder setAlgorithmList(List<GenericAlgorithm> list) {
	    this.algorithmList = new ArrayList<>(list) ;
	    return this ;
	}

	@SuppressWarnings("rawtypes")
	public LinkedProblemRunnerBuilder setProblemList(LinkedProblem problemList) {
	    this.problemList = problemList ;
	    return this ;
	}
	
	
	@SuppressWarnings("rawtypes")
	public LinkedProblemRunnerBuilder setOutputLinkedProblemFileName(String outputLinkedProblemFileName) {
	    this.outputLinkedProblemFileName = linkedproblemName+outputLinkedProblemFileName ;
	    return this ;
	}
	
	@SuppressWarnings("rawtypes")
	public LinkedProblemRunnerBuilder setIndependentRuns(int independentRuns) {
	    this.independentRuns = independentRuns ;
	    return this ;
	}

	
	public LinkedProblemRunner build() {
	  return new LinkedProblemRunner(this);
	}
	
	public String getLinkedProblemName() {
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

		 
}
