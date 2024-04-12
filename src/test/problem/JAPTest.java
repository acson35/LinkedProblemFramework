package test.problem;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import Example.FLP;
import Example.JAP;
import Example.OneMax;
import main.solution.binarySolution.BinarySolution;
import main.solution.integerSolution.IntegerSolution;
import main.util.constraintHandling.ConstraintHandling;
import main.util.factory.GetProblemFactory;

public class JAPTest {

	@Test
	public void isSolutionofProblemNotNull() throws IOException {
		JAP jap = new JAP("resources/JAPInstances/a10100");
		
		IntegerSolution sol = jap.createsolution();
		jap.evaluate(sol);
		
 		System.out.println(sol.toString()+ ConstraintHandling.isFeasible(sol));
		assertNotNull(sol.variables());
	}

}
