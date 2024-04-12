package main.util.densityEstimator;

import java.util.Comparator;
import java.util.List;

import main.solution.solutionPair;
import main.solution.solutionSet;

public interface DensityEstimator<S extends solutionSet> {
	
	void compute(List<S> solutionSet) ;
	Double getValue(S solution) ;
	Comparator<S> getComparator();
}
