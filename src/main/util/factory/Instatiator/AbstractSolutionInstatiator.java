package main.util.factory.Instatiator;

import java.util.ArrayList;
import java.util.List;

import main.linkedproblem.LinkedProblem;
import main.problem.Problem;
import main.solution.solution;
import main.solution.integerSolution.IntegerSolution;
import main.solution.permutationSolution.IntegerPermutationSolution;
import main.util.adjacencymatrix.LinkedProblemMatrix;
import main.util.createBinary.createBinary;
import main.util.factory.Instatiator.ChangeSolution.ChangeBinarySolution;
import main.util.factory.Instatiator.ChangeSolution.ChangeIntegerPermutationSolution;
import main.util.factory.Instatiator.ChangeSolution.ChangeIntegerSolution;

public abstract class AbstractSolutionInstatiator<T> implements SolutionInstantiator<T>{
	
	
	private List<T> variables;
	
	protected AbstractSolutionInstatiator(int numberOfVariables) {
		variables = new ArrayList<>(numberOfVariables);
		for (int i = 0; i < numberOfVariables; i++) {
			variables.add(i, null);
		} 
	}
	
	@Override
	public List<T> variables() {
		return this.variables;
	}
	
	public static List<Integer> size(solution<?> solution) {
		List<Integer> size = new ArrayList<>();
		if(solution.getSolutionType().equalsIgnoreCase("BINARY")) {
			for(int i=0; i<solution.variables().size(); i++) {
				createBinary binaryset = (createBinary) solution.variables().get(i);
				size.add(binaryset.length());
			}
			return size; 
		}else if(solution.getSolutionType().equalsIgnoreCase("INTEGERPERMUTATION")) {
			IntegerPermutationSolution sol = (IntegerPermutationSolution) solution;
			if(solution.problemname().equalsIgnoreCase("QAP")) {
				for(int i=0; i<sol.variables().size(); i++) {
					size.add(sol.variables().size());
				}
			}else {
				size.add(solution.variables().size());
			}
			
			return 	size;
		}else if(solution.getSolutionType().equalsIgnoreCase("INTEGER")) {
			IntegerSolution sol = (IntegerSolution) solution;
			size.add(sol.variables().size());
			return 	size;	
		}else if(solution.getSolutionType().equalsIgnoreCase("SEQUENCE")) {
			size.add(solution.variables().size());
			return 	size;
		}
		return null;
}
}
