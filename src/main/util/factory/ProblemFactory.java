package main.util.factory;

import main.problem.Problem;
import main.solution.solution;

public interface ProblemFactory {

	public Problem<?> getProblemInstance();
}
