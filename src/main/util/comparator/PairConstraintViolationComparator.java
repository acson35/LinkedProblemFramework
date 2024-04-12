package main.util.comparator;

import java.util.Comparator;

import main.solution.solution;
import main.solution.solutionPair;
import main.solution.solutionSet;
import main.util.constraintHandling.ConstraintHandling;

@SuppressWarnings("hiding")
public class PairConstraintViolationComparator< S extends solutionSet> implements Comparator<S> {

	
	 
	@SuppressWarnings("unchecked")
	@Override
	public int compare(S solution, S listIndividual) {
		    double violationDegreeSolutionpair1 ;
		    double violationDegreeSolutionpair2;

		    violationDegreeSolutionpair1 = ConstraintHandling.overallConstraintViolationDegree2(solution);
		    violationDegreeSolutionpair2 = ConstraintHandling.overallConstraintViolationDegree2(listIndividual);

		    if ((violationDegreeSolutionpair1 > 0) && (violationDegreeSolutionpair2 > 0)) {
		      return Double.compare(violationDegreeSolutionpair1, violationDegreeSolutionpair2);
		    } else if ((violationDegreeSolutionpair1 == 0) && (violationDegreeSolutionpair2 > 0)) {
		      return -1;
		    } else if ((violationDegreeSolutionpair1 > 0) && (violationDegreeSolutionpair2 == 0)) {
		      return 1;
		    } else {
		      return 0;
		    }
	 }

	
}
