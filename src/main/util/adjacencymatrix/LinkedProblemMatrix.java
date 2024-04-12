package main.util.adjacencymatrix;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class LinkedProblemMatrix {
	
	//int nMatrices = 3;
	private HashMap< MatrixType, AdjacencyMatrixLinkedProblem> lpAdjacencyMatrices;
	
	public LinkedProblemMatrix() {
		this.lpAdjacencyMatrices = new HashMap<MatrixType, AdjacencyMatrixLinkedProblem>();
	}
	
//	public void addMatrix(AdjacencyMatrixLinkedProblem adjmatrix) {
//		if(adjmatrix != null)
//			lpAdjacencyMatrices.put(adjmatrix.getMatrixType(), adjmatrix);
//	}
	
	public Collection<AdjacencyMatrixLinkedProblem> getAdjacencyMatrices(){
		return lpAdjacencyMatrices.values();
	}
	
//	public AdjacencyMatrixLinkedProblem getMatrix(MatrixType type) {
		
//		List<AdjacencyMatrixLinkedProblem> matrices = (List<AdjacencyMatrixLinkedProblem>) getAdjacencyMatrices();
//		for(AdjacencyMatrixLinkedProblem adj: matrices) {
//			if(adj.matrixtype==type) {
//				return adj;
//			}
//		}
//		return null;
//	}
	
	//public int[][] getAdjacencyMatrix(MatrixType matrixtype){
	//	return lpAdjacencyMatrices.get(matrixtype).getMatrix();
	//}
	
	
	
}
