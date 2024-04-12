package main.operator.selection.selectionClasses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import main.operator.selection.SelectionOperator;
import main.solution.solution;
import main.solution.solutionPair;
import main.solution.solutionSet;
import main.util.comparator.DominancePairComparator;
import main.util.densityEstimator.CrowdingDistanceDensityEstimator;
import main.util.ranking.FastNonDominatedSortRanking;
import main.util.ranking.Ranking;

@SuppressWarnings("serial")
public class PairRankingAndCrowdingSelection<S extends solutionSet> implements SelectionOperator<List<S>,List<S>> {
	
	private final int solutionpairsToSelect ;
	private Comparator<S> dominanceComparator ;
	//private List<Integer> solutionRanked;

	/** Constructor */
	public PairRankingAndCrowdingSelection(int solutionpairsToSelect, Comparator<S> dominanceComparator) {
		this.dominanceComparator = dominanceComparator ;
		this.solutionpairsToSelect = solutionpairsToSelect ;
	}

	/** Constructor */
	public PairRankingAndCrowdingSelection(int solutionpairsToSelect) {
		this(solutionpairsToSelect, new DominancePairComparator<S>()) ;
	}

	/* Getter */
	public int getNumberOfSolutionsToSelect() {
		return solutionpairsToSelect;
	}

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
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

		Ranking<S> ranking = new FastNonDominatedSortRanking<S>(dominanceComparator);
		ranking = ((FastNonDominatedSortRanking<S>) ranking).compute(solutionList) ;
	//	this.solutionList = solutionList;
		//System.out.println(ranking.getNumberOfSubFronts());
		
		return (List<S>) crowdingDistanceSelection(ranking);
	}
	
	@SuppressWarnings("rawtypes")
	protected List<S> crowdingDistanceSelection(Ranking<S> ranking) {
		CrowdingDistanceDensityEstimator<S> crowdingDistance = new CrowdingDistanceDensityEstimator<S>() ;
		List<S> population = new ArrayList<>(solutionpairsToSelect) ;
		
		int rankingIndex = 0;
		
		while(population.size() < solutionpairsToSelect) {
			//System.out.println(rankingIndex + " " + ranking.getNumberOfSubFronts());
		//	if(rankingIndex <= ranking.getNumberOfSubFronts()) {
			
				if (subfrontFillsIntoThePopulation(ranking, rankingIndex, population)) {
					
					//System.out.println(ranking.getSubFront(rankingIndex).size()+ " " + solutionpairsToSelect);
					crowdingDistance.compute(ranking.getSubFront(rankingIndex));
					addRankedSolutionsToPopulation(ranking, rankingIndex, population);
					rankingIndex++;
				
					
				} else {
					crowdingDistance.compute(ranking.getSubFront(rankingIndex));
					addLastRankedSolutionsToPopulation(ranking, rankingIndex, population);	
					
				}
		//	}
			
		}
		
		//System.out.println(ranking.getSubFront(rank));
		
		return population;
	}
	
	@SuppressWarnings("rawtypes")
	protected boolean subfrontFillsIntoThePopulation(Ranking<S> ranking, int rank, List<S> population) {
	//	System.out.println(rank + " "+ (solutionpairsToSelect - population.size()));
		return ranking.getSubFront(rank).size() < (solutionpairsToSelect - population.size()) ;
	}

	protected void addRankedSolutionsToPopulation(Ranking<S> ranking, int rank, List<S> population) {
		List<S> front ;

		front = ranking.getSubFront(rank);
		
		front.forEach(population::add);
	}


	protected void addLastRankedSolutionsToPopulation(Ranking<S> ranking, int rank, List<S>population) {
		List<S> currentRankedFront = ranking.getSubFront(rank) ;

		currentRankedFront.sort(new CrowdingDistanceDensityEstimator<>().getComparator());

		int i = 0 ;
		while (population.size() < solutionpairsToSelect) {
			population.add(currentRankedFront.get(i)) ;
			i++ ;
		}
	}	
	
	
}
