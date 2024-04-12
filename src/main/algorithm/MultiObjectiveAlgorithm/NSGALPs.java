package main.algorithm.MultiObjectiveAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import main.algorithm.EvolutionaryAlgorithms.GeneticAlgorithm.AbstractGeneticAlgorithmLP;
import main.algorithm.EvolutionaryAlgorithms.GeneticAlgorithm.AbstractGeneticAlgorithmLPs;
import main.linkedproblem.LinkedProblem;
import main.operator.crossover.CrossoverOperator;
import main.operator.mutation.MutationOperator;
import main.operator.selection.SelectionOperator;
import main.operator.selection.selectionClasses.BinaryTournamentSelection;
import main.operator.selection.selectionClasses.PairRankingAndCrowdingSelection;
import main.problem.Problem;
import main.solution.solution;
import main.solution.solutionPair;
import main.solution.solutionSet;
import main.util.SolutionListUtils;
import main.util.adjacencymatrix.AdjacencyMatrix;
import main.util.comparator.DominancePairComparator;
import main.util.comparator.HypervolumeContributionComparator;
import main.util.comparator.ObjectiveComparator;
import main.util.evaluator.SolutionListEvaluator;
import main.util.factory.GetProblemFactory;

public class NSGALPs<S extends solutionSet> extends AbstractGeneticAlgorithmLPs<S, List<S>> {
	  
	protected final int maxEvaluations;

	  protected final SolutionListEvaluator<S> evaluator;

	  protected int evaluations;
	  
	  protected Comparator<S> dominanceComparator ;

	  protected int matingPoolSize;
	  
	  protected int offspringPopulationSize ;

	  /**
	   * Constructor
	   */
	  public NSGALPs(LinkedProblem problem, int maxEvaluations, int populationSize,
	                int matingPoolSize, int offspringPopulationSize,
	                List<CrossoverOperator<solution<?>>> crossoverOperators, 
	                List<MutationOperator<solution<?>>> mutationOperators,
	                SelectionOperator<List<S>, S> selectionOperator, 
	                SolutionListEvaluator<S> evaluator) {
		  this(problem, maxEvaluations, populationSize, matingPoolSize, offspringPopulationSize,
		            crossoverOperators, mutationOperators, selectionOperator, new DominancePairComparator<S>(), evaluator);

	  }
	  /**
	   * Constructor
	   */
	@SuppressWarnings("rawtypes")
	public NSGALPs(LinkedProblem linkedproblem, int maxEvaluations, int populationSize,
	                int matingPoolSize, int offspringPopulationSize,
	                List<CrossoverOperator<solution<?>>> crossoverOperators, List<MutationOperator<solution<?>>> mutationOperators,
	                SelectionOperator<List<S>, S> selectionOperator, Comparator<S> dominanceComparator,
	                SolutionListEvaluator<S> evaluator) {
	    super(linkedproblem);
	    this.maxEvaluations = maxEvaluations;
	    setMaxPopulationSize(populationSize); ;

	    this.crossoverOperator = crossoverOperators;
	    
	    this.mutationOperator = mutationOperators;
	    
	    this.selectionOperator = selectionOperator;

	    this.evaluator = evaluator;
	    
	    this.dominanceComparator = dominanceComparator ;

	    this.matingPoolSize = matingPoolSize ;
	    
	    this.offspringPopulationSize = offspringPopulationSize ;
	  }

	  @Override protected void initProgress() {
	    evaluations = getMaxPopulationSize();
	  }

	  @Override protected void updateProgress() {
	    evaluations += offspringPopulationSize ;
	  }

	  @Override protected boolean isStoppingConditionReached() {
	    return evaluations >= maxEvaluations;
	  }

	  @SuppressWarnings("rawtypes")
	  @Override protected List<S> evaluatePopulation(List<S> population) {
		population = evaluator.evaluate(population, getProblem()); 
	    return population;
	  }

	  /**
	   * This method iteratively applies a {@link SelectionOperator} to the population to fill the mating pool population.
	   *
	   * @param population
	   * @return The mating pool population
	   */
	  @SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	  @Override
	  protected List<S> selection(List<S> population) {
	    
		List<S> matingPopulation = new ArrayList<>();
	   	  
	    for (int i = 0; i < matingPoolSize; i++) {
	      S solution = (S) selectionOperator.execute(population);
	      matingPopulation.add(solution);
	     // System.out.println(solution.getSolution(1).toString());
	      if(matingPopulation.size()>=matingPoolSize) {
	    	  break;
	      }
	    }
	    
	 //   for(solutionSet sols: matingPopulation)
	  //  	System.out.println(sols.getSolutionSet().toString());
	    
	    List<S> newList = population.stream().map(sol -> 
	    (S)sol.copy()).collect(Collectors.toList());
	    
	    return matingPopulation;
	  }

	  /**
	   * This methods iteratively applies a {@link CrossoverOperator} a  {@link MutationOperator} to the population to
	   * create the offspring population. The population size must be divisible by the number of parents required
	   * by the {@link CrossoverOperator}; this way, the needed parents are taken sequentially from the population.
	   *
	   * The number of solutions returned by the {@link CrossoverOperator} must be equal to the offspringPopulationSize
	   * state variable
	   *
	   * @param matingPool
	   * @return The new created offspring population
	   */
	  @SuppressWarnings({ "rawtypes", "unchecked" })
	  @Override
	  protected List<S> reproduction(List<S> matingPool) {
	   
		  List<S> offspringPopulationset = new ArrayList<>();
			 
			 AdjacencyMatrix matrix = getProblem().getAdjacencyMatrix();
			 int numOfProblem = getProblem().getLinkedProblems().size();	
			 
			//System.out.print(offspringPopulationset.isEmpty());
			 for(int a = 0; a < numOfProblem; a++) {
				if(offspringPopulationset.isEmpty()) {
					int nParents_parentsolution = crossoverOperator.get(a).getNumberOfRequiredParents();
					checkNumberOfParents(matingPool, nParents_parentsolution);
					List<solution<?>> offspringPopulation = new ArrayList<>(offspringPopulationSize);
					for (int i = 0; i < matingPool.size(); i += nParents_parentsolution) {
						 List<solution<?>> parents_parentsolution = new ArrayList<>(nParents_parentsolution);
						 for (int j = 0; j < nParents_parentsolution; j++) {
							 parents_parentsolution.add(matingPool.get(i+j).getSolution(a+1));
							//System.out.println(matingPool.get(i+j).getSolution(a+1).toString());
						 }
						 List<solution<?>> offspring =  crossoverOperator.get(a).execute(parents_parentsolution);
						 for(solution<?> s: offspring){ 
								mutationOperator.get(a).execute(s); 
								offspringPopulation.add(s);	
								
								if (offspringPopulation.size() >= offspringPopulationSize)
									 break;
						 } 	
					 } 
					
					for(int j=0; j<offspringPopulationSize; j++) {
						 Map<Integer, solution<?>> map = new HashMap<>();
						 map.put(a+1, (solution<?>) offspringPopulation.get(j));
						 S newSolutionSet = (S) new solutionSet(map);
						 offspringPopulationset.add(j, newSolutionSet);
						 
					 }	
					
				}
				for(int b = 0; b < numOfProblem; b++) {
					 if(a==b)continue;
					 if(matrix.getEdge(a,b)==1) {
							if(!offspringPopulationset.get(0).getSolutionSet().containsKey(b+1)) {
								int nParents_childsolution = crossoverOperator.get(b).getNumberOfRequiredParents();
								//checkNumberOfParents(matingPool, nParents_parentsolution);
								for(int i = 0; i<offspringPopulationset.size(); i++) {
							    	List<solution<?>> childsolutions = new ArrayList<>(nParents_childsolution);
							    	GetProblemFactory factory =  new GetProblemFactory((Problem<?>) getProblem().getLinkedProblems().get(b+1), offspringPopulationset.get(i).getSolutionSet().get(a+1));
							    	Problem<solution<?>> childproblem = (Problem<solution<?>>) factory.getProblemInstance();
							    	
							    	//System.out.println(offspringPopulationset.get(i).getSolutionSet().get(a+1).toString());
							    	for(int j=0; j<offspringPopulationSize; j++) {
							    		solution<?> s = (solution<?>) childproblem.createsolution();
							    		childproblem.evaluate(s);
							    		childsolutions.add(s);
							    	}
							    	checkNumberOfParents2(childsolutions, nParents_childsolution);
							    	List<solution<?>> offspringPopulation2 = new ArrayList<>(offspringPopulationSize);
							    	for (int k = 0; k < childsolutions.size(); k += nParents_childsolution) {
							  	      List<solution<?>> parents_childsolution = new ArrayList<>(nParents_childsolution);
							  	      for (int l = 0; l < nParents_childsolution; l++) {
							  	    	  parents_childsolution.add(childsolutions.get(k+l));
							  	      }
							  	      List<solution<?>> offspring2 =  crossoverOperator.get(b).execute(parents_childsolution);
							  	      for(solution<?> s: offspring2){
							  	        mutationOperator.get(b).execute(s);
							  	        childproblem.evaluate(s);
							  	        offspringPopulation2.add(s);
							  	        if (offspringPopulation2.size() >= offspringPopulationSize)
							  	          break;
							  	      }
							  	    }
							    	List<solution<?>> jointchildPopulation = new ArrayList<>();
								    jointchildPopulation.addAll(childsolutions);
								    jointchildPopulation.addAll(offspringPopulation2);
							    	
								    Collections.sort(jointchildPopulation, new ObjectiveComparator()) ;
								   
								    Map<Integer, solution<?>> map = offspringPopulationset.get(i).getSolutionSet();
									map.put(b+1, (solution<?>) jointchildPopulation.get(0));
									S newSolutionSet = (S) new solutionSet(map);
									offspringPopulationset.set(i, newSolutionSet);
								  }
								
							}
					 }
				 }
			 }  
			 
			 
			 
		    return offspringPopulationset;
	  }

	@Override protected List<S> replacement(List<S> population, List<S> offspringPopulation) {
	   List<S> jointPopulation = new ArrayList<>();
	   jointPopulation.addAll(population);
	   jointPopulation.addAll(offspringPopulation);
	   
	   //System.out.println(offspringPopulation.size());
	   
	   PairRankingAndCrowdingSelection<S> rankingAndCrowdingSelection ;
	   rankingAndCrowdingSelection = new PairRankingAndCrowdingSelection<S>(getMaxPopulationSize(), dominanceComparator) ;

	   try {
	   	jointPopulation = rankingAndCrowdingSelection.execute(jointPopulation) ;
	   }catch(Exception e) {	
	   }
	   //System.out.println(getMaxPopulationSize());
	 // System.out.println(jointPopulation.size() + " "+ offspringPopulation.size()+" "+ population.size()+ " "+ getMaxPopulationSize());
	   
	   return jointPopulation;
	}

	@Override public List<S> getResult() {
		List<S> solutionFronts = (List<S>) SolutionListUtils.getNonDominatedSolutions(getPopulation());
		solutionFronts.sort(new HypervolumeContributionComparator<S>().getComparator());
		return solutionFronts;	
	}
	  
	
	public List<S> getFrontPairs() { 
		List<S> solutionFronts = (List<S>) SolutionListUtils.getNonDominatedSolutions(getPopulation());
		solutionFronts.sort(new HypervolumeContributionComparator<S>().getComparator());
		return solutionFronts;
	}

	public String getName() {
	  return "NSGALPs" ;
	}

	public String getDescription() {
	  return "Nondominated Sorting Genetic Algorithm for Linked Problem" ;
	}
	
}

