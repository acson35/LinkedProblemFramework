package main.linkedproblem.holisticLinkedProblem.holisticImplementation;

import java.util.ArrayList;
import java.util.List;

import main.linkedproblem.AbstractGenericLinkedProblem;
import main.linkedproblem.holisticLinkedProblem.HolisticLinkedProblem;
import main.problem.Problem;
import main.solution.solution;
import main.solution.holisticSolution.OverallSolution;

@SuppressWarnings("serial")
public abstract class AbstractHolisticLinkedProblem extends AbstractGenericLinkedProblem implements HolisticLinkedProblem {
	
	public int nProblems=0;
	public List<solution<?>> solutions;
	
	public AbstractHolisticLinkedProblem(int nProblems) {
		this.nProblems = nProblems;
	}
	
	@Override
	public List<solution<?>> Solutions() {
		solutions = new ArrayList();
		for(int i=0; i<getLinkedProblems().size(); i++) {
			//System.out.println(getProblems().get(i).createsolution().toString());
			solutions.add((solution<?>) getLinkedProblems().get(i).createsolution());
			
		}
		return solutions;
	}
	
	
	
	public OverallSolution createlinkedsolution() {
		return new OverallSolution(Solutions());
	}
}
