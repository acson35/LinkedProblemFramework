package main.algorithm.SequentialAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import main.solution.*;
import main.util.LPDriver.LinkedProblemRunner;
import main.util.adjacencymatrix.AdjacencyMatrix;
import main.util.comparator.DominanceComparator;
import main.util.factory.GetProblemFactory;
import main.algorithm.Algorithm;
import main.algorithm.AlgorithmLP;
import main.problem.Problem;

public abstract class AbstractSequentialAlgorithm<S extends solution<?>, R> implements AlgorithmLP<S, R>  {
	
	@SuppressWarnings("rawtypes")
	protected R overallSolution;
	
	private LinkedProblemRunner runner;
		
	public LinkedProblemRunner getLinkedProblemRunner() {
		 return runner;
	}
	
	public void setLinkedProblemRunner(LinkedProblemRunner runner) {
	  this.runner = runner;
	}
	
	@SuppressWarnings("rawtypes")
	@Override public abstract R getResult();

	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	@Override public void run() {
		
		AdjacencyMatrix matrix = runner.getProblemList().getAdjacencyMatrix();    	
		for(int i = 0; i< runner.getProblemList().getLinkedProblems().size(); i++) {
			
   			Problem<S> parentproblem = (Problem<S>) runner.getProblemList().getLinkedProblems().get(i+1);
   			Algorithm<S, S> algorithm1 = (Algorithm<S, S>) runner.getAlgorithmList().get(i);
   			
   			
			for(int j = 0; j< runner.getProblemList().getLinkedProblems().size(); j++) {
				Problem<S> childproblem =  (Problem<S>) runner.getProblemList().getLinkedProblems().get(j+1);
				Algorithm<S, S> algorithm2 = (Algorithm<S, S>) runner.getAlgorithmList().get(j);
				if(matrix.getEdge(i,j)==1) {
					algorithm1.run();
					GetProblemFactory factory =  new GetProblemFactory(childproblem, algorithm1.getResult());
					childproblem = (Problem<S>) factory.getProblemInstance();
					algorithm2.setProblem(childproblem);
					algorithm2.run();
					overallSolution = (R) new solutionPair(algorithm1.getResult(), algorithm2.getResult());
				 }
			}
		 }
	   }
	
}