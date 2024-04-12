package main.util.adjacencymatrix;

import java.util.HashMap;
import java.util.Map;

import main.problem.Problem;
import main.solution.solution;
import main.solution.solutionSet;
import main.util.factory.GetProblemFactory;

public class LinkedSequence {
	Node head;
	solutionSet s;
	
	public void addNode(Problem<solution<?>> prob) {
		
		Node newNode = new Node(prob);
		if(head==null) {
			head = newNode;
		}else {
			Node temp = head;
			while(temp.next != null) {
				temp = temp.next;
			}
			temp.next = newNode;
		}	
	}
	
	public void setSolutions(solutionSet s) {
		this.s = s;
	}
	
	public solutionSet getSolutionSet() {
		return this.s;
	}
	
	solution<?> getEachSolution(Problem<solution<?>> prob){
		int k=0;
		for(int i: s.getSolutionSet().keySet()) {
			if(s.getSolution(i).problemname() == prob.getName()) {
				k = i;
			}
		}
		return s.getSolution(k);
	}
	
	
	public void traverseObjectiveNodes() {
		Node temp = head;
		while(temp != null) {
			solution<?> sol = getEachSolution(temp.prob);
			temp.prob.evaluate(sol);
			if(temp.next != null) {
				temp.next.prob.setParentsolution(sol);
			}
			temp = temp.next;
		}
	}	
	
	@SuppressWarnings("unused")
	public void traverseSolutionNodes() {
		Node temp = head;
		Map<Integer, solution<?>> sols = new HashMap<Integer, solution<?>>(); 
		int i = 1;
		solution<?> sol = temp.prob.createsolution();
		sols.put(1, sol);
		while(temp.next != null) {
			i++;
			Problem<?> nextProb = null;
			
			GetProblemFactory factory =  new GetProblemFactory(temp.next.prob, sol);
			nextProb = (Problem<?>) factory.getProblemInstance();
			sol = (solution<?>) nextProb.createsolution();
			
			sols.put(i, sol);
			temp = temp.next;
		}
		s = new solutionSet(sols);
	}	
	
}

class Node{
	Problem<solution<?>> prob;
	Node next;
	Node(Problem<solution<?>> prob){
		this.prob = prob;
		this.next = null;
	}
}
