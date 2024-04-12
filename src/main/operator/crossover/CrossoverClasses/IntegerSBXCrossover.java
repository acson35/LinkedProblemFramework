package main.operator.crossover.CrossoverClasses;

import java.util.ArrayList;
import java.util.List;

import main.operator.crossover.CrossoverOperator;
import main.solution.integerSolution.IntegerSolution;
import main.util.createRandomGeneration.LinkedRandom;
import main.util.range.Ranges;

@SuppressWarnings("serial")
public class IntegerSBXCrossover implements CrossoverOperator<IntegerSolution>{

	private static final double EPS = 1.0e-14;

	private double distributionIndex ;
	private double crossoverProbability  ;

	private LinkedRandom randomGenerator;

	
	public IntegerSBXCrossover(double crossoverProbability, double distributionIndex) {
	  this(crossoverProbability, distributionIndex, LinkedRandom.getInstance());
	}

	public IntegerSBXCrossover(double crossoverProbability, double distributionIndex, LinkedRandom randomGenerator) {
	  if (crossoverProbability < 0) {
	    throw new ArithmeticException("Crossover probability is negative: " + crossoverProbability) ;
	  } else if (distributionIndex < 0) {
	    throw new ArithmeticException("Distribution index is negative: " + distributionIndex);
	  }

	  this.crossoverProbability = crossoverProbability ;
	  this.distributionIndex = distributionIndex ;
	  this.randomGenerator = randomGenerator ;
	}
	
	
	@Override
	public List<IntegerSolution> execute(List<IntegerSolution> parents) {
		
		try {
			if(parents.size() == 2)
				return doCrossover(crossoverProbability, parents.get(0), parents.get(1));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public double getCrossoverProbability() {
		return crossoverProbability;
	}
	
	public double getDistributionIndex() {
	    return distributionIndex;
	}

	public void setDistributionIndex(double distributionIndex) {
	   this.distributionIndex = distributionIndex;
	}

	public void setCrossoverProbability(double crossoverProbability) {
	    this.crossoverProbability = crossoverProbability;
	}

	@Override
	public int getNumberOfRequiredParents() {
		return 2;
	}

	@Override
	public int getNumberOfGeneratedChildren() {
		return 2;
	}
	
	public List<IntegerSolution> doCrossover(
	        double probability, IntegerSolution parent1, IntegerSolution parent2) {
	  List<IntegerSolution> offspring = new ArrayList<IntegerSolution>(2);

	  offspring.add((IntegerSolution) parent1.copy()) ;
	  offspring.add((IntegerSolution) parent2.copy()) ;
	  
	  int i;
	  double rand;
	  double y1, y2, yL, yu;
	  double c1, c2;
	  double alpha, beta, betaq;
	  //List<Integer> arrVal1;
	  //List<Integer> arrVal2;
	  int valueX1, valueX2;
	  
	  if (randomGenerator.nextDouble() <= probability) {
	      for (i = 0; i < parent1.variables().size(); i++) {
	    	  
	    	  List<Integer> arrVal1 = new ArrayList<Integer>();
	    	  
	    	  for(int k =0; k < parent1.variables().get(i).size(); k++) {
	    		  int val = parent1.variables().get(i).get(k);
	    		  if(val>0) {
	    			  arrVal1.add(val);
	    		  }
	    	  }
	    	 // System.out.println(arrVal1);
	    	  List<Integer> arrVal2 = new ArrayList<Integer>();
	    	  
	    	  for(int k =0; k < parent2.variables().get(i).size(); k++) {
	    		  int val = parent2.variables().get(i).get(k);
	    		  if(val>0) {
	    			  arrVal2.add(val);
	    		  }
	    	  }
			  
	    	  if(parent1.variables().get(i).isEmpty())continue;
	    	  if(parent2.variables().get(i).isEmpty())continue;
	    	  for(int j=0; j < parent1.variables().get(i).size(); j++) {
	    		  //System.out.println(parent1.variables().get(i));
	    		  //System.out.println(parent2.variables().get(i));
	    		  if(0==parent1.variables().get(i).get(j) || 0==parent2.variables().get(i).get(j))continue;
	    		  valueX1 = parent1.variables().get(i).get(j);
		    	  valueX2 = parent2.variables().get(i).get(j);
		    	  
		    	  if (randomGenerator.nextDouble() <= 0.5) {
		    		  if (Math.abs(valueX1 - valueX2) > EPS) {

		    			  if (valueX1 < valueX2) {
		    				  y1 = valueX1;
		    				  y2 = valueX2;
		    			  } else {
		    				  y1 = valueX2;
		    				  y2 = valueX1;
		    			  }
		    			  
		    			  
		    			  int min1=arrVal1.get(0);
		    			  int max1=arrVal1.get(0);
		    			  for (int k = 1; k < arrVal1.size(); k++) {
		    				min1 = Math.min(min1, arrVal1.get(k));
			    			max1 = Math.max(max1, arrVal1.get(k)); 
		    			  }
		    			  int min2=arrVal2.get(0);
		    			  int max2=arrVal2.get(0);
		    			  for (int k = 1; k < arrVal2.size(); k++) {
		    				 min2 = Math.min(min2, arrVal2.get(k));
			    			 max2 = Math.max(max2, arrVal2.get(k));
		    			   }
		    			 
		    			  
		    			  
		    			  //Ranges<Integer> bounds = parent1.getRange(i);
		    			  //Ranges<Integer> bounds = new Ranges<Integer>(min,max);
		    			  
		    			  yL = Math.min(min1, min2);
		    			  yu = Math.max(max1, max2);
		    			  rand = randomGenerator.nextDouble();
		    			  beta = 1.0 + (2.0 * (y1 - yL) / (y2 - y1));
		    			  alpha = 2.0 - Math.pow(beta, -(distributionIndex + 1.0));

		    			  if (rand <= (1.0 / alpha)) {
		    				  betaq = Math.pow((rand * alpha), (1.0 / (distributionIndex + 1.0)));
		    			  } else {
		    				  betaq = Math
		    						  .pow(1.0 / (2.0 - rand * alpha), 1.0 / (distributionIndex + 1.0));
		    			  }

		    			  c1 = 0.5 * ((y1 + y2) - betaq * (y2 - y1));
		    			  beta = 1.0 + (2.0 * (yu - y2) / (y2 - y1));
		    			  alpha = 2.0 - Math.pow(beta, -(distributionIndex + 1.0));

		    			  if (rand <= (1.0 / alpha)) {
		    				  betaq = Math.pow((rand * alpha), (1.0 / (distributionIndex + 1.0)));
		    			  } else {
		    				  betaq = Math
		    						  .pow(1.0 / (2.0 - rand * alpha), 1.0 / (distributionIndex + 1.0));
		    			  }

		    			  c2 = 0.5 * (y1 + y2 + betaq * (y2 - y1));

		    			  if (c1 < yL) {
		    				  c1 = yL;
		    			  }

		    			  if (c2 < yL) {
		    				  c2 = yL;
		    			  }

		    			  if (c1 > yu) {
		    				  c1 = yu;
		    			  }

		    			  if (c2 > yu) {
		    				  c2 = yu;
		    			  }

		    			  if (randomGenerator.nextDouble() <= 0.5) {
		    				  if(arrVal1.contains((int)c2) && arrVal2.contains((int)c2)) {
		    					  offspring.get(0).variables().get(i).set(j, (int)c2);
			    				 // offspring.get(1).variables().get(i).set(j, (int)c1);
		    				  }
		    				  if(arrVal1.contains((int)c1) && arrVal2.contains((int)c1)) {
		    					 // offspring.get(0).variables().get(i).set(j, (int)c2);
			    				  offspring.get(1).variables().get(i).set(j, (int)c1);
		    				  }
		    				  
		    			  } else {
		    				  if(arrVal1.contains((int)c2) && arrVal2.contains((int)c2)) {
		    					  //offspring.get(0).variables().get(i).set(j, (int)c1);
			    				  offspring.get(1).variables().get(i).set(j, (int)c2);
		    				  }
		    				  if(arrVal1.contains((int)c1) && arrVal2.contains((int)c1)) {
		    					  offspring.get(0).variables().get(i).set(j, (int)c1);
			    				 // offspring.get(1).variables().get(i).set(j, (int)c2);
		    				  }
		    			  }
		    		  } else {
		    			  if(arrVal1.contains(valueX1) && arrVal2.contains(valueX1)) {
		    				  offspring.get(0).variables().get(i).set(j, valueX1);
			    			 // offspring.get(1).variables().get(i).set(j, valueX2);
	    				  }
		    			  if(arrVal1.contains(valueX2) && arrVal2.contains(valueX2)) {
		    				 // offspring.get(0).variables().get(i).set(j, valueX1);
			    			  offspring.get(1).variables().get(i).set(j, valueX2);
	    				  }
		    		  }
		    	  } else {
		    		  if(arrVal1.contains(valueX1) && arrVal2.contains(valueX1)) {
	    				 // offspring.get(0).variables().get(i).set(j, valueX2);
		    			  offspring.get(1).variables().get(i).set(j, valueX1);
    				  }
		    		  if(arrVal1.contains(valueX2) && arrVal2.contains(valueX2)) {
	    				  offspring.get(0).variables().get(i).set(j, valueX2);
		    			 // offspring.get(1).variables().get(i).set(j, valueX1);
    				  }
		    	  }
	    	  }
	      }
	    }

	 // System.out.println(offspring.get(0));
	  
	  
	  
	    return offspring;
	  }


}
