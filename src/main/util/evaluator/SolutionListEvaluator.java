package main.util.evaluator;

import java.io.Serializable;
import java.util.List;

import main.linkedproblem.LinkedProblem;
import main.problem.Problem;
import main.solution.solutionPair;
import main.solution.solutionSet;


/**
 * Created by Antonio J. Nebro on 30/05/14.
 */

public interface SolutionListEvaluator<S> extends Serializable {
	List<S> evaluate(List<S> solutionList, Problem<S> problem) ;
	//List<S> evaluate(List<S> solutionList, LinkedProblem problem);
	List<solutionPair> evaluate(LinkedProblem problem, List<solutionPair> solutionList) ;
	List<S> evaluate(List<S> solutionList, LinkedProblem problem) ;
	void shutdown() ;

}
