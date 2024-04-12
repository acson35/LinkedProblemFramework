package main.util.comparator;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

import main.solution.solution;
import main.solution.solutionPair;
import main.solution.solutionSet;
import main.util.solutionAttribute.HypervolumeContributionAttribute;

@SuppressWarnings("serial")
public class HypervolumeContributionComparator<S extends solutionSet> implements Comparator<S>, Serializable {
	  
	private final HypervolumeContributionAttribute<S> hvContribution = new HypervolumeContributionAttribute<S>() ;

	  /**
	   * Compare two solutions.
	   *
	   * @param solution1 Object representing the first <code>Solution</code>.
	   * @param solution2 Object representing the second <code>Solution</code>.
	   * @return -1, or 0, or 1 if solution1 is has lower, equal, or higher contribution value than solution2,
	   * respectively.
	   */
	  @Override
	  public int compare(S solution1, S solution2) {
	    int result ;
	    if (solution1 == null) {
	      if (solution2 == null) {
	        result = 0;
	      } else {
	        result = 1 ;
	      }
	    } else if (solution2 == null) {
	      result = -1;
	    } else {
	      double contribution1 = Double.MAX_VALUE ;
	      double contribution2 = Double.MAX_VALUE ;

	      if (hvContribution.getAttribute(solution1) != null) {
	        contribution1 = (double) hvContribution.getAttribute(solution1);
	      }

	      if (hvContribution.getAttribute(solution2) != null) {
	        contribution2 = (double) hvContribution.getAttribute(solution2);
	      }

	      result = Double.compare(contribution2, contribution1);
	    }

	    return result ;
	  }
	  
	
	@SuppressWarnings({ "rawtypes", "unused" })
	public Comparator<S> getComparator(){
		 return (solutionpair1, solutionpair2) -> compare(solutionpair1, solutionpair2);
	 }

 
	  
}
