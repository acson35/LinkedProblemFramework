package main.problem.permutationProblem;

import java.util.List;

import main.problem.Problem;
import main.solution.permutationSolution.IntegerPermutationSolution;


public interface PermutationProblem extends Problem<IntegerPermutationSolution>{
	
	List<Integer> getnSubVariables();
	int getPermutationLength(int index);
	int getTotalNumberOfVariables();
}
