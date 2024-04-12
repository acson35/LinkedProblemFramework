package main.util.adjacencymatrix;

import main.algorithm.EvolutionaryAlgorithms.GeneticAlgorithm.GenerationalGeneticAlgorithm;
import main.algorithm.EvolutionaryAlgorithms.GeneticAlgorithm.SteadyStateGeneticAlgorithm;
import main.algorithm.GeneticAlgorithmVariant.GeneticAlgorithmVariant;

public class AdjacencyMatrixLinkedProblem implements AdjacencyMatrix{

	int nProblems;
	int[][] matrix;
	//FeaturesAffected feature; 
	
	public AdjacencyMatrixLinkedProblem(int nProblems) {
		this.nProblems = nProblems;
		this.matrix = new int[nProblems][nProblems];
		
		for(int i=0; i<nProblems;i++) {
			for(int j=0; j<nProblems; j++) {
				matrix[i][j]=0;
			}		
		}	
	}

	public void addEdge(int problemA, int problemB, boolean affects, FeaturesAffected feature) {
		if(affects) {
			if(feature == FeaturesAffected.SOLUTION) {
				matrix[problemA][problemB] = 1;
			}else if(feature == FeaturesAffected.OBJECTIVE) {
				matrix[problemA][problemB] = 2;
			}else if(feature == FeaturesAffected.CONSTRAINT) {
				matrix[problemA][problemB] = 3;
			}else {
			      throw new RuntimeException("Unknown feature affected: " + feature) ;
			}
			
		}
	}

	@Override
	public int getEdge(int problemA, int problemB) {
		return matrix[problemA][problemB];
	}
	
	
	
	
	//public void setMatrixType(MatrixType matrixtype) {
	//	this.matrixtype = matrixtype;
	//}
	
	//public MatrixType getMatrixType() {
	//	return this.matrixtype;
	//}
	
	@Override
	public int[][] getMatrix(){
		return this.matrix;
	}
	
}
