package main.util.ranking.AHP;

public class UniqueSolution {

	 private double[] comArrayValues;
	 
	 UniqueSolution(AHP ahp){
	     comArrayValues = ahp.getPairwiseComparisonArray();
	 }

	 void setCriteriaImportance(double[] compArray){
	     System.arraycopy(compArray, 0, comArrayValues, 0, compArray.length);
	 }

	 public double[] getComArrayValues() {
	     return comArrayValues;
	 }

	   
}
