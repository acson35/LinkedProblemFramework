package Example;

import java.io.IOException;

import main.linkedproblem.AbstractGenericLinkedProblem;
import main.problem.Problem;
import main.solution.solution;
import main.util.adjacencymatrix.AdjacencyMatrixLinkedProblem;
import main.util.adjacencymatrix.FeaturesAffected;
import main.util.adjacencymatrix.LinkedSequence;

@SuppressWarnings("serial")
public class KP_PFSP_Problem extends  AbstractGenericLinkedProblem {
	
	KP Kp;
	PFSP_Makespan fsp;
	AdjacencyMatrixLinkedProblem adjmatrix;
	LinkedSequence objLinkedSeq;
	LinkedSequence solutionLinkedSeq;
	
	public KP_PFSP_Problem(String kp_filename, String pfsp_filename) throws IOException {
		
		Kp = new KP(kp_filename);
		addProblem(1, Kp);
		
		fsp = new PFSP_Makespan(pfsp_filename);
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
