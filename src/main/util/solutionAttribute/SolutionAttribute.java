package main.util.solutionAttribute;

import java.io.Serializable;

import main.solution.solutionPair;

public interface SolutionAttribute<S, V> extends Serializable {
	  void setAttribute(S solution, V value) ;
	 
	  V getAttribute(S solution) ;
	
	  Object getAttributeIdentifier() ;
}
