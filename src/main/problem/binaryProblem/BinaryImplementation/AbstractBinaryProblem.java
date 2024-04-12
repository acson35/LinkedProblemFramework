package main.problem.binaryProblem.BinaryImplementation;

import java.util.List;
import java.util.Map;

import main.problem.AbstractGenericProblem;
import main.problem.binaryProblem.BinaryProblem;
import main.solution.solution;
import main.solution.binarySolution.BinarySolution;
import main.solution.integerSolution.IntegerSolution;
import main.util.binaryType.BinaryProblemType;
import main.util.factory.Instatiator.ChangeSolution.ChangeBinarySolution;
import main.util.factory.Instatiator.ChangeSolution.ChangeIntegerSolution;

/**
 * Abstract class for binary problems.
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 * 
 * 
 * @author Akinola Ogunsemi <a.ogunsemi@rgu.ac.uk>
 * 
 */
//AUG2021 - Akinola - Modified to include a parent solution for a linked optimisation.

@SuppressWarnings("serial")
public abstract class AbstractBinaryProblem extends AbstractGenericProblem<BinarySolution> implements BinaryProblem {
	
	
	public abstract List<Integer> getnVariableBits();
	
	public static final String type = "BINARY";
	
	@Override
	public int getBitsFromVariable(int index) {
		return getnVariableBits().get(index);
	}
	
	@Override
	public int getTotalNumberOfBits() {
		int count=0;
		for (int i = 0; i < this.getnVariables(); i++) {
		  count += this.getnVariableBits().get(i);
		}
		return count;
	}
	
	/**
	   * This method creates and returns a binary solution modified by a parent solution if the solution problem is a dependent problem.
	   * @return
	   * 
	*/
	
	@Override
	public BinarySolution createsolution() {
		if(null == parentsolution()) {
			return new BinarySolution(getnVariableBits(), getnObjectives(), getnConstraints(), getName(), getData());
		}else{
			ChangeBinarySolution updateSolution = new ChangeBinarySolution(parentsolution());
			return new BinarySolution(getnObjectives(), getnConstraints(), getName(), updateSolution.variables(), getData());
		} 
	}
	
	public BinarySolution createsolution(Map<Integer, List<Integer>> gurobiSol) {
		return new BinarySolution(getnVariableBits(), getnObjectives(), getnConstraints(), getName(),getData(), gurobiSol);
	}
	
	@Override
	public String getProblemType() {
		return type;
	}
	
//	@Override
//	public BinarySolution updatesolution(solution<?> solution) {
//		return new BinarySolution(solution, getnConstraints(), getName());
//	}
	
}
