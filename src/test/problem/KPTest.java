package test.problem;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import Example.KP;
import Example.PFSP_Makespan;
import Example.PFSP_TotalFlowTime;
import Example.TSP;
import main.solution.solution;
import main.solution.binarySolution.BinarySolution;
import main.util.constraintHandling.ConstraintHandling;
import main.util.factory.GetProblemFactory;


public class KPTest {

	@Test
	public void isSolutionofProblemNotNull() throws IOException {
		KP kp = new KP("resources/KPInstances/n00100/R01000/s001.kp");
		BinarySolution sol = kp.createsolution();
		kp.evaluate(sol);
		System.out.println(sol.toString() + ConstraintHandling.isFeasible(sol));
		assertNotNull(sol.variables());
	}

}
