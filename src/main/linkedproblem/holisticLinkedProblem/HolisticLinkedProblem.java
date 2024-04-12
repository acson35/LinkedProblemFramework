package main.linkedproblem.holisticLinkedProblem;

import java.util.List;

import main.linkedproblem.LinkedProblem;
import main.problem.Problem;
import main.solution.solution;
import main.solution.holisticSolution.OverallSolution;
import main.util.adjacencymatrix.LinkedProblemMatrix;

public interface HolisticLinkedProblem extends LinkedProblem<LinkedProblemMatrix>{
	
	List<solution<?>> Solutions();
		
}
