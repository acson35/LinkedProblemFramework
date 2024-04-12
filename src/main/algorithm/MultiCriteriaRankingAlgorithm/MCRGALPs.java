package main.algorithm.MultiCriteriaRankingAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.algorithm.EvolutionaryAlgorithms.GeneticAlgorithm.AbstractGeneticAlgorithmLP;
import main.algorithm.EvolutionaryAlgorithms.GeneticAlgorithm.AbstractGeneticAlgorithmLPs;
import main.linkedproblem.LinkedProblem;
import main.operator.crossover.CrossoverOperator;
import main.operator.mutation.MutationOperator;
import main.operator.selection.SelectionOperator;
import main.operator.selection.selectionClasses.PrometheeIISelection;
import main.operator.selection.selectionClasses.TopsisSelection;
import main.problem.Problem;
import main.solution.solution;
import main.solution.solutionPair;
import main.solution.solutionSet;
import main.util.adjacencymatrix.AdjacencyMatrix;
import main.util.comparator.MultiCriteriaComparator;
import main.util.comparator.MultipleCriteriaComparator;
import main.util.comparator.ObjectiveComparator;
import main.util.evaluator.SolutionListEvaluator;
import main.util.factory.GetProblemFactory;
import main.util.ranking.Criteria;
import main.util.ranking.AHP.FitnessRanking;

/**
 * Multi-criteria decision making implementation. Allows a selection of TOPSIS or PrometheeII as a selection operator for multi-criteria decision making.
 *
 *@author Akinola Ogunsemi <a.ogunsemi1@rgu.ac.uk>
 *
 *@param <S>
 *
 * 
 */


public class MCRGALPs<S extends solutionSet> extends AbstractGeneticAlgorithmLPs<S, solutionSet> {
	
	protected final int maxGenerations;

	protected final SolutionListEvaluator<S> evaluator;

	protected int evaluations;
	  
	protected Comparator<S> multicriteriaComparator ;

	protected int matingPoolSize;
	  
	protected int offspringPopulationSize ;
	  
	protected List<Criteria> criteria;

	  /**
	   * Constructor
	   */
	@SuppressWarnings("rawtypes")
	public MCRGALPs(LinkedProblem problem, int maxGenerations, int populationSize,
	                int matingPoolSize, int offspringPopulationSize, List<Criteria> criteria,
	                List<CrossoverOperator<solution<?>>> crossoverOperators, 
	                List<MutationOperator<solution<?>>> mutationOperators,
	                SelectionOperator<List<S>, S> selectionOperator, 
	                SolutionListEvaluator<S> evaluator) {
	    this(problem, maxGenerations, populationSize, matingPoolSize, offspringPopulationSize, criteria,
	            crossoverOperators, mutationOperators, selectionOperator, new MultipleCriteriaComparator<S>(), evaluator);
	}
	  /**
	   * Constructor
	   */
	@SuppressWarnings("rawtypes")
	public MCRGALPs(LinkedProblem linkedproblem, int maxGenerations, int populationSize,
	                int matingPoolSize, int offspringPopulationSize, List<Criteria> criteria,
	                List<CrossoverOperator<solution<?>>> crossoverOperators, List<MutationOperator<solution<?>>> mutationOperators,
	                SelectionOperator<List<S>, S> selectionOperator, Comparator<S> multicriteriaComparator,
	                SolutionListEvaluator<S> evaluator) {
	    super(linkedproblem);
	    this.maxGenerations = maxGenerations;
	    setMaxPopulationSize(populationSize);

	    this.crossoverOperator = crossoverOperators;
	    
	    this.mutationOperator = mutationOperators;
	    
	    this.selectionOperator = selectionOperator;

	    this.evaluator = evaluator;
	    
	    this.multicriteriaComparator = multicriteriaComparator;

	    this.matingPoolSize = matingPoolSize ;
	    
	    this.offspringPopulationSize = offspringPopulationSize ;
	    
	    this.criteria = criteria ;
	  }

	  @Override protected void initProgress() {
	    evaluations = getMaxPopulationSize();
	  }

	  @Override protected void updateProgress() {
	    evaluations += offspringPopulationSize ;
	  }

	  @Override protected boolean isStoppingConditionReached() {
	    return evaluations >= maxGenerations;
	  }

	  @Override
	public List<S> evaluatePopulation(List<S> population) {
		population = evaluator.evaluate(population, getProblem()); 
	    return population;
	  }

	
	   
	  @SuppressWarnings({"unchecked", "unused", "rawtypes" })
	  @Override
	  protected List<S> selection(List<S> population) {
		  
		// selectionOperator = new TopsisSelection<solutionSet>(20, criteria);
		  FitnessRanking fr = new FitnessRanking(population, criteria);
		  fr.optimalCriteriaWeightings();
		 // SelectionOperator<List<S>, S> selectionOperator1 = 
		   // 		(SelectionOperator<List<S>, S>) new PrometheeIISelection<S>(10, criteria);
		  
		  if(selectionOperator instanceof TopsisSelection) {
			  selectionOperator = (SelectionOperator<List<S>, S>) new TopsisSelection<S>(20, criteria);
		  }else if(selectionOperator instanceof PrometheeIISelection) {
			  selectionOperator = (SelectionOperator<List<S>, S>) new PrometheeIISelection<S>(20, criteria);
		  }
		  List<S> matingPopulation = (List<S>) selectionOperator.execute(population);
	   	 
	    return matingPopulation;
	  }
	  
	  
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override	
	protected List<S> reproduction(List<S> matingPool) {
		LinkedProblem problem = getProblem();
		List<S> offspringPopulationset = new ArrayList<>();
		 
		AdjacencyMatrix matrix = problem.getAdjacencyMatrix();
		int numOfProblem = problem.getLinkedProblems().size();	
		 
		//System.out.print(offspringPopulationset.isEmpty());
		 for(int a = 0; a < numOfProblem; a++) {
			if(offspringPopulationset.isEmpty()) {
				int nParents_parentsolution = crossoverOperator.get(a).getNumberOfRequiredParents();
				//System.out.println(nParents_parentsolution);
				checkNumberOfParents(matingPool, nParents_parentsolution);
				List<solution<?>> offspringPopulation = new ArrayList<>(offspringPopulationSize);
				for (int i = 0; i < matingPool.size(); i += nParents_parentsolution) {
					 List<solution<?>> parents_parentsolution = new ArrayList<>(nParents_parentsolution);
					 for (int j = 0; j < nParents_parentsolution; j++) {
						 parents_parentsolution.add((solution<?>) matingPool.get(i+j).getSolution(a+1));
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
				
				for(int j=0; j<offspringPopulation.size(); j++) {
					 Map<Integer, solution<?>> map = new HashMap<>();
					 map.put(a+1, offspringPopulation.get(j));
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
						    	GetProblemFactory factory =  new GetProblemFactory((Problem<?>) problem.getLinkedProblems().get(b+1), offspringPopulationset.get(i).getSolutionSet().get(a+1));
						    	Problem<solution<?>> childproblem = (Problem<solution<?>>) factory.getProblemInstance();
						    	
						    	//System.out.println(offspringPopulationset.get(i).getSolutionSet().get(a+1).toString());
						    	for(int j=0; j<offspringPopulationSize; j++) {
						    		solution<?> s = childproblem.createsolution();
						    		childproblem.evaluate(s);
						    		childsolutions.add(s);
						    	}
						    	checkNumberOfParents2(childsolutions, nParents_childsolution);
						    	List<solution<?>> offspringPopulation2 = new ArrayList<>(offspringPopulationSize);
						    	for (int k = 0; k < childsolutions.size(); k += nParents_childsolution) {
						  	      List<solution<?>> parents_childsolution = new ArrayList<>(nParents_childsolution);
						  	      for (int l = 0; l < nParents_childsolution; l++) {
						  	    	  parents_childsolution.add((solution<?>) childsolutions.get(k+l));
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
								map.put(b+1, jointchildPopulation.get(0));
								S newSolutionSet = (S) new solutionSet(map);
								offspringPopulationset.set(i, newSolutionSet);
							  }
							
						}
				 }
			 }
		 }  
	    return offspringPopulationset;
	  }
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override protected List<S> replacement(List<S> population, List<S> offspringPopulation) {
		List<S> jointPopulation = new ArrayList<>();
		jointPopulation.addAll(population);
		jointPopulation.addAll(offspringPopulation);
		
		try {
		
			FitnessRanking fr = new FitnessRanking(jointPopulation, criteria);
			fr.optimalCriteriaWeightings();
			
			if(selectionOperator instanceof TopsisSelection) {
				TopsisSelection<S> solSelection;
				solSelection = new TopsisSelection<S>(getMaxPopulationSize(), criteria);
				jointPopulation = (List<S>)solSelection.execute(jointPopulation) ;
			 }else if(selectionOperator instanceof PrometheeIISelection) {
				  PrometheeIISelection<S> solSelection;
					solSelection = new PrometheeIISelection<S>(getMaxPopulationSize(), criteria);
					jointPopulation = (List<S>)solSelection.execute(jointPopulation) ;
			 }
			
		}catch(Exception e) {	
		}
		       
		return jointPopulation;
	}
	  
	  
	@Override public solutionSet getResult() {
		return getPopulation().get(0);	
	}
		  
		
	public String getName() {
		return "MCRGALPs" ;
	}

	public String getDescription() {
		return "Multiple-Criteria Ranking Genetic Algorithm for Linked Problem" ;
	}

}
