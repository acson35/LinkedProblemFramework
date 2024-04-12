package main.util.comparator;

import java.io.Serializable;
import java.util.Comparator;

import main.solution.solution;
import main.solution.solutionPair;
import main.solution.solutionSet;

@SuppressWarnings("serial")
public class DominancePairComparator<S extends solutionSet> implements Comparator<S>, Serializable {
	 
	private PairConstraintViolationComparator<S> constraintViolationComparator;

	  /** Constructor */
	  public DominancePairComparator() {
	    this(new PairConstraintViolationComparator<S>());
	  }

	  /** Constructor */
	  public DominancePairComparator(PairConstraintViolationComparator<S> constraintComparator) {
	    this.constraintViolationComparator = constraintComparator;
	  }

	  /**
	   * Compares two solutions.
	   *
	   * @param solution1 Object representing the first <code>Solution</code>.
	   * @param solution2 Object representing the second <code>Solution</code>.
	   * @return -1, or 0, or 1 if solution1 dominates solution2, both are non-dominated, or solution1
	   *     is dominated by solution2, respectively.
	   */
	 
	  
	 
	@SuppressWarnings("rawtypes")
	@Override
	public int compare(S bestKnown, S candidateSolution) {
		  if (null == bestKnown) {
				throw new NullPointerException("Null parameter") ;
		  }
		  if (null == candidateSolution) {
				throw new NullPointerException("Null parameter") ;
		  }
		  
		  int num = bestKnown.getSolutionSet().size();
		  
		  for(int i = 0; i < num; i++) {
			  if( ((main.solution.solutionSet) bestKnown).getSolutionSet().get(i+1).getConstraints().length > ((main.solution.solutionSet) candidateSolution).getSolutionSet().get(i+1).getConstraints().length) {
			    	throw new ArithmeticException("The number of constraints of both parent solutions must be the same ");
			  }
		  }

	    int result;
	    result = constraintViolationComparator.compare(bestKnown, candidateSolution);
	    if (result == 0) {
	      result = dominanceTest(bestKnown, candidateSolution);
	    }

	    return result;
	  }

	  private int dominanceTest(solutionSet linkedsolution1, solutionSet linkedsolution2) {
		    int bestIsOne = 0;
		    int bestIsTwo = 0;
		    int result;
		    
		    int num = linkedsolution1.getSolutionSet().size();
		    
		    for(int i=0; i<num; i++) {
		    	
		    	double[] value1 = ((main.solution.solutionSet) linkedsolution1).getSolutionSet().get(i+1).getFitness();
			    double[] value2 = ((main.solution.solutionSet) linkedsolution2).getSolutionSet().get(i+1).getFitness();
			    for(int j = 0; j < value1.length; j++) {
			    	if (value1[j] != value2[j]) {
					      if (value1[j] < value2[j]) {
					        bestIsOne = 1;
					      }
					      if (value2[j] < value1[j]) {
					        bestIsTwo = 1;
					      }
					 }
			    }  
		    }
		    
		    result = Integer.compare(bestIsTwo, bestIsOne);
		    return result;
		  }

}
