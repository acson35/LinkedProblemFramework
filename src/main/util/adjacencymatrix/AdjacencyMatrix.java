package main.util.adjacencymatrix;

public interface AdjacencyMatrix {
	
	//public void addEdge(int problemA, int problemB, boolean connected);
	
	public int getEdge(int problemA, int problemB);
	
	int[][] getMatrix();
	

}
