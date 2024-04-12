package main.util.comparator;

import java.io.Serializable;
import java.util.Comparator;

import main.solution.solutionPair;
import main.solution.solutionSet;

public class MultipleCriteriaComparator<S extends solutionSet> implements Comparator<S>, Serializable {

	private int objectiveId;
	  
	public MultipleCriteriaComparator() {
	}

	public MultipleCriteriaComparator(int objectiveId) {
	   this.objectiveId = objectiveId;
	   //order = Ordering.ASCENDING;
	}

	@Override
	public int compare(S solution1, S solution2) {
		int result;
		solution1.getSolution(1).attributes().get("Performance");
	  	double objective1 = (double) solution1.getSolution(1).attributes().get("Performance");
	  	double objective2 = (double) solution2.getSolution(1).attributes().get("Performance");
	  	result = Double.compare(objective2, objective1);
	    return result ;
	  }
}
