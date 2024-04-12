package main.problem.binaryProblem;

import java.util.List;
import main.problem.Problem;
import main.solution.binarySolution.BinarySolution;

/**
 * Binary problems interface
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */

public interface BinaryProblem extends Problem<BinarySolution> {
	List<Integer> getnVariableBits();
	int getBitsFromVariable(int index);
	int getTotalNumberOfBits();
	
}
