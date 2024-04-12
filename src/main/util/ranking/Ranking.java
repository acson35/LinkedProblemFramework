package main.util.ranking;

import java.util.List;

import main.solution.solutionPair;

public interface Ranking<S> {
	  Ranking<S> compute(List<S> solutionList) ;
	  List<S> getSubFront(int rank) ;
	  int getNumberOfSubFronts() ;
	  Integer getRank(S solution) ;
	  Object getAttributedId() ;
	  
}
