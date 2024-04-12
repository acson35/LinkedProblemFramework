package test.linkedProblem;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import Example.KP;
import Example.PFSP_Makespan;
import Example.PFSP_TotalFlowTime;
import main.solution.solution;
import main.solution.binarySolution.BinarySolution;
import main.solution.permutationSolution.IntegerPermutationSolution;
import main.util.constraintHandling.ConstraintHandling;
import main.util.factory.GetProblemFactory;

public class KP_PFSPTest {

	@Test
	public void isSolutionofProblemNotNull() throws IOException {
		KP kp = new KP("resources/KPInstances/n00100/R01000/s000.kp");
		BinarySolution sol = kp.createsolution();
		kp.evaluate(sol);
		//PFSP_Makespan fsp = new PFSP_Makespan("resources/FSPInstances/tai100_10_0.fsp");
		PFSP_TotalFlowTime fsp = new PFSP_TotalFlowTime("resources/FSPInstances/tai100_10_0.fsp");
		GetProblemFactory fac =  new GetProblemFactory(fsp, sol);
		//PFSP_Makespan prob2 = (PFSP_Makespan)fac.getProblemInstance();
		PFSP_TotalFlowTime prob2 = (PFSP_TotalFlowTime)fac.getProblemInstance();
		
		solution<?> sol2 = (solution<?>) prob2.createsolution();
		prob2.evaluate((IntegerPermutationSolution) sol2);
		System.out.println(sol.toString() + ConstraintHandling.isFeasible(sol));
		System.out.println(sol2.toString() + ConstraintHandling.isFeasible(sol2));
		assertNotNull(sol.variables());
	}

}
