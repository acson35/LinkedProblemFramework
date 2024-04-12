package main.operator.selection.selectionClasses;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import main.operator.selection.SelectionOperator;
import main.util.createRandomGeneration.LinkedRandom;

public class RandomSelection<S> implements SelectionOperator<List<S>, S> {


	public S execute(List<S> solutionList) {
		if (null == solutionList)
			throw new NullPointerException("Null parameter");
		if (solutionList.isEmpty())
	      throw new RuntimeException("The collection is empty") ;

		List<S> list = selectNRandomDifferentSolutions(1, solutionList, LinkedRandom.getInstance());

	    return list.get(0) ;
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
