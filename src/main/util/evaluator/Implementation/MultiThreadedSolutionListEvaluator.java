package main.util.evaluator.Implementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import main.linkedproblem.LinkedProblem;
import main.problem.Problem;
import main.solution.solution;
import main.solution.solutionPair;
import main.solution.solutionSet;
import main.util.adjacencymatrix.AdjacencyMatrix;
import main.util.constraintHandling.ConstraintHandling;
import main.util.evaluator.SolutionListEvaluator;
/**
 * @author Antonio J. Nebro
 */
import main.util.factory.GetProblemFactory;
@SuppressWarnings("serial")
public class MultiThreadedSolutionListEvaluator<S> implements SolutionListEvaluator<S> {

	  private final int numberOfThreads;
	  Logger logger;

	  public MultiThreadedSolutionListEvaluator(int numberOfThreads) {
	    if (numberOfThreads == 0) {
	      this.numberOfThreads = Runtime.getRuntime().availableProcessors();
	    } else {
	      this.numberOfThreads = numberOfThreads;
	      System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism",
	          "" + this.numberOfThreads);
	    }
	   // logger.info("Number of cores: " + numberOfThreads);
	  }

	  @Override
	  public List<S> evaluate(List<S> solutionList, Problem<S> problem) {
	    solutionList.parallelStream().forEach(problem::evaluate);

	    return solutionList;
	  }
	  
	
	  public int getNumberOfThreads() {
	    return numberOfThreads;
	  }

	  @Override
	  public void shutdown() {
	    //This method is an intentionally-blank override.
	  }

	@Override
	public List<solutionPair> evaluate(LinkedProblem problem, List<solutionPair> solutionList) {
		Problem<S> parentproblem = (Problem<S>) problem.getLinkedProblems().get(1);
		Problem<S> childproblem = (Problem<S>) problem.getLinkedProblems().get(2);
		solutionList.parallelStream().forEach(f -> {
			parentproblem.evaluate((S) f.getParentSolution());
	        childproblem.evaluate((S) f.getChildSolution());  
	        });
	    return solutionList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<S> evaluate(List<S> solutionList, LinkedProblem lProb) {
		LinkedProblem problem = lProb;
		AdjacencyMatrix matrix = problem.getAdjacencyMatrix();
		
		/**  
		solutionList.parallelStream().forEach(f -> {
			  f.getSolutionSet().forEach((a,b) ->{
				  Problem<S> prob = (Problem<S>) problem.getLinkedProblems().get(a);
				  prob.evaluate((S) b);
			  });
	    	}); **/
		
		  solutionList.parallelStream().forEach(f -> {
			  for(int j = 0; j< problem.getLinkedProblems().size(); j++) {
			  	
				Problem<solution<?>> prob = (Problem<solution<?>>) problem.getLinkedProblems().get(j+1);
				prob.evaluate((solution<?>)((solutionSet)f).getSolutionSet().get(j+1));
				for(int k = 0; k < problem.getLinkedProblems().size(); k++) {
						if(j==k)continue;
						Problem<solution<?>> prob2 =  (Problem<solution<?>>) problem.getLinkedProblems().get(k+1);
						if(matrix.getEdge(j,k)==1) {
							prob2.setParentsolution((solution<?>) ((solutionSet) f).getSolutionSet().get(j+1));
							problem.setProblem(k+1, prob2);
						 }else if(matrix.getEdge(j,k)==2) {
							prob2.setParentsolution((solution<?>) ((solutionSet) f).getSolutionSet().get(j+1));
							problem.setProblem(k+1, prob2);
							prob2.evaluate((solution<?>) ((solutionSet) f).getSolutionSet().get(k+1));
						 }
					 }
			   	}
		});	  
	    return solutionList;
	}

}
