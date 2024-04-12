package main.util.comparator;

import java.io.Serializable;
import java.util.Comparator;

import main.solution.solution;
import main.solution.solutionPair;
import main.solution.solutionSet;

public class EqualSolutionComparator<S extends solutionSet> implements Comparator<S>, Serializable {

	  /**
	   * Compares two solutions.
	   *
	   * @param solution1 First <code>Solution</code>.
	   * @param solution2 Second <code>Solution</code>.
	   * @return -1, or 0, or 1, or 2 if solution1 is dominates solution2, solution1
	   * and solution2 are equals, or solution1 is greater than solution2,
	   * respectively.
	   */
	 
	  
	@SuppressWarnings("rawtypes")
	@Override
	public int compare(solutionSet solution1, solutionSet solution2) {
		    if (solution1 == null) {
		      return 1;
		    } else if (solution2 == null) {
		      return -1;
		    }

		    int dominate1; // dominate1 indicates if some objective of solution1
		    // dominates the same objective in solution2. dominate2
		    int dominate2; // is the complementary of dominate1.

		    dominate1 = 0;
		    dominate2 = 0;

		    int flag=0;
		    double[] value1, value2;
		    for(int i = 0; i < solution1.getSolutionSet().size();i++) {
		    	 value1 = solution1.getSolutionSet().get(i+1).getFitness();
				 value2 = solution2.getSolutionSet().get(i+1).getFitness();
				 for(int j = 0; j < value1.length; j++) {
					 if (value1[j] < value2[j]) {
					      flag = -1;
					    } else if (value1[j] > value2[j]) {
					      flag = 1;
					    } else {
					      flag = 0;
					    }
					 
					 if (flag == -1) {
					      dominate1 += 1;
					    }

					    if (flag == 1) {
					      dominate2 += 1;
					 }
				 }
		    }

		    if (dominate1 == 0 && dominate2 == 0) {
		      //No one dominates the other
		      return 0;
		    }
		    if (dominate1 == dominate2) {
			      //No one dominates the other
			      return 0;
			}

		    if (dominate1 > dominate2) {
		      // solution1 dominates
		      return -1;
		    } else if (dominate1 < dominate2) {
		      // solution2 dominates
		      return 1;
		    }
		    return 2;
		  }
	  
}
