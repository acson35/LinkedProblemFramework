package main.operator.selection.selectionClasses;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import main.operator.selection.SelectionOperator;
import main.solution.solution;
import main.solution.solutionSet;
import main.util.comparator.DominancePairComparator;
import main.util.createRandomGeneration.LinkedRandom;

@SuppressWarnings("serial")
public class NaryTournamentSelection<S extends solutionSet>
	implements SelectionOperator<List<S>, S> {
	private Comparator<S> comparator;
	private int tournamentSize;

	public NaryTournamentSelection(int tournamentSize, Comparator<S> comparator) {
		this.tournamentSize = tournamentSize;
		this.comparator = comparator;
	}
	
	public NaryTournamentSelection() {
	    this(2, new DominancePairComparator<S>());
	}

	@Override
	public S execute(List<S> solutionList) {
		if (null == solutionList)
			throw new NullPointerException("Null parameter");
		if (solutionList.isEmpty())
	      throw new RuntimeException("The collection is empty") ;
		if(solutionList.size() < tournamentSize) {
			throw new ArithmeticException("The solution list size ("
					+ solutionList.size()
					+ ") is less than "
					+ "the number of requested solutions ("
					+ tournamentSize
					+ ")");
		}
		S result;
		if (solutionList.size() == 1) {
			result = solutionList.get(0);
		} else {
			List<S> selectedSolutions =
					selectNRandomDifferentSolutions(
              tournamentSize, solutionList, LinkedRandom.getInstance());
			result = findBestSolution(selectedSolutions, comparator);
		}

		return result;
	}

	public int getTournamentSize() {
		return tournamentSize ;
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
	
	@SuppressWarnings("rawtypes")
	public S findBestSolution(List<S> solutionList, Comparator<S> comparator) {
	    return solutionList.get(findIndexOfBestSolution(solutionList, comparator));
	}
	
	 @SuppressWarnings("rawtypes")
	public int findIndexOfBestSolution(List<S> solutionList, Comparator<S> comparator) {
		 
		 int index = 0;
		 S bestKnown = solutionList.get(0);
		 S candidateSolution;

		 int flag;
		 for (int i = 1; i < solutionList.size(); i++) {
		   candidateSolution = solutionList.get(i);
		   //flag = comparator.compare(null, null)
		   flag = comparator.compare(bestKnown, candidateSolution);
		   if (flag == 1) {
		     index = i;
		     bestKnown = candidateSolution;
		   }
		 }
		 return index;    
	 }


}
