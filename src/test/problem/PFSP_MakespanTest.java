package test.problem;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import Example.PFSP_Makespan;
import main.solution.permutationSolution.IntegerPermutationSolution;

public class PFSP_MakespanTest {

	@Test
	public void isSolutionofProblemNotNull() throws IOException {
		PFSP_Makespan fsp = new PFSP_Makespan("resources/FSPInstances/tai100_5_0.fsp");
		IntegerPermutationSolution sol = fsp.createsolution();
		fsp.evaluate(sol);
		int size = fsp.getTotalNumberOfVariables();
		System.out.println(sol.toString());
		System.out.println(size);
		assertNotNull(sol.variables());
	}

}
