package Example;

import java.io.IOException;

import main.linkedproblem.AbstractGenericLinkedProblem;
import main.problem.Problem;
import main.solution.solution;
import main.util.adjacencymatrix.AdjacencyMatrixLinkedProblem;
import main.util.adjacencymatrix.FeaturesAffected;
import main.util.adjacencymatrix.LinkedProblemMatrix;
import main.util.adjacencymatrix.LinkedSequence;
import main.util.adjacencymatrix.MatrixType;

/**
 * Example of linked problem implementation. 
 *
 *@author Akinola Ogunsemi <a.ogunsemi1@rgu.ac.uk>
 *
 *@param <S>
 * 
 */

@SuppressWarnings("serial")
public class FLP_PFSPproblems extends  AbstractGenericLinkedProblem {
	
	FLP flp;
	PFSP_Makespan fsp;
	AdjacencyMatrixLinkedProblem adjmatrix;
	LinkedSequence objLinkedSeq;
	LinkedSequence solutionLinkedSeq;
	
	@SuppressWarnings("unchecked")
	public FLP_PFSPproblems(String flp_filename, String fsp_filename) throws IOException {
		
		flp = new FLP(flp_filename);
		addProblem(1, flp);
		
		fsp = new PFSP_Makespan(fsp_filename);
		addProblem(2, fsp);
		
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
