package main.algorithm.EvolutionaryAlgorithms;

import java.util.ArrayList;
import java.util.List;

import main.algorithm.Algorithm;
import main.linkedproblem.LinkedProblem;
import main.problem.Problem;
import main.solution.solution;

public abstract class AbstractEvolutionaryAlgorithm<S, R>  implements Algorithm<S, R> {
	protected List<S> population = new ArrayList<>();
	protected Problem<S> problem ;
	//protected LinkedProblem linkedproblem ;

	@Override
	public List<S> getPopulation() {
	  return population;
	}
	public void setPopulation(List<S> population) {
	  this.population = population;
	}


	@Override
	public void setProblem(Problem<S> problem) {
	  this.problem = problem ;
	}
	
	//public void setProblem(LinkedProblem linkedproblem) {
	//	  this.linkedproblem = linkedproblem ;
	//}
	
	public Problem<S> getProblem() {
	  return problem ;
	}
	
	//public LinkedProblem getLinkedProblem() {
	//	return linkedproblem ;
	//}

	protected abstract void initProgress();

	protected abstract void updateProgress();

	protected abstract boolean isStoppingConditionReached();

	protected abstract  List<S> createInitialPopulation() ;

	protected abstract List<S> evaluatePopulation(List<S> population);

	protected abstract List<S> selection(List<S> population);

	protected abstract List<S> reproduction(List<S> population);

	protected abstract List<S> replacement(List<S> population, List<S> offspringPopulation); 

	@Override public abstract R getResult();

	@Override public void run() {
	
		List<S> offspringPopulation;
		List<S> matingPopulation = new ArrayList<>();

	    population = createInitialPopulation();
	    population = evaluatePopulation(population);
	    
	    initProgress();
	   
	    while (!isStoppingConditionReached()) {
	    
	      matingPopulation = selection(population);
	      offspringPopulation = reproduction(matingPopulation);
	      offspringPopulation = evaluatePopulation(offspringPopulation);
	      population = replacement(population, offspringPopulation);
	      updateProgress();
	   }
	   
	}
}
