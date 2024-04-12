package main.util.archive;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import main.solution.solution;
import main.solution.solutionPair;
import main.solution.solutionSet;
import main.util.comparator.DominanceComparator;
import main.util.comparator.DominancePairComparator;
import main.util.comparator.EqualSolutionComparator;

@SuppressWarnings("serial")
public class NonDominatedSolutionListArchive <S extends solutionSet> implements Archive<S> {
	private List<S> solutionList;
	
	@SuppressWarnings("rawtypes")
	private DominancePairComparator<S> dominanceComparator;
	
	private Comparator<S> equalSolutions = new EqualSolutionComparator<S>();

		  /**
		   * Constructor
		   */
	@SuppressWarnings("unchecked")
	public NonDominatedSolutionListArchive() {
	    this(new DominancePairComparator<S>());
	}

		  /**
		   * Constructor
		   */
	@SuppressWarnings("rawtypes")
	public NonDominatedSolutionListArchive(DominancePairComparator<S> comparator) {
	   dominanceComparator = comparator;
	   solutionList = new ArrayList<>();

	}

		  /**
		   * Inserts a solution in the list
		   *
		   * @param solution The solution to be inserted.
		   * @return true if the operation success, and false if the solution is dominated or if an
		   * identical individual exists. The decision variables can be null if the solution is read from a
		   * file; in that case, the domination tests are omitted
		   */
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public boolean add(S solution) {
	  boolean solutionInserted = false ;
	  if (solutionList.size() == 0) {
	    solutionList.add(solution) ;
	    solutionInserted = true ;
	  } else {
	    Iterator<S> iterator = solutionList.iterator();
	    boolean isDominated = false;
		     
	    boolean isContained = false;
	    while (((!isDominated) && (!isContained)) && (iterator.hasNext())) {
	      S listIndividual = iterator.next();
	      int flag = dominanceComparator.compare(solution, listIndividual);
		      
	      if (flag == -1) {
	    	  
	        iterator.remove();
	      }  else if (flag == 1) {
	        isDominated = true; // dominated by one in the list
	      } else if (flag == 0) {
	        int equalflag = equalSolutions.compare(solution, listIndividual);
		        
	        if (equalflag == 0) // solutions are equals
	          isContained = true;
	      }
	    }
		      
	    if (!isDominated && !isContained) {
	  	  solutionList.add(solution);
	   	  solutionInserted = true;
	    }
		      
	    return solutionInserted;
	  }

	  return solutionInserted ;
	}
 
	public Archive<S> join(Archive<S> archive) {
	  return this.addAll(archive.getSolutionList());
	}

	public Archive<S> addAll(List<S> list) {
	  for (S solution : list) {
	    this.add(solution) ;
	  }

	  return this ;
	}
		
	@Override
	public List<S> getSolutionList() {
	    return solutionList;
	}
		
	@Override
	public int size() {
	 return solutionList.size();
	}

	@Override
	public S get(int index) {
	 return solutionList.get(index);
	}
	
}
