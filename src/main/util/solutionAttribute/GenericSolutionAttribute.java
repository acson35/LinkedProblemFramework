package main.util.solutionAttribute;

import main.solution.solution;
import main.solution.solutionPair;
import main.solution.solutionSet;

@SuppressWarnings("serial")
public class GenericSolutionAttribute <S extends solutionSet, V> implements SolutionAttribute<S, V>{
	  private Object identifier;

	  /**
	   * Constructor
	   */
	  public GenericSolutionAttribute() {
	    identifier = this.getClass() ;
	  }

	  /**
	   * Constructor
	   * @param id Attribute identifier
	   */
	  public GenericSolutionAttribute(Object id) {
	    this.identifier = id ;
	  }

	 
	  
	  @SuppressWarnings({ "unchecked", "rawtypes" })
	  @Override
	  public V getAttribute(S solution) {
		// System.out.println((V)solution.getParentSolution().attributes().keySet());
	    return (V)solution.getSolutionSet().get(1).attributes().get(getAttributeIdentifier());
	  }

	  
	  @SuppressWarnings({ "unchecked", "rawtypes" })
	  @Override
	  public void setAttribute(S solution, V value) {
	     solution.getSolutionSet().get(1).attributes().put(getAttributeIdentifier(), value);
	  }

	  @Override
	  public Object getAttributeIdentifier() {
	    return identifier;
	  }
}
