package main.util.adjacencymatrix;

import java.util.HashMap;
import java.util.Map;

import main.algorithm.Algorithm;
import main.algorithm.GenericAlgorithm;
import main.linkedproblem.LinkedProblem;
import main.problem.GenericProblem;
import main.problem.Problem;
import main.solution.solution;
import main.solution.solutionSet;
import main.util.factory.GetProblemFactory;

/**
 * Sequence based algorithmic implementation.
 *
 *@author Akinola Ogunsemi <a.ogunsemi1@rgu.ac.uk>
 *
 *@param <S>
 *
 * 
 */

public class SequenceAlgorithmicProcess<S extends solution<?>> {

	aNode head;
	solutionSet s;
	
	public void addNode(GenericAlgorithm<S, S> algo, GenericProblem<solution<?>> prob) {
		aNode newNode = new aNode(algo, prob);
		if(head==null) {
			head = newNode;
		}else {
			aNode temp = head;
			while(temp.next != null) {
				temp = temp.next;
			}
			temp.next = newNode;
		}	
	}
	
	public solutionSet getSolutionSet() {
		return s;
	}
	
	@SuppressWarnings({ "unused", "unchecked" })
	public void sequenceRun(){
		Map<Integer, solution<?>> sol = new HashMap<>();
		aNode temp = head;
		int x = 1;
		while(temp != null) {
			//newprob = (Problem<S>)Class.forName(problemname).getConstructor(String.class, solution.class).newInstance(childproblem.getFilepath(), parentsolution);
			temp.algo.run();
			sol.put(x, (solution<?>) temp.algo.getResult());
			
			GenericProblem<?> childproblem;
			if(temp.next != null) {
				childproblem = temp.next.prob;
				
				if(childproblem instanceof Problem) {
					GetProblemFactory factory =  new GetProblemFactory((Problem<?>) childproblem, (solution<?>) temp.algo.getResult());
					childproblem = (Problem<solution<?>>) factory.getProblemInstance();
					temp.next.setProblem(childproblem);
				}else {
					
					GetProblemFactory factory =  new GetProblemFactory((Problem<?>) childproblem, (solution<?>) temp.algo.getResult());
					Problem<?> childproblem1 = (Problem<solution<?>>) factory.getProblemInstance();
					((LinkedProblem) childproblem).setProblem(1, childproblem1);
					
					((aNode) temp.next.algo).setProblem((Problem<?>) childproblem);
				}
				
			}
			temp = temp.next;
			x++;
		}
		s = new solutionSet(sol);
	}
}

class aNode{
	GenericAlgorithm<?, ?> algo;
	GenericProblem<?> prob;
	aNode next;
	aNode(GenericAlgorithm<?, ?> algo, GenericProblem<?> prob){
		this.algo = algo;
		this.prob = prob;
		this.next = null;
	}
	void setProblem(GenericProblem<?> prob) {
		this.prob = prob;
	}
}
