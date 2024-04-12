package Example;

import java.io.IOException;

import main.linkedproblem.AbstractGenericLinkedProblem;
import main.problem.Problem;
import main.solution.solution;
import main.util.adjacencymatrix.AdjacencyMatrixLinkedProblem;
import main.util.adjacencymatrix.FeaturesAffected;
import main.util.adjacencymatrix.LinkedSequence;

@SuppressWarnings("serial")
public class IWSP_VAP_MTSP_Problems extends  AbstractGenericLinkedProblem{
	
	IWSP iwsp;
	VAP vap;
	MTSP mtsp;
	AdjacencyMatrixLinkedProblem adjmatrix;
	LinkedSequence objLinkedSeq;
	LinkedSequence solutionLinkedSeq;
	
	
	@SuppressWarnings("unchecked")
	public IWSP_VAP_MTSP_Problems(String filename) throws IOException {
		
		iwsp = new IWSP(filename);
		addProblem(1, iwsp);
		
		vap = new VAP(filename);
		addProblem(2, vap);
		
		mtsp = new MTSP(filename);
		addProblem(3, mtsp);
		
		adjmatrix = new AdjacencyMatrixLinkedProblem(getLinkedProblems().size());
		adjmatrix.addEdge(0, 1, true, FeaturesAffected.SOLUTION);
		adjmatrix.addEdge(1, 2, true, FeaturesAffected.SOLUTION);
		adjmatrix.addEdge(1, 0, true, FeaturesAffected.OBJECTIVE);
		adjmatrix.addEdge(2, 1, true, FeaturesAffected.OBJECTIVE);
		addAdjacencyMatrix(adjmatrix);
		
		objLinkedSeq = new LinkedSequence();
		objLinkedSeq.addNode((Problem<solution<?>>) getLinkedProblems().get(3));
		objLinkedSeq.addNode((Problem<solution<?>>) getLinkedProblems().get(2));
		objLinkedSeq.addNode((Problem<solution<?>>) getLinkedProblems().get(1));
		setObjLinkedSeqs(objLinkedSeq);
		
		solutionLinkedSeq = new LinkedSequence();
		solutionLinkedSeq.addNode((Problem<solution<?>>) getLinkedProblems().get(1));
		solutionLinkedSeq.addNode((Problem<solution<?>>) getLinkedProblems().get(2));
		solutionLinkedSeq.addNode((Problem<solution<?>>) getLinkedProblems().get(3));
		setSolutionLinkedSeqs(objLinkedSeq);
		
		this.setNomOfObjectives(3);
	}


	@Override
	public Object createsolution() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
