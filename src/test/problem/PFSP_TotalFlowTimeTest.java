package test.problem;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import Example.PFSP_TotalFlowTime;
import main.solution.permutationSolution.IntegerPermutationSolution;

public class PFSP_TotalFlowTimeTest {

	@Test
	public void isSolutionofProblemNotNull() throws IOException {
		PFSP_TotalFlowTime fsp = new PFSP_TotalFlowTime("resources/FSPInstances/tai100_10_0.fsp");
		IntegerPermutationSolution sol = fsp.createsolution();
		
		System.out.println(sol.toString());
		assertNotNull(sol.variables());
	}

}
