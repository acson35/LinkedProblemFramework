package main.algorithm;

import java.util.List;

import main.problem.Problem;

public interface GenericAlgorithm<S, R> {
	void run();
	R getResult();
	//void setProblem(Problem<S> problem);
	//void setProblem(LinkedProblem linkedproblem);
	List<S> getPopulation();
}
