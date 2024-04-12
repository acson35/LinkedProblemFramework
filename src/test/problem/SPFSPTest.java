package test.problem;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import Example.SPFSP;
import main.solution.permutationSolution.IntegerPermutationSolution;

public class SPFSPTest {

	@Test
	public void test() throws IOException {
		SPFSP sfsp = new SPFSP("C:/Users/akinola/OneDrive - Robert Gordon University/Documents/Datapipe/InputData/SPFSP/SPFSP_10_10/SPFSP_10_10_200_1.txt");
		IntegerPermutationSolution sol = sfsp.createsolution();
		sfsp.evaluate(sol);
		int size = sfsp.getTotalNumberOfVariables();
		System.out.println(sol.toString());
		System.out.println(size);
		assertNotNull(sol.variables());
	}

}
