package main.util.ranking.AHP;

import java.util.List;
import java.util.Map.Entry;

import main.solution.solution;
import main.solution.solutionSet;
import main.util.ranking.Criteria;

public class FitnessRanking <S> {

	private List<S> solutionpairs;
	private double[][] rankMatrix;
	private List<Criteria> criteria;
	private int numberOfPopulation = 0;
	private int numberOfCriteria = 0;
	private double[][] scoreMatrix;
	private double[][] criteriaImportanceMatrix;
	private String[] criteriaName;
	private double[][] groupWeights;
	private double[] collectivePriorityWeightVector;
	
	public FitnessRanking(List<S> solutionpairs, List<Criteria> criteria) {
		super();
		this.solutionpairs = solutionpairs;
		this.criteria = criteria;
	}
	
	public static void rank(double ele[], int n, double[] r) {
		//r = new double[n];
		
		for(int i = 0; i < n; i++) {
			int a = 1, b = 1;
			for(int j = 0; j <n; j++) {
				
				if(j!=i && ele[j] < ele[i]) {
					a += 1;
				}
				
				if(j!=i && ele[j]==ele[i]) {
					b += 1;
				}
			}
			
			r[i] = a + (double)(b-1)/(double)2;	
		}	
	}
	
	public static double[][] scaling() {
		double[][] scale = new double[9][9];
		for(int i = 0; i < 9; i++) {
			int a = 0;
			for(int j = 0; j < 9; j++) {
				
				if(j!=i && i < j) {
					a++;
					scale[i][j] = a;
					
					scale[j][i] = 1/(scale[i][j]);
					
				}
				if(j!=i && i > j) {
					//a++;
					//scale[i][j] = a;
					
					scale[j][i] = 1/(scale[i][j]);
					
				}
				
				if(j==i) {
					a++;
					scale[i][j] = 1;
				}
			}
		}
		
		return scale;
	}
	
	@SuppressWarnings("unused")
	private void populateScoreMatrix() {
		numberOfPopulation = solutionpairs.size();
		numberOfCriteria = criteria.size();
		
		scoreMatrix = new double[numberOfPopulation][numberOfCriteria];
		rankMatrix = new double[numberOfPopulation][numberOfCriteria];
		
		int rowIndex = 0;		
		for (S eachsolutionpair : solutionpairs) {
			int columnIndex = 0;
			for(Entry<Integer, solution<?>> sol: ((solutionSet) eachsolutionpair).getSolutionSet().entrySet()) {
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

	public void populateRankMatrix() {
		int n = solutionpairs.size();
		for(int i = 0; i < criteria.size(); i++) {
			double ele[] = new double[n];
			double[] r = new double[n];
			for(int j = 0; j < n; j++) {
				
				ele[j] = scoreMatrix[j][i];
				
			}
			rank(ele, n, r);
			
			for(int j = 0; j < n; j++) {
				
				rankMatrix[j][i] = r[j];
				
			}
		}
	}
	
	public void setCriteriaImportance() {
		numberOfPopulation = solutionpairs.size();
		numberOfCriteria = criteria.size();
		
		int n = 0;
		for(int i=0; i< numberOfCriteria; i++) {
			for(int j=i; j< numberOfCriteria; j++) {
				if(j!=i) {
					n++;
				}
			}	
		}
		
		criteriaImportanceMatrix = new double[numberOfPopulation][n];
		
		for(int i=0; i< numberOfPopulation; i++) {
			double[] r = new double[numberOfCriteria];
			
			for(int j=0; j< numberOfCriteria; j++) {
				r[j] = rankMatrix[i][j];
			}
			double x = 0;
			for(int j=0; j< r.length; j++) {
				x = Math.max(x, r[j]);
			}
			int[] a = new int[numberOfCriteria];
			for(int j=0; j< r.length; j++) {
				int val = (int)Math.round((r[j] * 9)/x);
				if(val == 0) {
					a[j] = 1;
				}else {
					a[j] = val;
				}
			}
			
			int m = 0;
			for(int j=0; j<a.length; j++) {
				for(int k=j; k<a.length; k++) {
					if(k!=j) {
						criteriaImportanceMatrix[i][m] = scaling()[a[j]-1][a[k]-1];
						//System.out.print(scaling()[a[j]-1][a[k]-1] + " ");
						m++;
					}
				}
			}
			//System.out.println();
		}
	}
	
	public void groupDecisionMaking() {
		criteriaName = new String[criteria.size()];
		groupWeights = new double[numberOfPopulation][numberOfCriteria];
		
		for(int i = 0; i < criteria.size(); i++) {
			criteriaName[i] = criteria.get(i).getName();
			//System.out.print(criteriaName[i]+ " ");
		}
		
		AHP ahp = AHP.getInstance(criteriaName);
		
		for(int i = 0; i < criteriaImportanceMatrix.length; i++) {
			UniqueSolution us = new UniqueSolution(ahp);
			double[] criteriaImportance = new double[criteriaImportanceMatrix[0].length];
			for(int j = 0; j < criteriaImportanceMatrix[i].length; j++) {
				criteriaImportance[j]= criteriaImportanceMatrix[i][j];
			}
			us.setCriteriaImportance(criteriaImportance);
			double[] w = calculateAHP(ahp, us.getComArrayValues());
			
			for(int j = 0; j < numberOfCriteria; j++) {
				
				groupWeights[i][j] = w[j];
				//System.out.print(w[j] + " ");
			}
			//System.out.println();
		     //Array2DRowRealMatrix A1 = (Array2DRowRealMatrix) ahp.getMtx().copy();
		}
	}
	
	@SuppressWarnings("unused")
	private double[] calculateAHP(AHP ahp, double[] compArray){

        ahp.setPairwiseComparisonArray(compArray);
        ahp.setEvd();

        double[] ahpWeights = new double[criteria.size()];

        for (int k=0; k<ahp.getWeights().length; k++) {
            ahpWeights[k] = ahp.getWeights()[k];
            //System.out.println(Config.criteria[k] + ": " + Config.df.format(ahp.getWeights()[k]));
        }
        return ahpWeights;
    }
	
	public void collectiveWeighting() {
		
		collectivePriorityWeightVector = new double[groupWeights[0].length];
		//double dmWeights = 1/groupWeights.length;
		double[] collectiveWeight = new double[groupWeights[0].length];
		double total = 0;
        for (int i=0; i<groupWeights[0].length; i++) {
        	double wk = 1;
        	for (int j=0; j<groupWeights.length; j++) {
        		wk =  wk + groupWeights[j][i];
        	}   
        	collectiveWeight[i] = wk;
        	total += wk;
        	
            //collectivePriorityWeightVector[i] = Math.pow(wk, dmWeights);
        }
        
        for (int i=0; i<groupWeights[0].length; i++) {
        	collectivePriorityWeightVector[i] = collectiveWeight[i]/total;
        	 //System.out.println(collectivePriorityWeightVector[i]  + " ");
        }
        
	}
	
	public void setCriteriaWeightings() {
		
		for(int i = 0; i < criteria.size(); i++) {
			Criteria cr = criteria.get(i);
			cr.setWeight(collectivePriorityWeightVector[i]);
			criteria.set(i, cr);
			//System.out.print(collectivePriorityWeightVector[i] + " ");
		}
	}
	
	public void optimalCriteriaWeightings() {
		populateScoreMatrix();
		populateRankMatrix();
		setCriteriaImportance();
		groupDecisionMaking();
		collectiveWeighting();
		setCriteriaWeightings();
	}
	
}
