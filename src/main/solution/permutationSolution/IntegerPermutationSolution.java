package main.solution.permutationSolution;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import main.solution.AbstractSolution;
import main.solution.solution;
import main.solution.binarySolution.BinarySolution;
import main.solution.integerSolution.IntegerSolution;
import main.util.binaryType.BinaryProblemType;
import main.util.createBinary.createBinary;
import main.util.createRandomGeneration.LinkedRandom;
import main.util.permutationType.PermutationType;
import main.util.problemName.ProblemName;


@SuppressWarnings("serial")
public class IntegerPermutationSolution extends AbstractSolution<List<Integer>> implements solution<List<Integer>>{

	
	protected String solutionType = "INTEGERPERMUTATION";
	protected List<Integer> nPermutation;
	protected List<List<Integer>> permutations;
	protected Object object;
	
	public IntegerPermutationSolution(List<Integer> nSubVariables, int numberOfObjectives, int numberOfConstraints, String name, Object object) {
		super(nSubVariables.size(), numberOfObjectives, numberOfConstraints, name);
		
		this.nPermutation = nSubVariables;
		
		this.permutations = new ArrayList<>();
		
		this.object = object;
		
		for(int i=0; i<nPermutation.size(); i++) {
			List<Integer> perm = new ArrayList<>();
			int size = nPermutation.get(i);
			for(int j=0; j<size; j++) {
				perm.add(j);
			}
			if(perm.size()>1) {
				java.util.Collections.shuffle(perm);
			}
			
			permutations.add(perm);	
		}  
		initializePermutationSolutions();
	}
	
	
	public IntegerPermutationSolution(int nSubVariables, int nObjs, int nConstraints, String name, Object object) {
		super(nSubVariables, nObjs, nConstraints, name);
		
		this.object = object;
		
	}
	
	
	public IntegerPermutationSolution(String name, int nObjs, int nConstraints, List<List<Integer>> variables, Object object) {
		this(variables.size(), nObjs, nConstraints, name, object);
		for (int i = 0; i < variables().size(); i++) {
			variables().set(i, variables.get(i));
		}
	}

	
	public IntegerPermutationSolution(IntegerPermutationSolution solution) {
		super(solution.variables().size(), solution.getFitness().length, solution.getConstraints().length, solution.problemname());

		this.nPermutation = solution.nPermutation;
		
		for (int i = 0; i < variables().size(); i++) {
		   variables().set(i, solution.variables().get(i));
		}
		
		for (int i = 0; i < getFitness().length; i++) {
			   getFitness()[i]=solution.getFitness()[i];
		}

		for (int i = 0; i < getConstraints().length; i++) {
		   getConstraints()[i] =  solution.getConstraints()[i];
		}

		attributes = new HashMap<Object, Object>(solution.attributes);   
		
		problemname = solution.problemname();
		
		this.object = solution.object;
		
	}

	@Override
	public IntegerPermutationSolution copy() {
		return new IntegerPermutationSolution(this);
	}

	public int permutationSize() {
		int total = 0;
	    for (int i = 0; i < variables().size(); i++) {
	      total += variables().get(i).size();
	    }

	    return total;
	}
	
	
/**	public List<Integer> createPermutationSolution(int index, LinkedRandom generator) {
		List<Integer> perm;
		if(index>permutations.size()-1) {
			return (List<Integer>) null;
		}else {
			perm = permutations.get(index);
		}
		int nVariables = perm.size();
		List<Boolean> selected = new ArrayList<>(nVariables);
		for(int i=0; i<nVariables; i++) {
			 selected.add(false);
		} 
		  
        int noOfUnselectedVal = nVariables;
        for(int i=0; i<nVariables; i++){
        	double randomvalue = generator.nextDouble();
            int position = (int)(randomvalue*noOfUnselectedVal);
            int pointer=0;
            for(int j=0; j<nVariables; j++){
                if(!selected.get(j)){
                    if(pointer==position){ 
                       perm.set(i, permutations.get(index).get(j));
                       selected.set(j,true);
                        break;
                    }
                    pointer++;
                } 
            }
            noOfUnselectedVal--;   
        }
        return perm;
	}
	**/
	
	private void initializePermutationSolutions() {
	    for (int i = 0; i < variables().size(); i++) {
	      variables().set(i, permutations.get(i));
	    }
	 }
	
		
	@Override
	 public String getSolutionType() {
		return solutionType;
	 }

	public int getPermutationSize(int index) {
		return variables().get(index).size();
	}
	
	public Object getObject() {
		return this.object;
	}
		
}
