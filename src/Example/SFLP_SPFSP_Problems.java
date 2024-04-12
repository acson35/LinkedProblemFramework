package Example;

import java.io.IOException;

import main.linkedproblem.AbstractGenericLinkedProblem;
import main.problem.Problem;
import main.solution.solution;
import main.util.adjacencymatrix.AdjacencyMatrixLinkedProblem;
import main.util.adjacencymatrix.FeaturesAffected;
import main.util.adjacencymatrix.LinkedSequence;

@SuppressWarnings({ "serial", "rawtypes" })
public class SFLP_SPFSP_Problems extends  AbstractGenericLinkedProblem{

	
	SFLP sflp;
	SPFSP sfsp;
	AdjacencyMatrixLinkedProblem adjmatrix;
	LinkedSequence objLinkedSeq;
	LinkedSequence solutionLinkedSeq;
	
	@SuppressWarnings("unchecked")
	public SFLP_SPFSP_Problems(String sflp_filename, String sfsp_filename) throws IOException {
		
		sflp = new SFLP(sflp_filename);
		addProblem(1, sflp);
		
		sfsp = new SPFSP(sfsp_filename);
		addProblem(2, sfsp);
		
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
		
		this.setNomOfObjectives(4);
		
	}
	
	
	
	@Override
	public Object createsolution() {
		// TODO Auto-generated method stub
		return null;
	}

}
