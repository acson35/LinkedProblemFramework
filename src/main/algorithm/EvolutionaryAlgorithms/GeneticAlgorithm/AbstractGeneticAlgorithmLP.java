package main.algorithm.EvolutionaryAlgorithms.GeneticAlgorithm;

import java.util.ArrayList;
import java.util.List;

import main.algorithm.Algorithm;
import main.algorithm.AlgorithmLP;
import main.algorithm.EvolutionaryAlgorithms.AbstractEvolutionaryAlgorithm;
import main.linkedproblem.LinkedProblem;
import main.operator.crossover.CrossoverOperator;
import main.operator.mutation.MutationOperator;
import main.operator.selection.SelectionOperator;
import main.problem.Problem;
import main.solution.solution;
import main.solution.solutionPair;
import main.util.adjacencymatrix.AdjacencyMatrix;
import main.util.factory.GetProblemFactory;

public abstract class AbstractGeneticAlgorithmLP<S extends solution<?>, R> implements AlgorithmLP<S, R> {
	  
	protected int maxPopulationSize ;
	
	protected LinkedProblem linkedproblem;
	
	@SuppressWarnings("rawtypes")
	protected List<solutionPair> population = new ArrayList<>();
	
	@SuppressWarnings("rawtypes")
	protected SelectionOperator<List<solutionPair>, solutionPair> selectionOperator ;
	
	protected List<CrossoverOperator<S>> crossoverOperator ;
	 
	protected List<MutationOperator<S>> mutationOperator ;
	 
	
	protected abstract void initProgress();

	protected abstract void updateProgress();

	protected abstract boolean isStoppingConditionReached();


	public AbstractGeneticAlgorithmLP(LinkedProblem linkedproblem) {
		this.linkedproblem = linkedproblem;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<solutionPair> getPopulation() {
	  return (List<solutionPair>) population;
	}
	
	public void setPopulation(List<solutionPair> population) {
	  this.population = population;
	}
	
	public LinkedProblem getProblem() {
	  return linkedproblem;
	}
	
	@Override
	public void setProblem(LinkedProblem linkedproblem) {
	  this.linkedproblem = linkedproblem;
	}
	
	/* Setters and getters */
	public void setMaxPopulationSize(int maxPopulationSize) {
	  this.maxPopulationSize = maxPopulationSize ;
	}
	  
	public int getMaxPopulationSize() {
	  return maxPopulationSize ;
	}
	  
	public SelectionOperator<List<solutionPair>, solutionPair> getSelectionOperator() {
	  return selectionOperator;
	}

	public List<CrossoverOperator<S>> getCrossoverOperator() {
	  return crossoverOperator;
	}

	public List<MutationOperator<S>> getMutationOperator() {
	  return mutationOperator;
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected List<solutionPair> createInitialPopulation() {
	  List<solutionPair> population = new ArrayList<>(getMaxPopulationSize());
	  AdjacencyMatrix matrix = getProblem().getAdjacencyMatrix();
	  int i=0;
	  while ( i < getMaxPopulationSize()) {
	  	solutionPair newSolutionPair = null;
	   	for(int j = 0; j< linkedproblem.getLinkedProblems().size(); j++) {
	  		Problem<?> parentproblem = (Problem<?>) linkedproblem.getLinkedProblems().get(j+1);
	   		
			for(int k = 0; k< linkedproblem.getLinkedProblems().size(); k++) {
				Problem<?> childproblem =  (Problem<?>) linkedproblem.getLinkedProblems().get(k+1);
				if(matrix.getEdge(j,k)==1) {
					solution<?> parentsolution = (solution<?>) parentproblem.createsolution();
					GetProblemFactory factory =  new GetProblemFactory(childproblem, parentsolution);
					childproblem = (Problem<?>) factory.getProblemInstance();
					solution<?> childsolution = (solution<?>) childproblem.createsolution();
					newSolutionPair = new solutionPair(parentsolution, childsolution);
				 }
			 }
	   	}
	   
	   	population.add((solutionPair) newSolutionPair);
	    i++;
	  }
	  return population;
	}
	
	@SuppressWarnings("rawtypes")
	protected void checkNumberOfParents(List<solutionPair> population, int numberOfParentsForCrossover) {
		
	    if ((population.size() % numberOfParentsForCrossover) != 0) {
	      throw new ArithmeticException("Wrong number of parents: the remainder if the " +
	              "population size (" + population.size() + ") is not divisible by " +
	              numberOfParentsForCrossover) ;
	    }
	  }
	
	protected void checkNumberOfParents2(List<S> population, int numberOfParentsForCrossover) {
		
	    if ((population.size() % numberOfParentsForCrossover) != 0) {
	      throw new ArithmeticException("Wrong number of parents: the remainder if the " +
	              "population size (" + population.size() + ") is not divisible by " +
	              numberOfParentsForCrossover) ;
	    }
	  }
	  
	  protected abstract List<solutionPair> evaluatePopulation(List<solutionPair> population);

	  protected abstract List<solutionPair> selection(List<solutionPair> population);

	  protected abstract List<solutionPair> reproduction(List<solutionPair> population);
	  
	  protected abstract List<solutionPair> replacement(List<solutionPair> population, List<solutionPair> offspringPopulation); 


	  @Override public abstract R getResult();

	  
	  @Override public void run() {
			
			List<solutionPair> offspringPopulation;
			List<solutionPair> matingPopulation = new ArrayList<>();

		    population = createInitialPopulation();
		    population = evaluatePopulation(population);
		    
		    initProgress();
		   
		    while (!isStoppingConditionReached()) {
		    
		      matingPopulation =  selection(population);
		      offspringPopulation = reproduction(matingPopulation);
		      offspringPopulation = evaluatePopulation(offspringPopulation);
		      population = replacement(population, offspringPopulation);
		      updateProgress();
		   }
		   
		}

}
