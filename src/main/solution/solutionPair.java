package main.solution;

/**
 * Class representing a pair of solutions of different solution types. 
 *
 * @author Akinola Ogunsemi <a.ogunsemi@rgu.ac.uk>
 * 
 * 
 */

@SuppressWarnings("serial")
public class solutionPair {

	solution<?> parentSolution;
	solution<?> childSolution;
	
	public solutionPair(solution<?> parentSolution, solution<?> childSolution){
		this.parentSolution = parentSolution;
		this.childSolution = childSolution;
	}
	
	public solution<?> getChildSolution() {
		return this.childSolution;
	}
	
	public void setChildSolution(solution<?> childSolution) {
		this.childSolution = childSolution;
	}
	
	
	public solution<?> getParentSolution() {
		return this.parentSolution;
	}
	
	public void setParentSolution(solution<?> parentSolution) {
		this.parentSolution = parentSolution;
	}
	
	public solutionPair copy() {
		return this;
	}

}
