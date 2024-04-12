package main.util.archive;

import java.io.Serializable;
import java.util.List;

import main.solution.solutionPair;

public interface Archive<S> extends Serializable {
	  boolean add(S solution) ;
	  S get(int index) ;
	  List<S> getSolutionList() ;
	  int size() ;
}
