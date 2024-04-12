package main.operator.selection.selectionClasses;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.function.BinaryOperator;

import main.operator.selection.SelectionOperator;
import main.solution.solution;
import main.util.createRandomGeneration.LinkedRandom;

@SuppressWarnings("serial")
public class TournamentSelection<S extends solution<?>> implements SelectionOperator<List<S>,S> {
	  private Comparator<S> comparator;

	  private final int n_arity;


	  public TournamentSelection(Comparator<S> comparator, int n_arity) {
	    this.n_arity = n_arity;
	    this.comparator = comparator ;
	  }

	  @Override
	  public S execute(List<S> solutionList) {
		 if ((solutionList == null) || (solutionList.isEmpty())) {
		     throw new IllegalArgumentException("No solution provided: " + solutionList);
		 }

	    S result;
	    if (solutionList.size() == 1) {
	      result = solutionList.get(0);
	    } else {
	      result = selectNRandomDifferentSolutions(1, solutionList, LinkedRandom.getInstance()).get(0);
	      int count = 1; 
	      do {
	        S candidate = selectNRandomDifferentSolutions(1, solutionList, LinkedRandom.getInstance()).get(0);
	        result = getBestSolution(result, candidate, comparator, LinkedRandom.getInstance()) ;
	      } while (++count < this.n_arity);
	    }

	    return result;
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
	  
	  public static <S extends solution<?>> S getBestSolution(
		      S result, S candidate, Comparator<S> comparator2, LinkedRandom randomGenerator) {
		 return getBestSolution(
		        result, candidate, comparator2, (a, b) -> randomGenerator.nextDouble() < 0.5 ? a : b);
	  }

	  public static <S extends solution<?>> S getBestSolution(
		      S solution1, S solution2, Comparator<S> comparator, BinaryOperator<S> equalityPolicy) {
		    S result;
		    int flag = comparator.compare(solution1, solution2);
		    if (flag == -1) {
		      result = solution1;
		    } else if (flag == 1) {
		      result = solution2;
		    } else {
		      result = equalityPolicy.apply(solution1, solution2);
		    }

		    return result;
	  }
			
}
