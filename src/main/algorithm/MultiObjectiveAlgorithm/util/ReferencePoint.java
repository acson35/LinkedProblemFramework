package main.algorithm.MultiObjectiveAlgorithm.util;

import main.solution.solution;
import main.solution.solutionSet;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;

import main.util.createRandomGeneration.LinkedRandom;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class ReferencePoint<S extends solutionSet> {
	
	 public List<Double> position ;
	 private int memberSize ;
	 private List<Pair<S, Double>> potentialMembers ;
	 
	 public ReferencePoint() {
		 
	 }
	 
	 public ReferencePoint(int size) {
		 position = new ArrayList<>();
		 for (int i =0; i < size; i++)
		      position.add(0.0);
		 memberSize = 0 ;
		 potentialMembers = new ArrayList<>();  
	 }
	 
	 
	 public ReferencePoint(ReferencePoint<S> point) {
		 position = new ArrayList<>(point.position.size());
		 for (Double d : point.position) {
		      position.add(d);
		 }
		 memberSize = 0 ;
		 potentialMembers = new ArrayList<>(); 
	}
	 
	 public void generateReferencePoints(
	          List<ReferencePoint<S>> referencePoints,
	          int numberOfObjectives,
	          int numberOfDivisions) {

	    ReferencePoint<S> refPoint = new ReferencePoint<>(numberOfObjectives) ;
	    generateRecursive(referencePoints, refPoint, numberOfObjectives, numberOfDivisions, numberOfDivisions, 0);
	 }
	 
	 private void generateRecursive(
	          List<ReferencePoint<S>> referencePoints,
	          ReferencePoint<S> refPoint,
	          int numberOfObjectives,
	          int left,
	          int total,
	          int element) {
	    if (element == (numberOfObjectives - 1)) {
	      refPoint.position.set(element, (double) left / total) ;
	      referencePoints.add(new ReferencePoint<>(refPoint)) ;
	    } else {
	      for (int i = 0 ; i <= left; i +=1) {
	        refPoint.position.set(element, (double)i/total) ;

	        generateRecursive(referencePoints, refPoint, numberOfObjectives, left-i, total, element+1);
	      }
	    }
	  }
	 
	 public List<Double> pos()  { return this.position; }
	 public int  MemberSize(){ return memberSize; }
	 public boolean HasPotentialMember() { return potentialMembers.size()>0; }
	 public void clear(){ memberSize=0; this.potentialMembers.clear();}
	 public void AddMember(){this.memberSize++;}
	 public void AddPotentialMember(S member_ind, double distance){
	    this.potentialMembers.add(new ImmutablePair<S,Double>(member_ind,distance) );
	  }

	  public void sort() {
	    this.potentialMembers.sort(Comparator.comparing(Pair<S, Double>::getRight).reversed());
	  }

	  public S FindClosestMember() {
	    return this.potentialMembers.remove(this.potentialMembers.size() - 1)
	            .getLeft();
	  }
	  
	  public S RandomMember() {
		 int index = this.potentialMembers.size()>1 ? LinkedRandom.getInstance().nextInt(0, this.potentialMembers.size()-1):0;
	    return this.potentialMembers.remove(index).getLeft();
	  }
	


}
