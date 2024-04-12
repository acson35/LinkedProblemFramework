package Example;

import java.io.IOException;

import main.linkedproblem.AbstractGenericLinkedProblem;
import main.problem.Problem;
import main.solution.solution;
import main.util.adjacencymatrix.AdjacencyMatrixLinkedProblem;
import main.util.adjacencymatrix.FeaturesAffected;
import main.util.adjacencymatrix.LinkedSequence;

public class JAP_TSP_Problems extends  AbstractGenericLinkedProblem {
	
	JAP jap;
	TSP tsp;
	AdjacencyMatrixLinkedProblem adjmatrix;
	LinkedSequence objLinkedSeq;
	LinkedSequence solutionLinkedSeq;
	
	public JAP_TSP_Problems(String jap_filename, String tsp_filename) throws IOException {
		
		jap = new JAP(jap_filename);
		addProblem(1, jap);
		
		tsp = new TSP(tsp_filename);
		addProblem(2, tsp);
		
		
		adjmatrix = new AdjacencyMatrixLinkedProblem(getLinkedProblems().size());
		adjmatrix.addEdge(0, 1, true, FeaturesAffected.SOLUTION);
		addAdjacencyMatrix(adjmatrix);
		
		objLinkedSeq = new LinkedSequence();
		objLinkedSeq.addNode((Problem<solution<?>>) getLinkedProblems().get(1));
		objLinkedSeq.addNode((Problem<solution<?>>) getLinkedProblems().get(2));
		setObjLinkedSeqs(objLinkedSeq);
		
		solutionLinkedSeq = new LinkedSequence();
		solutionLinkedSeq.addNode((Problem<solution<?>>) getLinkedProblems().get(1));
		solutionLinkedSeq.addNode((Problem<solution<?>>) getLinkedProblems().get(2));
		setSolutionLinkedSeqs(objLinkedSeq);
		
		this.setNomOfObjectives(2);
	}

	@Override
	public Object createsolution() {
		// TODO Auto-generated method stub
		return null;
	}	
}
