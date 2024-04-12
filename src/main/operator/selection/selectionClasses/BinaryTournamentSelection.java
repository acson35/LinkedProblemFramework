package main.operator.selection.selectionClasses;

import java.util.Comparator;
import main.solution.solution;
import main.solution.solutionSet;
import main.util.comparator.DominancePairComparator;

@SuppressWarnings("serial")
public class BinaryTournamentSelection<S extends solutionSet> extends NaryTournamentSelection<S> {
  
  public BinaryTournamentSelection() {
    super(2, new DominancePairComparator<S>());
  }
  
  public BinaryTournamentSelection(Comparator<S> comparator) {
	super(2, comparator);
  }

}
