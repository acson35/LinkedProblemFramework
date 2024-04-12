package main.util.factory.Instatiator;

import java.util.List;

import main.problem.Problem;
import main.solution.solution;

public interface SolutionInstantiator<T> {
	
	List<T> variables();
	void setsolution();
	
}
