package main.util.constraintHandling;

import main.solution.solution;
import main.solution.solutionPair;
import main.solution.solutionSet;

public class ConstraintHandling {

	
	public static boolean isFeasible(solution<?> newIndividual) {
	    return numberOfViolatedConstraints(newIndividual) == 0 ;
	 }

	public static <S extends solution<?>> boolean isFeasible(solutionSet linkedsolutions) {
	    return numberOfViolatedConstraints(linkedsolutions) == 0 ;
	 }
	  
	public static int numberOfViolatedConstraints(solution<?> solution) {
	    int result = 0 ;
	    for (int i = 0; i < solution.getConstraints().length; i++) {
	      if (solution.getConstraints()[i] > 0) { 
	        result ++ ;
	      }
	    }

	    return result ;
	 }
	
	@SuppressWarnings("unchecked")
	public static int numberOfViolatedConstraints(solutionSet linkedsolutions) {
	    int result = 0 ;
	    for(int k = 0; k < linkedsolutions.getSolutionSet().size(); k++) {
	    	solution<?> parentSolution =  linkedsolutions.getSolutionSet().get(k);
	    	for (int i = 0; i < parentSolution.getConstraints().length; i++) {
	  	      if (parentSolution.getConstraints()[i] > 0) { 
	  	        result ++ ;
	  	      }
	  	    }
	    }
	    return result ;
	 }
	
	
	public static <S extends solution<?>> double overallConstraintViolationDegree(S solution) {
	    double overallConstraintViolation = 0.0;
	    for (int i = 0; i < solution.getConstraints().length; i++) {
	      if (solution.getConstraints()[i] > 0.0) {
	        overallConstraintViolation += solution.getConstraints()[i] ;
	      }
	    }
	    return overallConstraintViolation ;
	}

	@SuppressWarnings("unchecked")
	public static double overallConstraintViolationDegree2(solutionSet solution) {
		    double overallConstraintViolation = 0.0;
		    for(int k = 0; k < solution.getSolutionSet().size(); k++) {
		    	solution<?> parentSolution =  solution.getSolutionSet().get(k+1);
		    	for (int i = 0; i < parentSolution.getConstraints().length; i++) {
		  	      if (parentSolution.getConstraints()[i] > 0) { 
		  	    	overallConstraintViolation += parentSolution.getConstraints()[i] ;
		  	      }
		  	    }
		    }
		    return overallConstraintViolation ;
	  }

}
