package main.util.densityEstimator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import main.solution.solution;
import main.solution.solutionPair;
import main.solution.solutionSet;
import main.util.comparator.DominanceComparator;
import main.util.comparator.DominancePairComparator;
import main.util.comparator.ObjectiveComparator;
import main.util.comparator.ObjectiveSetComparator;

public class CrowdingDistanceDensityEstimator<S extends solutionSet> implements DensityEstimator<S> {

	  private final String attributeId = getClass().getName();

	  /**
	   * Assigns crowding distances to all population in a <code>SolutionSet</code>.
	   *
	   * @param solutionList The <code>SolutionSet</code>.
	   */

	  @SuppressWarnings({ "rawtypes", "unchecked" })
	  @Override
	  public void compute(List<S> solutionList) {
		  int size = solutionList.size();
		 // System.out.println(size);
		    if (size == 0) {
		      return;
		    }

		    if (size == 1) {
		      
		    	solutionList.get(0).getSolutionSet().get(1).attributes().put(attributeId, Double.POSITIVE_INFINITY);
		    	//solutionList.get(0).getParentSolution().attributes().put(attributeId, Double.POSITIVE_INFINITY);
		      return;
		    }

		    if (size == 2) {
		    	solutionList.get(0).getSolutionSet().get(1).attributes().put(attributeId, Double.POSITIVE_INFINITY);
		    	solutionList.get(1).getSolutionSet().get(1).attributes().put(attributeId, Double.POSITIVE_INFINITY);
		      return;
		    }

		    // Use a new SolutionSet to avoid altering the original solutionSet
		    List<S> front = new ArrayList<>(solutionList);
		    //System.out.println("Checking");
		    for (int i = 0; i < size; i++) {
		    	front.get(i).getSolutionSet().get(1).attributes().put(attributeId, 0.0);
		    }
		    
		   // int numberOfObjectives = 0;
		    
		    for(Integer prob:solutionList.get(0).getSolutionSet().keySet()) {
		    	
		    	int len = solutionList.get(0).getSolution(prob).getFitness().length;
		    	
		    	for(int i=0; i<len; i++) {
		    		
		    		//numberOfObjectives ++;
		    		
		    		front.sort(new ObjectiveSetComparator(prob, i));
		    		
		    		double minObjective = front.get(0).getSolution(prob).getFitness()[i];
				    double maxObjective = front.get(front.size() - 1).getSolution(prob).getFitness()[i];
				    
				    if (minObjective == maxObjective) {
				        continue; // otherwise all crowding distances will be NaN = 0.0 / 0.0 except for two
				    }

				    // Set the crowding distance for the extreme points
				    front.get(0).getSolution(1).attributes().put(attributeId, Double.POSITIVE_INFINITY);
				    front.get(size - 1).getSolution(1).attributes().put(attributeId, Double.POSITIVE_INFINITY);

				    // Increase the crowding distances for all the intermediate points
				    for (int j = 1; j < size - 1; j++) {
				      double distance = front.get(j + 1).getSolution(prob).getFitness()[i] - front.get(j - 1).getSolution(prob).getFitness()[i];
				      distance = distance / (maxObjective - minObjective);
				      distance += (double) front.get(j).getSolution(1).attributes().get(attributeId);
				      front.get(j).getSolution(1).attributes().put(attributeId, distance);
				     // System.out.println(distance);
				    }
		    		
		    		
		    		
		    	}
		    	
		    	
		    }
		    

		  /**
		      
		    // System.out.println(front.size());
		     // double minObjective = front.get(0).getSolutionSet().get(1).getFitness()[0];
		     // double maxObjective = front.get(front.size() - 1).getSolutionSet().get(1).getFitness()[0];
		      
		     // System.out.println(minObjective + " " + maxObjective);

		      // Set the crowding distance for the extreme points
		      front.get(0).getSolutionSet().get(1).attributes().put(attributeId, Double.POSITIVE_INFINITY);
		      front.get(size - 1).getSolutionSet().get(1).attributes().put(attributeId, Double.POSITIVE_INFINITY);

		      // Increase the crowding distances for all the intermediate points
		      List<Double> minObj = new ArrayList<>();
		      List<Double> maxObj = new ArrayList<>();
		      for(int a = 0; a < front.get(0).getSolutionSet().size(); a++) {
		    	  int num = front.get(0).getSolutionSet().get(a+1).getFitness().length;
			      for(int i = 0; i < num; i++) {
			    	  double minval = front.get(0).getSolutionSet().get(a+1).getFitness()[i];
			    	  double maxval = front.get(0).getSolutionSet().get(a+1).getFitness()[i];
			    	  for(int j=0; j<size; j++) {
			    		  if(front.get(j).getSolutionSet().get(a+1).getFitness()[i] < minval) {
			    			  minval = front.get(j).getSolutionSet().get(a+1).getFitness()[i];
			    		  }
			    		  if(front.get(j).getSolutionSet().get(a+1).getFitness()[i] > maxval) {
			    			  maxval = front.get(j).getSolutionSet().get(a+1).getFitness()[i];
			    		  }
			    	  }
			    	  //System.out.println(minval + " " + maxval);

			    	  minObj.add(minval);
			    	  maxObj.add(maxval);
			      }
		      }
		      for(int a = 0; a < front.get(0).getSolutionSet().size(); a++) {
		    	  int num = front.get(0).getSolutionSet().get(a+1).getFitness().length;
		    	  int count=0;
		    	  for(int i = 0; i < num; i++) {
			    	  for (int j = 1; j < size - 1; j++) {
					    	 if (minObj.get(count) == maxObj.get(count)) {
							        continue; // otherwise all crowding distances will be NaN = 0.0 / 0.0 except for two
							 }
					        double distance = front.get(j + 1).getSolutionSet().get(a+1).getFitness()[i] - front.get(j - 1).getSolutionSet().get(1).getFitness()[i];
					        distance = distance / (maxObj.get(count) - minObj.get(count));
					        distance += (double) front.get(j).getSolutionSet().get(1).attributes().get(attributeId);
					        front.get(j).getSolutionSet().get(1).attributes().put(attributeId, distance);
					   }
			    	  count++;
			      }
		      }**/
		  /**    for (int j = 0; j < size; j++) {
		    	  
		    	  for(int k = 1; k < size-1; k++) {
		    		  double distance = front.get(k + 1).getSolutionSet().get(j).getFitness()[0] - front.get(k - 1).getSolutionSet().get(1).getFitness()[0];
				      distance = distance / (maxObjective - minObjective);
				      distance += (double) front.get(k).getSolutionSet().get(j).attributes().get(attributeId);
				      front.get(k).getSolutionSet().get(j).attributes().put(attributeId, distance); 
		    	  }
			       // System.out.println(distance);
			  } **/  
	  }

	  @Override
	  public Double getValue(S pairedsolution) {
	    
	    if(null==pairedsolution) {
	    	throw new NullPointerException("Null parameter");
	    }

	    Double result = 0.0 ;
	    if (pairedsolution.getSolutionSet().get(1).attributes().get(attributeId) != null) {
	      result = (Double) pairedsolution.getSolutionSet().get(1).attributes().get(attributeId) ;
	    }
	    return result ;
	  }
	  
	  @Override
	  public Comparator<S> getComparator() {
	    return Comparator.comparing(this::getValue).reversed() ;
	  }

	
	  
	 
}
