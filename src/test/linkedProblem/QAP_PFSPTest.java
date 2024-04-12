package test.linkedProblem;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import Example.PFSP_Makespan;
import Example.PFSP_TotalFlowTime;
import Example.QAP;
import main.solution.solution;
import main.solution.permutationSolution.IntegerPermutationSolution;
import main.util.factory.GetProblemFactory;

public class QAP_PFSPTest {

	@Test
	public void isSolutionofProblemNotNull() throws IOException {
		QAP qap = new QAP("resources/QAPInstances/tai50a.dat");
		IntegerPermutationSolution sol = qap.createsolution();
		qap.evaluate(sol);
		//PFSP_Makespan fsp = new PFSP_Makespan("resources/FSPInstances/tai50_10_0.fsp");
		PFSP_TotalFlowTime fsp = new PFSP_TotalFlowTime("resources/FSPInstances/tai50_10_0.fsp");
		GetProblemFactory fac =  new GetProblemFactory(fsp, sol);
		PFSP_TotalFlowTime prob2 = (PFSP_TotalFlowTime)fac.getProblemInstance();
		//PFSP_Makespan prob2 = (PFSP_Makespan)fac.getProblemInstance();
		solution<?> sol2 = (solution<?>) prob2.createsolution();
		prob2.evaluate((IntegerPermutationSolution) sol2);
		System.out.println(sol.toString());
		System.out.println(sol2.toString());
		assertNotNull(sol2.variables());
	}

}
