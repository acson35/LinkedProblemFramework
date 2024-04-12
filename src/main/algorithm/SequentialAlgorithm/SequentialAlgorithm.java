package main.algorithm.SequentialAlgorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import main.linkedproblem.LinkedProblem;
import main.solution.solution;
import main.solution.solutionPair;
import main.util.LPDriver.LinkedProblemRunner;


public class SequentialAlgorithm<S extends solution<?>, R> extends AbstractSequentialAlgorithm<S, R> {


	public SequentialAlgorithm (LinkedProblemRunner runner){
		setLinkedProblemRunner(runner);
	}
	
	@SuppressWarnings("rawtypes")
	@Override public R getResult() {
		return overallSolution;
	}
		
	//public void run() {
		//run();
	//}

	@Override
	public void setProblem(LinkedProblem problemList) {
		//runner.getProblemList().getLinkedProblems()	
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<solutionPair> getPopulation() {
		// TODO Auto-generated method stub
		return null;
	}

}
