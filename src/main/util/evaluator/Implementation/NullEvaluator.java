package main.util.evaluator.Implementation;

import java.io.IOException;
import java.util.List;

import main.linkedproblem.LinkedProblem;
import main.problem.Problem;
import main.solution.solutionPair;
import main.solution.solutionSet;
import main.util.evaluator.SolutionListEvaluator;

/**
 * @author Antonio J. Nebro
 */
public class NullEvaluator<S> implements SolutionListEvaluator<S> {

	  @Override
	  public List<S> evaluate(List<S> solutionList, Problem<S> problem) throws RuntimeException {
	    return solutionList;
	  }
	  
	  @SuppressWarnings("rawtypes")
	  @Override
	  public List<S> evaluate(List<S> solutionList, LinkedProblem problem) throws RuntimeException {
	    return solutionList;
	  }

	  @Override
	  public void shutdown() {
	    // This method is an intentionally-blank override.
	  }

	@Override
	public List<solutionPair> evaluate(LinkedProblem problem, List<solutionPair> solutionList) {
		// TODO Auto-generated method stub
		return null;
	}

}
