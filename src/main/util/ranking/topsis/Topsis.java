package main.util.ranking.topsis;

import java.io.BufferedWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;

import main.solution.solution;
import main.solution.solutionPair;
import main.solution.solutionSet;
import main.util.ranking.Criteria;

/*
 * This is an implementation of the MCDM (Multi-Criteria Decision Making) solution using
 * TOPSIS (Technique for Order of Preference by Similarity to Ideal Solution) algorithm
 * 
 * See TopsisTest test class for example of usage
 *  
 * @author danigpam
 * https://github.com/danigpam
 * 
 */

public class Topsis {
	
	
	private List<solutionSet> solutionpairs;
	
	//Attributes below are all populated from calculateOptimalSolution method
	private List<Criteria> criteria;
	private int numberOfPopulation = 0;
	private int numberOfCriteria = 0;
	private double[][] scoreMatrix;
	private double[][] normalizedDecisionMatrix;
	private double[] idealBest;
	private double[] idealWorst;
	private double[] distancesFromIdealWorst;
	private double[] distancesFromIdealBest;

	public Topsis(List<solutionSet> solutionpairs, List<Criteria> criteria) {
		super();
		this.solutionpairs = solutionpairs;
		this.criteria = criteria;
	}

	public Topsis() {
		super();
	}
	
	public void addAlternative(solutionSet eachSolution) {
		if (eachSolution == null) {
			solutionpairs = new ArrayList<solutionSet>();
		}
		this.solutionpairs.add(eachSolution);
	}
	
	public List<solutionSet> getSolutionPairs() {
		return solutionpairs;
	}

	/**
	 * Given the list of alternatives, each populated with a list of criteria values,
	 * this method will find the optimal solution, that is, which alternative
	 * has the highest closeness score to the ideal solution
	 *
	 * @return List of Alternative
	 * @throws TopsisIncompleteAlternativeDataException
	 */
	public List<solutionSet> calculateOptimalSolutionSortedList() throws TopsisIncompleteAlternativeDataException {

		validateData();
		populateScoreMatrix();
		calculateNormalizedDecisionMatrix();
		findIdealBestAndWorst();
		calculateDistancesFromIdealBestAndWorst();
		calculatePerformanceScore();
		sortSolutionPairsByPerformanceScoreDesc();
		//System.out.println(solutionpairs.get(0).getSolutionSet().get(1).attributes());
		return solutionpairs; // Sorted result from the ideal solution to the worse one.
	}

	/**
	 * Given the list of alternatives, each populated with a list of criteria values, 
	 * this method will find the optimal solution, that is, which alternative
	 * has the highest closeness score to the ideal solution
	 *
	 * @return Alternative
	 * @throws TopsisIncompleteAlternativeDataException
	 */
	public solutionSet calculateOptimalSolution() throws TopsisIncompleteAlternativeDataException {
		return this.calculateOptimalSolutionSortedList().get(0); // Top solution after sorting is the best score
	}

	/**
	 * Write alternatives into CSV file
	 *
	 * @Parameters the File path & the filename
	 * @return void
	 * @throws Exception
	 */

	private void validateData() throws TopsisIncompleteAlternativeDataException {
		if (!hasIncompleteSolutionPairCriteria() && !hasNullOrEmptySolutionpairs()) {
			throw new TopsisIncompleteAlternativeDataException();
		}
	}
	
	private boolean hasNullOrEmptySolutionpairs() {
		return solutionpairs == null || solutionpairs.size() == 0;
	}

	@SuppressWarnings("unused")
	private boolean hasIncompleteSolutionPairCriteria() {
		//criteria = new ArrayList<Criteria>();
		for (solutionSet eachsolutionpair : solutionpairs) {
			//Alternatives cannot have null criteria values
			if (null == eachsolutionpair) {
				return false;
			}
			int criteriasize=0;
			
			for(Entry<Integer, solution<?>> sol: eachsolutionpair.getSolutionSet().entrySet()) {
				if(null!=sol) {
					
					criteriasize += sol.getValue().getFitness().length;
					criteriasize += sol.getValue().getConstraints().length;
				}
			}
			//Other alternatives must match same criteria list size
			if (criteriasize != criteria.size()) {
				return false;
			}
		}
		return true;
	}
	
	private void populateScoreMatrix() {
		numberOfPopulation = solutionpairs.size();
		numberOfCriteria = criteria.size();
		
		scoreMatrix = new double[numberOfPopulation][numberOfCriteria];
		normalizedDecisionMatrix = new double[numberOfPopulation][numberOfCriteria];
		
		int rowIndex = 0;		
		for (solutionSet eachsolutionpair : solutionpairs) {
			int columnIndex = 0;
			for(Entry<Integer, solution<?>> sol: eachsolutionpair.getSolutionSet().entrySet()) {
				if(null!=sol) {
					double[] fitness = sol.getValue().getFitness();
					for (Double val : fitness) {
						scoreMatrix[rowIndex][columnIndex] = val;
						columnIndex++;
					}
					double[] con = sol.getValue().getConstraints();
					for (Double val : con) {
						scoreMatrix[rowIndex][columnIndex] = val;
						columnIndex++;
					}
				}
			}
			rowIndex++;
		}
	}

	private void calculateNormalizedDecisionMatrix() {
		
		for (int c = 0; c < numberOfCriteria; c++) {
			//if(criteria.get(c).getWeight()==0)continue;
			double divisor = 0;
			for (int a = 0; a < numberOfPopulation; a++) {
				divisor += scoreMatrix[a][c] * scoreMatrix[a][c]; //sum the squares
			}
			divisor = Math.pow(divisor, 0.5); //square root of the sum
		//	System.out.println(divisor);
			
			for (int a = 0; a < numberOfPopulation; a++) {
				if(divisor==0.0) {
					double normalizedValue = 0.0; //divide original value by the sqrt of the sum
					normalizedValue = normalizedValue * criteria.get(c).getWeight(); //apply criteria weight
					normalizedDecisionMatrix[a][c] = 0.0;
					
				}else {
					double normalizedValue = scoreMatrix[a][c] / divisor; //divide original value by the sqrt of the sum
					normalizedValue = normalizedValue * criteria.get(c).getWeight(); //apply criteria weight
					normalizedDecisionMatrix[a][c] = normalizedValue;
					
				}	
			}
		}
		
		//printMatrix(normalizedDecisionMatrix);
	}
	
	private void findIdealBestAndWorst() {

		idealBest = new double[numberOfCriteria];
		idealWorst = new double[numberOfCriteria];
		
		for (int c = 0; c < numberOfCriteria; c++) {
			double minValue = Double.MAX_VALUE;
			double maxValue = 0;
			
			for (int a = 0; a < numberOfPopulation; a++) {

				if ( normalizedDecisionMatrix[a][c] > maxValue ) {
					maxValue = normalizedDecisionMatrix[a][c];
				}
				if ( normalizedDecisionMatrix[a][c] < minValue ) {
					minValue = normalizedDecisionMatrix[a][c];
				}
			}
			
			//If negative, the ideal best is the min value
			//Otherwise, ideal best is the max value
			if (criteria.get(c).isNegative()) {
				idealBest[c] = minValue;
				idealWorst[c] = maxValue;
			} else {
				idealBest[c] = maxValue;
				idealWorst[c] = minValue;
			}
			//System.out.println(idealBest[c]+ " "+idealWorst[c]);
		}
		
		//printIdealBestAndWorst();
	}
	

	private void calculateDistancesFromIdealBestAndWorst() {
		
		distancesFromIdealBest = new double[numberOfPopulation];
		distancesFromIdealWorst = new double[numberOfPopulation];
		
		for (int a = 0; a < numberOfPopulation; a++) {
			double distanceFromBest = 0;
			double distanceFromWorst = 0;
			
			for (int c = 0; c < numberOfCriteria; c++) {

				double squareOfDifferenceFromBest = normalizedDecisionMatrix[a][c] - idealBest[c]; //subtract from ideal best
				squareOfDifferenceFromBest = squareOfDifferenceFromBest * squareOfDifferenceFromBest; //calculate square
				distanceFromBest += squareOfDifferenceFromBest; //sum squares for all criteria
				
				//System.out.println(normalizedDecisionMatrix[a][c] + " "+idealBest[c]+ " "+idealWorst[c]);
				

				double squareOfDifferenceFromWorst = normalizedDecisionMatrix[a][c] - idealWorst[c]; //subtract from ideal worst
				squareOfDifferenceFromWorst = squareOfDifferenceFromWorst * squareOfDifferenceFromWorst; //calculate square
				distanceFromWorst += squareOfDifferenceFromWorst; //sum squares for all criteria
			}

			
			
			distancesFromIdealBest[a] = Math.pow(distanceFromBest, 0.5); //square root of sum
			distancesFromIdealWorst[a] = Math.pow(distanceFromWorst, 0.5); //square root of sum
		}
	}

	private void calculatePerformanceScore() {

		for (int i = 0; i < numberOfPopulation; i++) {
			double performanceScore = distancesFromIdealWorst[i] / (distancesFromIdealBest[i] + distancesFromIdealWorst[i]);
			//alternatives.get(a).setCalculatedPerformanceScore(performanceScore);
			//System.out.println(performanceScore);
			solutionpairs.get(i).getSolutionSet().get(1).attributes().put("Performance", performanceScore);
			//System.out.println(solutionpairs.get(i).getSolutionSet().get(1).attributes());
		}
	}

	@SuppressWarnings("unused")
	private void sortSolutionPairsByPerformanceScoreDesc() {

		Collections.sort(solutionpairs, new Comparator<solutionSet>() {
			
		/**	public int compare(solutionPair a1, solutionPair a2) {
				//a2.getSolutionSet().get(1).attributes().get("Performance")
				return Double.compare((Double)a2.getParentSolution().attributes().get("Performance"), (Double)a1.getParentSolution().attributes().get("Performance"));
			} **/

			@Override
			public int compare(solutionSet o1, solutionSet o2) {
				// TODO Auto-generated method stub
				return Double.compare((Double)(o2.getSolutionSet().get(1).attributes().get("Performance")), (Double)(o1.getSolutionSet().get(1).attributes().get("Performance")));
			}
		});
	}

	@SuppressWarnings("unused")
	private void printMatrix(double[][] matrix) {

		for (double[] row : matrix) {

			System.out.print(" [ ");
			for (double columnValue : row) {
				System.out.print(columnValue + " ");
			}
			System.out.println(" ] ");
		}
		
	}
	
	@SuppressWarnings("unused")
	private void printIdealBestAndWorst() {

		System.out.print("Ideal best is : [ ");
		for (double d : idealBest) {
			System.out.print(d + " ");
		}
		System.out.println("]");
		
		System.out.print("Ideal worst is : [ ");
		for (double d : idealWorst) {
			System.out.print(d + " ");
		}
		System.out.println("]");
	}
}
