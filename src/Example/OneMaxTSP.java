package Example;

import java.io.IOException;
import java.util.List;

import main.linkedproblem.AbstractGenericLinkedProblem;
import main.linkedproblem.holisticLinkedProblem.holisticImplementation.AbstractHolisticLinkedProblem;
import main.problem.Problem;
import main.solution.solution;
import main.solution.holisticSolution.OverallSolution;
import main.util.adjacencymatrix.AdjacencyMatrixLinkedProblem;
import main.util.adjacencymatrix.FeaturesAffected;

@SuppressWarnings("serial")
public class OneMaxTSP extends AbstractGenericLinkedProblem {

	TSP tsp;
	OneMax onemax;
	
	AdjacencyMatrixLinkedProblem adjmatrix;
	public OneMaxTSP() throws IOException {
		//this(2);
		this.tsp = new TSP("resources/tspInstances/kroA100.tsp");
		onemax = new OneMax();


		addProblem(1, onemax);
		addProblem(2, tsp);
		
		adjmatrix = new AdjacencyMatrixLinkedProblem(getLinkedProblems().size());
		adjmatrix.addEdge(0, 1, true, FeaturesAffected.SOLUTION);
		addAdjacencyMatrix(adjmatrix);
	}

	
	public OverallSolution linkedEvaluate(OverallSolution overallSolution) {
		
		for(int i =0; i<getLinkedProblems().size(); i++) {
		//	holisticSolution
			
		}
		
		return overallSolution;
	}


	@Override
	public Object createsolution() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
