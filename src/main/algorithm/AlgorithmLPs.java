package main.algorithm;

import java.util.List;

import main.linkedproblem.LinkedProblem;
import main.problem.Problem;
import main.solution.solutionPair;
import main.solution.solutionSet;

public interface AlgorithmLPs<S, R> extends GenericAlgorithm<S, R>{

	
	void run();
	
	R getResult();
	
	void setProblem(LinkedProblem problemList);
	
	void setFirstProblem(Problem<?> firstProblem);
	
	List<S> getPopulation();
}
