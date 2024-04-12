package main.util.ranking.promethee;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;

import main.solution.solution;
import main.solution.solutionSet;
import main.util.ranking.Criteria;
import main.util.ranking.topsis.TopsisIncompleteAlternativeDataException;

public class Promethee_II {
	
	private List<solutionSet> solutionpairs;
	//Attributes below are all populated from calculateOptimalSolution method
	private List<Criteria> criteria;
	private int numberOfPopulation = 0;
	private int numberOfCriteria = 0;
	private double[][] scoreMatrix;
	private double[][] normalizedDecisionMatrix;
	private double[][] evaluationDiff;
	private double[] aggregatedPreferenceFunction;
	private double[][] outrankingFlowMatrix;
	private double[] leavingOutrankingFlow;
	private double[] enteringOutrankingFlow;
	private double[] netOutrankingFlow;
	private double[] rank;

	public Promethee_II(List<solutionSet> solutionpairs, List<Criteria> criteria) {
		super();
		this.solutionpairs = solutionpairs;
		this.criteria = criteria;
	}

	public Promethee_II() {
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
	
	
	public List<solutionSet> calculateOptimalSolutionSortedList() throws TopsisIncompleteAlternativeDataException {

		//validateData();
		//System.out.print("Seen ");
		populateScoreMatrix();
		calculateNormalizedDecisionMatrix();
		calculateEvaluationDifference();
		calculatePreferenceFunction();
		calculateAggregatedPreferenceFunction();
		calculateOutrankingFlowsMatrix();
		computeLeavingEnteringOutrankingFlows();
		computeNetOutrankingFlows();
		rank();
		rankingScore();
		sortSolutionPairsByRankingDesc();
		//System.out.println(solutionpairs.get(0).getSolutionSet().get(1).attributes());
		return solutionpairs; // Sorted result from the ideal solution to the worse one.
	}

	
	public solutionSet calculateOptimalSolution() throws TopsisIncompleteAlternativeDataException {
		
		return this.calculateOptimalSolutionSortedList().get(0); // Top solution after sorting is the best score
	}

	
	@SuppressWarnings("unused")
	public void populateScoreMatrix() {
		numberOfPopulation = solutionpairs.size();
		numberOfCriteria = criteria.size();
		
		scoreMatrix = new double[numberOfPopulation][numberOfCriteria];
		
		int rowIndex = 0;		
		for (solutionSet eachsolutionpair : solutionpairs) {
			int columnIndex = 0;
			for(Entry<Integer, solution<?>> sol: eachsolutionpair.getSolutionSet().entrySet()) {
				if(null!=sol) {
					double[] fitness = sol.getValue().getFitness();
					for (Double val : fitness) {
						scoreMatrix[rowIndex][columnIndex] = val;
						//System.out.print(val+ " ");
						columnIndex++;
					}
					double[] con = sol.getValue().getConstraints();
					for (Double val : con) {
						scoreMatrix[rowIndex][columnIndex] = val;
						//System.out.print(val+ " ");
						columnIndex++;
					}
				}
			}
			//System.out.println();
			rowIndex++;
		}
	}
	
	@SuppressWarnings("unused")
	public void calculateNormalizedDecisionMatrix() {
		
		normalizedDecisionMatrix = new double[numberOfPopulation][numberOfCriteria];
		
		for (int c = 0; c < numberOfCriteria; c++) {
			
			double[] val = new double[numberOfPopulation];
			for (int a = 0; a < numberOfPopulation; a++) {
				val[a] = scoreMatrix[a][c]; //sum the squares
				//System.out.print(val[a]);
			}
			double max = val[0];
			double min = val[0];

			for (int i = 1; i < val.length; i++) {
				if(val[i] == 0 && max == 0) {
					max = 0;
				}else {
					 max = Math.max(max, val[i]);
				}
				if(val[i] == 0 && min == 0) {
					min = 0;
				}else {
					min = Math.min(min, val[i]);
				} 
			}
			
			if(criteria.get(c).isNegative()) {
				for (int a = 0; a < numberOfPopulation; a++) {
					if(val[a] == 0.0) {
						normalizedDecisionMatrix[a][c] = 0.0;
					}else{
						normalizedDecisionMatrix[a][c] = (max - val[a])/(max - min);
					}
					
					//System.out.print(normalizedDecisionMatrix[a][c]+ " ");
				}	
			}else {
				for (int a = 0; a < numberOfPopulation; a++) {
					if(val[a] == 0.0) {
						normalizedDecisionMatrix[a][c] = 0.0;
					}else{
						normalizedDecisionMatrix[a][c] = (val[a] - min)/(max - min);
					}
					//System.out.print(normalizedDecisionMatrix[a][c]+ " ");
				}	
			}
			//System.out.println();
		}
		
	}
	
	
	@SuppressWarnings("unused")
	public void calculateEvaluationDifference() {
		
		//System.out.println("seen");
		int count = (int) numberOfPopulation*numberOfPopulation;
		//System.out.println(count);
		evaluationDiff = new double[count][numberOfCriteria];
		for (int c = 0; c < numberOfCriteria; c++) {
			int x = 0;
			for (int a = 0; a < numberOfPopulation; a++) {
				for (int b = 0; b < numberOfPopulation; b++) {
					
					evaluationDiff[x][c]  = (normalizedDecisionMatrix[a][c] - normalizedDecisionMatrix[b][c]);
				
					//System.out.println(evaluationDiff[x][c]);
					x++;
				}
			}
			//System.out.println(evaluationDiff[x-1][c] + " " + x);
			//System.out.println();
		}
		//System.out.println(numberOfCriteria);
	}
	
	@SuppressWarnings("unused")
	public void calculatePreferenceFunction() {
		
		for (int c = 0; c < numberOfCriteria ; c++) {
			for (int a = 0; a < evaluationDiff.length; a++) {
				double x = evaluationDiff[a][c];
				if(x < 0.0) {
					evaluationDiff[a][c] = 0.0;
				}
				//System.out.print(evaluationDiff[a][c] + " ");
			}
			
			//System.out.println();
		}
	}
	
	
	@SuppressWarnings("unused")
	public void calculateAggregatedPreferenceFunction() {
		
		aggregatedPreferenceFunction = new double[evaluationDiff.length];
		for (int a = 0; a < evaluationDiff.length; a++) {
			double aggregatedF = 0.0;
			double sum_criteria = 0.0;
			for (int c = 0; c < numberOfCriteria; c++) {
				double w = criteria.get(c).getWeight();
				double x = evaluationDiff[a][c];
				aggregatedF += w*x;
				sum_criteria += w;
			}
			if(aggregatedF == 0.0) {
				aggregatedPreferenceFunction[a] = 0.0;
			}else {
				aggregatedPreferenceFunction[a] = aggregatedF/sum_criteria;
			}
			//System.out.println(aggregatedPreferenceFunction[a] + " ");
		}
	}
	
	@SuppressWarnings("unused")
	public void calculateOutrankingFlowsMatrix() {
		
		outrankingFlowMatrix = new double[numberOfPopulation][numberOfPopulation];
		int idx = 0;
		for (int a = 0; a < numberOfPopulation; a++) {
			for (int b = 0; b < numberOfPopulation; b++) {
				outrankingFlowMatrix[a][b] = aggregatedPreferenceFunction[idx];
				//System.out.print(aggregatedPreferenceFunction[idx]);
				idx++;
			}
			//System.out.println();
		}
	}
	
	@SuppressWarnings("unused")
	public void computeLeavingEnteringOutrankingFlows() {
		leavingOutrankingFlow = new double[numberOfPopulation];
		enteringOutrankingFlow = new double[numberOfPopulation];
	
		double leaving = 0;
		double entering = 0;
		for (int a = 0; a < numberOfPopulation; a++) {
			for (int b = 0; b < numberOfPopulation; b++) {
				leaving += outrankingFlowMatrix[a][b];
			}
			leavingOutrankingFlow[a] = leaving/(numberOfPopulation-1);
		}
		
		for (int a = 0; a < numberOfPopulation; a++) {
			for (int b = 0; b < numberOfPopulation; b++) {
				entering += outrankingFlowMatrix[b][a];
			}
			enteringOutrankingFlow[a] = entering/(numberOfPopulation-1);
		}
		
	}
	
	@SuppressWarnings("unused")
	public void computeNetOutrankingFlows() {
		
		netOutrankingFlow = new double[numberOfPopulation];
		for (int a = 0; a < numberOfPopulation; a++) {
			netOutrankingFlow[a] = (leavingOutrankingFlow[a] - enteringOutrankingFlow[a]);
			//System.out.println(netOutrankingFlow[a]);
		}
	}
	
	public void rank() {
		rank = new double[netOutrankingFlow.length];
		
		for(int i = 0; i < netOutrankingFlow.length; i++) {
			int a = 1, b = 1;
			for(int j = 0; j < netOutrankingFlow.length; j++) {
				
				if(j!=i && netOutrankingFlow[j] < netOutrankingFlow[i]) {
					a += 1;
				}
				if(j!=i && netOutrankingFlow[j] == netOutrankingFlow[i]) {
					b += 1;
				}
			}
			
			rank[i] = a + (double)(b-1)/(double)2;	
		}	
	}
	
	@SuppressWarnings("unused")
	public void rankingScore() {
		//System.out.println("Each pop");
		for (int i = 0; i < numberOfPopulation; i++) {
			solutionpairs.get(i).getSolutionSet().get(1).attributes().put("Performance", rank[i]);
			//System.out.println(netOutrankingFlow[i] + " " +solutionpairs.get(i).getSolutionSet().get(1).attributes());
		}
	}
	
	@SuppressWarnings("unused")
	private void sortSolutionPairsByRankingDesc() {

		Collections.sort(solutionpairs, new Comparator<solutionSet>() {
			@Override
			public int compare(solutionSet o1, solutionSet o2) {
				// TODO Auto-generated method stub
				return Double.compare((Double)(o2.getSolutionSet().get(1).attributes().get("Performance")), (Double)(o1.getSolutionSet().get(1).attributes().get("Performance")));
			}
		});
	}

}
