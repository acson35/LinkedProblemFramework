package main.operator.selection.selectionClasses;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import main.operator.selection.SelectionOperator;
import main.util.createRandomGeneration.LinkedRandom;

@SuppressWarnings("serial")
public class NaryRandomSelection<S> implements SelectionOperator<List<S>, List<S>> {
	  private int numberOfSolutionsToBeReturned;

	
	  public NaryRandomSelection() {
	    this(1);
	  }

	
	  public NaryRandomSelection(int numberOfSolutionsToBeReturned) {
	    this.numberOfSolutionsToBeReturned = numberOfSolutionsToBeReturned;
	  }

	 
	  public List<S> execute(List<S> solutionList) {
		  if (null == solutionList)
				throw new NullPointerException("Null parameter");
		  if (solutionList.isEmpty())
		      throw new RuntimeException("The collection is empty") ;
		  if(solutionList.size() < numberOfSolutionsToBeReturned) {
			throw new ArithmeticException("The solution list size ("
						+ solutionList.size()
						+ ") is less than "
						+ "the number of requested solutions ("
						+ numberOfSolutionsToBeReturned
						+ ")");
		}

	    return selectNRandomDifferentSolutions(
	        numberOfSolutionsToBeReturned, solutionList, LinkedRandom.getInstance());
	  }
	  
	  public static <S> List<S> selectNRandomDifferentSolutions(
			int numberOfSolutionsToBeReturned,
			List<S> solutionList,
			LinkedRandom randomGenerator) {
			List<S> resultList = new ArrayList<>(numberOfSolutionsToBeReturned);

			if (solutionList.size() == 1) {
				   resultList.add(solutionList.get(0));
			} else {
				   Collection<Integer> positions = new HashSet<>(numberOfSolutionsToBeReturned);
				   while (positions.size() < numberOfSolutionsToBeReturned) {
				     int nextPosition = randomGenerator.nextInt(0, solutionList.size() - 1);
				     if (!positions.contains(nextPosition)) {
				        positions.add(nextPosition);
				        resultList.add(solutionList.get(nextPosition));
				    }
			   }
			}

			return resultList;
	  }
	
}
