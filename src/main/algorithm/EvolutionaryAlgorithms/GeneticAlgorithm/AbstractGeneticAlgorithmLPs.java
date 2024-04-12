package main.algorithm.EvolutionaryAlgorithms.GeneticAlgorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.algorithm.AlgorithmLP;
import main.algorithm.AlgorithmLPs;
import main.linkedproblem.LinkedProblem;
import main.operator.crossover.CrossoverOperator;
import main.operator.mutation.MutationOperator;
import main.operator.selection.SelectionOperator;
import main.problem.Problem;
import main.solution.solution;
import main.solution.solutionPair;
import main.solution.solutionSet;
import main.util.adjacencymatrix.AdjacencyMatrix;
import main.util.adjacencymatrix.LinkedSequence;
import main.util.factory.GetProblemFactory;

public abstract class AbstractGeneticAlgorithmLPs<S extends solutionSet, R> implements AlgorithmLPs<S, R> {
	
	
	protected int maxPopulationSize ;
	
	protected LinkedProblem linkedproblem;
	
	@SuppressWarnings("rawtypes")
	protected List<S> population = new ArrayList<>();
	
	@SuppressWarnings("rawtypes")
	protected SelectionOperator<List<S>, S> selectionOperator ;
	
	protected List<CrossoverOperator<solution<?>>> crossoverOperator ;
	 
	protected List<MutationOperator<solution<?>>> mutationOperator ;
	 
	
	protected abstract void initProgress();

	protected abstract void updateProgress();

	protected abstract boolean isStoppingConditionReached();


	public AbstractGeneticAlgorithmLPs(LinkedProblem linkedproblem) {
		this.linkedproblem = linkedproblem;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<S> getPopulation() {
	  return population;
	}
	
	public void setPopulation(List<S> population) {
	  this.population = population;
	}
	
	public LinkedProblem getProblem() {
	  return linkedproblem;
	}
	
	@Override
	public void setProblem(LinkedProblem linkedproblem) {
	  this.linkedproblem = linkedproblem;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void setFirstProblem(Problem<?> firstProblem) {
		linkedproblem.setProblem(1, firstProblem);
	}
	
	/* Setters and getters */
	public void setMaxPopulationSize(int maxPopulationSize) {
	  this.maxPopulationSize = maxPopulationSize ;
	}
	  
	public int getMaxPopulationSize() {
	  return maxPopulationSize ;
	}
	  
	public SelectionOperator<List<S>, S> getSelectionOperator() {
	  return selectionOperator;
	}

	public List<CrossoverOperator<solution<?>>> getCrossoverOperator() {
	  return crossoverOperator;
	}

	public List<MutationOperator<solution<?>>> getMutationOperator() {
	  return mutationOperator;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<S> createInitialPopulation(){
		
		List<S> population = new ArrayList<>(getMaxPopulationSize());
		LinkedProblem problem = getProblem();
		LinkedSequence linkedsSequence = problem.getSolutionLinkedSeq();
		
		for(int i = 0; i < getMaxPopulationSize(); i++) {
			 linkedsSequence.traverseSolutionNodes();
			  S f = (S) linkedsSequence.getSolutionSet();
			  population.add(f);
		} 
		
		 return population;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected List<S> createInitialPopulation1() {
		LinkedProblem problem = getProblem();
	  List<S> population = new ArrayList<>(getMaxPopulationSize());
	  AdjacencyMatrix matrix = problem.getAdjacencyMatrix();
	  
	  int i=0;
	  while ( i < getMaxPopulationSize()) {
		  Map<Integer, solution<?>> SolutionSet = new HashMap<Integer, solution<?>>(); 
		  	//solutionSet newSolutionSet = null;  
		   	for(int j = 0; j< problem.getLinkedProblems().size(); j++) {
		  		
		  		solution<?> solution;
		  		if(!SolutionSet.containsKey(j+1)) {
		  			Problem<?> parentproblem = (Problem<?>) problem.getLinkedProblems().get(j+1);
		  			solution = (solution<?>) parentproblem.createsolution();
		  			SolutionSet.put(j+1, solution);
				}else {
					solution = SolutionSet.get(j+1);
				}
				for(int k = 0; k< problem.getLinkedProblems().size(); k++) {
					if(j==k)continue;
					Problem<?> childproblem =  (Problem<?>) problem.getLinkedProblems().get(k+1);
					solution<?> sol;
					if(matrix.getEdge(j,k)==1) {
						
						if(!SolutionSet.containsKey(k+1)) {
							
							GetProblemFactory factory =  new GetProblemFactory(childproblem, solution);
							childproblem = (Problem<?>) factory.getProblemInstance();
							solution<?> childsolution = (solution<?>) childproblem.createsolution();
							SolutionSet.put(k+1, childsolution);
							
						}else {
							//sol = SolutionSet.get(k+1);
						}
					 }else if(matrix.getEdge(j,k)==2 || matrix.getEdge(j,k)==3){
						 //solution<?> childsolution = (solution<?>) childproblem.createsolution();
						 if(!SolutionSet.containsKey(k+1)) {
							 sol = (solution<?>) childproblem.createsolution();
							 SolutionSet.put(k+1, sol);
						 }else {
								//sol = SolutionSet.get(k+1);
						 }
					 }
				 }
		   	}
		solutionSet newSolutionSet = new solutionSet(SolutionSet);
	   	population.add((S) newSolutionSet);
	    i++;
	  }
	  return population;
	}
	
	@SuppressWarnings("rawtypes")
	protected void checkNumberOfParents(List<S> matingPool, int numberOfParentsForCrossover) {
		
	    if ((matingPool.size() % numberOfParentsForCrossover) != 0) {
	      throw new ArithmeticException("Wrong number of parents: the remainder if the " +
	              "population size (" + matingPool.size() + ") is not divisible by " +
	              numberOfParentsForCrossover) ;
	    }
	  }
	
	protected void checkNumberOfParents2(List<solution<?>> childsolutions, int numberOfParentsForCrossover) {
		
	    if ((childsolutions.size() % numberOfParentsForCrossover) != 0) {
	      throw new ArithmeticException("Wrong number of parents: the remainder if the " +
	              "population size (" + childsolutions.size() + ") is not divisible by " +
	              numberOfParentsForCrossover) ;
	    }
	  }
	  
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
		    	matingPopulation =  selection(population);
		    	offspringPopulation = reproduction(matingPopulation);
		      //System.out.println(offspringPopulation.size());
		    	offspringPopulation = evaluatePopulation(offspringPopulation);
		      
		    	population = replacement(population, offspringPopulation);
		     // System.out.println(population.size());
		    	updateProgress();
		   }
		   
		}


}
