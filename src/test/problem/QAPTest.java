package test.problem;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import Example.PFSP_Makespan;
import Example.QAP;
import main.solution.permutationSolution.IntegerPermutationSolution;

public class QAPTest {

	@Test
	public void isSolutionofProblemNotNull() throws IOException {
		QAP qap = new QAP("resources/QAPInstances/chr20a.dat");
		IntegerPermutationSolution sol = qap.createsolution();
		qap.evaluate(sol);
		System.out.println(sol.problemname());
		System.out.println(sol.toString());
		assertNotNull(sol.variables());
	}

}
