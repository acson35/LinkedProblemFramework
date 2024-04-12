package main.util.factory.Builder;

import main.problem.Problem;
import main.solution.solution;

public interface InstanceBuilder<S> {
	
	public Problem<S> build(Problem<S> childproblem, solution<?> parentsolution);
	
}
