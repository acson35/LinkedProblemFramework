package main.algorithm.MultiObjectiveAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.stream.Collectors;

import main.algorithm.EvolutionaryAlgorithms.GeneticAlgorithm.AbstractGeneticAlgorithmLPs;
import main.algorithm.MultiObjectiveAlgorithm.util.EnvironmentalSelection;
import main.algorithm.MultiObjectiveAlgorithm.util.ReferencePoint;
import main.linkedproblem.LinkedProblem;
import main.problem.Problem;
import main.solution.solution;
import main.solution.solutionSet;
import main.util.SolutionListUtils;
import main.util.adjacencymatrix.AdjacencyMatrix;
import main.util.comparator.ObjectiveComparator;
import main.util.evaluator.SolutionListEvaluator;
import main.util.factory.GetProblemFactory;
import main.util.ranking.FastNonDominatedSortRanking;
import main.util.ranking.Ranking;

public class NSGALPIII<S extends solutionSet> extends AbstractGeneticAlgorithmLPs<S, List<S>> {

	 protected int iterations ;
	 protected int maxIterations;

	 protected SolutionListEvaluator<S> evaluator ;

	 protected int numberOfDivisions  ;
	 protected List<ReferencePoint<S>> referencePoints = new Vector<>() ;
	 public NSGALPIII(LinkedProblem linkedproblem) {
		super(linkedproblem);
	}
	
	public NSGALPIII(NSGALPIIIbuilder<S> builder) { // can be created from the NSGAIIIBuilder within the same package
	    super(builder.getProblem()) ;
	    maxIterations =  builder.getMaxIterations() ;

	    crossoverOperator =  builder.getCrossoverOperator() ;
	    mutationOperator  =  builder.getMutationOperator() ;
	    selectionOperator =  builder.getSelectionOperator() ;

	    evaluator = builder.getEvaluator() ;

	    /// NSGAIII
	    numberOfDivisions = builder.getNumberOfDivisions() ;

	    (new ReferencePoint<S>()).generateReferencePoints(referencePoints,getProblem().numberOfObjectives() , numberOfDivisions);

	    int populationSize = referencePoints.size();
	    while (populationSize%4>0) {
	      populationSize++;
	    }

	    setMaxPopulationSize(populationSize);

	   // JMetalLogger.logger.info("rpssize: " + referencePoints.size()); ;
	 }

	@Override
	protected void initProgress() {
		iterations = 1 ;
	}

	@Override
	protected void updateProgress() {
		iterations ++;
	}

	@Override
	protected boolean isStoppingConditionReached() {
		return iterations >= maxIterations;
	}

	@Override
	protected List<S> evaluatePopulation(List<S> population) {
		population = evaluator.evaluate(population, getProblem()); 
	    return population;	
	}

	@Override
	protected List<S> selection(List<S> population) {
		List<S> matingPopulation = new ArrayList<>();
	   	  
	    for (int i = 0; i < getMaxPopulationSize(); i++) {
	      S solution = (S) selectionOperator.execute(population);
	      matingPopulation.add(solution);
	     // System.out.println(solution.getSolution(1).toString());
	      if(matingPopulation.size()>=getMaxPopulationSize()) {
	    	  break;
	      }
	    }
	  //  List<S> newList = population.stream().map(sol -> 
	  //  (S)sol.copy()).collect(Collectors.toList());
	    
	    return matingPopulation;
	}

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
					List<solution<?>> offspringPopulation = new ArrayList<>(getMaxPopulationSize());
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
								
								if (offspringPopulation.size() >= getMaxPopulationSize())
									 break;
						 } 	
					 } 
					
					for(int j=0; j<getMaxPopulationSize(); j++) {
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
							    	for(int j=0; j<getMaxPopulationSize(); j++) {
							    		solution<?> s = (solution<?>) childproblem.createsolution();
							    		childproblem.evaluate(s);
							    		childsolutions.add(s);
							    	}
							    	checkNumberOfParents2(childsolutions, nParents_childsolution);
							    	List<solution<?>> offspringPopulation2 = new ArrayList<>(getMaxPopulationSize());
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
							  	        if (offspringPopulation2.size() >= getMaxPopulationSize())
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


	//@Override
	protected List<S> reproduction1(List<S> population) {
		 List<S> offspringPopulation = new ArrayList<>(getMaxPopulationSize());
		 AdjacencyMatrix matrix = getProblem().getAdjacencyMatrix();
		 int numOfProblem = getProblem().getLinkedProblems().size();
		 for(int a = 0; a < numOfProblem; a++) {
			 if(offspringPopulation.isEmpty()) {
				 
					List<solution<?>> parents = new ArrayList<>(2);
					List<solution<?>> offspringPop = new ArrayList<>(getMaxPopulationSize());
					for (int i = 0; i < getMaxPopulationSize(); i += 2) {
						 
						parents.add(population.get(i).getSolution(a+1));
						parents.add(population.get(Math.min(i + 1, getMaxPopulationSize()-1)).getSolution(a+1));
						
						 List<solution<?>> offspring = crossoverOperator.get(a).execute(parents);
						 
						 mutationOperator.get(a).execute(offspring.get(0));
					     mutationOperator.get(a).execute(offspring.get(1));
						
					     offspringPop.add(offspring.get(0));	
					     offspringPop.add(offspring.get(1));	
						
					 } 
					
					for(int j=0; j<offspringPop.size(); j++) {
						 Map<Integer, solution<?>> map = new HashMap<>();
						 map.put(a+1, (solution<?>) offspringPop.get(j));
						 S newSolutionSet = (S) new solutionSet(map);
						 offspringPopulation.add(j, newSolutionSet);
						 
					 }	
					
			 }
			 for(int b = 0; b < numOfProblem; b++) {
				 if(a==b)continue;
				 if(matrix.getEdge(a,b)==1) {
						if(!offspringPopulation.get(0).getSolutionSet().containsKey(b+1)) {
							
							for(int i = 0; i<offspringPopulation.size(); i++) {
						    	List<solution<?>> childsolutions = new ArrayList<>();
						    	GetProblemFactory factory =  new GetProblemFactory((Problem<?>) getProblem().getLinkedProblems().get(b+1), offspringPopulation.get(i).getSolutionSet().get(a+1));
						    	Problem<solution<?>> childproblem = (Problem<solution<?>>) factory.getProblemInstance();
						    	
						    	
						    	for(int j=0; j<getMaxPopulationSize(); j++) {
						    		solution<?> s = (solution<?>) childproblem.createsolution();
						    		childproblem.evaluate(s);
						    		childsolutions.add(s);
						    	}
						    	
						    	List<solution<?>> offspringPopulation2 = new ArrayList<>();
						    	for (int k = 0; k < childsolutions.size(); k += 2) {
						  	      List<solution<?>> parents_childsolution = new ArrayList<>(2);
						  	      
						  	      parents_childsolution.add(childsolutions.get(k));
						  	      parents_childsolution.add(childsolutions.get(Math.min(k + 1, childsolutions.size()-1)));
						  	      					  	    
						  	      List<solution<?>> offspring2 =  crossoverOperator.get(b).execute(parents_childsolution);
						  	      
						  	      for(solution<?> s: offspring2){
						  	        mutationOperator.get(b).execute(s);
						  	        childproblem.evaluate(s);
						  	        offspringPopulation2.add(s);
						  	        if (offspringPopulation2.size() >= offspringPopulation.size())
						  	          break;
						  	      }
						  	    }
						    	List<solution<?>> jointchildPopulation = new ArrayList<>();
							    jointchildPopulation.addAll(childsolutions);
							    jointchildPopulation.addAll(offspringPopulation2);
						    	
							    Collections.sort(jointchildPopulation, new ObjectiveComparator()) ;
							   
							    Map<Integer, solution<?>> map = offspringPopulation.get(i).getSolutionSet();
								map.put(b+1, (solution<?>) jointchildPopulation.get(0));
								S newSolutionSet = (S) new solutionSet(map);
								offspringPopulation.set(i, newSolutionSet);
							  }
						}
				 }
			 } 
		 }
		 
		return offspringPopulation;
	}
	
	private List<ReferencePoint<S>> getReferencePointsCopy() {
		  List<ReferencePoint<S>> copy = new ArrayList<>();
		  for (ReferencePoint<S> r : this.referencePoints) {
			  copy.add(new ReferencePoint<>(r));
		  }
		  return copy;
	 }

	@Override
	protected List<S> replacement(List<S> population, List<S> offspringPopulation) {
		List<S> jointPopulation = new ArrayList<>();
	    jointPopulation.addAll(population) ;
	    jointPopulation.addAll(offspringPopulation) ;

	    Ranking<S> ranking = computeRanking(jointPopulation);
	    
	    //List<Solution> pop = crowdingDistanceSelection(ranking);
	    List<S> last = new ArrayList<>();
	    List<S> pop = new ArrayList<>();
	    List<List<S>> fronts = new ArrayList<>();
	    int rankingIndex = 0;
	    int candidateSolutions = 0;
	    while (candidateSolutions < getMaxPopulationSize()) {
	      last = ranking.getSubFront(rankingIndex);
	      fronts.add(last);
	      candidateSolutions += last.size();
	      if ((pop.size() + last.size()) <= getMaxPopulationSize())
	        pop.addAll(last);
	      rankingIndex++;
	    }

	    
	    if (pop.size() == this.getMaxPopulationSize())
	      return pop;
	    
	   // System.out.println(this.getMaxPopulationSize());
	   // System.out.println(fronts.size() + " " + fronts.get(0).size());
	    // A copy of the reference list should be used as parameter of the environmental selection
	    EnvironmentalSelection<S> selection =
	            new EnvironmentalSelection<>(fronts,getMaxPopulationSize() - pop.size(),getReferencePointsCopy(),
	                    getProblem().numberOfObjectives());
	    
	    var choosen = selection.execute(last);
	    pop.addAll(choosen);
	     
	    return pop;
	}
	
	protected Ranking<S> computeRanking(List<S> solutionList) {
	    Ranking<S> ranking = new FastNonDominatedSortRanking<>() ;
	    ranking.compute(solutionList) ;

	    return ranking ;
	}

	@Override
	public List<S> getResult() {
		return getNonDominatedSolutions(getPopulation());
	}
	
	protected List<S> getNonDominatedSolutions(List<S> solutionList) {
	    return SolutionListUtils.getNonDominatedSolutions(solutionList) ;
	}

	
}
