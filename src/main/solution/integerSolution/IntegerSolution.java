package main.solution.integerSolution;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import main.solution.AbstractSolution;
import main.solution.solution;
import main.solution.binarySolution.BinarySolution;
import main.solution.permutationSolution.IntegerPermutationSolution;
import main.util.createBinary.createBinary;
import main.util.createRandomGeneration.LinkedRandom;
import main.util.problemName.ProblemName;
import main.util.range.Ranges;

@SuppressWarnings("serial")
public class IntegerSolution extends AbstractSolution<List<Integer>> implements solution<List<Integer>>  {
	
	protected String solutionType = "INTEGER";
	protected List<Ranges<Integer>> ranges;
	//protected List<Integer> nSizes;
	protected List<List<Integer>> arrSol;
	protected Object object;
	
	public IntegerSolution(List<Ranges<Integer>> rangesList, int numberOfObjectives, int numberOfConstraints, String name, Object object) {
		super(rangesList.size(), numberOfObjectives, numberOfConstraints, name);

		this.object = object;
		this.arrSol = new ArrayList<>();
		this.ranges = rangesList ;
		initialiseSolution();
	    
	}
	
	public IntegerSolution(int nObjs, int nConstraints, String name, List<Ranges<Integer>> rangesList, List<List<Integer>> variables, Object obj) {
		this(variables.size(), nObjs, nConstraints, name, obj);
		this.ranges = rangesList ;
		for (int i = 0; i < variables.size(); i++) {
			variables().set(i, (List<Integer>) variables.get(i));
		}
	}
	
	public int createSolution(int index, Ranges<Integer> range) {
		int agent = LinkedRandom.getInstance().nextInt(range.getLowerRange(), range.getUpperRange());
		return agent;
	}
	
	
	public void initialiseSolution() {
		List<Integer> var = new ArrayList<Integer>();
		for (int i = 0; i < ranges.size(); i++) {
			  Ranges<Integer> range = ranges.get(i);
			  var.add(createSolution(i, range));
		}
		variables().set(0, var);
	}
	
	
	public IntegerSolution(List<Ranges<Integer>> rangesList, String name, Object object) {
		this(rangesList, 0, 0, name, object);
	}
	
	
	public IntegerSolution(int numberOfVariables, int numberOfObjectives, int numberOfConstraints, String name, Object object) {
		super(numberOfVariables, numberOfObjectives, numberOfConstraints, name);
		this.object = object;
	}

	public IntegerSolution(IntegerSolution solution) {
		super(solution.variables().size(), solution.getFitness().length, solution.getConstraints().length, solution.problemname());

		   for (int i = 0; i < variables().size(); i++) {
		     variables().set(i, solution.variables().get(i));
		   }

		   for (int i = 0; i < getConstraints().length; i++) {
		     getConstraints()[i] =  solution.getConstraints()[i];
		   }
		   
		   ranges = solution.ranges;

		   attributes = new HashMap<>(solution.attributes);
		   
		   problemname = solution.problemname();
		   
		   object = solution.object;
	}


	@Override
	public String getSolutionType() {
		return solutionType;
	}
	
	public Object getObject(){
		return object;
	}

	@Override
	public solution<List<Integer>> copy() {
		return new IntegerSolution(this);
	}

	
	public Integer getLowerRange(int ind) {
		return this.ranges.get(ind).getLowerRange();
	}


	public Integer getUpperRange(int ind) {
		return this.ranges.get(ind).getUpperRange();
	}
	
	public Ranges<Integer> getRange(int index) {
	    Integer lowerRange = this.getLowerRange(index);
	    Integer upperRange = this.getUpperRange(index);
	    return Ranges.create(lowerRange, upperRange);
	}
	/**
	private void updateSolutions(solution<?> solution) {
		
		if(solution.getSolutionType().equalsIgnoreCase("BINARY")) {
			BinarySolution sol = (BinarySolution) solution;
			if(sol.problemname().equalsIgnoreCase("FLP")){
				for(int i = 0; i<sol.variables().size(); i++) {
					for (int j = 0; j < sol.variables().get(i).getNumberOfBits(); j++) {
						if(sol.variables().get(i).get(j)) {
							variables().set(j, createSolution(j, getRange(j)));
						}
					}
				}
			}else {
				for (int i = 0; i < sol.variables().get(0).getNumberOfBits(); i++) {
					if(sol.variables().get(0).get(i)) {
						variables().set(i, createSolution(i, getRange(i)));
					}
				}
			}	
		}else if(solution.getSolutionType().equalsIgnoreCase("INTEGERPERMUTATION")) {
			IntegerPermutationSolution sol = (IntegerPermutationSolution) solution;
			List<Integer> temp = variables();
			for(int i=0; i<((List<Integer>) sol.variables().get(0)).size(); i++) {
				int val = (int) sol.variables().get(0).get(i);
				variables().set(i, (Integer) temp.get(val));
			}
		}else if(solution.getSolutionType().equalsIgnoreCase("INTEGER")) {
			for(int i=0; i<solution.variables().size(); i++) {
				variables().set(i, (Integer) solution.variables().get(i));
			}
		}else if(solution.getSolutionType().equalsIgnoreCase("SEQUENCE")) {
			
		}
		
	 }
	**/
}
