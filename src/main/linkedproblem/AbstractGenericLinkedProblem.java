package main.linkedproblem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import main.problem.Problem;
import main.util.adjacencymatrix.AdjacencyMatrix;
import main.util.adjacencymatrix.AdjacencyMatrixLinkedProblem;
import main.util.adjacencymatrix.LinkedProblemMatrix;
import main.util.adjacencymatrix.LinkedSequence;
import main.util.createBinary.createBinary;

@SuppressWarnings("serial")
public abstract class AbstractGenericLinkedProblem<S> implements LinkedProblem<S> {
	
	HashMap<Integer, Problem<?>> linkedproblems = new HashMap<>();
	AdjacencyMatrixLinkedProblem adjmatrix;
	int numOfObj;
	LinkedSequence solutionLinkedSeq;
	LinkedSequence objLinkedSeq;
	
	protected void addProblem(Integer index, Problem<?> problem) {
		this.linkedproblems.put(index, problem);
	}
	
	
	protected void addAdjacencyMatrix(AdjacencyMatrixLinkedProblem adjmatrix) {
		this.adjmatrix = adjmatrix;
	}
	
	@Override
	public HashMap<Integer, Problem<?>> getLinkedProblems(){
		return  this.linkedproblems;
	}
	
	
	@Override
	public AdjacencyMatrix getAdjacencyMatrix() {
		return adjmatrix;
	}
	
	
	public Problem<?> getProblem(Integer index){
		return  this.linkedproblems.get(index);
	}
	
	@Override
	public void setProblem(Integer index, Problem<?> problem){
		this.linkedproblems.replace(index, problem);
	}
	
	public void setNomOfObjectives(int num) {
		this.numOfObj = num;
	}
	@Override
	public int numberOfObjectives() {
		return this.numOfObj;
	}
	
	public void setObjLinkedSeqs(LinkedSequence linkedSeq) {
		this.objLinkedSeq = linkedSeq;
	}
	
	@Override
	public LinkedSequence getObjLinkedSeq() {
		return this.objLinkedSeq;
	}
	
	public void setSolutionLinkedSeqs(LinkedSequence linkedSeq) {
		this.solutionLinkedSeq = linkedSeq;
	}
	
	@Override
	public LinkedSequence getSolutionLinkedSeq() {
		return this.solutionLinkedSeq;
	}
	

}
