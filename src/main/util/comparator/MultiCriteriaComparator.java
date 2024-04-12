package main.util.comparator;

import java.io.Serializable;
import java.util.Comparator;

import main.solution.solution;
import main.solution.solutionPair;
import main.solution.solutionSet;
import main.util.comparator.ObjectiveComparator.Ordering;

@SuppressWarnings("serial")
public class MultiCriteriaComparator<S extends solutionSet> implements Comparator<S>, Serializable {
	  
	//public enum Ordering {ASCENDING, DESCENDING} ;
	
	private int objectiveId;
	  
	public MultiCriteriaComparator() {
	}

	public MultiCriteriaComparator(int objectiveId) {
	   this.objectiveId = objectiveId;
	   //order = Ordering.ASCENDING;
	}

	@Override
	public int compare(S solution1, S solution2) {
		int result;
	  	double objective1 = (double) solution1.getSolutionSet().get(1).attributes().get("Performance");;
	  	double objective2 = (double) solution2.getSolutionSet().get(1).attributes().get("Performance");;
	  	result = Double.compare(objective2, objective1);
	    return result ;
	  }

}
