package main.util.comparator;

import java.io.Serializable;
import java.util.Comparator;

import main.solution.solution;
import main.solution.solutionPair;

public class DominanceComparator<S extends solution<?>> implements Comparator<S>, Serializable {
	 
	private ConstraintViolationComparator<S> constraintViolationComparator;

	  /** Constructor */
	  public DominanceComparator() {
	    this(new ConstraintViolationComparator<S>());
	  }

	  /** Constructor */
	  public DominanceComparator(ConstraintViolationComparator<S> constraintComparator) {
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
	  @Override
	  public int compare(S solution1, S solution2) {
		  if (null == solution1) {
				throw new NullPointerException("Null parameter") ;
		  }
		  if (null == solution2) {
				throw new NullPointerException("Null parameter") ;
		  }
		  
	    int result;
	    result = constraintViolationComparator.compare(solution1, solution2);
	    if (result == 0) {
	      result = dominanceTest(solution1, solution2);
	    }
	    return result;
	  }
	  
	  private int dominanceTest(S solution1, S solution2) {
	    int bestIsOne = 0;
	    int bestIsTwo = 0;
	    int result;
	    
	    double value1 = solution1.getFitness()[0];
	    double value2 = solution2.getFitness()[0];
	    if (value1 != value2) {
	      if (value1 < value2) {
	        bestIsOne = 1;
	      }
	      if (value2 < value1) {
	        bestIsTwo = 1;
	      }
	    } 
	    
	    result = Integer.compare(bestIsTwo, bestIsOne);
	    return result;
	  }
	  
	  
	 
}
