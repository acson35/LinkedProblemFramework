package main.util.comparator;

import java.io.Serializable;
import java.util.Comparator;

import main.solution.solutionSet;
import main.util.densityEstimator.CrowdingDistanceDensityEstimator;
import main.util.ranking.FastNonDominatedSortRanking;
import main.util.ranking.Ranking;

@SuppressWarnings("serial")
public class RankingAndCrowdingDistanceComparator<S extends solutionSet>
implements Comparator<S>, Serializable {

	private final Comparator<S> rankComparator;
	private final Comparator<S> crowdingDistanceComparator;

	public RankingAndCrowdingDistanceComparator(Ranking<S> ranking) {
	    rankComparator = Comparator.comparing(ranking::getRank);
	    CrowdingDistanceDensityEstimator<S> crowdingDistanceDensityEstimator =
	        new CrowdingDistanceDensityEstimator<>();
	    crowdingDistanceComparator = crowdingDistanceDensityEstimator.getComparator();
	}

	public RankingAndCrowdingDistanceComparator() {
	    this(new FastNonDominatedSortRanking<>());
	}
	
	@Override
	public int compare(S solution1, S solution2) {
		int result = rankComparator.compare(solution1, solution2);
	    if (result == 0) {
	      result = crowdingDistanceComparator.compare(solution1, solution2);
	    }
	    return result;
	}
	
	

}
