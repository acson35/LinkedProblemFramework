package main.solution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.solution.integerSolution.IntegerSolution;
import main.solution.permutationSolution.IntegerPermutationSolution;
import main.util.createBinary.createBinary;
import main.util.permutationType.PermutationType;
import main.util.problemName.ProblemName;

/**
 * Abstract class representing a generic solution
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 * 
 * 
 */
//AUG2021 - Akinola - Modified to include an attribute.

@SuppressWarnings("serial")
public abstract class AbstractSolution<T> implements solution<T> {
	
	private List<T> variables;
	protected String problemname;
	//private double fitness;
	public double[] fitness;
	private double[] constraints;
	protected Map<Object, Object> attributes;

	protected AbstractSolution(int numberOfVariables, int nObj,  int nConstraints, String name) {
		variables = new ArrayList<>(numberOfVariables);
		for (int i = 0; i < numberOfVariables; i++) {
			variables.add(i, null);
		} 
		
		fitness = new double[nObj];
		for (int i = 0; i < nObj; i++) {
			fitness[i]=0.0;
		} 
		constraints = new double[nConstraints];
		for (int i = 0; i < nConstraints; i++) {
			constraints[i]=0.0;
		} 
		
		attributes = new HashMap<Object, Object>();  
		
		problemname = name;  
		  
	}
	
	protected AbstractSolution(int numberOfVariables, String name) {
		this(numberOfVariables, 0, 0, name);
	}
	 
	@Override
	public List<T> variables() {
	   return variables ;
	}
	 
	@Override
	public String problemname() {
	 return problemname;
	}
	 
	@Override
	public double[] getFitness() {
		return fitness;
	}
	
	@Override
	public double[] getConstraints() {
		return constraints;
	}
	
	@Override
	public Map<Object, Object> attributes() {
		return attributes;
	}
		
	@Override
	public String toString() {
	  StringBuilder result = new StringBuilder("Problem Name: ");
	   
	  result.append(problemname).append(" ");
	  result.append("Variables: ");
	  for (T var : variables) {
	    result.append(var).append(" ");
	  }
	  result.append("Objectives: ");
	  for (Double obj : fitness) {
		result.append(obj).append(" ");
	  }
	  
	  result.append("Constraints: ");
	  for (Double con : constraints) {
	    result.append(con).append(" ");
	  }
	  result.append("\t");
	  result.append("AlgorithmAttributes: ");
	  for(Object obj: attributes.keySet()) {
		  result.append(obj).append(" ");
	  }
	  result.append("\n");
	  return result.toString();
	} 	
		
	@Override
	public boolean equals(Object obj) {
		    if (obj == null) {
		    	
		      throw new NullPointerException("Solution is null");
		    }

		    solution<T> solution = (solution<T>)obj;

		    return this.variables().equals(solution.variables());
	 }
	 
	// public String getProblemName(solution<?> sol) {
	//		if(sol.getSolutionType().equalsIgnoreCase("BINARY")) {
	//			BinarySolution solution = (BinarySolution) sol;
				
	//			return solution.getSolutionName();
	//		}else if(sol.getSolutionType().equalsIgnoreCase("INTEGERPERMUTATION")) {
	//			System.out.println(solution.getData());
	//			return solution.getSolutionName();
	//		}
	//		return null;
	//} 
	 
	
}
