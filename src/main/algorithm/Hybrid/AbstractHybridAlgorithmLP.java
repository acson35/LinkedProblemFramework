package main.algorithm.Hybrid;

import main.algorithm.AlgorithmLPs;
import main.solution.solution;
import main.solution.solutionSet;
import main.util.LPDriver.LinkedProblemRunner;

public abstract class AbstractHybridAlgorithmLP<S extends solutionSet, R> implements AlgorithmLPs<S, R>{

	protected R overallSolution;
	
	private LinkedProblemRunner runner;
		
	public LinkedProblemRunner getLinkedProblemRunner() {
		 return runner;
	}
	
	public void setLinkedProblemRunner(LinkedProblemRunner runner) {
	  this.runner = runner;
	}
	
	@SuppressWarnings("rawtypes")
	@Override public abstract R getResult();
	
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	@Override public void run() {
		
	}

}
