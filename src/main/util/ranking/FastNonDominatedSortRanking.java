package main.util.ranking;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import main.solution.solution;
import main.solution.solutionPair;
import main.solution.solutionSet;
import main.util.comparator.ConstraintViolationComparator;
import main.util.comparator.DominanceComparator;
import main.util.comparator.DominancePairComparator;
import main.util.comparator.PairConstraintViolationComparator;

public class FastNonDominatedSortRanking<S extends solutionSet> implements Ranking<S> {
	 
	private String attributeId = getClass().getName();
	private Comparator<S> dominanceComparator;
	private final Comparator<S> CONSTRAINT_VIOLATION_COMPARATOR =
	      new PairConstraintViolationComparator<S>();

	private List<ArrayList<S>> rankedSubPopulations;
	//private List<ArrayList<solutionPair>> rankedSubPopulations2;

	  /** Constructor */
	public FastNonDominatedSortRanking(Comparator<S> comparator) {
	   this.dominanceComparator = comparator;
	   rankedSubPopulations = new ArrayList<>();
	  // rankedSubPopulations2 = new ArrayList<>();
	}

	 /** Constructor */
	public FastNonDominatedSortRanking() {
	   this(new DominancePairComparator<S>());
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Ranking<S> compute(List<S> solutionList) {
		  List<S> population = solutionList;

		  // dominateMe[i] contains the number of population dominating i
		  int[] dominateMe = new int[population.size()];

		  // iDominate[k] contains the list of population dominated by k
		  List<List<Integer>> iDominate = new ArrayList<>(population.size());

		  // front[i] contains the list of individuals belonging to the front i
		  ArrayList<List<Integer>> front = new ArrayList<>(population.size()+1);

		  // Initialize the fronts
		  for (int i = 0; i < population.size()+1; i++) {
		    front.add(new LinkedList<Integer>());
		  }

		  // Fast non dominated sorting algorithm
		  // Contribution of Guillaume Jacquenot
		  for (int p = 0; p < population.size(); p++) {
		    // Initialize the list of individuals that i dominate and the number
		    // of individuals that dominate me
		    iDominate.add(new LinkedList<Integer>());
		    dominateMe[p] = 0;
		  }
		  
		  int flagDominate;
		  for (int p = 0; p < (population.size() - 1); p++) {
		    // For all q individuals , calculate if p dominates q or vice versa
		    for (int q = p+1; q < population.size(); q++) {
		    	//if(p==q)continue;
		      flagDominate = dominanceComparator.compare(solutionList.get(p), solutionList.get(q));    
		     // if (flagDominate == 0) {
		     //   flagDominate = CONSTRAINT_VIOLATION_COMPARATOR.compare(solutionList.get(p), solutionList.get(q));}
		      if (flagDominate == -1) {
		        iDominate.get(p).add(q);
		        dominateMe[q]++;
		      } else if (flagDominate == 1) {
		        iDominate.get(q).add(p);
		        dominateMe[p]++;
		      }//else if(flagDominate == 0) {
		    	//  iDominate.get(p).add(q);
			   //   dominateMe[q]++;
		    //  }
		    }
		  }

		  
		  
		  for (int i = 0; i < population.size(); i++) {
		    if (dominateMe[i] == 0) {
		      front.get(0).add(i);
		      solutionList.get(i).getSolution(1).attributes().put(attributeId, 0);
		    //  System.out.println("sol: -> " +i+" "+ solutionList.get(i).getSolution(1).attributes().get(attributeId));
		    }
		  
		  }
		  
		  

		  // Obtain the rest of fronts
		  int i = 0;
		  Iterator<Integer> it1, it2; // Iterators
		  while (!front.get(i).isEmpty()) {
		    i++;
		    it1 = front.get(i - 1).iterator();
		    while (it1.hasNext()) {
		      it2 = iDominate.get(it1.next()).iterator();
		      while (it2.hasNext()) {
		        int index = it2.next();
		        dominateMe[index]--;
		        if (dominateMe[index] == 0) {
		          front.get(i).add(index);
		          solutionList.get(index).getSolutionSet().get(1).attributes().put(attributeId, (Object)i);
		        }
		      }
		    }
		  }
		  
		//  for(int x=0; x<front.size(); x++)
		//	  System.out.println("front: -> " +x+" "+ front.get(x));
		
	    rankedSubPopulations = new ArrayList<>();
	    // 0,1,2,....,i-1 are fronts, then i fronts
		for (int j = 0; j < i; j++) {
		    rankedSubPopulations.add(j, new ArrayList<S>(front.get(j).size()));
		    it1 = front.get(j).iterator();
		    while (it1.hasNext()) {
		      rankedSubPopulations.get(j).add(solutionList.get(it1.next()));
		    }
		}
	//	System.out.println(rankedSubPopulations.size());
		return this;
	}

	@Override
	public List<S> getSubFront(int rank) {
	   //System.out.println(rankedSubPopulations.size());
	    if(rank >= rankedSubPopulations.size()) {
	    	throw new ArithmeticException("Invalid rank: " + rank + ". Max rank = " + (rankedSubPopulations.size() - 1));
	    }
	   // System.out.println(rankedSubPopulations.size());
	    return (List<S>) rankedSubPopulations.get(rank);
	 }

	@Override
	public int getNumberOfSubFronts() {
	  return rankedSubPopulations.size();
	}
	  
	@Override
	public Integer getRank(S solution) {
		 
		if ((solution == null)) {
		    throw new IllegalArgumentException("No solution provided: " );
		}

	    
	    Integer result = -1;
	    if (solution.getSolution(1).attributes().get(attributeId) != null) {
	      result = (Integer) solution.getSolution(1).attributes().get(attributeId);
	    }
	    return result;
	}

	@Override
	public Object getAttributedId() {
	  return attributeId;
	}
	  
}

	
