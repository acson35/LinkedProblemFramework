package main.problem.integerProblem;

import java.util.List;

import main.problem.Problem;
import main.solution.integerSolution.IntegerSolution;
import main.util.range.Ranges;


public interface IntegerProblem extends Problem<IntegerSolution>{
	 Integer getLowerRange(int index);
	 Integer getUpperRange(int index);
	 List<Ranges<Integer>> getRanges() ;
	 
}
