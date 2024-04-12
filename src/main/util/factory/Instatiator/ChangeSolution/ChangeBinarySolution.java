package main.util.factory.Instatiator.ChangeSolution;

import java.util.ArrayList;
import java.util.List;
import main.solution.solution;
import main.solution.binarySolution.BinarySolution;
import main.solution.integerSolution.IntegerSolution;
import main.solution.permutationSolution.IntegerPermutationSolution;
import main.util.adjacencymatrix.AdjacencyMatrixLinkedProblem;
import main.util.createBinary.createBinary;
import main.util.factory.Instatiator.AbstractSolutionInstatiator;
import main.util.factory.Instatiator.SolutionInstantiator;
import main.util.createRandomGeneration.LinkedRandom;

public class ChangeBinarySolution extends AbstractSolutionInstatiator<createBinary> implements SolutionInstantiator<createBinary>{
	
	solution<?> solution;
	
	public ChangeBinarySolution(int nVariables) {
		super(nVariables);
	}
	
	public ChangeBinarySolution(solution<?> solution) {
		this(size(solution).size());
		this.solution = solution;
		setsolution();
	}

	@Override
	public void setsolution() {
		List<Integer> bitsNum = new ArrayList<>();
		if(solution.getSolutionType().equalsIgnoreCase("BINARY")) {
			BinarySolution sol = (BinarySolution) solution;
			for (int i = 0; i < sol.variables().size(); i++) {
				createBinary binaryset = (createBinary) sol.variables().get(i);
				bitsNum.set(i, binaryset.cardinality());
				variables().set(i, createBinarySet(bitsNum.get(i), LinkedRandom.getInstance()));
			}
			 
		}else if(solution.getSolutionType().equalsIgnoreCase("INTEGERPERMUTATION")) {
			IntegerPermutationSolution sol = (IntegerPermutationSolution) solution;
			bitsNum.set(0, sol.variables().get(0).size());
			variables().set(0, createBinarySet(bitsNum.get(0), LinkedRandom.getInstance()));
		}else if(solution.getSolutionType().equalsIgnoreCase("INTEGER")) {
			IntegerSolution sol = (IntegerSolution) solution;
			for(int i=0; i<sol.variables().size(); i++) {
				createBinary bits = new createBinary(sol.variables().get(i).size());
				if(0==sol.variables().get(i).size()) {
					for(int j=0; j < sol.variables().get(i).size(); j++) {
						bits.clear(j);
					}	
				}else {	
					for(int j=0; j < sol.variables().get(i).size(); j++) {
						if (sol.variables().get(i).get(j) !=0) {
						     bits.set(j);
						 } else {
							 bits.clear(j);
						}
					}
				}
				variables().set(i, bits);
			}
		}else if(solution.getSolutionType().equalsIgnoreCase("SEQUENCE")) {
			
		}
	}
	
	
	private static createBinary createBinarySet(int numberOfBits, LinkedRandom generator) {
		createBinary binaryset = new createBinary(numberOfBits);

	    for (int i = 0; i < numberOfBits; i++) {
	      double randomvalue = generator.nextDouble();
	      if (randomvalue < 0.5) {
	        binaryset.set(i);
	      } else {
	        binaryset.clear(i);
	      }
	    }
	    return binaryset;
	}
	
}
