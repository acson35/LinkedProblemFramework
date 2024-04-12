package main.operator.selection.selectionClasses;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import main.operator.selection.SelectionOperator;
import main.solution.solutionSet;
import main.util.comparator.MultiCriteriaComparator;
import main.util.ranking.Criteria;
import main.util.ranking.promethee.Promethee_II;
import main.util.ranking.topsis.TopsisIncompleteAlternativeDataException;

public class PrometheeIISelection<S extends solutionSet> implements SelectionOperator<List<S>,List<S>> {

	private final int solutionpairsToSelect ;
	@SuppressWarnings("unused")
	private Comparator<S> comparator;
	private List<Criteria> criteria;


	/** Constructor */
	public PrometheeIISelection(int solutionpairsToSelect, Comparator<S> comparator, List<Criteria> criteria) {
		this.comparator = comparator ;
		this.solutionpairsToSelect = solutionpairsToSelect ;
		this.criteria = criteria;
	}

	/** Constructor */
	public PrometheeIISelection(int solutionpairsToSelect, List<Criteria> criteria) {
		this(solutionpairsToSelect, new MultiCriteriaComparator<S>(), criteria) ;
	}

	/* Getter */
	public int getNumberOfSolutionsToSelect() {
		return solutionpairsToSelect;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<S> execute(List<S> solutionList) {
		if (null == solutionList) {
			throw new NullPointerException("The solution list is null");
		} else if (solutionList.isEmpty()) {
			throw new NullPointerException("The solution list is empty") ;
		}  else if (solutionList.size() < solutionpairsToSelect) {
			throw new ArithmeticException("The population size ("+solutionList.size()+") is smaller than" +
          "the solutions to selected ("+solutionpairsToSelect+")")  ;
		}

		Promethee_II promethee = new Promethee_II((List<solutionSet>) solutionList, criteria);
		try {
			solutionList = (List<S>) promethee.calculateOptimalSolutionSortedList();
		} catch (TopsisIncompleteAlternativeDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return (List<S>) getSelelectedPairs(solutionList);

	}
	
	@SuppressWarnings("unused")
	private List<S> getSelelectedPairs(List<S> solutionList) {
		List<S> selectedSolutionPairs = new ArrayList<S>();
		for(int i = 0; i < solutionpairsToSelect; i++) {
			selectedSolutionPairs.add(solutionList.get(i));
		}
		return selectedSolutionPairs;
	}	

}
