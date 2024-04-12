package test.problem;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import Example.SFLP;
import main.solution.binarySolution.BinarySolution;
import main.util.constraintHandling.ConstraintHandling;

public class SFLPTest {

	@Test
	public void test() throws IOException {
		
		SFLP sflp = new SFLP("C:/Users/akinola/OneDrive - Robert Gordon University/Documents/Datapipe/InputData/SFLP/SFLP_10/SFLP_10_200_2.txt");
		for(int i=0; i<10; i++) {
			BinarySolution sol = sflp.createsolution();
			sflp.evaluate(sol);
			//System.out.println(sol.toString());
		}
		
		
		BinarySolution sol = sflp.createsolution();
		sflp.evaluate(sol);
		System.out.println(sol.getFitness()[0] + " " + sol.getFitness()[1] + " " + ConstraintHandling.isFeasible(sol));
	//	System.out.println(size);
		
		assertNotNull(sol.variables());
	}

}
