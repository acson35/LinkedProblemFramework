package main.util.factory;

import main.problem.Problem;
import main.solution.solution;
import main.util.factory.Builder.ProblemBuilder;


public class GetProblemFactory implements ProblemFactory {

	Problem<?> childproblem;
	solution<?> parentSolution;
	
	public GetProblemFactory(Problem<?> childproblem, solution<?> parentSolution) {
		this.childproblem = childproblem;
		this.parentSolution = parentSolution;
	}
	
	public Problem<?> childproblem(){
		return this.childproblem;
	}
	
	public solution<?> parentsolution(){
		return this.parentSolution;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Problem<?> getProblemInstance(){  
		return new ProblemBuilder().build(childproblem(), parentsolution());
	}

	
}
