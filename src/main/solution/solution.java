package main.solution;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import main.util.problemName.ProblemName;
/**
 * Interface representing a Solution. Modified to include additional attributes for a linked structure.
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 * 
 *  
 * @author Akinola Ogunsemi <a.ogunsemi@rgu.ac.uk>
 * 
 * @param <T> Type
 */
//AUG2021 - Akinola - Modified to include a parent solution for a linked optimisation.

public interface solution<T> extends Serializable {

	List<T> variables();
	String getSolutionType();
	String problemname();
	
	//double getFitness();
	double[] getFitness();
	double[] getConstraints();
	Map<Object,Object> attributes() ;
 
	solution<T> copy() ;
}
