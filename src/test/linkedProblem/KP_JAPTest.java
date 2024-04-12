package test.linkedProblem;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import Example.JAP;
import Example.KP;
import Example.PFSP_Makespan;
import main.solution.solution;
import main.solution.binarySolution.BinarySolution;
import main.solution.integerSolution.IntegerSolution;
import main.util.constraintHandling.ConstraintHandling;
import main.util.factory.GetProblemFactory;

public class KP_JAPTest {

	@Test
	public void isSolutionofProblemNotNull() throws IOException {
		KP kp = new KP("resources/KPInstances/n00100/R01000/s000.kp");
		BinarySolution sol = kp.createsolution();
		kp.evaluate(sol);
		JAP jap = new JAP("resources/JAPInstances/a10100");
		GetProblemFactory fac =  new GetProblemFactory(jap, sol);
		JAP prob2 = (JAP)fac.getProblemInstance();
		solution<?> sol2 = (solution<?>) prob2.createsolution();
		prob2.evaluate((IntegerSolution) sol2);
		System.out.println(sol.toString() + ConstraintHandling.isFeasible(sol));
		System.out.println(sol2.toString() + ConstraintHandling.isFeasible(sol2));
		assertNotNull(sol.variables());
	}
}
