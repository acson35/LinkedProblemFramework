package main.operator.selection.selectionClasses;

import java.util.Comparator;
import java.util.EmptyStackException;
import java.util.List;

import main.operator.selection.SelectionOperator;

@SuppressWarnings("serial")
public class BestSolutionSelection<S> implements SelectionOperator<List<S>, S> {
	private final Comparator<S> comparator ;

	public BestSolutionSelection(Comparator<S> comparator) {
	    
	    if (null == comparator)
			throw new NullPointerException("Null parameter");
	    this.comparator = comparator ;
	}

	public S execute(List<S> solutionList) {
	  
	  if (null == solutionList)
			throw new NullPointerException("Null parameter");
	  if (solutionList.isEmpty())
	      throw new RuntimeException("The collection is empty") ;
	    
	  return solutionList.stream().reduce(solutionList.get(0), (x, y) -> (comparator.compare(x, y) < 0) ? x : y);
	}

}
