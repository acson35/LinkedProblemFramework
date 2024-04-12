package main.util.comparator;

import java.io.Serializable;
import java.util.Comparator;

import main.solution.solution;
import main.util.constraintHandling.ConstraintHandling;

/**
 * This class implements a comparator based on a given objective
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
@SuppressWarnings("serial")
public class ObjectiveComparator<S extends solution<?>> implements Comparator<S>, Serializable {
	  public enum Ordering {ASCENDING, DESCENDING};
	  private int objectiveId;
	  private ConstraintViolationComparator<S> constraintViolationComparator;


	  private Ordering order;
	  
	  public ObjectiveComparator() {
	    order = Ordering.ASCENDING;
	  }

	  public ObjectiveComparator(int objectiveId) {
		    this.objectiveId = objectiveId;
		    order = Ordering.ASCENDING;
	  }
	  
	  public ObjectiveComparator(Ordering order) {
	    this.order = order ;
	  }

	  @Override
	  public int compare(S solution1, S solution2) {
		constraintViolationComparator = new ConstraintViolationComparator<S>();
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
	    		double objective1 = solution1.getFitness()[objectiveId];
	    		double objective2 = solution2.getFitness()[objectiveId];
	    		
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
