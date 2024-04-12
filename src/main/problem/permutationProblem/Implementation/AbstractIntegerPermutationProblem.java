package main.problem.permutationProblem.Implementation;

import java.util.List;

import main.problem.AbstractGenericProblem;
import main.problem.permutationProblem.PermutationProblem;
import main.solution.solution;
import main.solution.binarySolution.BinarySolution;
import main.solution.permutationSolution.IntegerPermutationSolution;
import main.util.factory.Instatiator.ChangeSolution.ChangeIntegerPermutationSolution;
import main.util.problemName.ProblemName;

/**
 * Abstract class for integer permutation problems.
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 * 
 * 
 * @author Akinola Ogunsemi <a.ogunsemi@rgu.ac.uk>
 * 
 */
//AUG2021 - Akinola - Modified to include a parent solution for a linked optimisation.

@SuppressWarnings("serial")
public abstract class AbstractIntegerPermutationProblem extends AbstractGenericProblem<IntegerPermutationSolution> implements PermutationProblem {
	
	public static final String type = "INTEGERPERMUTATION";
	
	public abstract List<Integer> getnSubVariables();
	
	/**
	   * This method creates and returns a integer permutation solution modified by a parent solution if the solution problem is a dependent problem.
	   * @return
	   * 
	*/
	
	@Override
	public IntegerPermutationSolution createsolution() {
		if(null == parentsolution()) {
			return new IntegerPermutationSolution(getnSubVariables(), getnObjectives(), getnConstraints(), getName(), getData());
		}else {
			ChangeIntegerPermutationSolution updatedSolution = new ChangeIntegerPermutationSolution(parentsolution());
			return new IntegerPermutationSolution(getName(), getnObjectives(), getnConstraints(), updatedSolution.variables(), getData());
		}	
	}
	
//	@Override
//	public IntegerPermutationSolution updatesolution(solution<?> solution) {
		
//	}
	
	@Override
	public String getProblemType() {
		return type;
	}
	
	
	@Override
	public int getTotalNumberOfVariables() {
		int count=0;
		for (int i = 0; i < this.getnVariables(); i++) {
		  count += this.getnSubVariables().get(i);
		}
		return count;
	}
	
	@Override
	public int getPermutationLength(int index) {
		return getnSubVariables().get(index);
	}
	
	
	
	
}
