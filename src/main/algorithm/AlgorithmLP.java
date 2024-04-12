package main.algorithm;

import java.util.List;

import main.linkedproblem.LinkedProblem;
import main.solution.solutionPair;

@SuppressWarnings("rawtypes")
public interface AlgorithmLP<S, R> {
	void run();
	
	R getResult();
	
	void setProblem(LinkedProblem problemList);
	
	List<solutionPair> getPopulation();
}
