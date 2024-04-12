package main.linkedproblem;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import main.problem.GenericProblem;
import main.problem.Problem;
import main.util.adjacencymatrix.AdjacencyMatrix;
import main.util.adjacencymatrix.LinkedSequence;

public interface LinkedProblem<S> extends GenericProblem<S>{

	HashMap<Integer, Problem<?>> getLinkedProblems();
	AdjacencyMatrix getAdjacencyMatrix();
	void setProblem(Integer index, Problem<?> childproblem);
	int numberOfObjectives();
	LinkedSequence getObjLinkedSeq();
	LinkedSequence getSolutionLinkedSeq();
	@Override
	S createsolution();
;	
}
