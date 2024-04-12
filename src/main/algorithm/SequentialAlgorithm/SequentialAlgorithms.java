package main.algorithm.SequentialAlgorithm;

import java.util.List;
import main.linkedproblem.LinkedProblem;
import main.problem.Problem;
import main.solution.solution;
import main.solution.solutionPair;
import main.solution.solutionSet;
import main.util.LPDriver.LinkedProblemRunner;
import main.algorithm.SequentialAlgorithm.*;

@SuppressWarnings("hiding")
public class SequentialAlgorithms<S extends solution<?>, solutionSet> extends AbstractSequencialAlgorithms<S, solutionSet> {
	
	public SequentialAlgorithms (LinkedProblemRunner runner){
		setLinkedProblemRunner(runner);
	}
	
	@SuppressWarnings("rawtypes")
	@Override public solutionSet getResult() {
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
	public List<S> getPopulation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFirstProblem(Problem<?> firstProblem) {
		// TODO Auto-generated method stub
		
	}


}
