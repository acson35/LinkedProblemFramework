package main.problem.integerProblem.Implementation;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import main.problem.AbstractGenericProblem;
import main.problem.integerProblem.IntegerProblem;
import main.solution.integerSolution.IntegerSolution;
import main.util.factory.Instatiator.ChangeSolution.ChangeIntegerSolution;
import main.util.range.Ranges;


/**
 * Abstract class for integer problems.
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 * 
 * 
 * @author Akinola Ogunsemi <a.ogunsemi@rgu.ac.uk>
 * 
 */
//AUG2021 - Akinola - Modified to include a parent solution for a linked optimisation.

@SuppressWarnings("serial")
public abstract class AbstractIntegerProblem extends AbstractGenericProblem<IntegerSolution> implements IntegerProblem {

	 protected List<Ranges<Integer>> ranges;
	
	 @Override
	  public Integer getUpperRange(int index) {
	    return getRanges().get(index).getUpperRange();
	  }

	  @Override
	  public Integer getLowerRange(int index) {
	    return getRanges().get(index).getLowerRange();
	  }

	  public void setVariableRanges(List<Integer> lowerRanges, List<Integer> upperRanges) {
	  
	   if(lowerRanges==null) {
		 throw new NullPointerException() ;	  
	   }
	   if(upperRanges==null) {
			 throw new NullPointerException();
	   }
	   if (lowerRanges.size() != upperRanges.size()) {
		   System.out.print("The size of the lower range list is not equal to the size of the upper range list");
	    }
	   ranges =
	        IntStream.range(0, lowerRanges.size())
	            .mapToObj(i -> Ranges.create(lowerRanges.get(i), upperRanges.get(i)))
	            .collect(Collectors.toList());
	  }
	  
	  /**
	   * This method creates and returns a integer solution modified by a parent solution if the solution problem is a dependent problem.
	   * @return
	   * 
	*/

	  @Override
	  public IntegerSolution createsolution() {
		if(null == parentsolution()) {
			return new IntegerSolution(getRanges(), getnObjectives(), getnConstraints(), getName(), getData());
		}else {
			ChangeIntegerSolution updateSolution = new ChangeIntegerSolution(parentsolution(), getRanges());
			return new IntegerSolution(getnObjectives(), getnConstraints(), getName(), getRanges(), updateSolution.variables(), getData());
		} 
	  }
	  
	//  @Override
	 // public IntegerSolution updatesolution(solution<?> solution) {
	//	ChangeIntegerSolution updateSolution = new ChangeIntegerSolution(solution, getRanges());
	//	return new IntegerSolution(getnConstraints(), getName(), updateSolution.variables());
	//  }

	  @Override
	  public List<Ranges<Integer>> getRanges() {
	    return ranges ;
	  }
	
	
	 
}
