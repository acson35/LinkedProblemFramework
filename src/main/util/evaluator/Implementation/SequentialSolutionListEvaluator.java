package main.util.evaluator.Implementation;

import java.util.List;

import main.linkedproblem.LinkedProblem;
import main.problem.Problem;
import main.solution.solution;
import main.solution.solutionPair;
import main.solution.solutionSet;
import main.util.adjacencymatrix.AdjacencyMatrix;
import main.util.adjacencymatrix.LinkedSequence;
import main.util.evaluator.SolutionListEvaluator;
/**
 * @author Antonio J. Nebro
 */
@SuppressWarnings("serial")
public class SequentialSolutionListEvaluator<S> implements SolutionListEvaluator<S> {

	  @Override
	  public List<S> evaluate(List<S> solutionList, Problem<S> problem) throws RuntimeException {
	    solutionList.forEach(problem::evaluate);

	    return solutionList;
	  }

	  @Override
	  public void shutdown() {
	    // This method is an intentionally-blank override.
	  }

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<solutionPair> evaluate(LinkedProblem problem, List<solutionPair> solutionList) {
		Problem<S> parentproblem = (Problem<S>) problem.getLinkedProblems().get(1);
		Problem<S> childproblem = (Problem<S>) problem.getLinkedProblems().get(2);
		solutionList.forEach(f -> {
			parentproblem.evaluate((S) f.getParentSolution());
	        childproblem.evaluate((S) f.getChildSolution());  
	        });
		
	    return solutionList;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	  @Override
	  public List<S> evaluate(List<S> solutionList, LinkedProblem lProb) {
		 
		/**  solutionList.forEach(f -> {
			  f.getSolutionSet().forEach((a,b) ->{
				  Problem<S> prob = (Problem<S>) problem.getLinkedProblems().get(a);
				  prob.evaluate((S) b);
			  });
	        });
		
		**/
		  LinkedProblem problem = lProb;
		  LinkedSequence linkedsSequence = problem.getObjLinkedSeq();
		  solutionList.forEach(f -> {
			  
			  linkedsSequence.setSolutions((solutionSet) f);
			  linkedsSequence.traverseObjectiveNodes();
			  
	        });
		  
		 
		  
		  
		//  LinkedSequence linkedsSequence = new LinkedSequence();
		  
		  
		 // solutionList.parallelStream().forEach(f -> {
		/**	for(int i = 0; i < solutionList.size(); i++) {  
			
				Problem<solution<?>> prob1 = (Problem<solution<?>>) problem.getLinkedProblems().get(1);
				
				Problem<solution<?>> prob2 = (Problem<solution<?>>) problem.getLinkedProblems().get(2);
				
			
				Problem<solution<?>> prob3 = (Problem<solution<?>>) problem.getLinkedProblems().get(3);
				
				prob3.setParentsolution((solution<?>) ((solutionSet)solutionList.get(i)).getSolution(2));
				prob3.evaluate((solution<?>) ((solutionSet)solutionList.get(i)).getSolution(3));
				
				prob2.setParentsolution((solution<?>) ((solutionSet)solutionList.get(i)).getSolution(3));
				prob2.evaluate((solution<?>) ((solutionSet)solutionList.get(i)).getSolution(2));
				
				prob1.setParentsolution((solution<?>) ((solutionSet)solutionList.get(i)).getSolution(2));
				prob1.evaluate((solution<?>) ((solutionSet)solutionList.get(i)).getSolution(1));
				
		}	 **/ 
	    return solutionList;
	  }

}
