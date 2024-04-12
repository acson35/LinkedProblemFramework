package Example;

import java.io.IOException;

import main.linkedproblem.AbstractGenericLinkedProblem;
import main.problem.Problem;
import main.solution.solution;
import main.util.adjacencymatrix.AdjacencyMatrixLinkedProblem;
import main.util.adjacencymatrix.FeaturesAffected;
import main.util.adjacencymatrix.LinkedSequence;

/**
 * Example of linked problem implementation. 
 *
 *@author Akinola Ogunsemi <a.ogunsemi1@rgu.ac.uk>
 *
 *@param <S>
 * 
 */

public class FLP_JAP_Problem extends  AbstractGenericLinkedProblem{
	
	FLP flp;
	JAP jap;
	AdjacencyMatrixLinkedProblem adjmatrix;
	LinkedSequence objLinkedSeq;
	LinkedSequence solutionLinkedSeq;
	
	@SuppressWarnings("unchecked")
	public FLP_JAP_Problem(String flp_filename, String jap_filename) throws IOException {
		
		flp = new FLP(flp_filename);
		addProblem(1, flp);
		
		jap = new JAP(jap_filename);
		addProblem(2, jap);
		
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
