package main.util.comparator;

import java.util.Comparator;
import main.util.constraintHandling.*;
import main.solution.solution;
import main.solution.solutionPair;

public class ConstraintViolationComparator<S extends solution<?>> implements Comparator<S> {

	 public int compare(S solution1, S solution2) {
		    double violationDegreeSolution1 ;
		    double violationDegreeSolution2;

		    violationDegreeSolution1 = ConstraintHandling.overallConstraintViolationDegree(solution1);
		    violationDegreeSolution2 = ConstraintHandling.overallConstraintViolationDegree(solution2);

		    if ((violationDegreeSolution1 > 0) && (violationDegreeSolution2 > 0)) {
		      return Double.compare(violationDegreeSolution1, violationDegreeSolution2);
		    } else if ((violationDegreeSolution1 == 0) && (violationDegreeSolution2 > 0)) {
		      return -1;
		    } else if ((violationDegreeSolution1 > 0) && (violationDegreeSolution2 == 0)) {
		      return 1;
		    } else {
		      return 0;
		    }
	 }
	 
	
	
}
