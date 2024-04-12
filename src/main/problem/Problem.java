package main.problem;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import main.solution.solution;

/**
 * Interface representing a problem in linked optimisation problem. Modified to include a parent solution for a linked optimisation. Problem is instantiated by a parent solution.
 *
 * 
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 *
 * @param <S> Generic type representing encoding of the solutions.
 * 
 * 
 * @author Akinola Ogunsemi <a.ogunsemi@rgu.ac.uk>
 * 
 */
//AUG2021 - Akinola - Modified to include a parent solution for a linked optimisation.

public interface Problem<S> extends GenericProblem<S> {

	
	String getName();
	String getProblemType();
	String getFilepath();
	Object getData();
	
	/**
	   * This method receives a solution, evaluates it, and returns the evaluated solution.
	   * @param s
	   * @return
	 */
	
	void evaluate(S s);
	void evaluateConstraint(S solution);
	@Override
	S createsolution();
	S createsolution(Map<Integer, List<Integer>> gurobiSol);
	
	/**
	   * This method returns parent solution that modifies an instance of a problem.
	   * @return
	   * 
	*/
	solution<?> parentsolution();
	void setParentsolution(solution<?> parentsolution);
	Map<Object,Object> attributes() ;
	  
}
