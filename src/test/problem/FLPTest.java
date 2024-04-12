package test.problem;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import Example.FLP;
import main.solution.binarySolution.BinarySolution;
import main.util.constraintHandling.ConstraintHandling;

public class FLPTest {

	@Test
	public void isSolutionofProblemNotNull() throws IOException {
		FLP flp = new FLP("resources/FLPInstances/p13");
		for(int i=0; i<10; i++) {
			BinarySolution sol = flp.createsolution();
			flp.evaluate(sol);
			System.out.println(sol.toString());
		}
		
		
		BinarySolution sol = flp.createsolution();
		flp.evaluate(sol);
		System.out.println(sol.getFitness()[0] + " " + ConstraintHandling.isFeasible(sol));
	//	System.out.println(size);
		
		assertNotNull(sol.variables());
	}

}
