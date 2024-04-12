package test.problem;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import Example.OneMax;
import main.problem.AbstractGenericProblem;
import main.problem.Problem;
import main.problem.binaryProblem.BinaryImplementation.AbstractBinaryProblem;
import main.solution.binarySolution.BinarySolution;

public class BinaryProblemTest {

	@Test
	public void isgetTotalNumberOfBitsEqualInputParameter() {
		OneMax pro = new OneMax();
		
		assertEquals(100, pro.getTotalNumberOfBits());
	}
	
	@Test
	public void isSolutionofProblemNotNull() {
		OneMax pro = new OneMax();
		//BinarySolution sol = pro.evaluate(pro.createsolution());
		//System.out.println(sol.toString());
		assertNotNull(pro.createsolution());
	}
	
	@Test
	public void isObjectiveofProblemNotNull() {
		OneMax pro = new OneMax();
		BinarySolution sol = pro.createsolution();
		pro.evaluate(sol);
		assertNotNull(sol.getFitness());
	}
	
	@Test
	public void isNumberofBitsofDecisionVariableEqualsnBitsperVariable() {
		OneMax pro = new OneMax(Arrays.asList(4,6,10));
		BinarySolution sol = pro.createsolution();
		
		System.out.println(sol.toString());
		for(int i=0; i<sol.variables().size(); i++) {
			assertEquals(sol.variables().get(i).getNumberOfBits(), pro.getBitsFromVariable(i));	
		}
	}

}
