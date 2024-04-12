package main.algorithm.SequentialAlgorithm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.algorithm.Algorithm;
import main.algorithm.AlgorithmLP;
import main.algorithm.AlgorithmLPs;
import main.algorithm.GenericAlgorithm;
import main.linkedproblem.LinkedProblem;
import main.problem.GenericProblem;
import main.problem.Problem;
import main.solution.solution;
import main.solution.solutionSet;
import main.solution.binarySolution.BinarySolution;
import main.solution.integerSolution.IntegerSolution;
import main.util.LPDriver.LinkedProblemRunner;
import main.util.adjacencymatrix.AdjacencyMatrix;
import main.util.adjacencymatrix.SequenceAlgorithmicProcess;
import main.util.factory.GetProblemFactory;

/**
 * Abstract implementation of sequence algorithm.
 *
 *@author Akinola Ogunsemi <a.ogunsemi1@rgu.ac.uk>
 *
 *@param <S>
 *
 * 
 */

@SuppressWarnings("hiding")
public abstract class AbstractSequencialAlgorithms<S extends solution<?>, R> implements AlgorithmLPs<S, R> {

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
	
	@SuppressWarnings("unchecked")
	@Override public void run() {
		
		@SuppressWarnings("rawtypes")
		SequenceAlgorithmicProcess seq = new SequenceAlgorithmicProcess();
		for(int i = 0; i< runner.getProblemList().getLinkedProblems().size(); i++) {
			GenericProblem<S> prob = (Problem<S>) runner.getProblemList().getLinkedProblems().get(i+1);
   			GenericAlgorithm<S, S> algo = (Algorithm<S, S>) runner.getAlgorithmList().get(i);
			seq.addNode(algo, prob);	
		}
		seq.sequenceRun();
		overallSolution = (R) seq.getSolutionSet();
	}
/**
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	@Override public void run1() {
		
		AdjacencyMatrix matrix = runner.getProblemList().getAdjacencyMatrix(); 
		Map<Integer, solution<?>> sol = new HashMap<>();
		
		for(int i = 0; i< runner.getProblemList().getLinkedProblems().size()-1; i++) {
			
   			Problem<solution<?>> parentproblem = (Problem<solution<?>>) runner.getProblemList().getLinkedProblems().get(i+1);
   			Algorithm<solution<?>> algorithm1 = (Algorithm<solution<?>>) runner.getAlgorithmList().get(i);
   			
   			Problem<solution<?>> childproblem =  (Problem<solution<?>>) runner.getProblemList().getLinkedProblems().get(i+2);
			Algorithm<solution<?>> algorithm2 = (Algorithm<solution<?>>) runner.getAlgorithmList().get(i+1);
			if(matrix.getEdge(i,i+1)==1) {
				algorithm1.run();
				GetProblemFactory factory =  new GetProblemFactory(childproblem, (solution<?>) algorithm1.getResult());
				childproblem = (Problem<solution<?>>) factory.getProblemInstance();
				algorithm2.setProblem(childproblem);
				algorithm2.run();
				
				sol.put(i+1, (solution<?>) algorithm1.getResult());
			//	overallSolution = (R) new solutionSet(algorithm1.getResult(), algorithm2.getResult());
			 }
			
		 }
		Problem<solution<?>> parentproblem1 = (Problem<solution<?>>) runner.getProblemList().getLinkedProblems().get(1);
		Algorithm<solution<?>> algorithm1 = (Algorithm<solution<?>>) runner.getAlgorithmList().get(0);
		
		
		Problem<solution<?>> parentproblem2 = (Problem<solution<?>>) runner.getProblemList().getLinkedProblems().get(2);
		Algorithm<solution<?>> algorithm2 = (Algorithm<solution<?>>) runner.getAlgorithmList().get(1);
		
		algorithm1.run();
		GetProblemFactory factory =  new GetProblemFactory(parentproblem2, (BinarySolution)algorithm1.getResult());
		parentproblem2 = (Problem<solution<?>>) factory.getProblemInstance();
		algorithm2.setProblem(parentproblem2);
		algorithm2.run();
		

		Problem<solution<?>> parentproblem3 = (Problem<solution<?>>) runner.getProblemList().getLinkedProblems().get(3);
		Algorithm<solution<?>> algorithm3 = (Algorithm<solution<?>>) runner.getAlgorithmList().get(2);
		
		factory =  new GetProblemFactory(parentproblem3, (IntegerSolution)algorithm2.getResult());
		parentproblem3 = (Problem<solution<?>>) factory.getProblemInstance();
		algorithm3.setProblem(parentproblem3);
		algorithm3.run();
				
			
		parentproblem2.setParentsolution(algorithm3.getResult());
		parentproblem2.evaluate(algorithm2.getResult());
		
		parentproblem1.setParentsolution(algorithm2.getResult());
		parentproblem1.evaluate(algorithm1.getResult());
		
		sol.put(1, algorithm1.getResult());
		sol.put(2, algorithm2.getResult());
		sol.put(3, algorithm3.getResult());
		overallSolution = (R) new solutionSet(sol);
	   }

		**/
	
}
