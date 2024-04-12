package main.solution;

import java.util.Map;

public class solutionSet {
	
	Map<Integer, solution<?>> SolutionSet;
	
	public solutionSet(Map<Integer,solution<?>> Solutions){
		this.SolutionSet = Solutions;
	}
	
	public solution<?> getSolution(int key) {
		return this.SolutionSet.get(key);
	}
	
	public Map<Integer, solution<?>> getSolutionSet() {
		return this.SolutionSet;
	}
	
	public void setSolution(int key, solution<?> Solution) {
		this.SolutionSet.put(key, Solution);
	}
	
	public solutionSet copy() {
		return this;
	}

}
