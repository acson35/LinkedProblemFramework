package main.util.comparator;

import java.io.Serializable;
import java.util.Comparator;

import main.solution.solution;
import main.solution.solutionSet;
import main.util.comparator.ObjectiveComparator.Ordering;

@SuppressWarnings("serial")
public class ObjectiveSetComparator<S extends solutionSet> implements Comparator<S>, Serializable {
	  public enum Ordering {ASCENDING, DESCENDING};
	  private int objectiveId;
	  private int solutionId;
	  private PairConstraintViolationComparator<S> constraintViolationComparator;


	  private Ordering order;
	  
	  public ObjectiveSetComparator() {
	    order = Ordering.ASCENDING;
	  }

	  public ObjectiveSetComparator(int solutionId, int objectiveId) {
		    this.objectiveId = objectiveId;
		    this.solutionId = solutionId;
		    order = Ordering.ASCENDING;
	  }
	  
	  public ObjectiveSetComparator(Ordering order) {
	    this.order = order ;
	  }

	  @Override
	  public int compare(S solution1, S solution2) {
		constraintViolationComparator = new PairConstraintViolationComparator<S>();
	    int result;
	    if (solution1 == null) {
	      if (solution2 == null) {
	        result = 0;
	      } else {
	        result =  1;
	      }
	    } else if (solution2 == null) {
	      result =  -1;
	    } else {
	    		double objective1 = solution1.getSolution(solutionId).getFitness()[objectiveId];
	    		double objective2 = solution2.getSolution(solutionId).getFitness()[objectiveId];
	    		
	    		result = constraintViolationComparator.compare(solution1, solution2);
	    		if(result == 0) {
	    			if (order == Ordering.ASCENDING) { 
		    			
		    			result = Double.compare(objective1, objective2);
		    		} else {
		    			result = Double.compare(objective2, objective1);
		   	      }
	    		}	
	    }
	    return result ;
	  }

}
