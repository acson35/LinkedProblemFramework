package main.algorithm;

import java.util.List;

import main.linkedproblem.LinkedProblem;
import main.problem.Problem;
import main.solution.solution;

public interface Algorithm<S, R> extends GenericAlgorithm<S, R>{
	void run();
	R getResult();
	void setProblem(Problem<S> problem);
	//void setProblem(LinkedProblem linkedproblem);
	List<S> getPopulation();
}
